package movies

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import movies.db.MovieStatsRepository
import movies.db.MovieStatsService
import movies.dto.MovieStatsDto
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rest.PageDto
import rest.RestResponseFactory
import rest.WrappedResponse
import java.util.concurrent.TimeUnit

@Api(value = "/api/movies", description = "Scores and ranks of the players, based on their victories and defeats")
@RequestMapping(
        path = ["/api/movies"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)
@RestController
class RestApi(
        private val statsRepository: MovieStatsRepository,
        private val statsService: MovieStatsService
) {


    @ApiOperation("Retrieve the current score statistics for the given player")
    @GetMapping(path = ["/{movieId}"])
    fun getMovieStatsInfo(
            @PathVariable("movieId") movieId: String
    ): ResponseEntity<WrappedResponse<MovieStatsDto>> {

        val movie = statsRepository.findById(movieId).orElse(null)
        if (movie == null) {
            return RestResponseFactory.notFound("Movie $movieId not found")
        }

        return RestResponseFactory.payload(200, DtoConverter.transform(movie))
    }

    @ApiOperation("Create default info for a new player")
    @PutMapping(path = ["/{movieId}"])
    fun createMovie(
            @PathVariable("movieId") movieId: String
    ): ResponseEntity<WrappedResponse<Void>> {
        val ok = statsService.registerNewUser(movieId)
        return if (!ok) RestResponseFactory.userFailure("Movie $movieId already exist")
        else RestResponseFactory.noPayload(201)
    }


    @ApiOperation("Return an iterable page of leaderboard results, starting from the top player")
    @GetMapping
    fun getAll(
            @ApiParam("Id of player in the previous page")
            @RequestParam("keysetId", required = false)
            keysetId: String?,
            //
            @ApiParam("Score of the player in the previous page")
            @RequestParam("keysetYear", required = false)
            keysetYear: Int?): ResponseEntity<WrappedResponse<PageDto<MovieStatsDto>>> {

        val page = PageDto<MovieStatsDto>()

        val n = 10
        val year = DtoConverter.transform(statsService.getNextPage(n, keysetId, keysetYear))
        page.list = year

        if (year.size == n) {
            val last = year.last()
            page.next = "/api/movies?keysetId=${last.movieId}&keysetYear=${last.year}"
        }

        return ResponseEntity
                .status(200)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES).cachePublic())
                .body(WrappedResponse(200, page).validated())
    }
}
