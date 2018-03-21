package mkm.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class AppUser(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val username: String = "",
        var email: String = "",
        var password: String = ""
) {
}