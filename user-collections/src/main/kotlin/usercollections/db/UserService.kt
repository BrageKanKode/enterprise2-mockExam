package usercollections.db

import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import usercollections.TicketService
import javax.persistence.LockModeType

@Repository
interface UserRepository : CrudRepository<User, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.userId = :id")
    fun lockedFind(@Param("id") userId: String) : User?

}


@Service
@Transactional
class UserService(
        private val userRepository: UserRepository,
        private val ticketService: TicketService
) {

    companion object{
        const val CARDS_PER_PACK = 5
    }

    fun findByIdEager(userId: String) : User?{

        val user = userRepository.findById(userId).orElse(null)
        if(user != null){
            user.ownedTickets.size
        }
        return user
    }

    fun registerNewUser(userId: String) : Boolean{

        if(userRepository.existsById(userId)){
            return false
        }

        val user = User()
        user.userId = userId
//        user.cardPacks = 3
        user.coins = 100
        userRepository.save(user)
        return true
    }

    private fun validateTicket(ticketId: String) {
        if (!ticketService.isInitialized()) {
            throw IllegalStateException("Ticket service is not initialized")
        }

        if (!ticketService.ticketCollection.any { it.ticketId == ticketId }) {
            throw IllegalArgumentException("Invalid ticketId: $ticketId")
        }
    }

    private fun validateUser(userId: String) {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User $userId does not exist")
        }
    }

    private fun validate(userId: String, ticketId: String) {
        validateUser(userId)
        validateTicket(ticketId)
    }

    fun buyTicket(userId: String, ticketId: String) {
        validate(userId, ticketId)

        val price = ticketService.price(ticketId)
        val user = userRepository.lockedFind(userId)!!

        if (user.coins < price) {
            throw IllegalArgumentException("Not enough coins")
        }

        user.coins -= price

        addTicket(user, ticketId)
    }

    private fun addTicket(user: User, ticketId: String) {
        user.ownedTickets.find { it.ticketId == ticketId }
                ?.apply { numberOfCopies++ }
                ?: TicketCopy().apply {
                    this.ticketId = ticketId
                    this.user = user
                    this.numberOfCopies = 1
                }.also { user.ownedTickets.add(it) }
    }

//    fun millTicket(userId: String, ticketId: String) {
//        validate(userId, ticketId)
//
//        val user = userRepository.lockedFind(userId)!!
//
//        val copy = user.ownedTickets.find { it.ticketId == ticketId }
//        if(copy == null || copy.numberOfCopies == 0){
//            throw IllegalArgumentException("User $userId does not own a copy of $ticketId")
//        }
//
//        copy.numberOfCopies--
//
//        val millValue = ticketService.millValue(ticketId)
//        user.coins += millValue
//    }

//    fun openPack(userId: String) : List<String> {
//
//        validateUser(userId)
//
//        val user = userRepository.lockedFind(userId)!!
//
//        if(user.cardPacks < 1){
//            throw IllegalArgumentException("No pack to open")
//        }
//
//        user.cardPacks--
//
//        val selection = ticketService.getRandomSelection(CARDS_PER_PACK)
//
//        val ids = mutableListOf<String>()
//
//        selection.forEach {
//            addTicket(user, it.ticketId)
//            ids.add(it.ticketId)
//        }
//
//        return ids
//    }
}
