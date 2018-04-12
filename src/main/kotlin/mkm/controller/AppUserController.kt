package mkm.controller

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class AppUserController(private val appUserRepository: AppUserRepository,
                        val bCryptPasswordEncoder: BCryptPasswordEncoder) {
    @GetMapping("/findAll")
    fun findAll(): List<AppUser> = appUserRepository.findAll().toList()

    @GetMapping("/findById")
    fun findById(@RequestParam("id") id: Long): AppUser = appUserRepository.findById(id).get()

    @GetMapping("/checkToken")
    fun checkToken(): String = "ok"
}