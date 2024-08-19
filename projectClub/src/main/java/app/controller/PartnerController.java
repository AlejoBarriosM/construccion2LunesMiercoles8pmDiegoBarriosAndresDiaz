package app.controller;

import app.controller.validator.PartnerValidator;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.PartnerService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PartnerController extends UserController{


    private PartnerService service;
    private PartnerValidator parnerValidator;


    private final String[] TYPES = {"Regular", "VIP"};

    public PartnerController() {
        this.parnerValidator = new PartnerValidator();
        this.service = new Service();
    }

    private void createPartner() throws Exception {

        //userController.createUser("socio");

        String[] labels = {"Fondos", "Tipo"};

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField amountField = new JTextField(30);

        panel.add(new JLabel( "Fondos: "));
        panel.add(amountField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel( "Tipo: "));
        JComboBox<String> typeComboBox = new JComboBox<>(TYPES);
        panel.add(typeComboBox);

        if (Utils.showConfirmDialog(panel, "Crear Socio")) {
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setAmountPartner(parnerValidator.validAmount(amountField.getText()));
            partnerDto.setTypePartner(typeComboBox.getSelectedItem().toString());
            this.service.createPartner(partnerDto);
            Utils.showMessage("El socio se ha creado con éxito");
        }
    }
}
