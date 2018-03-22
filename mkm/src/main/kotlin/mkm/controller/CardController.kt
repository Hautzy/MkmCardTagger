package mkm.controller

import mkm.entities.Card
import mkm.repos.CardRepository
import mkm.services.CardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
    fun findAll(): List<Card> = cardRepository.findAll().toList()

    @GetMapping("/findByEnglishName")
    fun findByEnglishName(@RequestParam(value = "englishName", defaultValue = "") englishNameParam: String,
                          @RequestParam(value = "resultCnt", defaultValue = "10") resultCntParam: String): List<Card> {
        var cnt: Int? = resultCntParam.toIntOrNull()
        cnt = if (cnt == null || cnt < 1) 10 else cnt
        if (englishNameParam.isNullOrEmpty())
            return cardRepository.findAll().toList().take(cnt)
        else
            return cardRepository.findByEnglishName(englishNameParam).take(cnt)
    }

    @GetMapping("/cardCnt")
    fun cardCnt(): Int = cardService.countOfCards()

    @GetMapping("/loadToCsv")
    fun loadToCsv(): Long {
        return cardService.locaToCsv()
    }

}