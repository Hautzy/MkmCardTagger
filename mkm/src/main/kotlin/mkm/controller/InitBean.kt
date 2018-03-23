package mkm.controller

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import mkm.services.AppUserService
import mkm.services.CardService
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitBean(val cardService: CardService,
               val appUserService: AppUserService){
    @PostConstruct
    fun init() {
        print("***********INIT***********")
        cardService.initTestData()
        appUserService.initTestData()
    }
}