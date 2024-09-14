package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.InvoiceDao;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.helpers.Helper;
import app.entity.*;
import app.repository.InvoiceDetailRepository;
import app.repository.InvoiceRepository;
import app.repository.PartnerRepository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InvoiceDaoImplementation implements InvoiceDao {

    private InvoiceRepository invoiceRepository;
    private InvoiceDetailRepository invoiceDetailRepository;
    private PartnerRepository partnerRepository;

    @Override
    public Long createInvoice(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        return invoiceRepository.save(invoice).getIdInvoice();
    }

    @Override
    public void createDetailInvoice(InvoiceDetailDto invoiceDetailDto) throws Exception {
        InvoiceDetail invoiceDetail = Helper.parse(invoiceDetailDto);
        invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices (Long partnerId) throws Exception {

        List<Invoice> invoices;

        if (partnerId == null){
            invoices = invoiceRepository.findAll();
        } else {
            Partner partner = new Partner();
            partner.setIdPartner(partnerId);
            invoices = invoiceRepository.findAllByIdPartnerAndStatusInvoiceIsNot(partner, "Pagó");
        }

        Map<InvoiceDto, Map<Long, InvoiceDetailDto>> invoiceMap = new HashMap<>();
        for (Invoice invoice : invoices) {
            InvoiceDto invoiceDto = Helper.parse(invoice);
            invoiceMap.put(invoiceDto, this.showAllInvoicesDetails(invoiceDto));
        }

        return invoiceMap;
    }

    @Override
    public Map<Long, InvoiceDetailDto> showAllInvoicesDetails(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findAllByIdInvoice(invoice);


        Map<Long, InvoiceDetailDto> items = new HashMap<>();
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            InvoiceDetailDto invoiceDetailDto = Helper.parse(invoiceDetail);
            items.put(invoiceDetail.getIdInvoiceDetail(), invoiceDetailDto);
        }

        return items;
    }

    @Override
    public void payInvoice(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        invoiceRepository.updateStatusInvoiceByIdInvoice(invoice, "Pagó");
    }

}

