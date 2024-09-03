package app.service.interfaces;

import app.dto.PersonDto;
import app.dto.UserDto;

public interface UserService {
    void createAdmin(UserDto userDto, PersonDto personDto) throws Exception;
}

