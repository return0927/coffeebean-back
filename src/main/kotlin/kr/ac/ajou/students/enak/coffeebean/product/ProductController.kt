package kr.ac.ajou.students.enak.coffeebean.product

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@Api(tags = ["상품"])
@RestController
@RequestMapping("/api/products/")
class ProductController(
    private val service: ProductService,
) {
    @GetMapping("/")
    @ApiOperation("모든 상품 불러오기")
    fun listProducts(@RequestParam(value = "size", defaultValue = "10") size: Int): List<ProductBriefDto> {
        return service.listProducts(size).map { it.toBrief() }
    }

    @GetMapping("/{id}")
    @ApiOperation("상품 정보 가져오기")
    fun getProductInfo(@PathVariable("id") id: Int): ProductDto? {
        return service.searchByProductId(id.toLong())
    }
}