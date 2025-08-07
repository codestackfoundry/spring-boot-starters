package com.codestackfoundry.starters.mapstruct.autoconfig;

import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

/**
 * Auto-configuration class for registering MapStruct mappers in a Spring Boot application.
 *
 * <p>This configuration is activated automatically when the MapStruct {@link Mapper} annotation
 * is present on the classpath. It delegates to {@link MapStructImplRegistrar} to scan for
 * generated implementation classes of MapStruct mappers and register them as Spring beans
 * if they are not already annotated with {@code @Component}.
 *
 * @author Ritesh Chopade(codeswithritesh)
 *
 * @see Mapper
 * @see MapStructImplRegistrar
 *
 *
 */
@AutoConfiguration
@ConditionalOnClass(Mapper.class)
@Import(MapStructImplRegistrar.class)
public class MapStructAutoConfiguration {
}
