package kr.ac.ajou.students.enak.coffeebean.order

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerEntity
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import kr.ac.ajou.students.enak.coffeebean.product.ProductService
import kr.ac.ajou.students.enak.coffeebean.seller.SellerEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService,
) {
    fun getAllOrdersOfCustomer(entity: CustomerEntity): List<OrderDto> =
        orderRepository.findByCustomerId(entity.id!!).map { OrderDto(it) }

    fun getAllOrdersOfSeller(seller: SellerEntity): List<OrderDto> =
        orderRepository.findBySellerId(seller.id!!).map { OrderDto(it) }

    fun getOrderOnBehalfOf(customer: CustomerEntity, orderId: Int): OrderDetailDto? {
        val order = orderRepository.findByOrderId(orderId) ?: return null
        if (order.customerId != customer.id) return null

        val dto = OrderDto(order)
        val product = productService.searchByProductId(order.itemId)!!

        return OrderDetailDto(
            orderId = dto.orderId,
            name = product.name,
            status = dto.status,
            price = dto.price,
            amount = dto.amount,
            recipient = dto.recipient,
            createAt = order.orderDate
        )
    }

    fun createNewOrder(customer: CustomerEntity, order: NewOrderDto): OrderDto {
        val product = productService.searchByProductId(order.itemId)
            ?: throw ReportingError("상품 id=${order.itemId} (을)를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        val newOrder = OrderEntity(
            itemId = product.productId,
            customerId = customer.id!!,
            orderDate = Date(),
            price = product.price,
            amount = order.amount,
            deliverAddress = order.deliverAddress,
            recipient = order.recipient,
            status = OrderStatus.PENDING,
        )
        return OrderDto(orderRepository.saveNewOrder(newOrder))
    }

    fun updateOrderOnBehalfOf(user: Entity, orderId: Int, order: UpdateOrderDto): OrderDto {
        assert(user is CustomerEntity || user is SellerEntity) {
            "사용자는 소비자 혹은 판매자이어야 합니다."
        }

        if (user is CustomerEntity) {
            if (order.status != null) throw ReportingError("소비자는 주문 상태를 수정할 수 없습니다.", HttpStatus.NOT_ACCEPTABLE)
        }

        return OrderDto(orderRepository.updateOrder(orderId, order))
    }
}
