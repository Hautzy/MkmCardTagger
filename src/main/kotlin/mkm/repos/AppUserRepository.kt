package mkm.repos

import mkm.entities.AppUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository: CrudRepository<AppUser, Long> {
    fun findByUsername(username: String): AppUser
}