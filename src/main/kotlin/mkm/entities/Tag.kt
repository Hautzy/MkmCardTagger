package mkm.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "mkm_Tag")
data class Tag(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    val card: Card? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appuser_id")
    val appUser: AppUser? = null,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null
)