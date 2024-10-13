package app.dto;

import app.entity.Role;
import lombok.Data;

@Data
public class UpdateUserDto {
    Long documentPerson;
    String namePerson;
    Long cellphonePerson;
    String userName;
    String password;
    Role roleUser;
}
