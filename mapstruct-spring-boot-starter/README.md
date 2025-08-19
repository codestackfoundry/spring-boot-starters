# MapStruct Spring Boot Starter

A lightweight Spring Boot 3+ MapStruct Starter – Auto-registers MapStruct mappers without `componentModel = "spring"` in your `@Mapper` or `@MapperConfig`. Includes production-ready auto-configuration and customizable mapper scanning via `application.yml`.

[![Maven Central](https://img.shields.io/maven-central/v/com.codestackfoundry.starters/mapstruct-spring-boot-starter)](https://central.sonatype.com/artifact/com.codestackfoundry.starters/mapstruct-spring-boot-starter)
[![Java 17+](https://img.shields.io/badge/java-17+-blue.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docs](https://img.shields.io/badge/docs-available-brightgreen.svg)](https://codestackfoundry.com/docs/mapstruct-spring-boot-starter.html)
[![GitHub Discussions](https://img.shields.io/badge/discussions-join-blue.svg)](https://github.com/codestackfoundry/spring-boot-starters/discussions)


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
//Mapper Config for centralize configuration
@MapperConfig(componentModel = "spring")
public interface CentralMapperConfig {
}

//Add config in every mapper
@Mapper(config = CentralMapperConfig.class)
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

#### Gradle

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
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>17</source> <!-- or your project’s Java version -->
                <target>17</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>1.6.3</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>

```
#### MapStruct Processor Requirement

MapStruct generates the mapper implementations at compile time using an annotation processor.
To enable this, you must add ``` mapstruct-processor ``` to your build configuration.

⚠️ Without ```mapstruct-processor```, no mapper implementations will be generated.
The starter itself does not provide this dependency.
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

To override it, 
#### Using `application.yml`

```yaml
mapstruct:
  base-packages:
    - com.example.demo.mapper
    - com.shared.mappers
  fail-if-no-mappers: true
```

#### Using `application.properties`

```properties
mapstruct.base-packages[0]=com.example.demo.mapper
mapstruct.base-packages[1]=com.shared.mappers
mapstruct.fail-if-no-mappers=true
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
2. Detect their generated implementations
3. Registers the implementation class as a Spring bean **only if** it’s not already annotated with `@Component` or not marked as `componentModel = "spring"`

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

## 📖 Documentation

Full documentation is available here:  
👉 [MapStruct Spring Boot Starter Docs](https://codestackfoundry.com/docs/mapstruct-spring-boot-starter.html)

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

This project is licensed under the [MIT License](../LICENSE).

---

## 👤 Author

Created and maintained by [Ritesh Chopade](https://github.com/codeswithritesh)

---