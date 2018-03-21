package mkm.controller

import mkm.services.CardService
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitBean(val cardService: CardService) {
    @PostConstruct
    fun init() {
        print("***********INIT***********")
        cardService.initTestData()
    }
}