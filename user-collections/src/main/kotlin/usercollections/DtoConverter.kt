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
            ownedCards = user.ownedCards.map { transform(it) }.toMutableList()
        }
    }

    fun transform(cardCopy: TicketCopy) : TicketCopyDto {
        return TicketCopyDto().apply {
            cardId = cardCopy.cardId
            numberOfCopies = cardCopy.numberOfCopies
        }
    }
}
