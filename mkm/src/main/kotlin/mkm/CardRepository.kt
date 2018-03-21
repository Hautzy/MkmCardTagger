package mkm

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CardRepository: CrudRepository<Card, Int> {
    @Query("SELECT c FROM Card c WHERE UPPER(c.englishName) LIKE '%' || UPPER(:englishName) || '%'")
    fun findByEnglishName(@Param("englishName") englishName: String): List<Card>
}