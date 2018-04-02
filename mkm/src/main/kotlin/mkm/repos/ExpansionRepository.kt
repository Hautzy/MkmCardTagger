package mkm.repos

import mkm.entities.Expansion
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpansionRepository: PagingAndSortingRepository<Expansion, Long>