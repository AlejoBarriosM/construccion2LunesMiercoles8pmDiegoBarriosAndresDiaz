package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.InvoiceDao;
import app.dto.InvoiceDto;
import app.helpers.Helper;
import app.model.Invoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoiceDaoImplementation implements InvoiceDao {


    @Override
    public void createInvoice(InvoiceDto invoiceDto) throws Exception {
//        Invoice invoice = Helper.parse(invoiceDto);
//        String query = "INSERT INTO INVOICE(ID, IDPARTNER, DATE, TOTALAMOUNT) VALUES (?,?,?,?)";
//        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
//        preparedStatement.setInt(1,invoiceDto.getIdPerson());
//        preparedStatement.setInt(2,invoiceDto.getIdPartner());
//        preparedStatement.setDate(3,new java.sql.Date(invoiceDto.getDate().getTime()));
//        preparedStatement.setDouble(4,invoiceDto.getTotalAmount());
//        preparedStatement.execute();
//        preparedStatement.close();
    }

    @Override
    public void updateInvoice(InvoiceDto invoiceDto) throws Exception {

    }

    @Override
    public void deleteInvoice(InvoiceDto invoiceDto) throws Exception {

    }

    @Override
    public boolean payInvoice(InvoiceDto invoiceDto) throws Exception {
        String query = "UPDATE invoice SET STATUS =? WHERE ID =?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setBoolean(1,true);
        preparedStatement.setInt(2,invoiceDto.getId());
        int rowsUpdated = preparedStatement.executeUpdate();
        return rowsUpdated > 0;
    }
}
