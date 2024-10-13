package app.dto.mapper;

import app.dto.NewPersonDto;
import app.dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewPersonMapper {
    NewPersonMapper INSTANCE = Mappers.getMapper(NewPersonMapper.class);
    PersonDto toNewPerson(NewPersonDto newPersonDto);
}
