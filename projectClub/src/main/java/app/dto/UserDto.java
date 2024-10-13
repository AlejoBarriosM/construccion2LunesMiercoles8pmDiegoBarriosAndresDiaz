package app.dto;

import app.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private long idUser;
    private PersonDto idPerson;
    private String userName;
    private String passwordUser;
    private Role roleUser;

}

