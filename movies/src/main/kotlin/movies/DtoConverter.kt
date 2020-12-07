package movies

import movies.db.MovieStats
import movies.dto.MovieStatsDto

object DtoConverter {

    fun transform(stats: MovieStats) : MovieStatsDto =
            stats.run { MovieStatsDto(movieId, description, director, year) }

    fun transform(year: Iterable<MovieStats>) : List<MovieStatsDto> = year.map { transform(it) }
}
