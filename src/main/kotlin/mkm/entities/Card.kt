package mkm.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "mkm_Card")
data class Card(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val englishName: String = "",
        val germanName: String = "",
        val detailUrl: String = "",
        val imgUrl: String = "",
        @JsonIgnore
        @OneToMany(mappedBy = "card", cascade = arrayOf(CascadeType.ALL))
        val cardExpansions: MutableList<CardExpansion> = mutableListOf(),
        @JsonIgnore
        @OneToMany(mappedBy = "card", cascade = arrayOf(CascadeType.ALL))
        val tags: MutableList<Tag> = mutableListOf()
) {
}