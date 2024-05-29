package kr.ac.ajou.students.enak.coffeebean.seller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.ajou.students.enak.coffeebean.auth.AuthRequired
import org.springframework.web.bind.annotation.*

@Api(tags = ["생산자"])
@RestController
@RequestMapping("/api/sellers/")
class SellerController(
    private val service: SellerService,
) {
    @AuthRequired
    @GetMapping("/")
    @ApiOperation("모든 생산자 불러오기")
    fun listSellers(@RequestParam(required = false, defaultValue = "10") size: Int): List<SellerBriefDto> {
        return service.listSellers(size).map { it.toBrief() }
    }

    @GetMapping("/{id}")
    @ApiOperation("생산자 정보를 ID로 가져오기")
    fun getSellerById(@PathVariable("id") id: Long): SellerDto? {
        return service.getById(id)
    }

    @GetMapping("/byname/{name}")
    @ApiOperation("생산자 정보를 상호명으로 가져오기")
    fun getSellerByName(@PathVariable("name") name: String): SellerDto? {
        return service.getByName(name)
    }
}