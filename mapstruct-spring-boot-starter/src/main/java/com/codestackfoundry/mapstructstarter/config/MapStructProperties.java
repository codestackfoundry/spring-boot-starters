package com.codestackfoundry.mapstructstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Configuration properties for MapStruct Spring Boot integration.
 *
 * <p>This class is bound to properties defined under the {@code mapstruct} prefix
 * in {@code application.properties} or {@code application.yml}.
 *
 * <p>These properties control the mapper scanning behavior and error-handling strategy
 * of the MapStruct auto-configuration.
 *
 * <p>Example usage in {@code application.yml}:
 * <pre>{@code
 * mapstruct:
 *   base-packages:
 *     - com.example.demo.mapper
 *     - com.shared.mapper
 *   fail-if-no-mappers: true
 * }</pre>
 *
 * @author Ritesh Chopade(codeswithritesh)
 */
@ConfigurationProperties(prefix = "mapstruct")
public class MapStructProperties {

    /**
     * List of base packages to scan for MapStruct mappers.
     *
     * <p>If not set, the base package is inferred from the main class annotated with
     * {@code @SpringBootApplication}.
     */
    private List<String> basePackages;

    /**
     * Whether to fail application startup if no MapStruct mappers are found in the configured packages.
     *
     * <p>Defaults to {@code false}. Recommended to set {@code true} in production to catch
     * misconfigurations early.
     */
    private boolean failIfNoMappers = false;

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    public boolean isFailIfNoMappers() {
        return failIfNoMappers;
    }

    public void setFailIfNoMappers(boolean failIfNoMappers) {
        this.failIfNoMappers = failIfNoMappers;
    }
}
