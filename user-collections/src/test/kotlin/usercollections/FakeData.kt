package usercollections

import tickets.dto.CollectionDto
import tickets.dto.TicketDto
import tickets.dto.Rarity.*

object FakeData {

    fun getCollectionDto() : CollectionDto {

        val dto = CollectionDto()

        dto.prices[BRONZE] = 100
        dto.prices[SILVER] = 500
        dto.prices[GOLD] = 1_000
        dto.prices[PINK_DIAMOND] = 100_000

        dto.prices.forEach { dto.millValues[it.key] = it.value / 4 }
        dto.prices.keys.forEach { dto.rarityProbabilities[it] = 0.25 }

        dto.tickets.run {
            add(TicketDto(ticketId = "c00", rarity = BRONZE))
            add(TicketDto(ticketId = "c01", rarity = BRONZE))
            add(TicketDto(ticketId = "c02", rarity = BRONZE))
            add(TicketDto(ticketId = "c03", rarity = BRONZE))
            add(TicketDto(ticketId = "c04", rarity = SILVER))
            add(TicketDto(ticketId = "c05", rarity = SILVER))
            add(TicketDto(ticketId = "c06", rarity = SILVER))
            add(TicketDto(ticketId = "c07", rarity = GOLD))
            add(TicketDto(ticketId = "c08", rarity = GOLD))
            add(TicketDto(ticketId = "c09", rarity = PINK_DIAMOND))
        }

        return dto

    }

}
