package kr.ac.ajou.students.enak.coffeebean

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class CoffeebeanBackApplication

fun main(args: Array<String>) {
    runApplication<CoffeebeanBackApplication>(*args)
}
