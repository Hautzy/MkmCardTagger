package mkm.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "mkm_Expansion")
data class Expansion(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val germanName: String = "",
        val englishName: String = "",
        val symbolPosition: String = "",
        val detailUrl: String = "",
        val creationDate: String = "",
        var numberOfCards: Int = 0,
        @JsonIgnore
        @OneToMany(mappedBy = "expansion", cascade = arrayOf(CascadeType.ALL))
        val cardExpansions: MutableList<CardExpansion> = mutableListOf()
) {
}