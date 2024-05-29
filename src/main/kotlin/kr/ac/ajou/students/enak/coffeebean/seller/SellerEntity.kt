package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import java.sql.ResultSet

data class SellerEntity(
    val id: Long,
    var companyName: String,
    var companyRegistrationNumber: Long,
    var businessAddress: String,
) : Entity {
    constructor(rs: ResultSet) : this(
        id = rs.getLong("id"),
        companyName = rs.getString("company_name"),
        companyRegistrationNumber = rs.getLong("company_registration_number"),
        businessAddress = rs.getString("business_address"),
    )
}