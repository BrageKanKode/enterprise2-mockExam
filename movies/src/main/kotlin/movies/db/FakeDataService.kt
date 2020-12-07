package movies.db

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import kotlin.random.Random

@Profile("FakeData")
@Service
@Transactional
class FakeDataService(
        val repository: MovieStatsRepository
) {

    @PostConstruct
    fun init(){
        for(i in 0..49){
            createRandomUser("Foo" + i.toString().padStart(2, '0'))
        }
    }

    fun createRandomUser(userId: String){
        val stats = MovieStats(userId,
                Random.nextInt(50),
                Random.nextInt(50),
                Random.nextInt(5))
        repository.save(stats)
    }
}
