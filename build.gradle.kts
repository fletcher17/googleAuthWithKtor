val koinVersion: String by project
val googleClientVersion: String by project
val kMongoVersion: String by project

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.create("stage") {
    dependsOn("installDist")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    //Content Negotiation
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)

    //Sessions
    implementation(libs.ktor.server.sessions)

    // Auth
    implementation(libs.ktor.server.auth)

    // Google Client API Library
    implementation("com.google.api-client:google-api-client:$googleClientVersion")

    // KMongo
    implementation("org.litote.kmongo:kmongo-async:$kMongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kMongoVersion")

    // Koin core Features
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
}
