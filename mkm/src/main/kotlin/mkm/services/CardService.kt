package mkm.services

import mkm.entities.Card
import mkm.repos.CardRepository
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.sql.SQLException

@Service
class CardService(private val cardRepository: CardRepository) {
    val LIST_URL: String = "https://www.cardmarket.com/de/Magic/Products/Singles?name=&idExpansion=0&idRarity=&onlyAvailable=&sortBy=popularity&sortDir=desc&view=list&resultsPage="
    val ROOT_IMG_URL: String = "https://www.cardmarket.com/de/Magic"

    fun initTestData() {
        var cardTestDataList = listOf(
                Card(1,
                        "Field of Ruin",
                        "Schauplatz der Verheerung",
                        "/de/Magic/Products/Singles/Ixalan/Schauplatz+der+Verheerung",
                        "/img/9e848a75683f5382fed04513a016340b/expansionicons/expicons.png"),
                Card(2,
                        "Unclaimed Territory",
                        "Unbeanspruchtes Territorium",
                        "/de/Magic/Products/Singles/Ixalan/Unbeanspruchtes+Territorium",
                        "/img/9e848a75683f5382fed04513a016340b/expansionicons/expicons.png"),
                Card(3,
                        "Island",
                        "Insel",
                        "/de/Magic/Products/Singles/Unstable/Insel",
                        "/img/9e848a75683f5382fed04513a016340b/expansionicons/expicons.png"))

        cardRepository.saveAll(cardTestDataList)
    }

    fun countOfCards(): Int{
        val doc = Jsoup.connect(LIST_URL + 0).get()
        var cntElement = doc.getElementById("hitCountBottom")
        var cntOfCards = cntElement.html().toInt()
        return cntOfCards
    }

    fun locaToCsv(): Long {
        var maxCardCnt = countOfCards()
        var cardCnt = 0L
        var site = 0
        try {
            File("cards.csv").printWriter().use { out ->
                while (cardCnt < maxCardCnt) {
                    try {
                        val doc = Jsoup.connect(LIST_URL + site).get()
                        val tables = doc.select("table")
                        val cardTrs = tables[0].children().select("tr")

                        for (i in 1 until cardTrs.size) {
                            val cardTr = cardTrs[i]
                            if (cardTr.children().size >= 2) {
                                var imgUrl = cardTr.child(1).child(0).child(0).child(0).attr("style")

                                val parts = imgUrl.split("[']".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                imgUrl = parts[1]

                                val englishName = cardTr.child(2).child(0).html()
                                val detailUrl = cardTr.child(2).child(0).attr("href")
                                val germanName = cardTr.child(3).child(0).html()

                                var c = Card(cardCnt, englishName, germanName, detailUrl, imgUrl)
                                out.println("${c.id};${c.englishName};${c.gerName};${c.detailUrl};${c.imgUrl}")
                                cardCnt++
                                println(cardCnt.toString() + " | " + englishName)
                            }
                        }
                        site++
                        println("---------> currently on site: " + site)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return cardCnt
    }
}