package com.codestackfoundry.mapstructstarter.internal;

import com.codestackfoundry.mapstructstarter.config.MapStructProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Resolves base packages to scan for MapStruct mappers.
 *
 * <p>It supports the following resolution order:
 * <ol>
 *     <li>Explicit configuration via {@code mapstruct.base-packages} in {@code application.properties} or {@code application.yml}</li>
 *     <li>Inferred package of the main application class annotated with {@code @SpringBootApplication}</li>
 *     <li>Throws an error if no valid base package is found</li>
 * </ol>
 *
 * <p>This utility helps decouple mapper scanning logic from direct application class references,
 * making it more flexible for library use.
 *
 * @author Ritesh Chopade(codeswithritesh)
 *
 * @see com.codestackfoundry.mapstructstarter.config.MapStructProperties
 */
public class MapStructBasePackageResolver {

    private static final Logger log = LoggerFactory.getLogger(MapStructBasePackageResolver.class);

    /**
     * Resolves the base packages to scan for MapStruct mappers.
     *
     * <p>Resolution priority:
     * <ol>
     *     <li>Returns the configured {@code mapstruct.base-packages} if present</li>
     *     <li>Otherwise, attempts to infer it from the package of the class containing {@code main()} method annotated with {@code @SpringBootApplication}</li>
     *     <li>Throws {@link IllegalStateException} if none are found</li>
     * </ol>
     *
     * @param props configuration properties bound from the application context
     * @return list of base packages to scan
     * @throws IllegalStateException if base package cannot be determined
     */
    public List<String> resolveBasePackages(MapStructProperties props) {
        List<String> basePackages = props.getBasePackages();
        if( basePackages != null && !basePackages.isEmpty() ) {
            log.debug("Using configured mapstruct.base-packages: {}", basePackages);
            return basePackages;
        }

        // Attempt to infer from main application class
        String inferred = inferFromMainApplication();
        if (inferred != null && !inferred.isBlank()) {
            log.info("Falling back to inferred base package from main class: {}", inferred);
            return List.of(inferred);
        }

        // 3. no valid package found
        log.error("Could not determine base package via configuration or inference from main application. Aborting mapper scan.");
        throw new IllegalStateException("""
            Unable to determine base package for MapStruct mapper scanning.
            
            Please do one of the following:
            
             Option 1: Set the property 'mapstruct.base-packages' in your application.yml or application.properties
                Example:
                mapstruct.base-packages:
                  - com.example.demo.mapper
                  - com.shared.mapper
            
             Option 2: Ensure your main class is annotated with @SpringBootApplication 
               and located in the base package of your mappers.
            """);
    }

    /**
     * Attempts to infer the package of the class containing the {@code main()} method
     * and annotated with {@code @SpringBootApplication}.
     *
     * @return the inferred package name, or {@code null} if not resolvable
     */
    private String inferFromMainApplication() {
        try {
            for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                if ("main".equals(element.getMethodName())) {
                    Class<?> mainClass = Class.forName(element.getClassName());
                    if (mainClass.isAnnotationPresent(SpringBootApplication.class)) {
                        return mainClass.getPackageName();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to infer base package from main application class: {}", e.getMessage());
        }
        return null;
    }
}