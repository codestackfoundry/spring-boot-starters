# 🌱 Spring Boot Starters by CodestackFoundry

A collection of high-quality, production-ready Spring Boot starters developed and maintained by the [CodestackFoundry](https://github.com/codestackfoundry) team.  
Each starter is designed to simplify integration with commonly used libraries and patterns, with minimal configuration and sensible defaults.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/codestackfoundry/spring-boot-starters/blob/main/LICENSE)
[![Build](https://github.com/codestackfoundry/spring-boot-starters/actions/workflows/gradle.yml/badge.svg)](https://github.com/codestackfoundry/spring-boot-starters/actions)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/codestackfoundry/spring-boot-starters/blob/main/CONTRIBUTING.md)

---

## 📦 Available Starters

| Starter Name                     | Description                                                                                                                                                   | Docs / Usage                  |
|----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------|
| [`mapstruct-spring-boot-starter`](./mapstruct-spring-boot-starter) | Seamless Spring Boot 3+ MapStruct Starter – Auto-registers mappers without `componentModel = "spring"` or `@Component`, with production-ready auto-configuration. | [Usage](./mapstruct-spring-boot-starter/README.md) |

> ⚙️ More starters coming soon.

---

## 🔧 Build Instructions

To build the entire multi-module project:

```bash
./gradlew build
```

To run an individual example app:

```bash
./gradlew :mapstruct-spring-boot-starter:example-app:bootRun
```

---

## 🤝 Contributing

We welcome contributions!

1. Check open issues or suggest a new starter idea
2. Follow the structure of existing starters (starter + example-app)
3. Submit a pull request with proper documentation and tests

See [CONTRIBUTING.md](./CONTRIBUTING.md) for guidelines.

---

## 📅 Roadmap

- ✅ `mapstruct-spring-boot-starter` – Auto-registers mappers via YAML config

---

## 📜 License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

---

## 🌟 Why Another Starter Library?

While Spring Boot provides many starters out of the box, this collection focuses on:

- Missing but much-needed integrations
- Plug-and-play experience with no annotations or boilerplate
- Clean YAML-driven configuration
- Real-world examples included with every starter

---

Made with ❤️ by [CodestackFoundry](https://github.com/codestackfoundry)
