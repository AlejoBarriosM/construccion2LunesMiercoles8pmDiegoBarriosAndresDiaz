package app.controller;

import app.controller.validator.PartnerValidator;
import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.PartnerService;
import app.service.interfaces.UserService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    private UserValidator userValidator;
    private PersonValidator personValidator;
    private PartnerValidator partnerValidator;
    private UserService userService;
    private PartnerService partnerService;

    private PersonDto personDto;
    private UserDto userDto;
    private PartnerDto partnerDto;

    private final String[] OPTIONS = {"Crear", "Modificar", "Eliminar", "Salir"};
    private final String[] TYPES = {"Regular", "VIP"};
    private String role;

    public UserController() {
        this.userValidator = new UserValidator();
        this.personValidator = new PersonValidator();
        this.partnerValidator = new PartnerValidator();
        this.userDto = new UserDto();
        this.personDto = new PersonDto();
        this.partnerDto = new PartnerDto();
        this.userService = new Service();
        this.partnerService = new Service();
    }

    protected void menuUser(String role) {
        this.role = role;
        try {
            int option = Utils.showMenu("Menú " + this.role, "\nSelecciona una opción:\n", OPTIONS);
            options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
        }
    }

    private void options(int option) throws Exception{
        switch (option) {
            case 0: {
                this.create(this.role);
                break;
            }
            case 1: {
                System.out.println("Opción 1: fut: modificar");
                break;
            }
            case 2: {
                System.out.println("Opción 2: fut: eliminar");
                break;
            }
            case 3: {
                System.out.println("Opción 3: fut: salir");
                break;
            }
            default: {
                Utils.showError("Ingrese una opcion valida");
            }
        }
    }

    private void create(String role) throws Exception {
        switch (role) {
        case "administrador": {
            this.createUser();
            Utils.showMessage("El usuario se ha creado con éxito");
            break;
        }
        case "socio": {
            this.createPartner();
            Utils.showMessage("El socio se ha creado con éxito");
            break;
        }
        default: {
            break;
        }
        }
    }


    private void createUser() throws Exception {
        String[] labels = {"Cedula", "Nombre", "Celular", "Usuario", "Contraseña"};
        Map<String, Object> fieldsToPanel = new HashMap<>(Utils.addFieldsToPanel(labels, role));
        Map<String, JTextField> fieldsMap = new HashMap<>();
        fieldsMap.putAll((Map<? extends String, ? extends JTextField>) fieldsToPanel.get("fields"));

        JPanel panel = (JPanel) fieldsToPanel.get("panel");

        if (Utils.showConfirmDialog(panel, "Crear Usuario")) {

            userValidator.validUserName(fieldsMap.get("Usuario").getText());
            userValidator.validPassword(fieldsMap.get("Contraseña").getText());

            this.userDto.setPasswordUser(fieldsMap.get("Contraseña").getText());
            this.userDto.setNameUser(fieldsMap.get("Usuario").getText());
            this.userDto.setRoleUser(this.role);

            personValidator.validName(fieldsMap.get("Nombre").getText());

            this.personDto.setDocumentPerson(personValidator.validDocument(fieldsMap.get("Cedula").getText()));
            this.personDto.setNamePerson(fieldsMap.get("Nombre").getText());
            this.personDto.setCellphonePerson(personValidator.validCellphone(fieldsMap.get("Celular").getText()));

            this.userService.createAdmin(this.userDto, this.personDto);
        }
    }

    private void createPartner() throws Exception {
        String[] labels = {"Cedula", "Nombre", "Celular", "Usuario", "Contraseña"};
        Map<String, Object> fieldsToPanel = new HashMap<>(Utils.addFieldsToPanel(labels, role));
        Map<String, JTextField> fieldsMap = new HashMap<>();
        fieldsMap.putAll((Map<? extends String, ? extends JTextField>) fieldsToPanel.get("fields"));

        JPanel panel = (JPanel) fieldsToPanel.get("panel");

        JTextField amountField = new JTextField(30);

        panel.add(new JLabel( "Fondos: "));
        panel.add(amountField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel( "Tipo: "));
        JComboBox<String> typeComboBox = new JComboBox<>(TYPES);
        panel.add(typeComboBox);


        if (Utils.showConfirmDialog(panel, "Crear Socio")) {

            userValidator.validUserName(fieldsMap.get("Usuario").getText());
            userValidator.validPassword(fieldsMap.get("Contraseña").getText());

            this.userDto.setPasswordUser(fieldsMap.get("Contraseña").getText());
            this.userDto.setNameUser(fieldsMap.get("Usuario").getText());
            this.userDto.setRoleUser(this.role);

            personValidator.validName(fieldsMap.get("Nombre").getText());

            this.personDto.setDocumentPerson(personValidator.validDocument(fieldsMap.get("Cedula").getText()));
            this.personDto.setNamePerson(fieldsMap.get("Nombre").getText());
            this.personDto.setCellphonePerson(personValidator.validCellphone(fieldsMap.get("Celular").getText()));

            this.partnerDto.setIdUserPartner(this.userDto);
            this.partnerDto.setAmountPartner(partnerValidator.validAmount(amountField.getText()));
            this.partnerDto.setTypePartner(typeComboBox.getSelectedItem().toString());
            this.partnerService.createPartner(this.partnerDto, this.userDto, this.personDto);

        }
    }

}
