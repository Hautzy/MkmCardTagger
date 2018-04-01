package mkm.controller

import mkm.repos.ExpansionRepository
import mkm.services.ExpansionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/expansion")
class ExpansionController(private val expansionRepository: ExpansionRepository,
                          private val expansionService: ExpansionService) {
    @GetMapping("createExpansions")
    fun createExpansions(): Int = expansionService.createExpansions()
}