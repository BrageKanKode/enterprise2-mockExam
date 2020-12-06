package usercollections

import rooms.dto.CollectionDto
import rooms.dto.TicketDto
import rooms.dto.Rooms.*

object FakeData {

    fun getCollectionDto() : CollectionDto {

        val dto = CollectionDto()

        dto.prices[BRONZE] = 100
        dto.prices[SILVER] = 500
        dto.prices[GOLD] = 1_000
        dto.prices[PINK_DIAMOND] = 100_000

        dto.prices.forEach { dto.millValues[it.key] = it.value / 4 }
        dto.prices.keys.forEach { dto.roomsProbabilities[it] = 0.25 }

        dto.rooms.run {
            add(TicketDto(ticketId = "c00", rooms = BRONZE))
            add(TicketDto(ticketId = "c01", rooms = BRONZE))
            add(TicketDto(ticketId = "c02", rooms = BRONZE))
            add(TicketDto(ticketId = "c03", rooms = BRONZE))
            add(TicketDto(ticketId = "c04", rooms = SILVER))
            add(TicketDto(ticketId = "c05", rooms = SILVER))
            add(TicketDto(ticketId = "c06", rooms = SILVER))
            add(TicketDto(ticketId = "c07", rooms = GOLD))
            add(TicketDto(ticketId = "c08", rooms = GOLD))
            add(TicketDto(ticketId = "c09", rooms = PINK_DIAMOND))
        }

        return dto

    }

}
