package movies.db

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class MovieStats(

        @get:Id @get:NotBlank
        var movieId: String? = null,

        @get:Min(0) @get:NotNull
        var description : Int = 0,

        @get:Min(0) @get:NotNull
        var director: Int = 0,

        @get:Min(0) @get:NotNull
        var year : Int = 0
)
