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

    @Query("SELECT c FROM Card c WHERE c.detailUrl LIKE :expansion")
    fun findByUrlExpansion(@Param("expansion") expansion: String): List<Card>

    @Query("SELECT c FROM Card c WHERE c.id in (SELECT e.card.id FROM CardExpansion e WHERE e.expansion.id=:expansion)")
    fun findAllByExpansion(@Param("expansion") expansion: Long, pageable: Pageable): Page<Card>

    @Query("SELECT c FROM Card c WHERE c.id in (SELECT e.card.id FROM CardExpansion e WHERE e.expansion.id=:expansion) AND UPPER(c.englishName) LIKE '%' || UPPER(:englishName) || '%'")
    fun findByEnglishNameAndExpansion(@Param("englishName") englishName: String, @Param("expansion") expansion: Long, pageable: Pageable): Page<Card>
}