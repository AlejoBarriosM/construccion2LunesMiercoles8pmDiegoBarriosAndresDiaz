package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.GuestDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.GuestService;
import app.service.interfaces.PartnerService;
import app.service.interfaces.UserService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class UserController implements ControllerInterface{

    private UserValidator userValidator;
    private PersonValidator personValidator;
    private UserService userService;
    private PartnerService partnerService;
    private GuestService guestService;

    private PersonDto personDto;
    private UserDto userDto;
    private GuestDto guestDto;

    private final String[] OPTIONS = {"Crear", "Modificar", "Eliminar", "Volver"};
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
    public void session() throws Exception {
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
            case 1 -> {
                System.out.println("Opción 1: fut: modificar");
                yield true;
            }
            case 2 -> {
                System.out.println("Opción 2: fut: eliminar");
                yield true;
            }
            case 3 -> {
                System.out.println("Opción 3: fut: salir");
                yield false;
            }
            default -> {
                Utils.showError("Ingrese una opcion valida");
                yield true;
            }
        };
    }

    private void createUser() throws Exception {
        String[] labels = {"Cedula", "Nombre", "Celular", "Usuario", "Contraseña"};
        JPanel panel = new JPanel();
        Map<String, JTextField> fieldsMap = Utils.createPanelWithFields(labels, panel);

        if (Utils.showConfirmDialog(panel, "Crear " + this.role)) {

            userValidator.validUserName(fieldsMap.get("Usuario").getText());
            userValidator.validPassword(fieldsMap.get("Contraseña").getText());

            this.userDto.setPasswordUser(fieldsMap.get("Contraseña").getText());
            this.userDto.setNameUser(fieldsMap.get("Usuario").getText());
            this.userDto.setRoleUser(this.role);

            personValidator.validName(fieldsMap.get("Nombre").getText());

            this.personDto.setDocumentPerson(personValidator.validDocument(fieldsMap.get("Cedula").getText()));
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
