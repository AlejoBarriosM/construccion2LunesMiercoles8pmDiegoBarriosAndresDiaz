package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.UserService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    private UserValidator userValidator;
    private PersonValidator personValidator;
    private UserService service;

    private final String[] OPTIONS = {"Crear", "Modificar", "Eliminar", "Salir"};

    public UserController() {
        this.userValidator = new UserValidator();
        this.personValidator = new PersonValidator();
        this.service = new Service();
    }

    public void menu() {
        try {
            int option = Utils.showMenu("Menú Usuarios", "\nSelecciona una opción:\n", OPTIONS);
            this.options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
        }
    }

    private void options(int option) throws Exception{
        switch (option) {
            case 0: {
                this.createUser();
                return;
            }
            case 1: {
                System.out.println("1");
                return;
            }
            case 2: {
                System.out.println("2");
                return;
            }
            default: {
                System.out.println("Ingrese una opcion valida");
            }
        }
    }

    protected void createUser() throws Exception {

        String[] labels = {"Cedula", "Nombre", "Celular", "Usuario", "Contraseña"};
        Map<String, Object> fieldsToPanel = new HashMap<>(Utils.addFieldsToPanel(labels));
        Map<String, JTextField> fieldsMap = new HashMap<>();
        fieldsMap.putAll((Map<? extends String, ? extends JTextField>) fieldsToPanel.get("fields"));

        if (Utils.showConfirmDialog((JPanel) fieldsToPanel.get("panel"), "Crear Usuario")) {

            PersonDto personDto = new PersonDto();
            UserDto userDto = new UserDto();

            userValidator.validUserName(fieldsMap.get("Usuario").getText());
            userValidator.validPassword(fieldsMap.get("Contraseña").getText());

            userDto.setPasswordUser(fieldsMap.get("Contraseña").getText());
            userDto.setNameUser(fieldsMap.get("Usuario").getText());
            userDto.setRoleUser("admin");

            personValidator.validName(fieldsMap.get("Nombre").getText());

            personDto.setDocumentPerson(personValidator.validDocument(fieldsMap.get("Cedula").getText()));
            personDto.setNamePerson(fieldsMap.get("Nombre").getText());
            personDto.setCellphonePerson(personValidator.validCellphone(fieldsMap.get("Celular").getText()));

            this.service.createAdmin(userDto, personDto);
            Utils.showMessage("El usuario se ha creado con éxito");
        }
    }


}
