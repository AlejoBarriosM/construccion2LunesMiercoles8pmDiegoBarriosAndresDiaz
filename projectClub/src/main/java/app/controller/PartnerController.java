package app.controller;

import app.controller.validator.PartnerValidator;
import app.dto.PartnerDto;
import app.service.Service;
import app.service.interfaces.PartnerService;

import javax.swing.*;
import java.util.Map;


public class PartnerController extends UserController implements ControllerInterface{

    private final PartnerValidator partnerValidator;
    private PartnerDto partnerDto;
    private final PartnerService partnerService;
    private final Service service;
    private final InvoiceController invoiceController;

    private final String[] OPTIONS = {"Invitados", "Aumentar Fondos", "Facturas", "Cambio Suscripción", "Darse de baja", "Cerrar Sesión"};

    public PartnerController() {
        this.partnerValidator = new PartnerValidator();
        this.partnerDto = new PartnerDto();
        this.partnerService = new Service();
        this.service = new Service();
        this.invoiceController = new InvoiceController();
    }

    @Override
    public void session() {
        boolean session = true;
        while (session) {
            session = menuPartner();
        }
    }

    private boolean menuPartner() {
        this.partnerDto = Service.partner;

        String message = "Bienvenido\n"
                + "\nNombre: " + Service.user.getNameUser()
                + "\nMonto: " + partnerDto.getAmountPartner()
                + "\nTipo: " + partnerDto.getTypePartner()
                + "\n\nSelecciona una opción:\n\n";

        try {
            int option = Utils.showMenu("Menú Principal", message, OPTIONS);
            return options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
            return true;
        }
    }

    private boolean options(int option) throws Exception{
        switch (option) {
            case 0: {
                super.setRole("invitado");
                super.session();
                return true;
            }
            case 1: {
                this.increaseAmount();
                return true;
            }
            case 2: {
                this.invoiceController.session();
                return true;
            }
            case 3: {
                return Utils.showYesNoDialog("cambio suscripción");
            }
            case 4: {
                return Utils.showYesNoDialog("Darse de Baja") || (this.service.logout());
            }
            case 5: {
                return Utils.showYesNoDialog("¿Desea cerrar sesión?") || (this.service.logout());
            }
            default: {
                Utils.showError("Ingrese una opción valida");
                return true;
            }
        }
    }

    private void increaseAmount () throws Exception {
        String[] labels = {"Monto"};
        JPanel panel = new JPanel();
        Map<String, JTextField> fieldsMap = Utils.createPanelWithFields(labels, panel);

        if (Utils.showConfirmDialog(panel, "Aumentar Monto")) {
            this.partnerService.increaseAmount(this.partnerDto, partnerValidator.validAmount(fieldsMap.get("Monto").getText()));
            this.payInvoices();
        }
    }



    private void payInvoices () throws Exception {
        service.payInvoices(partnerDto);
    }




}
