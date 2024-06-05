package kr.ac.ajou.students.enak.coffeebean.order

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.ajou.students.enak.coffeebean.auth.AuthRequired
import kr.ac.ajou.students.enak.coffeebean.auth.getUser
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerEntity
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Api(tags = ["주문 관리"])
@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping("/my")
    @AuthRequired
    @ApiOperation("내 주문 모두 불러오기")
    fun getMyOrders(req: HttpServletRequest): List<OrderDto> {
        val customer: CustomerEntity = req.getUser()
        return orderService.getAllOrdersOfCustomer(customer)
    }

    @GetMapping("/{id}")
    @AuthRequired
    @ApiOperation("주문 내역 받아오기")
    fun getOrder(req: HttpServletRequest, @PathVariable id: Int): OrderDto {
        return orderService.getOrderOnBehalfOf(req.getUser(), id)
            ?: throw ReportingError("해당 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    }
}
