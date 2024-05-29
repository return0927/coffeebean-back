package kr.ac.ajou.students.enak.coffeebean.seller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sellers/")
class SellerController(
    private val service: SellerService,
) {
    @GetMapping("/{id}")
    fun getSellerById(@PathVariable("id") id: Long): SellerDto? {
        return service.getById(id)
    }

    @GetMapping("/byname/{name}")
    fun getSellerByName(@PathVariable("name") name: String): SellerDto? {
        return service.getByName(name)
    }
}