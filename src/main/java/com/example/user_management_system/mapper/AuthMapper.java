package com.example.user_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.user_management_system.dto.AuthDto;
import com.example.user_management_system.entity.Auth;

@Mapper
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "id", ignore = true)
    Auth toEntity(AuthDto.RegisterDto authDto);
}
