package app.dto.mapper;

import app.dto.PersonDto;
import app.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    PersonDto ToPersonDto(Person person);
    Person DtoToPerson(PersonDto personDto);
}
