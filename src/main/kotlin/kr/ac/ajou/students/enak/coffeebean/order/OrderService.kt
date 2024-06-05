package kr.ac.ajou.students.enak.coffeebean.order

import kr.ac.ajou.students.enak.coffeebean.customer.CustomerEntity
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {
    fun getAllOrdersOfCustomer(entity: CustomerEntity): List<OrderDto> =
        orderRepository.findByCustomerId(entity.id!!).map { OrderDto(it) }

    fun getOrderOnBehalfOf(customer: CustomerEntity, orderId: Int): OrderDto? {
        val order = orderRepository.findByOrderId(orderId) ?: return null
        if (order.customerId != customer.id) return null
        return OrderDto(order)
    }
}
