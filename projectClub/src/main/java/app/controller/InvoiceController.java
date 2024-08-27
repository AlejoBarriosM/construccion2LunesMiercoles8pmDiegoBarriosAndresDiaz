package app.controller;


import app.controller.validator.InvoiceValidator;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.service.Service;
import app.service.interfaces.InvoiceService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class InvoiceController {

    private InvoiceDto invoiceDto;
    private InvoiceDetailDto invoiceDetailDto;
    private InvoiceService invoiceService;
    private InvoiceValidator invoiceValidator;
    private PartnerDto partnerDto;

    private final String[] OPTIONS = {"Crear Factura", "Ver Facturas", "Pagar facturas", "Salir"};

    public InvoiceController() {
        this.invoiceDto = new InvoiceDto();
        this.invoiceDetailDto = new InvoiceDetailDto();
        this.invoiceService = new Service();
        this.invoiceValidator = new InvoiceValidator();
        this.partnerDto = new PartnerDto();
    }

    public boolean menuInvoice() {
        this.partnerDto = Service.partner;

        String message = "\nMonto: " + partnerDto.getAmountPartner()
                + "\nFacturas pendientes: " + partnerDto.getTypePartner()
                + "\nValor pendientes " + partnerDto.getTypePartner()
                + "\n\nSelecciona una opción:\n\n";

        try {
            int option = Utils.showMenu("Menú Facturas", message, OPTIONS);
            return options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
            return true;
        }
    }

    private boolean options(int option) throws Exception{
        switch (option) {
            case 0: {
                this.createInvoice();
                return true;
            }
            case 1: {

                return Utils.showYesNoDialog("Ver Facturas");
            }
            case 2: {
                return Utils.showYesNoDialog("Pagar Facturas");
            }
            case 3: {
                return !Utils.showYesNoDialog("Salir");
            }
            default: {
                Utils.showError("Ingrese una opcion valida");
                return true;
            }
        }
    }

    private void createInvoice() throws Exception {
        String[] labels = {"Número de ítem", "Concepto", "Valor"};
        Map<String, Object> fieldsToPanel = new HashMap<>(Utils.addFieldsToPanel(labels));
        Map<String, JTextField> fieldsMap = new HashMap<>();
        fieldsMap.putAll((Map<? extends String, ? extends JTextField>) fieldsToPanel.get("fields"));

        JPanel panel = (JPanel) fieldsToPanel.get("panel");

        if (Utils.showConfirmDialog(panel, "Crear Factura")) {

            invoiceValidator.validDesciption(fieldsMap.get("Concepto").getText());
            invoiceDto.setAmountInvoice(invoiceValidator.validItemValue(fieldsMap.get("Valor").getText()));
            invoiceDetailDto.setDescriptionInvoiceDetail(fieldsMap.get("Concepto").getText());
            invoiceDetailDto.setItem(invoiceValidator.validItemValue(fieldsMap.get("Número de ítem").getText()));
            invoiceDetailDto.setAmountInvoiceDetail(invoiceValidator.validItemValue(fieldsMap.get("Valor").getText()));


        }
    }

//    private void viewAllInvoices() {
//        try {
//            List<InvoiceDto> invoices = invoiceService.getAllInvoices();
//            for (InvoiceDto invoice : invoices) {
//                JOptionPane.showMessageDialog(null, "ID: " + invoice.getId()
//                        + "\n, Socio ID: " + invoice.getIdPartner()
//                        + "\n, Fecha: " + invoice.getDate()
//                        + "\n, Monto Total: " + invoice.getTotalAmount());
//            }
//        } catch (Exception e) {
//            Utils.showError("Error al recuperar las facturas: " + e.getMessage());
//        }
//    }




}
