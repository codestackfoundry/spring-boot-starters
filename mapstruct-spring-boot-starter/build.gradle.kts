plugins {
    id("java-library")
    id("maven-publish")
    id("io.spring.dependency-management") version "1.1.4"
}

group = project.property("projectGroup") as String
version = project.property("projectVersion") as String
val artifactId = project.property("projectArtifactId") as String

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.1.7")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework.boot:spring-boot")
    api("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.reflections:reflections:0.10.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
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
            artifactId = artifactId
            version = version.toString()
        }
    }
    repositories {
        mavenLocal()
    }
}
