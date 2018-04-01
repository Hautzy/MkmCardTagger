package mkm.entities

import javax.persistence.*

@Entity
@Table(name = "mkm_CardExpansion")
data class CardExpansion(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "card_id")
        val card: Card? = null,
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "expansion_id")
        val expansion: Expansion? = null) {
}