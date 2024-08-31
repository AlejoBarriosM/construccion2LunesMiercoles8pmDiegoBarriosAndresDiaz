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

public class InvoiceController implements ControllerInterface{

    private InvoiceDto invoiceDto;
    private InvoiceService invoiceService;
    private InvoiceValidator invoiceValidator;
    private PartnerDto partnerDto;

    private final String[] OPTIONS = {"Crear Factura", "Ver Facturas", "Pagar facturas", "Volver"};


    public InvoiceController() {
        this.invoiceDto = new InvoiceDto();
        this.invoiceService = new Service();
        this.invoiceValidator = new InvoiceValidator();
        this.partnerDto = new PartnerDto();
    }

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menuInvoice();
        }
    }

    private boolean menuInvoice() {
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
        return switch (option) {
            case 0 -> this.createInvoice();
            case 1 -> Utils.showYesNoDialog("Ver Facturas");
            case 2 -> Utils.showYesNoDialog("Pagar Facturas");
            default -> false;
        };
    }

    private InvoiceDetailDto addItems() throws Exception {
        InvoiceDetailDto item = new InvoiceDetailDto();

        String[] labels = {"Número de ítem", "Concepto", "Valor"};
        JPanel panel = new JPanel();
        Map<String, JTextField> fieldsMap = Utils.createPanelWithFields(labels, panel);


        if (Utils.showConfirmDialog(panel, "Agregar Item")) {

            invoiceValidator.validDesciption(fieldsMap.get("Concepto").getText());
            item.setDescriptionInvoiceDetail(fieldsMap.get("Concepto").getText());
            item.setItem(invoiceValidator.validItemNumber(fieldsMap.get("Número de ítem").getText()));
            item.setAmountInvoiceDetail(invoiceValidator.validItemValue(fieldsMap.get("Valor").getText()));
            return item;
        }
        return null;
    }

    private boolean createInvoice() throws Exception {
        String[] menu = {"Agregar Item", "Guardar Factura"};
        Map<Long, InvoiceDetailDto> items = new HashMap<>();
        double totalAmount = 0.0;
        int cantItems = 0;

        String message = "\nCantidad items: " + cantItems
                + "\nValor total: " + totalAmount
                + "\n\n";

        while (Utils.showMenu("Crear Factura", message, menu) != 1) {
            InvoiceDetailDto item = addItems();
            addItemToInvoice(items, item);

            totalAmount += item.getAmountInvoiceDetail();
            cantItems++;
            message = "\nCantidad items: " + cantItems
                    + "\nValor total: " + totalAmount
                    + "\n\n" + items + "\n";
        }

        invoiceDto.setAmountInvoice(totalAmount);
        invoiceService.createInovice(invoiceDto, items);
        return true;
    }

    private void addItemToInvoice(Map<Long, InvoiceDetailDto> items, InvoiceDetailDto item) {
        String message = (items.putIfAbsent(item.getItem(), item) == null) ? "Item añadido" : "La clave '" + item.getItem() + "' ya existe. No se puede agregar.";
        Utils.showMessage(message);
    }




//    private boolean createInvoice() throws Exception {
//
//
//        String[] labels = {"Número de ítem", "Concepto", "Valor"};
//        Map<String, Object> fieldsToPanel = new HashMap<>(Utils.addFieldsToPanel(labels));
//        Map<String, JTextField> fieldsMap = new HashMap<>();
//        fieldsMap.putAll((Map<? extends String, ? extends JTextField>) fieldsToPanel.get("fields"));
//
//        JPanel panel = (JPanel) fieldsToPanel.get("panel");
//
//        if (Utils.showConfirmDialog(panel, "Crear Factura")) {
//
//            invoiceValidator.validDesciption(fieldsMap.get("Concepto").getText());
//            invoiceDto.setAmountInvoice(invoiceValidator.validItemValue(fieldsMap.get("Valor").getText()));
//            invoiceDetailDto.setDescriptionInvoiceDetail(fieldsMap.get("Concepto").getText());
//            invoiceDetailDto.setItem(invoiceValidator.validItemNumber(fieldsMap.get("Número de ítem").getText()));
//            invoiceDetailDto.setAmountInvoiceDetail(invoiceValidator.validItemValue(fieldsMap.get("Valor").getText()));
//            invoiceService.createInovice(invoiceDto, invoiceDetailDto);
//            return true;
//
//        }
//        return false;
//    }



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
