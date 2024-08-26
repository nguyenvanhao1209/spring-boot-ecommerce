package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", source = "role")
    UserDto toDTO(User user);

    @Mapping(target = "role", source = "role")
    User toEntity(UserDto userDto);

    List<UserDto> toDTOs(List<User> users);
    List<User> toEntities(List<UserDto> userDtos);
}
