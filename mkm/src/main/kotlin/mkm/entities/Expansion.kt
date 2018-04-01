package mkm.entities

import javax.persistence.*

@Entity
@Table(name = "mkm_Expansion")
data class Expansion(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
       val id: Long = 0,
        val englishName: String = "",
        @OneToMany(mappedBy = "expansion", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        val cardExpansions: MutableList<CardExpansion> = mutableListOf()
) {

}