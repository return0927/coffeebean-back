package kr.ac.ajou.students.enak.coffeebean.order

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.ajou.students.enak.coffeebean.AccountType
import kr.ac.ajou.students.enak.coffeebean.auth.*
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerEntity
import kr.ac.ajou.students.enak.coffeebean.errors.AuthRequiredError
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Api(tags = ["주문 관리"])
@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping("/")
    @AuthRequired
    @ApiOperation("내 주문 모두 불러오기", notes = "소비자로 로그인 한 경우에는 내가 주문한 것들이, 판매자로 로그인 한 경우에는 내 상품의 주문이 나옴")
    fun getMyOrders(req: HttpServletRequest): List<OrderDto> {
        return when (req.getUserType()) {
            AccountType.CUSTOMER -> orderService.getAllOrdersOfCustomer(req.getCustomer()!!)
            AccountType.SELLER -> orderService.getAllOrdersOfSeller(req.getSeller()!!)
            else -> throw AuthRequiredError()
        }
    }

    @PostMapping("/")
    @AuthRequired
    @ApiOperation("주문 만들기")
    fun createNewOrder(req: HttpServletRequest, @RequestBody order: NewOrderDto): OrderDto {
        val customer: CustomerEntity = req.getUser()
        return orderService.createNewOrder(customer, order)
    }

    @GetMapping("/{id}")
    @AuthRequired
    @ApiOperation("주문 내역 받아오기")
    fun getOrder(req: HttpServletRequest, @PathVariable id: Int): OrderDto {
        return orderService.getOrderOnBehalfOf(req.getUser(), id)
            ?: throw ReportingError("해당 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    @AuthRequired
    @ApiOperation("주문 수정하기")
    fun updateOrder(req: HttpServletRequest, @PathVariable id: Int, @RequestBody order: UpdateOrderDto): OrderDto {
        val user = when (req.getUserType()) {
            AccountType.CUSTOMER -> req.getCustomer()!!
            AccountType.SELLER -> req.getSeller()!!
            else -> throw AuthRequiredError()
        }
        return orderService.updateOrderOnBehalfOf(user, id, order)
    }
}
