package usercollections.model

import rooms.dto.TicketDto
import rooms.dto.Rooms


data class Ticket(
        val ticketId : String,
        val rooms: Rooms
){

    constructor(dto: TicketDto): this(
            dto.ticketId ?: throw IllegalArgumentException("Null ticketId"),
            dto.rooms ?: throw IllegalArgumentException("Null rarity"))
}
