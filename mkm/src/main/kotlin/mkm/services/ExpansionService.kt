package mkm.services

import mkm.entities.Card
import mkm.entities.CardExpansion
import mkm.entities.Expansion
import mkm.repos.CardExpansionRepository
import mkm.repos.CardRepository
import mkm.repos.ExpansionRepository
import org.springframework.stereotype.Service
import java.net.URLDecoder
import java.net.URLEncoder

@Service
class ExpansionService(private val expansionRepository: ExpansionRepository,
                       private val cardExpansionRepository: CardExpansionRepository,
                       private val cardRepository: CardRepository) {
    fun createExpansions(): Int {
        val cards: MutableIterable<Card> = cardRepository.findAll()
        val expansions: MutableList<Expansion> = mutableListOf()

        cards.forEach { it -> checkForDoubles(it, expansions)}

        expansionRepository.saveAll(expansions)
        return expansions.count()
    }

    fun checkForDoubles(card: Card, expansions: MutableList<Expansion>) {
        val expansionParts = card.detailUrl.split("/")
        var expansionName = expansionParts[5]

        expansionName = expansionName.replace("+", "%20")
        //expansionName = URLEncoder.encode(expansionName, "UTF-8")
        expansionName = URLDecoder.decode(expansionName, "UTF-8")
        //expansionName.replace("+", " ")

        print(expansionName)

        val results = expansions.filter { e ->
            e.englishName.compareTo(expansionName, true) == 0 }
        if(results.count() == 0) {
            val e = Expansion(id = 0, englishName = expansionName)
            expansions.add(e)
            e.cardExpansions.add(CardExpansion(card = card, expansion = e))
        } else {
            results[0].cardExpansions.add(CardExpansion(card = card, expansion = results[0]))
        }
    }
}