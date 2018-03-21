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
                        "/img/7c85aa7f8132e0940a23d825f7b445fd/cards/Ixalan/field_of_ruin.jpg"),
                Card(2,
                        "Unclaimed Territory",
                        "Unbeanspruchtes Territorium",
                        "/de/Magic/Products/Singles/Ixalan/Unbeanspruchtes+Territorium",
                        "/img/c33ff6f524a15735dd6f99ed95054876/cards/Ixalan/unclaimed_territory.jpg"),
                Card(3,
                        "Island",
                        "Insel",
                        "/de/Magic/Products/Singles/Unstable/Insel",
                        "/img/640c9e1cb032649cee35d7bf2c95506f/cards/Unstable/island.jpg"),
                Card(4,
                        "Field of Ruin",
                        "Schauplatz der Verheerung",
                        "/de/Magic/Products/Singles/Ixalan/Schauplatz+der+Verheerung",
                        "/img/7c85aa7f8132e0940a23d825f7b445fd/cards/Ixalan/field_of_ruin.jpg"),
                Card(5,
                        "Unclaimed Territory",
                        "Unbeanspruchtes Territorium",
                        "/de/Magic/Products/Singles/Ixalan/Unbeanspruchtes+Territorium",
                        "/img/c33ff6f524a15735dd6f99ed95054876/cards/Ixalan/unclaimed_territory.jpg"),
                Card(6,
                        "Island",
                        "Insel",
                        "/de/Magic/Products/Singles/Unstable/Insel",
                        "/img/640c9e1cb032649cee35d7bf2c95506f/cards/Unstable/island.jpg"),
                Card(7,
                        "Field of Ruin",
                        "Schauplatz der Verheerung",
                        "/de/Magic/Products/Singles/Ixalan/Schauplatz+der+Verheerung",
                        "/img/7c85aa7f8132e0940a23d825f7b445fd/cards/Ixalan/field_of_ruin.jpg"),
                Card(8,
                        "Unclaimed Territory",
                        "Unbeanspruchtes Territorium",
                        "/de/Magic/Products/Singles/Ixalan/Unbeanspruchtes+Territorium",
                        "/img/c33ff6f524a15735dd6f99ed95054876/cards/Ixalan/unclaimed_territory.jpg"),
                Card(9,
                        "Island",
                        "Insel",
                        "/de/Magic/Products/Singles/Unstable/Insel",
                        "/img/640c9e1cb032649cee35d7bf2c95506f/cards/Unstable/island.jpg"),
                Card(10,
                "Field of Ruin",
                "Schauplatz der Verheerung",
                "/de/Magic/Products/Singles/Ixalan/Schauplatz+der+Verheerung",
                "/img/7c85aa7f8132e0940a23d825f7b445fd/cards/Ixalan/field_of_ruin.jpg"),
                Card(11,
                        "Unclaimed Territory",
                        "Unbeanspruchtes Territorium",
                        "/de/Magic/Products/Singles/Ixalan/Unbeanspruchtes+Territorium",
                        "/img/c33ff6f524a15735dd6f99ed95054876/cards/Ixalan/unclaimed_territory.jpg"),
                Card(12,
                        "Island",
                        "Insel",
                        "/de/Magic/Products/Singles/Unstable/Insel",
                        "/img/640c9e1cb032649cee35d7bf2c95506f/cards/Unstable/island.jpg"))

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
                                var imgUrl = cardTr.child(0).child(0).child(0).child(0).attr("style")

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