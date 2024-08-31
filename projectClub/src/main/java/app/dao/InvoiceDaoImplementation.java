package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.InvoiceDao;
import app.dao.interfaces.PartnerDao;
import app.dao.interfaces.PersonDao;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.helpers.Helper;
import app.model.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class InvoiceDaoImplementation implements InvoiceDao {

    private PartnerDao partnerDao = new PartnerDaoImplementation();
    private PersonDao personDao = new PersonDaoImplementation();

    @Override
    public Long createInvoice(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        String query = "INSERT INTO INVOICE(PERSONID, PARTNERID, AMOUNT, STATUS) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1,invoice.getIdPerson().getIdPerson());
        preparedStatement.setLong(2,invoice.getIdPartner().getIdPartner());
        preparedStatement.setDouble(3, invoice.getAmountInvoice());
        preparedStatement.setString(4, invoice.getStatusInvoice());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        resultSet.close();
        preparedStatement.close();
        return 0L;
    }

    @Override
    public void createDetailInvoice(InvoiceDetailDto invoiceDetailDto) throws Exception {
        InvoiceDetail invoiceDetail = Helper.parse(invoiceDetailDto);
        String query = "INSERT INTO INVOICEDETAIL(INVOICEID, ITEM, DESCRIPTION, AMOUNT) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1,invoiceDetail.getIdInvoice().getIdInvoice());
        preparedStatement.setLong(2,invoiceDetail.getItem());
        preparedStatement.setString(3, invoiceDetail.getDescriptionInvoiceDetail());
        preparedStatement.setDouble(4, invoiceDetail.getAmountInvoiceDetail());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public InvoiceDto findIdInvoce (Long idInvoice) throws Exception {
        String query = "SELECT * FROM INVOICE WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, idInvoice);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Invoice invoice = new Invoice();
            invoice.setIdInvoice(resultSet.getLong("ID"));
            invoice.setIdPerson(Helper.parse(personDao.findById(resultSet.getLong("PERSONID"))));
            invoice.setIdPartner(Helper.parse(partnerDao.findById(resultSet.getLong("PARTNERID"))));
            invoice.setCreationDateInvoice(resultSet.getString("CREATIONDATE"));
            invoice.setAmountInvoice(resultSet.getDouble("AMOUNT"));
            invoice.setStatusInvoice(resultSet.getString("STATUS"));

            resultSet.close();
            preparedStatement.close();
            return Helper.parse(invoice);
        }
        resultSet.close();
        preparedStatement.close();
        return null;

    }

    @Override
    public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices () throws Exception {
        String query = "SELECT * FROM INVOICE";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<InvoiceDto, Map<Long, InvoiceDetailDto>> invoiceMap = new HashMap<>();

        while (resultSet.next()) {

            Invoice invoice = new Invoice();
            invoice.setIdInvoice(resultSet.getLong("ID"));
            invoice.setIdPerson(Helper.parse(personDao.findById(resultSet.getLong("PERSONID"))));
            invoice.setIdPartner(Helper.parse(partnerDao.findById(resultSet.getLong("PARTNERID"))));
            invoice.setAmountInvoice(resultSet.getDouble("AMOUNT"));
            invoice.setStatusInvoice(resultSet.getString("STATUS"));
            invoice.setCreationDateInvoice(resultSet.getString("CREATIONDATE"));

            InvoiceDto invoiceDto = Helper.parse(invoice);
            invoiceMap.put(invoiceDto, this.showAllInvoicesDetails(invoiceDto));
        }
        resultSet.close();
        preparedStatement.close();

        return invoiceMap;
    }

    @Override
    public Map<Long, InvoiceDetailDto> showAllInvoicesDetails(InvoiceDto invoiceDto) throws Exception {
        String query = "SELECT * FROM INVOICEDETAIL WHERE INVOICEID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1,invoiceDto.getIdInvoice());
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<Long, InvoiceDetailDto> items = new HashMap<>();

        while (resultSet.next()) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setIdInvoiceDetail(resultSet.getLong("ID"));
            Invoice invoice = Helper.parse(invoiceDto);
            invoiceDetail.setIdInvoice(invoice);
            invoiceDetail.setItem(resultSet.getLong("ITEM"));
            invoiceDetail.setDescriptionInvoiceDetail(resultSet.getString("DESCRIPTION"));
            invoiceDetail.setAmountInvoiceDetail(resultSet.getDouble("AMOUNT"));
            items.put(invoiceDetail.getIdInvoiceDetail(),Helper.parse(invoiceDetail));
        }
        resultSet.close();
        preparedStatement.close();

        return items;
    }


    @Override
    public void updateInvoice(InvoiceDto invoiceDto) throws Exception {

    }

    @Override
    public void deleteInvoice(InvoiceDto invoiceDto) throws Exception {

    }

    @Override
    public boolean payInvoice(InvoiceDto invoiceDto) throws Exception {
        return true;
    }
}
