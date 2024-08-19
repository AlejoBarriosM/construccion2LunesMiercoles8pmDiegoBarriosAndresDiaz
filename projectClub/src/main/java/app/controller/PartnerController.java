package app.controller;

import app.controller.validator.PartnerValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.PartnerService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PartnerController {


    private PartnerService service;
    private PartnerValidator parnerValidator;
    private UserController userController;

    private final String[] OPTIONS = {"Crear", "Modificar", "Eliminar", "Salir"};

    public PartnerController() {
        this.parnerValidator = new PartnerValidator();
        this.userController = new UserController();
        this.service = new Service();
    }

    public void menu() {
        try {
            int option = Utils.showMenu("Menú Socio", "\nSelecciona una opción:\n", OPTIONS);
            this.options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
        }
    }

    private void options(int option) throws Exception{
        switch (option) {
            case 0: {
                this.createPartner();
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

    private void createPartner() throws Exception {

        userController.createUser();

        String[] labels = {"Fondos", "Tipo", "Fecha"};
        Map<String, Object> fieldsToPanel = new HashMap<>(Utils.addFieldsToPanel(labels));
        Map<String, JTextField> fieldsMap = new HashMap<>();
        fieldsMap.putAll((Map<? extends String, ? extends JTextField>) fieldsToPanel.get("fields"));

        if (Utils.showConfirmDialog((JPanel) fieldsToPanel.get("panel"), "Crear Socio")) {
            PersonDto personDto = new PersonDto();
            UserDto userDto = new UserDto();

//            parnerValidator.validAmount(fieldsMap.get("Fondos").getText());
//            userValidator.validPassword(fieldsMap.get("Contraseña").getText());
//
//            userDto.setPasswordUser(fieldsMap.get("Contraseña").getText());
//            userDto.setNameUser(fieldsMap.get("Usuario").getText());
//            userDto.setRoleUser("admin");
//
//            personValidator.validName(fieldsMap.get("Nombre").getText());
//
//            personDto.setDocumentPerson(personValidator.validDocument(fieldsMap.get("Cedula").getText()));
//            personDto.setNamePerson(fieldsMap.get("Nombre").getText());
//            personDto.setCellphonePerson(personValidator.validCellphone(fieldsMap.get("Celular").getText()));
//
//            this.service.createAdmin(userDto, personDto);
//            Utils.showMessage("El usuario se ha creado con éxito");
        }
    }
}
