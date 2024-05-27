package kr.ac.ajou.students.enak.coffeebean.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products/")
class ProductController {
    @GetMapping("/{id}")
    fun getProductInfo(@PathVariable("id") id: Int): ProductDto {
        return ProductDto(
            productId = id,
            brandName = "A Company Name",
            origins = "Africa",
            quantity = 999,
            processing = "",
            grinding = "",
            price = 100000,
            discounts = 0,
        )
    }
}