package com.codestackfoundry.mapstructstarter.autoconfig;

import com.codestackfoundry.mapstructstarter.test.mappers.SampleMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.FilteredClassLoader;

import static org.assertj.core.api.Assertions.assertThat;

class MapStructAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(AutoConfigurations.of(MapStructAutoConfiguration.class));

    @Test
    void autoConfigIsLoadedWhenMapperClassIsPresent() {
        contextRunner
                .withPropertyValues("mapstruct.base-packages=com.codestackfoundry.mapstructstarter.test.mappers")
                .run(context -> {
            assertThat(context).hasSingleBean(MapStructAutoConfiguration.class);
                    assertThat(context).hasBean("sampleMapperImpl"); // this is correct bean name
                    assertThat(context).hasSingleBean(SampleMapper.class); // even better
        });
    }

    @Test
    void autoConfigIsNotLoadedWhenMapperClassIsMissing() {
        ApplicationContextRunner noMapperContextRunner =
                new ApplicationContextRunner()
                        .withClassLoader(new FilteredClassLoader(Mapper.class)) // simulate missing class
                        .withConfiguration(AutoConfigurations.of(MapStructAutoConfiguration.class));

        noMapperContextRunner.run(context ->
                assertThat(context).doesNotHaveBean(MapStructAutoConfiguration.class));
    }

    @Test
    void shouldRegisterMapperBeanWhenBasePackageIsSet() {
        contextRunner
                .withPropertyValues("mapstruct.base-packages=com.codestackfoundry.mapstructstarter.test.mappers")
                //.withUserConfiguration(SampleMapperTestConfig.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(SampleMapper.class);
                });
    }

}

