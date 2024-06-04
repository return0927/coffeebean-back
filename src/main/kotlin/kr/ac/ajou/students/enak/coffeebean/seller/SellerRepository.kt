package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.stereotype.Component

@Component
class SellerRepository : Repository<SellerEntity>() {
    fun listSellers(size: Int): List<SellerEntity> {
        val result = query("SELECT * FROM producers LIMIT ?", size) {
            return@query SellerEntity(it)
        }
        return result.filterNotNull()
    }

    fun getById(id: Long): SellerEntity? {
        val result = query("SELECT * FROM producers WHERE id = ?", id) {
            return@query SellerEntity(it)
        }
        return result.firstOrNull()
    }

    fun getByName(name: String): SellerEntity? {
        val result = query("SELECT * FROM producers WHERE company_name = ?", name) {
            return@query SellerEntity(it)
        }
        return result.firstOrNull()
    }

    fun findByLoginId(loginId: String): SellerEntity? {
        val result = query("SELECT * FROM producers WHERE login_id = ?", loginId) {
            return@query SellerEntity(it)
        }
        return result.firstOrNull()
    }

    fun insert(seller: SellerEntity): SellerEntity {
        val fetched = query(
            "INSERT INTO producers " +
                    "(login_id, pw, company_name, company_registration_number, business_address) " +
                    "VALUES (?, ?, ?, ?, ?) " +
                    "RETURNING *;",
            seller.loginId,
            seller.pw,
            seller.companyName,
            seller.companyRegistrationNumber,
            seller.businessAddress,
        ) { rs ->
            return@query SellerEntity(rs)
        }.firstOrNull()

        fetched ?: throw ReportingError("$seller (을)를 저장하는데 오류가 발생했습니다.")
        return seller.copyFrom(fetched)
    }
}