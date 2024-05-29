package kr.ac.ajou.students.enak.coffeebean.seller

import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class SellerService(
    private val repository: SellerRepository,
) {
    fun listSellers(size: Int = 100): List<SellerDto> {
        return repository.listSellers(min(size, 100)).map {
            it.toDto()
        }
    }

    fun getById(id: Long): SellerDto? {
        val result = repository.getById(id)
        return result?.toDto()
    }

    fun getByName(name: String): SellerDto? {
        val result = repository.getByName(name)
        return result?.toDto()
    }

    private fun SellerEntity.toDto(): SellerDto = SellerDto(
        id = this.id,
        companyName = this.companyName,
        companyRegistrationNumber = this.companyRegistrationNumber,
        businessAddress = this.businessAddress,
    )
}
