package usercollections

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import rest.WrappedResponse
import tickets.dto.CollectionDto
import tickets.dto.Rarity
import usercollections.model.Ticket
import usercollections.model.Collection
import javax.annotation.PostConstruct
import kotlin.random.Random

@Service
class TicketService(
        private val client: RestTemplate,
        private val circuitBreakerFactory: Resilience4JCircuitBreakerFactory
) {

    companion object{
        private val log = LoggerFactory.getLogger(TicketService::class.java)
    }

    protected var collection: Collection? = null

    @Value("\${ticketServiceAddress}")
    private lateinit var ticketServiceAddress: String

    val ticketCollection : List<Ticket>
        get() = collection?.tickets ?: listOf()

    private val lock = Any()

    private lateinit var cb: CircuitBreaker


    @PostConstruct
    fun init(){

        cb = circuitBreakerFactory.create("circuitBreakerToCards")

        synchronized(lock){
            if(ticketCollection.isNotEmpty()){
                return
            }
            fetchData()
        }
    }

    fun isInitialized() = ticketCollection.isNotEmpty()

    protected fun fetchData(){

        val version = "v1_000"
        val uri = UriComponentsBuilder
                .fromUriString("http://${ticketServiceAddress.trim()}/api/tickets/collection_$version")
                .build().toUri()

        val response = cb.run(
                {
                    client.exchange(
                            uri,
                            HttpMethod.GET,
                            null,
                            object : ParameterizedTypeReference<WrappedResponse<CollectionDto>>() {})
                },
                { e ->
                    log.error("Failed to fetch data from Ticket Service: ${e.message}")
                    null
                }
        ) ?: return


        if (response.statusCodeValue != 200) {
            log.error("Error in fetching data from Ticket Service. Status ${response.statusCodeValue}." +
                    "Message: " + response.body.message)
        }

        try {
            collection = Collection(response.body.data!!)
        } catch (e: Exception) {
            log.error("Failed to parse ticket collection info: ${e.message}")
        }
    }

    private fun verifyCollection(){

        if(collection == null){
            fetchData()

            if(collection == null){
                throw IllegalStateException("No collection info")
            }
        }
    }

//    fun millValue(ticketId: String) : Int {
//        verifyCollection()
//        val ticket : Ticket = ticketCollection.find { it.ticketId  == ticketId} ?:
//        throw IllegalArgumentException("Invalid cardId $ticketId")
//
//        return collection!!.millValues[ticket.rarity]!!
//    }

    fun price(ticketId: String) : Int {
        verifyCollection()
        val ticket : Ticket = ticketCollection.find { it.ticketId  == ticketId} ?:
        throw IllegalArgumentException("Invalid cardId $ticketId")

        return collection!!.prices[ticket.rarity]!!
    }

    fun getRandomSelection(n: Int) : List<Ticket>{

        if(n <= 0){
            throw IllegalArgumentException("Non-positive n: $n")
        }

        verifyCollection()

        val selection = mutableListOf<Ticket>()

        val probabilities = collection!!.rarityProbabilities
        val bronze = probabilities[Rarity.BRONZE]!!
        val silver = probabilities[Rarity.SILVER]!!
        val gold = probabilities[Rarity.GOLD]!!
        //val pink = probabilities[Rarity.PINK_DIAMOND]!!

        repeat(n) {
            val p = Math.random()
            val r = when{
                p <= bronze -> Rarity.BRONZE
                p > bronze && p <= bronze + silver -> Rarity.SILVER
                p > bronze + silver && p <= bronze + silver + gold -> Rarity.GOLD
                p > bronze + silver + gold -> Rarity.PINK_DIAMOND
                else -> throw IllegalStateException("BUG for p=$p")
            }
            val ticket = collection!!.cardsByRarity[r].let{ it!![Random.nextInt(it.size)] }
            selection.add(ticket)
        }

        return selection
    }
}
