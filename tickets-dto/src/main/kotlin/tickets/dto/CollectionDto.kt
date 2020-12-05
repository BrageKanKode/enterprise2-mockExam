package tickets.dto

class CollectionDto(

        var cards: MutableList<MovieDto> = mutableListOf(),

        var prices: MutableMap<Rarity, Int> = mutableMapOf(),

        var millValues: MutableMap<Rarity, Int> = mutableMapOf(),

        var rarityProbabilities: MutableMap<Rarity, Double> = mutableMapOf()

)
