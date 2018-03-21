package mkm

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

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