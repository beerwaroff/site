plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("org.springframework.boot") version "2.7.5"
    application
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:30.1.1-jre")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	implementation("org.jetbrains.exposed:exposed:0.17.14")
	implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.springframework:spring-web:5.3.23")
    implementation("org.springframework:spring-webmvc:5.3.23")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    implementation("org.springframework.security:spring-security-core:5.7.5")



}

application {
    mainClass.set("Forum.AppKt")
}
