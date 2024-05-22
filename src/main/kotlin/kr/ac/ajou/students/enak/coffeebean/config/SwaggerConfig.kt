package kr.ac.ajou.students.enak.coffeebean.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    private fun apiInfo() = ApiInfoBuilder()
        .title("CoffeeBean API Backend")
        .description("아주대학교 2024-1학기 데이터베이스 팀플 백엔드입니다")
        .contact(Contact("Eunhak Lee", "https://enak.kr", "bc1916@ajou.ac.kr"))
        .build()

    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
}