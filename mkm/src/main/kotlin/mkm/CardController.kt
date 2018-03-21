package mkm

import org.jsoup.Jsoup
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.sql.SQLException

@RestController
@RequestMapping("/api")
class CardController(private val cardRepository: CardRepository) {
    val LIST_URL: String = "https://www.cardmarket.com/de/Magic/Products/Singles?name=&idExpansion=0&idRarity=&onlyAvailable=&sortBy=popularity&sortDir=desc&view=list&resultsPage="
    val ROOT_IMG_URL: String = "https://www.cardmarket.com/de/Magic"

    @GetMapping("/save")
    fun save(): Card {
        var c = Card(1, "Test")
        return cardRepository.save(c)
    }

    @GetMapping("/findAll")
    fun findAll(): List<Card> = cardRepository.findAll()

    @GetMapping("/reloadAll")
    fun reload(): Long {
        var cardCnt = 0L
        var site = 0
        var run = true
        try {

            while (run) {
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

                            cardRepository.save(Card(cardCnt, englishName, germanName, detailUrl, imgUrl))
                            cardCnt++
                            println(cardCnt.toString() + " | " + englishName)
                        }
                    }
                    site++
                    println("---------> currently on site: " + site)

                    if(cardCnt > 41985) {
                        run = false
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return cardCnt
    }
}