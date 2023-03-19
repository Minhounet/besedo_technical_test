plugins {
    java
    id("org.springframework.boot") version "3.1.0-M1"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.freefair.lombok") version "6.6.3"
}

group = "com.qmt.besedo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Lombok
    implementation("io.freefair.lombok:io.freefair.lombok.gradle.plugin:6.6.3")
    annotationProcessor ("org.projectlombok:lombok:1.18.26")
    implementation("org.projectlombok:lombok:1.18.26")
    // Apache
    implementation("org.apache.commons:commons-csv:1.10.0")
    implementation("commons-validator:commons-validator:1.7")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    // FP
    implementation("io.vavr:vavr:0.10.4")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:5.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
