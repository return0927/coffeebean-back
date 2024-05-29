package kr.ac.ajou.students.enak.coffeebean.product

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products/")
class ProductController(
    private val service: ProductService,
) {
    @GetMapping("/")
    fun listProducts(@RequestParam(value = "size", defaultValue = "10") size: Int): List<ProductDto> {
        return service.listProducts(size)
    }

    @GetMapping("/{id}")
    fun getProductInfo(@PathVariable("id") id: Int): ProductDto? {
        return service.searchByProductId(id.toLong())
    }
}