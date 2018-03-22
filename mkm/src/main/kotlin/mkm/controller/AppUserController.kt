package mkm.controller

import mkm.entities.AppUser
import mkm.repos.AppUserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class AppUserController(private val appUserRepository: AppUserRepository) {
    @GetMapping("findAll")
    fun findAll(): List<AppUser> = appUserRepository.findAll().toList()

    @GetMapping("/findById")
    fun findById(@RequestParam("id") id: Long): AppUser = appUserRepository.findById(id).get()
}