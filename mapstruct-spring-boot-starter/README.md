# MapStruct Spring Boot Starter

A lightweight Spring Boot 3+ MapStruct Starter – Auto-registers MapStruct mappers without `componentModel = "spring"` in your `@Mapper` or `@MapperConfig`. Includes production-ready auto-configuration and customizable mapper scanning via `application.yml`.

[![Maven Central](https://img.shields.io/maven-central/v/com.codestackfoundry.starters/mapstruct-spring-boot-starter)](https://central.sonatype.com/artifact/com.codestackfoundry.starters/mapstruct-spring-boot-starter)
[![Java 17+](https://img.shields.io/badge/java-17+-blue.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

---

## ✨ Features

- ✅ Auto-detects MapStruct-generated implementation classes
- ✅ Registers mapper beans automatically with Spring context
- ✅ No need to annotate mappers with `@Component` or use `componentModel = "spring"`
- ✅ Supports YAML and properties-based configuration
- ✅ Fails fast (optional) when no mappers are found
- ✅ Works with both Java and Kotlin (via kapt)

---

## 🔥 Before vs. After

**❌ Before (manual config):**
```java
// Mapper with Spring component model
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
```
Or
```java
@Configuration
public class MapperConfig {
    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
```

**✅ After (with starter):**
```java
// No @ComponentModel, no config required
@Mapper
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}

// Automatically registered by the starter 🎉
```

---

## 🚀 Quick Start

### 1. Add the dependency

#### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.codestackfoundry.starters:mapstruct-spring-boot-starter:1.0.1")

    // Required explicitly
    kapt("org.mapstruct:mapstruct-processor:1.6.3")
}
```

> ✅ You must also apply the kapt plugin if using Kotlin:
```kotlin
plugins {
    kotlin("kapt")
}
```

#### Gradle (Groovy DSL)

```groovy
dependencies {
    implementation 'com.codestackfoundry.starters:mapstruct-spring-boot-starter:1.0.1'

    // Required explicitly
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3'
}
```

#### Maven

```xml
<dependencies>
    <dependency>
        <groupId>com.codestackfoundry.starters</groupId>
        <artifactId>mapstruct-spring-boot-starter</artifactId>
        <version>1.0.1</version>
    </dependency>

    <!-- Required explicitly -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.6.3</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

---

### 2. Define your mappers

```java
package com.example.demo.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto toDto(User entity);
}
```

> ⚠️ Do **not** set `componentModel = "spring"` — the starter takes care of it.

---

### 3. Configure base packages (optional)

By default, the starter will try to infer the base package from your `@SpringBootApplication` class.

To override it, specify in `application.yml`:

```yaml
mapstruct:
  base-packages:
    - com.example.demo.mapper
    - com.shared.mappers
  fail-if-no-mappers: true
```

---

## ⚙️ Configuration Options

| Property                     | Description                                                                      | Default                      |
|-----------------------------|----------------------------------------------------------------------------------|------------------------------|
| `mapstruct.base-packages`   | List of packages to scan for `@Mapper` interfaces                                | Inferred from the main class package if not explicitly set.     |
| `mapstruct.fail-if-no-mappers` | If true, throws an exception during startup when no MapStruct mappers are found. | `false`                      |

---

## 📦 How It Works

1. Scans base packages for interfaces annotated with `@Mapper`
2. Uses [Reflections](https://github.com/ronmamo/reflections) to detect their generated implementations
3. Registers the implementation class as a Spring bean **only if** it’s not already annotated with `@Component`

---

## 📁 Folder Structure

```
mapstruct-spring-boot-starter/
├── autoconfig/
│   ├── MapStructAutoConfiguration.java
│   └── MapStructImplRegistrar.java
├── config/
│   └── MapStructProperties.java
├── internal/
│   └── MapStructBasePackageResolver.java
└── support/
    ├── MapperScanUtils.java
    └── MapperScanResult.java
```

---

## ❗ Why Not Just Use `componentModel = "spring"`?

Because:
- It's intrusive in mapper code
- Makes portability between non-Spring modules harder
- With this starter, mappers are clean POJOs and Spring handles wiring via auto-configuration

---

## 📊 Why Use This Starter?

- No more repetitive `componentModel = "spring"`.
- No bean boilerplate.
- Works out of the box with Spring Boot auto-configuration.
- Cleaner mappers, faster setup.

---

## 🧪 Example Project

See the [example-app](./example-app/) for a working Spring Boot demo with:
- MapStruct mappers
- JPA + H2
- Auto-registered mappers via this starter

---

## ✅ Compatibility

- Spring Boot `3.1+`
- Java `17+`
- MapStruct `1.6+`
- Kotlin via `kapt` also supported

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👤 Author

Created and maintained by [Ritesh Chopade](https://github.com/codeswithritesh)

---