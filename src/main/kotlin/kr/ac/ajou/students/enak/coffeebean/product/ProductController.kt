package kr.ac.ajou.students.enak.coffeebean.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products/")
class ProductController {
    @GetMapping("/{id}")
    fun getProductInfo(@PathVariable("id") id: Int): String {
        return "Your id is $id"
    }
}