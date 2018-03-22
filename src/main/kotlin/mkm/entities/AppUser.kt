package mkm.entities

import javax.persistence.*

@Entity
data class AppUser(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        @Column(unique = true)
        val username: String = "",
        var email: String = "",
        @Column(unique = true)
        var password: String = ""
) {
}