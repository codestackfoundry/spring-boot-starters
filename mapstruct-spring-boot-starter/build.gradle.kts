plugins {
    id("java-library")
    id("maven-publish")
    id("io.spring.dependency-management") version "1.1.4"
    signing
}

group = project.property("projectGroup") as String
version = project.property("projectVersion") as String
val artifact = project.property("projectArtifact") as String
val springBootVersion = project.property("springBootVersion") as String
val mapstructVersion = project.property("mapstructVersion") as String
val reflectionsVersion = project.property("reflectionsVersion") as String
description = "A lightweight Spring Boot 3+ MapStruct Starter – Auto-registers MapStruct mappers without `componentModel = \"spring\"` in your `@Mapper` or `@MapperConfig`. Includes production-ready auto-configuration and customizable mapper scanning via `application.yml`."

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Boot 3.0 min requirement
    }
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
    }
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework.boot:spring-boot")
    api("org.mapstruct:mapstruct:${mapstructVersion}")
    implementation("org.reflections:reflections:${reflectionsVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
}

tasks.matching { it.name == "bootJar" }.configureEach {
    enabled = false
}

tasks.named("jar") {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = artifact
            version = version.toString()
            pom {
                name.set("mapstruct-spring-boot-starter")
                description.set("Spring Boot MapStruct Starter with auto-registration of mappers, zero boilerplate, and customizable scanning via application.yml.")
                url.set("https://github.com/codestackfoundry/spring-boot-starters")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id = "codeswithritesh"
                        name = "Ritesh Chopade"
                        url.set("https://github.com/codeswithritesh")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/codestackfoundry/spring-boot-starters.git")
                    developerConnection.set("scm:git:ssh://github.com/codestackfoundry/spring-boot-starters.git")
                    url.set("https://github.com/codestackfoundry/spring-boot-starters")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

signing {
    val keyId: String? = findProperty("signing.keyId") as String?
        ?: System.getenv("SIGNING_KEY_ID")

    val secretKey: String? = findProperty("signing.secretKeyRingFile")?.let { file(it).readText() }
        ?: System.getenv("SIGNING_KEY") // ASCII-armored key as string

    val password: String? = findProperty("signing.password") as String?
        ?: System.getenv("SIGNING_PASSWORD")

    if (keyId != null && secretKey != null && password != null) {
        useInMemoryPgpKeys(keyId, secretKey, password)
        sign(publishing.publications["mavenJava"])
        logger.lifecycle("Signing enabled for publication")
    } else {
        logger.warn("Signing skipped — keys not provided.")
    }
}