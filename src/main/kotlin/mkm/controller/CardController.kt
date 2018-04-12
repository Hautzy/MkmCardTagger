package mkm.controller

import mkm.entities.Card
import mkm.repos.CardRepository
import mkm.services.CardService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/card")
class CardController(private val cardRepository: CardRepository,
                     private val cardService: CardService) {


    @GetMapping("/deleteTestData")
    fun removeTestData(): String {
        cardRepository.deleteAll()
        return "done"
    }

    @GetMapping("/findAll")
    fun findAll(@RequestParam(value = "page", required = true) page: Int,
                @RequestParam(value = "limit", required = true) limit: Int): List<Card> {
        val sort = Sort(Sort.Direction.ASC, "englishName")
        val pageable = PageRequest(page, limit, sort)
        val cards = cardRepository.findAll(pageable)
        return cards.content
    }

    @GetMapping("/findByEnglishName")
    fun findByEnglishName(@RequestParam(value = "englishName", defaultValue = "") englishNameParam: String,
                          @RequestParam(value = "expansion", required = true) expansion: Long,
                          @RequestParam(value = "page", required = true) page: Int,
                          @RequestParam(value = "limit", required = true) limit: Int): List<Card> {
        val sort = Sort(Sort.Direction.ASC, "englishName")
        val pageable = PageRequest(page, limit, sort)
        var cards: List<Card> = emptyList()

        if(expansion == 0L) {
            if (englishNameParam.isEmpty())
                return cardRepository.findAll(pageable).content
            else
                return cardRepository.findByEnglishName(englishNameParam, pageable).content
        } else {
            if (englishNameParam.isEmpty())
                return cardRepository.findAllByExpansion(expansion, pageable).content
            else
                cards = cardRepository.findByEnglishNameAndExpansion(englishNameParam, expansion, pageable).content
            return cards
        }
    }

    @GetMapping("/cardCnt")
    fun cardCnt(): Int = cardService.countOfCards()

    @GetMapping("/loadToCsv")
    fun loadToCsv(): Long = cardService.locaToCsv()

    @GetMapping("/persistCsv")
    fun persistCsv(@RequestParam(value = "from") from: Int): Int {
        return cardService.persistCsv(from)
    }

}