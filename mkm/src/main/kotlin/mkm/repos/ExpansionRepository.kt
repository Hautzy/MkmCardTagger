package mkm.repos

import mkm.entities.Expansion
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpansionRepository: CrudRepository<Expansion, Long>