import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.15"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "kr.ac.ajou.students.enak"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

fun boot(packageName: String) = "org.springframework.boot:spring-boot-$packageName"

dependencies {
    /* SPRING */
    implementation(boot("starter-data-jpa"))
    implementation(boot("starter-data-rest"))
    implementation(boot("starter-web"))

    /* DOCUMENTATION */
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    /* DATABASE & SERIALIZATION */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    /* SERIALIZATION */
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    /* MISC */
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    /* AUTHORIZATION */
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    testImplementation(boot("starter-test"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    enabled = false
}
