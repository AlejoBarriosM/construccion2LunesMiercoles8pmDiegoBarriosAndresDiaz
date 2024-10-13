package app.dto.mapper;

import app.dto.NewUserDto;
import app.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewUserMapper {
    NewUserMapper INSTANCE = Mappers.getMapper(NewUserMapper.class);
    UserDto toUserDto(NewUserDto newUserDto);
}
