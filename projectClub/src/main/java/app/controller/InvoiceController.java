package app.controller;


import app.controller.validator.InvoiceValidator;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.service.Service;
import app.service.interfaces.InvoiceService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


public class InvoiceController implements ControllerInterface{

    private final InvoiceDto invoiceDto;
    private final InvoiceService invoiceService;
    private final InvoiceValidator invoiceValidator;
    private final Service service;
    private PartnerDto partnerDto;
    private GuestDto guestDto;

    private final String[] OPTIONS = {"Crear Factura", "Ver Facturas", "Pagar facturas", "Volver"};


    public InvoiceController() {
        this.invoiceDto = new InvoiceDto();
        this.invoiceService = new Service();
        this.service = new Service();
        this.invoiceValidator = new InvoiceValidator();
    }

    @Override
    public void session() {
        boolean session = true;
        while (session) {
            session = menuInvoice();
        }
    }

    private boolean menuInvoice() {
        this.guestDto = Service.guest;
        this.partnerDto = Service.partner;

        String message = "\nMonto disponible: " + partnerDto.getAmountPartner()
                //+ "\nFacturas pendientes: " + service.getMaxAmount()
                //+ "\nValor pendientes " + partnerDto.getTypePartner()
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
            case 1 -> showAllInvoices(partnerDto);
            case 2 -> invoiceService.payInvoices(partnerDto);
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
        invoiceService.validateInvoice(invoiceDto, items);
        return true;
    }

    private void addItemToInvoice(Map<Long, InvoiceDetailDto> items, InvoiceDetailDto item) {
        String message = (items.putIfAbsent(item.getItem(), item) == null) ? "Item añadido" : "La clave '" + item.getItem() + "' ya existe. No se puede agregar.";
        Utils.showMessage(message);
    }

    protected boolean showAllInvoices(PartnerDto partnerDto) throws Exception {

        Map<InvoiceDto, Map<Long, InvoiceDetailDto>> invoices;
        invoices = (partnerDto == null) ? service.showAllInvoices() : service.showAllInvoicesByPartner(partnerDto);
        StringBuilder messageBuilder = new StringBuilder();
        Map<Long, String> fields = new HashMap<>();

        for (Map.Entry<InvoiceDto, Map<Long, InvoiceDetailDto>> invoiceEntry : invoices.entrySet()) {
            messageBuilder.setLength(0);
            InvoiceDto invoiceDto = invoiceEntry.getKey();
            Map<Long, InvoiceDetailDto> invoiceDetailDto  = invoiceEntry.getValue();

            messageBuilder.append(invoiceDto.toString()).append("\n");
            messageBuilder.append(showAllDetailInvoice(invoiceDetailDto)).append("\n");
            messageBuilder.append("-------------------------------------------------------------------------------");

            fields.put(invoiceDto.getIdInvoice(),messageBuilder.toString());
        }

        Utils.createPanelWithScroll(fields, "Listado Facturas");
        return true;
    }

    private String showAllDetailInvoice(Map<Long, InvoiceDetailDto> invoiceDetailDto){
        StringBuilder messageBuilder = new StringBuilder();
        for (Map.Entry<Long, InvoiceDetailDto> detailEntry : invoiceDetailDto.entrySet()) {
            InvoiceDetailDto detail = detailEntry.getValue();
            messageBuilder.append(detail.toString()).append("\n");
        }
        return messageBuilder.toString();
    }

}

