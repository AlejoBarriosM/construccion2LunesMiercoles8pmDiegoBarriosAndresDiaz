package app.dto;

import app.entity.Role;
import lombok.Data;

@Data
public class NewUserDto {

    NewPersonDto idPerson;
    String userName;
    String passwordUser;
    Role roleUser;

}
