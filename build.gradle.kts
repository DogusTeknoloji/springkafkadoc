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

group = "io.github.DogusTeknoloji"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(sourcesJar)
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
