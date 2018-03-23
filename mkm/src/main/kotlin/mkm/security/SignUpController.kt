package mkm.security

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sign-up")
//@CrossOrigin(origins = arrayOf("*"), allowedHeaders = arrayOf("*"))
class SignUpController(val appUserRepository: AppUserRepository, val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @PostMapping
    fun signUp(@RequestBody appUser: AppUser): String {
        appUser.password = bCryptPasswordEncoder.encode(appUser.password)
        appUserRepository.save(appUser)
        return "ok"
    }
}