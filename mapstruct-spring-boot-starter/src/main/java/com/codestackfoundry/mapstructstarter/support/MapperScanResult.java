package com.codestackfoundry.mapstructstarter.support;

import java.util.Set;

/**
 * Holds the result of scanning a base package for MapStruct mappers.
 *
 * <p>This model captures:
 * <ul>
 *     <li>whether any mappers (interfaces annotated with {@code @Mapper}) were found</li>
 *     <li>a set of their corresponding implementation classes that are not annotated with {@code @Component}</li>
 * </ul>
 *
 * <p>This separation helps in distinguishing mappers already registered as Spring beans
 * from those that need to be registered manually.
 *
 * @param hasAnyMappers         Whether any MapStruct mappers (interfaces annotated with {@code @Mapper}) were found.
 * @param implementationClasses Implementation classes of the mappers that are not already Spring-managed components.
 *
 * @author Ritesh Chopade(codeswithritesh)
 *
 * @see org.mapstruct.Mapper
 */
public record MapperScanResult(boolean hasAnyMappers,
                               Set<Class<?>> implementationClasses) {

}
