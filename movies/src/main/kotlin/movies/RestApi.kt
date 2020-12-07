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

@Api(value = "/api/scores", description = "Scores and ranks of the players, based on their victories and defeats")
@RequestMapping(
        path = ["/api/scores"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)
@RestController
class RestApi(
        private val statsRepository: MovieStatsRepository,
        private val statsService: MovieStatsService
) {


    @ApiOperation("Retrieve the current score statistics for the given player")
    @GetMapping(path = ["/{userId}"])
    fun getUserStatsInfo(
            @PathVariable("userId") userId: String
    ): ResponseEntity<WrappedResponse<MovieStatsDto>> {

        val user = statsRepository.findById(userId).orElse(null)
        if (user == null) {
            return RestResponseFactory.notFound("User $userId not found")
        }

        return RestResponseFactory.payload(200, DtoConverter.transform(user))
    }

    @ApiOperation("Create default info for a new player")
    @PutMapping(path = ["/{userId}"])
    fun createUser(
            @PathVariable("userId") userId: String
    ): ResponseEntity<WrappedResponse<Void>> {
        val ok = statsService.registerNewUser(userId)
        return if (!ok) RestResponseFactory.userFailure("User $userId already exist")
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
            @RequestParam("keysetScore", required = false)
            keysetScore: Int?): ResponseEntity<WrappedResponse<PageDto<MovieStatsDto>>> {

        val page = PageDto<MovieStatsDto>()

        val n = 10
        val scores = DtoConverter.transform(statsService.getNextPage(n, keysetId, keysetScore))
        page.list = scores

        if (scores.size == n) {
            val last = scores.last()
            page.next = "/api/scores?keysetId=${last.movieId}&keysetScore=${last.year}"
        }

        return ResponseEntity
                .status(200)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES).cachePublic())
                .body(WrappedResponse(200, page).validated())
    }
}
