package app.helpers;

import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Person;
import app.model.User;

public class Helper {

    public static PersonDto parse(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setIdPerson(person.getIdPerson());
        personDto.setDocumentPerson(person.getDocumentPerson());
        personDto.setNamePerson(person.getNamePerson());
        personDto.setCellphonePerson(person.getCellphonePerson());
        return personDto;
    }

    public static Person parse(PersonDto personDto) {
        Person person = new Person();
        person.setIdPerson(personDto.getIdPerson());
        person.setDocumentPerson(personDto.getDocumentPerson());
        person.setNamePerson(personDto.getNamePerson());
        person.setCellphonePerson(personDto.getCellphonePerson());
        return person;
    }

    public static UserDto parse(User user) {
        UserDto userDto = new UserDto();
        userDto.setIdUser(user.getIdUser());
        userDto.setPasswordUser(user.getPasswordUser());
        userDto.setIdPerson(parse(user.getIdPerson()));
        userDto.setRoleUser(user.getRoleUser());
        userDto.setNameUser(user.getNameUser());
        return userDto;
    }

    public static User parse(UserDto userDto) {
        User user = new User();
        user.setIdUser(userDto.getIdUser());
        user.setPasswordUser(userDto.getPasswordUser());
        user.setIdPerson(parse(userDto.getIdPerson()));
        user.setRoleUser(userDto.getRoleUser());
        user.setNameUser(userDto.getNameUser());
        return user;
    }
    
}
