package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.*;
import app.service.Service;
import app.service.interfaces.GuestService;
import app.service.interfaces.PartnerService;
import app.service.interfaces.UserService;

import javax.swing.*;
import java.util.Map;

public class UserController implements ControllerInterface{

    private final UserValidator userValidator;
    private final PersonValidator personValidator;
    private final UserService userService;
    private final PartnerService partnerService;
    private final GuestService guestService;


    private final PersonDto personDto;
    private final UserDto userDto;
    private final GuestDto guestDto;

    private final String[] OPTIONS = {"Crear", "Volver"};//, "Modificar", "Eliminar", "Volver"};
    private String role;

    public UserController() {
        this.userValidator = new UserValidator();
        this.personValidator = new PersonValidator();
        this.userDto = new UserDto();
        this.personDto = new PersonDto();
        this.guestDto = new GuestDto();
        this.userService = new Service();
        this.partnerService = new Service();
        this.guestService = new Service();
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void session()  {
        boolean session = true;
        while (session) {
            session = menuUser();
        }
    }

    private boolean menuUser() {
        try {
            int option = Utils.showMenu("Menú " + this.role, "\nSelecciona una opción:\n", OPTIONS);
            return options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
        }
        return false;
    }

    private boolean options(int option) throws Exception{
        return switch (option) {
            case 0 -> {
                this.createUser();
                yield true;
            }
            case 1 -> false;
            default -> {
                Utils.showError("Ingrese una opción valida");
                yield true;
            }
        };
    }

    private void createUser() throws Exception {
        String[] labels = {"Cédula", "Nombre", "Celular", "Usuario", "Contraseña"};
        JPanel panel = new JPanel();
        Map<String, JTextField> fieldsMap = Utils.createPanelWithFields(labels, panel);

        if (Utils.showConfirmDialog(panel, "Crear " + this.role)) {

            userValidator.validUserName(fieldsMap.get("Usuario").getText());
            userValidator.validPassword(fieldsMap.get("Contraseña").getText());

            this.userDto.setPasswordUser(fieldsMap.get("Contraseña").getText());
            this.userDto.setNameUser(fieldsMap.get("Usuario").getText());
            this.userDto.setRoleUser(this.role);

            personValidator.validName(fieldsMap.get("Nombre").getText());

            this.personDto.setDocumentPerson(personValidator.validDocument(fieldsMap.get("Cédula").getText()));
            this.personDto.setNamePerson(fieldsMap.get("Nombre").getText());
            this.personDto.setCellphonePerson(personValidator.validCellphone(fieldsMap.get("Celular").getText()));

            this.createByRole();
            Utils.showMessage("El %s se ha creado con éxito".formatted(this.role));
        }
    }

    private void createByRole() throws Exception{
        switch (this.role){
            case "administrador": {
                this.userService.createAdmin(this.userDto, this.personDto);
                break;
            }
            case "socio": {
                this.partnerService.createPartner(this.userDto, this.personDto);
                break;
            }
            case "invitado": {
                this.guestService.createGuest(this.guestDto, this.userDto, this.personDto);
                break;
            }
            default: {
                break;
            }
        }
    }

}
