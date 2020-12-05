package usercollections

import usercollections.db.TicketCopy
import usercollections.db.User
import usercollections.dto.TicketCopyDto
import usercollections.dto.UserDto

object DtoConverter {

    fun transform(user: User) : UserDto {
        return UserDto().apply {
            userId = user.userId
            coins = user.coins
            cardPacks = user.cardPacks
            ownedTickets = user.ownedTickets.map { transform(it) }.toMutableList()
        }
    }

    fun transform(ticketCopy: TicketCopy) : TicketCopyDto {
        return TicketCopyDto().apply {
            ticketId = ticketCopy.ticketId
            numberOfCopies = ticketCopy.numberOfCopies
        }
    }
}
