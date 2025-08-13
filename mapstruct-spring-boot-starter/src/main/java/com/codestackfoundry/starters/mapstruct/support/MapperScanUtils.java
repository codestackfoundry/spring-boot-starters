package com.codestackfoundry.starters.mapstruct.support;

import org.mapstruct.Mapper;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for scanning MapStruct mapper interfaces and identifying
 * their corresponding implementation classes.
 *
 * <p>This is used by the MapStruct starter to automatically detect
 * generated mapper implementations that are not managed by Spring
 * (i.e., those without {@code componentModel = "spring"}).
 *
 * <p>Only classes that:
 * <ul>
 *   <li>Implement a {@code @Mapper}-annotated interface</li>
 *   <li>Are not interfaces themselves</li>
 *   <li>Are not annotated with {@code @Component}</li>
 * </ul>
 * will be included in the result.
 *
 * @author Ritesh Chopade(codeswithritesh)
 *
 * @see org.mapstruct.Mapper
 * @see MapperScanResult
 */
public class MapperScanUtils {

    private static final Logger log = LoggerFactory.getLogger(MapperScanUtils.class);


    private MapperScanUtils(){ }

    /**
     * Finds all MapStruct-generated mapper implementation classes within the given base package.
     *
     * <p>The method:
     * <ul>
     *   <li>Scans for interfaces annotated with {@code @Mapper}</li>
     *   <li>Finds their non-interface, non-Spring-managed implementation classes</li>
     * </ul>
     *
     * @param basePackage the base package to scan for mappers and their implementations
     * @return a {@link MapperScanResult} containing the found implementation classes
     */
    public static MapperScanResult findMapperImpls(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> allMappers = reflections.getTypesAnnotatedWith(Mapper.class);
        Set<Class<?>> mapperImpls = new HashSet<>();
        if (log.isDebugEnabled()) {
            log.debug("Discovered @Mapper interfaces in '{}': {}", basePackage,
                    allMappers.stream().map(Class::getSimpleName).toList());
        }
        for (Class<?> mapperInterface : allMappers) {
            @SuppressWarnings("unchecked")
            Set<Class<?>> subTypes = reflections.getSubTypesOf((Class<Object>) mapperInterface);

            for (Class<?> implClass : subTypes) {
                if (!implClass.isInterface() && !implClass.isAnnotationPresent(Component.class)) {
                    mapperImpls.add(implClass);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Generated MapStruct implementation classes found: {}",
                    mapperImpls.stream().map(Class::getSimpleName).toList());
        }

        return new MapperScanResult(!allMappers.isEmpty(), mapperImpls);
    }
}
