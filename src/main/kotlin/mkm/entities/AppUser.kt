package mkm.entities

import javax.persistence.*

@Entity
data class AppUser(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        @Column(unique = true)
        val username: String = "",
        @Column(unique = true)
        var email: String = "",
        var password: String = ""
) {
}