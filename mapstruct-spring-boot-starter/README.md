# MapStruct Spring Boot Starter

A lightweight Spring Boot 3+ starter that **auto-registers MapStruct mappers** without requiring `componentModel = "spring"` in your `@Mapper` or `@MapperConfig`.

---

## ✨ Features

- ✅ Auto-detects MapStruct-generated implementation classes
- ✅ Registers mapper beans automatically with Spring context
- ✅ No need to annotate mappers with `@Component` or use `componentModel = "spring"`
- ✅ Supports YAML and properties-based configuration
- ✅ Fails fast (optional) when no mappers are found
- ✅ Works with both Java and Kotlin (via kapt)

---

## 🚀 Quick Start

### 1. Add the dependency

#### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.codestackfoundry:mapstruct-spring-boot-starter:0.0.1-SNAPSHOT")

    // Required explicitly
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
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
    implementation 'com.codestackfoundry:mapstruct-spring-boot-starter:0.0.1-SNAPSHOT'

    // Required explicitly
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
}
```

#### Maven

```xml
<dependencies>
    <dependency>
        <groupId>com.codestackfoundry</groupId>
        <artifactId>mapstruct-spring-boot-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>

    <!-- Required explicitly -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.5.5.Final</version>
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

## 🧪 Example Project

See the [example-app](../example-app/) for a working Spring Boot demo with:
- MapStruct mappers
- JPA + H2
- Auto-registered mappers via this starter

---

## ✅ Compatibility

- Spring Boot `3.0+`
- Java `17+`
- MapStruct `1.5+`
- Kotlin via `kapt` also supported

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👤 Author

Created and maintained by [Ritesh Chopade](https://github.com/codeswithritesh)

---