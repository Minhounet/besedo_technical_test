plugins {
    java
    id("org.springframework.boot") version "3.1.0-M1"
    id("io.spring.dependency-management") version "1.1.0"
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
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
