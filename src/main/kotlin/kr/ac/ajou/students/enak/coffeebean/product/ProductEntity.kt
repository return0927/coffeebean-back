package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import java.sql.ResultSet

data class ProductEntity(
    val id: Long,
    var brandName: String,
    var origins: String? = null,
    var quantity: Long,
    var processing: String? = null,
    var grinding: String? = null,
    var price: Long,
    var discounts: Long,
) : Entity {
    constructor(rs: ResultSet) : this(
        id = rs.getLong("id"),
        brandName = rs.getString("brand_name"),
        origins = rs.getString("origins"),
        quantity = rs.getLong("quantity"),
        processing = rs.getString("processing"),
        grinding = rs.getString("grinding"),
        price = rs.getLong("price"),
        discounts = rs.getLong("discounts"),
    )
}
