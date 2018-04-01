package mkm.repos

import mkm.entities.Card
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CardRepository: PagingAndSortingRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE UPPER(c.englishName) LIKE '%' || UPPER(:englishName) || '%'")
    fun findByEnglishName(@Param("englishName") englishName: String, pageable: Pageable): Page<Card>
}