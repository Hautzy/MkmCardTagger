package mkm.entities

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
        @OneToMany(mappedBy = "card", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        val cardExpansions: MutableList<CardExpansion> = mutableListOf()
) {
}