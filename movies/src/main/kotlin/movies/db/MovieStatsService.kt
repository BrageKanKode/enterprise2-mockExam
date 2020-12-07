package movies.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
interface MovieStatsRepository : CrudRepository<MovieStats, String>

@Service
@Transactional
class MovieStatsService(
        val repository: MovieStatsRepository,
        val em: EntityManager
) {

    fun registerNewUser(userId: String) : Boolean{

        if(repository.existsById(userId)){
            return false
        }

        val stats = MovieStats(userId, 0, 0, 0)
        repository.save(stats)
        return true
    }

    fun getNextPage(size: Int, keysetId: String? = null, keysetYear: Int? = null): List<MovieStats>{

        if (size < 1 || size > 1000) {
            throw IllegalArgumentException("Invalid size value: $size")
        }

        if((keysetId==null && keysetYear!=null) || (keysetId!=null && keysetYear==null)){
            throw IllegalArgumentException("keysetId and keysetYear should be both missing, or both present")
        }

        val query: TypedQuery<MovieStats>
        if (keysetId == null) {
            query = em.createQuery(
                    "select s from MovieStats s order by s.year DESC, s.movieId DESC"
                    , MovieStats::class.java)
        } else {
            query = em.createQuery(
                    "select s from MovieStats s where s.year<?2 or (s.year=?2 and s.movieId<?1) order by s.year DESC, s.movieId DESC"
                    , MovieStats::class.java)
            query.setParameter(1, keysetId)
            query.setParameter(2, keysetYear)
        }
        query.maxResults = size

        return query.resultList
    }
}
