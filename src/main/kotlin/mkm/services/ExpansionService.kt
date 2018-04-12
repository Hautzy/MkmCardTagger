package mkm.services

import mkm.entities.Card
import mkm.entities.CardExpansion
import mkm.entities.Expansion
import mkm.repos.CardExpansionRepository
import mkm.repos.CardRepository
import mkm.repos.ExpansionRepository
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.net.URLDecoder

@Service
class ExpansionService(private val expansionRepository: ExpansionRepository,
                       private val cardExpansionRepository: CardExpansionRepository,
                       private val cardRepository: CardRepository) {
    val MAGIC_URL: String = "https://www.cardmarket.com"
    val EXPANSION_URL: String = "https://www.cardmarket.com/de/Magic/Expansions"
    val EXPANSION_SYMBOL_URL: String = "/img/3971507472eb0e9038fa4c4a70317aa2/expansionicons/expicons.png"

    fun createExpansionsFromWebsite(): Int {
        val expansions: MutableList<Expansion> = mutableListOf()

        val doc = Jsoup.connect(EXPANSION_URL).timeout(10*1000).get()
        val expansionContainers = doc.getElementsByClass("alphabeticExpansion")

        expansionContainers.forEach { it ->
            try {
                val detailUrl = it.attr("href")

                val parts = it.child(0).child(0).attr("style").split(";")
                val symbolPosition = parts[parts.count() - 2]

                val subDoc = Jsoup.connect(MAGIC_URL + detailUrl).timeout(10*10000).get()
                val subCreationDateContainer = subDoc.getElementsByClass("bannerExpRelease")[0]
                val subCardCntContainer = subDoc.getElementsByClass("bannerExpCardNr")[0]
                val subNameContainers = subDoc.getElementsByClass("expTitle")

                val creationDateString = subCreationDateContainer.child(1).text() + " " +
                        subCreationDateContainer.child(2).text() + " " +
                        subCreationDateContainer.child(3).text()

                val cardCnt = subCardCntContainer.child(0).attr("alt").toInt()

                var englishName: String
                var germanName: String

                if (subNameContainers.count() > 2) {
                    germanName = subNameContainers.get(1).text()
                    englishName = subNameContainers.get(2).text()
                } else {
                    germanName = subNameContainers.get(1).text()
                    englishName = germanName
                }

                val expansion = Expansion(id = 0, englishName = englishName, germanName = germanName, detailUrl = detailUrl,
                        creationDate = creationDateString,
                        numberOfCards = cardCnt,
                        symbolPosition = symbolPosition)
                expansions.add(expansion)
                println("${expansions.size}) ${expansion.germanName} | ${expansion.englishName}")
            }catch (e: Exception) {
                println(e)
            }
        }
        expansionRepository.saveAll(expansions)

        return expansions.size
    }

    fun createExpansionsFromCards(): Int {
        val cards: MutableIterable<Card> = cardRepository.findAll()
        val expansions: MutableList<Expansion> = mutableListOf()

        cards.forEach { it -> checkForDoubles(it, expansions) }

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
            e.englishName.compareTo(expansionName, true) == 0
        }
        if (results.count() == 0) {
            val e = Expansion(id = 0, englishName = expansionName)
            expansions.add(e)
            e.cardExpansions.add(CardExpansion(card = card, expansion = e))
        } else {
            results[0].cardExpansions.add(CardExpansion(card = card, expansion = results[0]))
        }
    }

    fun connectExpansionAndCards(): Int {
        cardExpansionRepository.deleteAll()
        val expansions = expansionRepository.findAll()

        for(exp in expansions) {
            val parts = exp.detailUrl.split("/")
            val cards = cardRepository.findByUrlExpansion("%${parts[parts.size - 1]}%")
            val cardExpansions = mutableListOf<CardExpansion>()
            for (card in cards) {
                val cardExpansion = CardExpansion(card = card, expansion = exp)
                cardExpansions.add(cardExpansion)
            }
            cardExpansionRepository.saveAll(cardExpansions)
            println("${exp.englishName}")
        }

        return 0
    }
}