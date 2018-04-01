package mkm.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "mkm_Expansion")
data class Expansion(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
       val id: Long = 0,
        val englishName: String = "",
        @JsonIgnore
        @OneToMany(mappedBy = "expansion", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        val cardExpansions: MutableList<CardExpansion> = mutableListOf()
) {

}