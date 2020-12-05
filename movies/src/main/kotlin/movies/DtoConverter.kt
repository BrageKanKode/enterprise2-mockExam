package movies

import movies.db.MovieStats
import movies.dto.MovieStatsDto

object DtoConverter {

    fun transform(stats: MovieStats) : MovieStatsDto =
            stats.run { MovieStatsDto(userId, victories, defeats, draws, score) }

    fun transform(scores: Iterable<MovieStats>) : List<MovieStatsDto> = scores.map { transform(it) }
}
