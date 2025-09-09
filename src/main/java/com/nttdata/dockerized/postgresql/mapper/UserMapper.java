package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.UserDto;
import com.nttdata.dockerized.postgresql.model.dto.UserSaveRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.UserSaveResponseDto;
import com.nttdata.dockerized.postgresql.model.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public UserDto map(User user);

    public List<UserDto> map(List<User> users);

    public User toEntity(UserSaveRequestDto userSaveRequestDto);

    public UserSaveResponseDto toUserSaveResponseDto(User user);

    @AfterMapping
    default void setRemainingValues(User user, @MappingTarget UserDto userDto) {
        userDto.setStatus(Boolean.TRUE.equals(user.getActive()) ? "Active" : "Inactive");
    }

    @AfterMapping
    default void setFechaOnSave(User user, @MappingTarget UserSaveResponseDto resp) {
        if (user.getFechaRegistro() != null) {
            resp.setFechaRegistro(user.getFechaRegistro().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }
}
