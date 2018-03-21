package mkm.entities

import javax.persistence.*

@Entity
data class Card(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val englishName: String = "",
        val gerName: String = "",
        val detailUrl: String = "",
        val imgUrl: String = ""
) {
}