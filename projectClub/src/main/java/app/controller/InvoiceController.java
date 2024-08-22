package app.controller;

import static app.config.MYSQLConnection.getConnection;
import app.dao.interfaces.InvoiceDao;
import app.dto.InvoiceDto;
import app.service.InvoiceService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class InvoiceController {

    private InvoiceDao invoiceDao;
    private InvoiceService invoiceService;

    public InvoiceController() {
        this.invoiceDao = new InvoiceDao(getConnection()) {

            @Override
            public void createInvoice(InvoiceDto invoice) throws Exception {

            }

            @Override
            public void updateInvoice(InvoiceDto invoice) throws Exception {

            }

            @Override
            public void deleteInvoice(InvoiceDto invoice) throws Exception {

            }

            @Override
            public boolean payInvoice(InvoiceDto invoice) throws Exception {
                return false;
            }
        };
        this.invoiceService = new InvoiceService(invoiceDao);
    }

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = true;
        }
    }

    private boolean menu() {
        try {
            String[] options = {"Crear Factura", "Ver todas las Facturas", "Salir"};
            int choice = Utils.showMenu("Menú de Facturas", "Seleccione una opción", options);
            switch (choice) {

                case 0:
                    createInvoice();
                    return true;

                case 1:
                    viewAllInvoices();
                    return true;

                case 2:
                    JOptionPane.showMessageDialog(null, "Saliendo del menú de Facturas.");
                    return false;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Inténtelo de nuevo.");
                    return true;

            }

        } catch (Exception e) {
            Utils.showError(e.getMessage());
            return true;
        }
    }

    private void createInvoice() {
        try {
            int id = Utils.promptIntInput("Ingrese el ID de la factura:");
            int idPartner = Utils.promptIntInput("Ingrese el ID del socio:");
            java.sql.Date date = java.sql.Date.valueOf(Utils.promptInput("Ingrese la fecha de la Facutra (yyyy-mm-dd)"));
            double totalAmount = Utils.promptDoubleInput("Ingrese el monto toal de la factura:");

            InvoiceDto invoiceDto = new InvoiceDto(id, idPartner, date, totalAmount);
            invoiceService.createInvoice(invoiceDto);
            Utils.showMessage("Factura creada exitosamente.");
        } catch (Exception e) {
            Utils.showError("Error al crear la factura: " + e.getMessage());
        }
    }

    private void viewAllInvoices() {
        try {
            List<InvoiceDto> invoices = invoiceService.getAllInvoices();
            for (InvoiceDto invoice : invoices) {
                JOptionPane.showMessageDialog(null, "ID: " + invoice.getId()
                        + "\n, Socio ID: " + invoice.getIdPartner()
                        + "\n, Fecha: " + invoice.getDate()
                        + "\n, Monto Total: " + invoice.getTotalAmount());
            }
        } catch (Exception e) {
            Utils.showError("Error al recuperar las facturas: " + e.getMessage());
        }
    }


    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_base_de_datos", "usuario", "contraseña");
        } catch (Exception e) {
            throw new RuntimeException("Eror al conectar con la base de datos", e);
        }
    }

}
