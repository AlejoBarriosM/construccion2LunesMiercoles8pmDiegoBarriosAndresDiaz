package app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class UserDto {

    private long idUser;
    private PersonDto idPerson;
    private String userName;
    private String passwordUser;
    private String roleUser;



}

