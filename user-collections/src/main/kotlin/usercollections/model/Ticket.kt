package usercollections.model

import tickets.dto.TicketDto
import tickets.dto.Rarity


data class Ticket(
        val cardId : String,
        val rarity: Rarity
){

    constructor(dto: TicketDto): this(
            dto.cardId ?: throw IllegalArgumentException("Null cardId"),
            dto.rarity ?: throw IllegalArgumentException("Null rarity"))
}
