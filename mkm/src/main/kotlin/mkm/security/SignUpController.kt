package mkm.security

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign-up")
class SignUpController(val appUserRepository: AppUserRepository, val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @PostMapping
    fun signUp(@RequestBody appUser: AppUser) {
        appUser.password = bCryptPasswordEncoder.encode(appUser.password)
        appUserRepository.save(appUser)
    }
}