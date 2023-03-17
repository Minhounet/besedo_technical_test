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
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.freefair.lombok:io.freefair.lombok.gradle.plugin:6.6.3")
    implementation("io.vavr:vavr:0.10.4")
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor ("org.projectlombok:lombok:1.18.26")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
