package usercollections.model

import tickets.dto.MovieDto
import tickets.dto.Rarity


data class Movie(
        val cardId : String,
        val rarity: Rarity
){

    constructor(dto: MovieDto): this(
            dto.cardId ?: throw IllegalArgumentException("Null cardId"),
            dto.rarity ?: throw IllegalArgumentException("Null rarity"))
}
