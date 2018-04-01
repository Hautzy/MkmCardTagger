package mkm.repos

import mkm.entities.CardExpansion
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CardExpansionRepository: CrudRepository<CardExpansion, Int>