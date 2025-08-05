package com.codestackfoundry.example.mapstruct.mapper2;

import com.codestackfoundry.example.mapstruct.dto.UserDTO;
import com.codestackfoundry.example.mapstruct.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface MyUserMapper {
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "name", target = "name"),
                    @Mapping(source = "email", target = "email"),
            }
    )
    UserDTO toDto(User user);

    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "name", target = "name"),
                    @Mapping(source = "email", target = "email"),
            }
    )
    User toEntity(UserDTO dto);
}