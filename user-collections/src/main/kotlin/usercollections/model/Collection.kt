package usercollections.model

import tickets.dto.CollectionDto
import tickets.dto.Rarity
import kotlin.math.abs

data class Collection(

        val tickets : List<Ticket>,

        val prices: Map<Rarity, Int>,

        val millValues: Map<Rarity, Int>,

        val rarityProbabilities: Map<Rarity, Double>
){

    constructor(dto: CollectionDto) : this(
            dto.tickets.map { Ticket(it) },
            dto.prices.toMap(),
            dto.millValues.toMap(),
            dto.rarityProbabilities.toMap()
    )

    val cardsByRarity : Map<Rarity, List<Ticket>> = tickets.groupBy { it.rarity }

    init{
        if(tickets.isEmpty()){
            throw IllegalArgumentException("No cards")
        }
        Rarity.values().forEach {
            requireNotNull(prices[it])
            requireNotNull(millValues[it])
            requireNotNull(rarityProbabilities[it])
        }

        val p = rarityProbabilities.values.sum()
        if(abs(1 - p) > 0.00001){
            throw IllegalArgumentException("Invalid probability sum: $p")
        }
    }
}
