package usercollections

import tickets.dto.CollectionDto
import tickets.dto.MovieDto
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
            add(MovieDto(cardId = "c00", rarity = BRONZE))
            add(MovieDto(cardId = "c01", rarity = BRONZE))
            add(MovieDto(cardId = "c02", rarity = BRONZE))
            add(MovieDto(cardId = "c03", rarity = BRONZE))
            add(MovieDto(cardId = "c04", rarity = SILVER))
            add(MovieDto(cardId = "c05", rarity = SILVER))
            add(MovieDto(cardId = "c06", rarity = SILVER))
            add(MovieDto(cardId = "c07", rarity = GOLD))
            add(MovieDto(cardId = "c08", rarity = GOLD))
            add(MovieDto(cardId = "c09", rarity = PINK_DIAMOND))
        }

        return dto

    }

}
