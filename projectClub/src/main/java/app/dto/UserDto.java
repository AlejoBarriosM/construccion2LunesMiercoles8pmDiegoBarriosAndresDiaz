package app.dto;

public class UserDto {

    private long idUser;
    private PersonDto idPerson;
    private String nameUser;
    private String passwordUser;
    private String roleUser;

    public UserDto() {
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public PersonDto getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(PersonDto idPerson) {
        this.idPerson = idPerson;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }
}
