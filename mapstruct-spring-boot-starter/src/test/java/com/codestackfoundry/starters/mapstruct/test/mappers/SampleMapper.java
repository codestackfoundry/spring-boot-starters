package com.codestackfoundry.starters.mapstruct.test.mappers;

import org.mapstruct.Mapper;

@Mapper
public interface SampleMapper {
    Target map(Source source);

    record Source(String name) {}
    record Target(String name) {}
}