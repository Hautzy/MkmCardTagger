package mkm.repos

import mkm.entities.Expansion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExpansionRepository: PagingAndSortingRepository<Expansion, Long> {
    @Query("SELECT e FROM Expansion e WHERE UPPER(e.englishName) LIKE '%' || UPPER(:englishName) || '%'")
    fun findByEnglishName(@Param("englishName") englishName: String, pageable: Pageable): Page<Expansion>
}