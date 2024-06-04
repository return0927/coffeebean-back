package kr.ac.ajou.students.enak.coffeebean.config

import kr.ac.ajou.students.enak.coffeebean.auth.AuthRequired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.HttpAuthenticationScheme
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
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
    fun api(): Docket = Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .securityContexts(listOf(securityContext()))
        .securitySchemes(listOf(token()))
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths { each -> each.startsWith("/api/") }
        .build()

    fun securityContext() = SecurityContext.builder()
        .securityReferences(
            listOf(
                SecurityReference("apiToken", arrayOf(AuthorizationScope("global", "accessEverything"))),
            )
        )
        .operationSelector { ctx -> ctx.findAnnotation(AuthRequired::class.java).isPresent }
        .build()

    //    fun token() = ApiKey("Bearer", "Authorization", "header")
    fun token() = HttpAuthenticationScheme.JWT_BEARER_BUILDER
        .name("apiToken")
        .description("사용자 토큰")
        .bearerFormat("JWT")
        .scheme("bearer")
        .build()

}