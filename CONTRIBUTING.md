# 🤝 Contributing to Spring Boot Starters by CodestackFoundry

First off, thank you for taking the time to contribute! 🎉  
Here are a few guidelines to help you contribute effectively.

---

## 📦 Repository Structure

This is a **monorepo** that hosts multiple independent Spring Boot starters.  
Each starter is a standalone module with its own example app and build configuration.

```
spring-boot-starters/
├── settings.gradle.kts
├── build.gradle.kts
├── mapstruct-spring-boot-starter/
│   ├── build.gradle.kts
│   ├── src/
│   └── example-app/
│       ├── build.gradle.kts
│       └── src/
```

---

## 🛠️ How to Contribute

### 1. Fork the Repository

Click the **Fork** button at the top right of this page.

### 2. Clone your fork

```bash
git clone https://github.com/<your-username>/spring-boot-starters.git
cd spring-boot-starters
```

### 3. Create a new branch
Use a descriptive branch name following Conventional Commit style:

```bash
git checkout -b feat/<starter-name>-<feature>
```

Example:  
```bash
git checkout -b feat-mapstructstarter-custom-mapper-filter
```
#### or
```bash
git checkout -b feat/mapstructstarter/custom-mapper-filter
```

### 4. Branch Naming Convention

Please use the following prefixes to name your branches based on the type of change:

| Prefix       | Meaning                                      | Example Branch Name                           |
|--------------|----------------------------------------------|------------------------------------------------|
| `feat/`      | Adding a new feature                         | `feat/mapstruct-custom-mapper-filter`         |
| `fix/`       | Bug fix                                      | `fix/redis-starter-missing-cache-bean`        |
| `docs/`      | Documentation-only changes                   | `docs/mapstruct-update-readme`                |
| `chore/`     | Build process, dependencies, cleanup, etc.   | `chore/gradle-upgrade`                        |
| `test/`      | Adding or improving tests                    | `test/redis-starter-integration-tests`        |
| `refactor/`  | Refactoring code without changing behavior   | `refactor/mapstruct-internal-scanner`         |

These conventions align with [Conventional Commits](https://www.conventionalcommits.org/) and help automate changelogs and releases.

---

## ✍️ Making Changes

- Use Java 17+
- Use Gradle with Kotlin DSL (`.kts`)
- Keep formatting consistent with the existing codebase
- If you're modifying a starter:
  - Add or update tests
  - Update the `README.md` inside that starter
- If adding a new starter, follow the structure:
  ```
  your-starter-name/
  ├── build.gradle.kts
  └── example-app/
      └── build.gradle.kts
  ```

---

## ✅ Commit Guidelines

Use **Conventional Commit** format:

```
feat(mapstruct-starter): add support for scanning interfaces in additional packages
fix(redis-starter): resolve TTL not applied on empty keys
docs(mapstruct): improve README usage section
```

---

## 🧪 Running Tests & Example Apps

To run all tests:
```bash
./gradlew test
```

To run a specific example app:
```bash
./gradlew :mapstruct-spring-boot-starter:example-app:bootRun
```

---

## 🚀 Submitting a Pull Request

1. Push your branch:  
   `git push origin feat/<starter-name>-<feature>`
2. Open a PR on the main repo
3. Add a clear description with context, screenshots (if applicable), and usage

---

## 🧑‍💻 Code of Conduct

We expect contributors to follow the [Contributor Covenant Code of Conduct](https://www.contributor-covenant.org/version/2/1/code_of_conduct/).

---

Thanks for helping make these Spring Boot starters better!  
– The CodestackFoundry Team
