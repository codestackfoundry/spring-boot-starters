package com.codestackfoundry.starters.mapstruct.autoconfig;

import com.codestackfoundry.starters.mapstruct.config.MapStructProperties;
import com.codestackfoundry.starters.mapstruct.internal.MapStructBasePackageResolver;
import com.codestackfoundry.starters.mapstruct.support.MapperScanResult;
import com.codestackfoundry.starters.mapstruct.support.MapperScanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.Introspector;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Registers MapStruct mapper implementation classes as Spring beans during application startup.
 *
 * <p>This class is imported by {@link com.codestackfoundry.starters.mapstruct.autoconfig.MapStructAutoConfiguration}
 * and triggered automatically via {@code @EnableAutoConfiguration} when {@link org.mapstruct.Mapper}
 * is present on the classpath.
 *
 * <p>It resolves mapper base packages either from application configuration properties
 * or by inferring from the main class annotated with {@code @SpringBootApplication}.
 * Then it scans for generated MapStruct implementation classes (those not annotated with {@code @Component})
 * and registers them as Spring beans, avoiding duplicates.
 *
 * <p>If no mappers are found and {@code mapstruct.fail-if-no-mappers=true}, it throws an error to fail fast.
 *
 * @author Ritesh Chopade(codeswithritesh)
 *
 * @see org.mapstruct.Mapper
 * @see com.codestackfoundry.starters.mapstruct.config.MapStructProperties
 * @see com.codestackfoundry.starters.mapstruct.support.MapperScanUtils
 */
public class MapStructImplRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(MapStructImplRegistrar.class);
    private Environment environment;

    /**
     * Public no-argument constructor.
     *
     * Needed for Spring Boot's auto-configuration mechanism to instantiate
     * this registrar class when loading MapStruct mappers dynamically.
     */
    public MapStructImplRegistrar() { }
    /**
     * Scans for MapStruct mapper implementations and registers them as Spring beans.
     *
     * <p>Uses {@link com.codestackfoundry.starters.mapstruct.config.MapStructProperties} to resolve base packages,
     * and delegates scanning logic to {@link com.codestackfoundry.starters.mapstruct.support.MapperScanUtils}.
     *
     * @param metadata the metadata of the class importing this configuration
     * @param registry the bean definition registry used to register mapper beans
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        MapStructBasePackageResolver resolver = new MapStructBasePackageResolver();

        MapStructProperties props = Binder.get(environment)
                .bind("mapstruct", Bindable.of(MapStructProperties.class)).orElse(new MapStructProperties());

        List<String> basePackages = resolver.resolveBasePackages(props);
        Set<Class<?>> mapperImpls = new HashSet<>();
        boolean hasAnyMappers = false;

        for (String basePackage : basePackages) {
            MapperScanResult result = MapperScanUtils.findMapperImpls(basePackage);
            hasAnyMappers |= result.hasAnyMappers();
            mapperImpls.addAll(result.implementationClasses());

            if (log.isDebugEnabled()) {
                result.implementationClasses().forEach(mapper ->
                        log.debug("Found mapper implementation: {}", mapper.getName())
                );
            }
        }

        if (mapperImpls.isEmpty()) {
            if (props.isFailIfNoMappers() && !hasAnyMappers) {
                throw new IllegalStateException("No MapStruct mappers found in any base package: " + basePackages);
            } else {
                log.warn("""
                     No MapStruct mappers or @Mapper interfaces found in base packages: {}
                    
                    Tip: If you expected mappers to be registered, check the 'mapstruct.base-packages' property
                    or enable 'fail-if-no-mappers: true' to catch this during startup.
                    """, basePackages);
            }
        }

        for (Class<?> implClass : mapperImpls) {
            String beanName = Introspector.decapitalize(implClass.getSimpleName());

            if (registry.containsBeanDefinition(beanName)) {
                log.warn("Skipping registration of duplicate bean: {}", beanName);
                continue;
            }

            GenericBeanDefinition def = new GenericBeanDefinition();
            def.setBeanClass(implClass);
            def.setAutowireCandidate(true);
            def.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);

            registry.registerBeanDefinition(beanName, def);
            log.debug("Registered mapper : {}", beanName);
        }

        if (!mapperImpls.isEmpty() && log.isInfoEnabled()) {
            log.info("Total {} MapStruct mappers registered from base packages: {}",
                    mapperImpls.size(),
                    basePackages);
        }
    }

    /**
     * Injects the Spring {@link Environment} used to bind external configuration properties.
     *
     * @param environment the current application environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
