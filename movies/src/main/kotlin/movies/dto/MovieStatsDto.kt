package movies.dto

import io.swagger.annotations.ApiModelProperty

data class MovieStatsDto(

        @get:ApiModelProperty("The id of the player")
        var movieId: String? = null,

        @get:ApiModelProperty("How many victories the player had so far")
        var description : Int? = null,

        @get:ApiModelProperty("How many defeats the player had so far")
        var director: Int? = null,

        @get:ApiModelProperty("How many draws the player had so far")
        var year : Int? = null
)
