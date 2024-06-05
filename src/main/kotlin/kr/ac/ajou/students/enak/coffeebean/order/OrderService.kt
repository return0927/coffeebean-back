package kr.ac.ajou.students.enak.coffeebean.order

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

    fun getOrderOnBehalfOf(customer: CustomerEntity, orderId: Int): OrderDto? {
        val order = orderRepository.findByOrderId(orderId) ?: return null
        if (order.customerId != customer.id) return null
        return OrderDto(order)
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
}
