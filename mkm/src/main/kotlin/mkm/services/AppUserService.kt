package mkm.services

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import org.springframework.stereotype.Service

@Service
class AppUserService(private val appUserRepository: AppUserRepository) {
    fun initTestData() {
        var testData = arrayListOf(
                AppUser(0 ,
                        "Hautzy",
                        "julian.hautzmayer@gmail.com",
                        "test123")
        )
        appUserRepository.saveAll(testData)
    }
}