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

        dto.cards.run {
            add(TicketDto(cardId = "c00", rarity = BRONZE))
            add(TicketDto(cardId = "c01", rarity = BRONZE))
            add(TicketDto(cardId = "c02", rarity = BRONZE))
            add(TicketDto(cardId = "c03", rarity = BRONZE))
            add(TicketDto(cardId = "c04", rarity = SILVER))
            add(TicketDto(cardId = "c05", rarity = SILVER))
            add(TicketDto(cardId = "c06", rarity = SILVER))
            add(TicketDto(cardId = "c07", rarity = GOLD))
            add(TicketDto(cardId = "c08", rarity = GOLD))
            add(TicketDto(cardId = "c09", rarity = PINK_DIAMOND))
        }

        return dto

    }

}
