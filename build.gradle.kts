import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.tasks.KtLintFormatTask

plugins {
    java
    jacoco
    `maven-publish`
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "com.dteknoloji"
version = "1.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17
val repoUsername: String by project
val repoPassword: String by project

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.dteknoloji"
            version = "1.0.1"
            artifactId = "springkafkadoc"
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/DogusTeknoloji/springkafkadoc")
            credentials {
                username = repoUsername
                password = repoPassword
            }
        }
    }
}

dependencies {
    api("com.asyncapi:asyncapi-core:1.0.0-EAP")
    implementation("io.swagger:swagger-inflector:2.0.8")
    compileOnly("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    jar {
        enabled = true
    }

    publish {
        dependsOn(check)
    }

    bootJar {
        enabled = false
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all-compatibility")
            jvmTarget = "17"
        }
    }

    withType<KtLintFormatTask> {
        description = "KtLint Format"
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
