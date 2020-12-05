package usercollections.model

import tickets.dto.TicketDto
import tickets.dto.Rarity


data class Ticket(
        val ticketId : String,
        val rarity: Rarity
){

    constructor(dto: TicketDto): this(
            dto.ticketId ?: throw IllegalArgumentException("Null ticketId"),
            dto.rarity ?: throw IllegalArgumentException("Null rarity"))
}
