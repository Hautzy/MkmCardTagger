package mkm.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class AppUser(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        @Column(unique = true)
        val username: String = "",
        @Column(unique = true)
        var email: String = "",
        var password: String = "",
        @JsonIgnore
        @OneToMany(mappedBy = "appUser", cascade = arrayOf(CascadeType.ALL))
        val tags: MutableList<Tag> = mutableListOf()
) {
}