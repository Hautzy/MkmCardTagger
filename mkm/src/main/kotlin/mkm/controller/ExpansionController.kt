package mkm.controller

import mkm.entities.Expansion
import mkm.repos.ExpansionRepository
import mkm.services.ExpansionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/expansion")
class ExpansionController(private val expansionRepository: ExpansionRepository,
                          private val expansionService: ExpansionService) {

    @GetMapping("connectExpansionsAndCards")
    fun connectExpansionsAndCards() {
        //TODO: add cards to expansions
    }

    @GetMapping("createExpansionsFromWebsite")
    fun createExpansionsFromWebsite(): Int {
        return expansionService.createExpansionsFromWebsite()
    }

    @GetMapping("createExpansionsFromCards")
    fun createExpansionsFromCards(): Int = expansionService.createExpansionsFromCards()

    @GetMapping("findAll")
    fun findAll(@RequestParam(value = "page", required = true) page: Int,
                @RequestParam(value = "limit", required = true) limit: Int): List<Expansion> {
        val sort = Sort(Sort.Direction.ASC, "englishName")
        val pageable = PageRequest(page, limit, sort)
        val expansions = expansionRepository.findAll(pageable)
        return expansions.content
    }

    @GetMapping("findByEnglishName")
    fun findByEnglishName(@RequestParam(value = "englishName", required = true) englishName: String,
                          @RequestParam(value = "page", required = true) page: Int,
                          @RequestParam(value = "limit", required = true) limit: Int): List<Expansion> {
        val sort = Sort(Sort.Direction.ASC, "englishName")
        val pageable = PageRequest(page, limit, sort)
        val expansions: Page<Expansion>
        if(englishName.isEmpty())
            expansions = expansionRepository.findAll(pageable)
        else
            expansions = expansionRepository.findByEnglishName(englishName, pageable)
        return expansions.content
    }
}