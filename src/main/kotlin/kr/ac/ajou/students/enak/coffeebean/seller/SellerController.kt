package kr.ac.ajou.students.enak.coffeebean.seller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sellers/")
class SellerController(
    private val service: SellerService,
) {
    @GetMapping("/")
    fun listSellers(@RequestParam(required = false, defaultValue = "10") size: Int): List<SellerDto> {
        return service.listSellers(size)
    }

    @GetMapping("/{id}")
    fun getSellerById(@PathVariable("id") id: Long): SellerDto? {
        return service.getById(id)
    }

    @GetMapping("/byname/{name}")
    fun getSellerByName(@PathVariable("name") name: String): SellerDto? {
        return service.getByName(name)
    }
}