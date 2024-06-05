package kr.ac.ajou.students.enak.coffeebean.product

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(tags = ["2. 상품"])
@RestController
@RequestMapping("/api/products/")
class ProductController(
    private val service: ProductService,
) {
    companion object {
        enum class ProductSortMethod {
            NEWEST,
            OLDEST,
            LOWEST_PRICE,
            HIGHEST_PRICE,
            ;

            companion object {
                operator fun get(code: String): ProductSortMethod = when (code) {
                    "newest" -> NEWEST
                    "oldest" -> OLDEST
                    "lowest price" -> LOWEST_PRICE
                    "highest price" -> HIGHEST_PRICE
                    else -> NEWEST
                }
            }
        }
    }

    @GetMapping("/")
    @ApiOperation("모든 상품 불러오기")
    fun listProducts(
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "kind", defaultValue = "unset") kind: String,
        @RequestParam(value = "amount", defaultValue = "unset") amount: String,
        @RequestParam(value = "origin", defaultValue = "unset") origin: String,
        @RequestParam(value = "sort", defaultValue = "NEWEST") sort: ProductSortMethod,
    ): List<ProductBriefDto> {
        val kind = if (kind == "unset") null else kind
        val amount = when (amount) {
            "unset" -> null
            "200g" -> 200
            "500g" -> 500
            "1kg" -> 1000
            else -> throw ReportingError("지원되지 않는 필터 값: $amount", HttpStatus.BAD_REQUEST)
        }
        val origin = if (origin == "unset") null else origin

        return service.listProducts(size, kind, amount, origin, sort).map { it.toBrief() }
    }

    @GetMapping("/{id}")
    @ApiOperation("상품 정보 가져오기")
    fun getProductInfo(@PathVariable("id") id: Int): ProductDto? {
        return service.searchByProductId(id)
    }
}