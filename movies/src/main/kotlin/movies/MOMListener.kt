package movies

import movies.db.MovieStatsService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MOMListener(
        private val statsService: MovieStatsService
) {

    companion object{
        private val log = LoggerFactory.getLogger(MOMListener::class.java)
    }


    @RabbitListener(queues = ["#{queue.name}"])
    fun receiveFromAMQP(movieId: String) {
        val ok = statsService.registerNewUser(movieId)
        if(ok){
            log.info("Registered new user via MOM: $movieId")
        }
    }
}

