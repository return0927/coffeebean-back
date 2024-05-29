package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
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
}