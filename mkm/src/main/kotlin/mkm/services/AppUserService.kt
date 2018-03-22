package mkm.services

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AppUserService(private val appUserRepository: AppUserRepository,
                     val bCryptPasswordEncoder: BCryptPasswordEncoder) {
    fun initTestData() {
        var testData = arrayListOf(
                AppUser(0 ,
                        "Hautzy",
                        "julian.hautzmayer@gmail.com",
                        bCryptPasswordEncoder.encode("test123"))
        )
        appUserRepository.saveAll(testData)
    }
}