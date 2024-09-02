package app.helpers;

import app.dto.*;
import app.model.*;

public class Helper {

    public static PersonDto parse(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setIdPerson(person.getIdPerson());
        personDto.setDocumentPerson(person.getDocumentPerson());
        personDto.setNamePerson(person.getNamePerson());
        personDto.setCellphonePerson(person.getCellphonePerson());
        return personDto;
    }

    public static Person parse(PersonDto personDto) {
        Person person = new Person();
        person.setIdPerson(personDto.getIdPerson());
        person.setDocumentPerson(personDto.getDocumentPerson());
        person.setNamePerson(personDto.getNamePerson());
        person.setCellphonePerson(personDto.getCellphonePerson());
        return person;
    }

    public static UserDto parse(User user) {
        UserDto userDto = new UserDto();
        userDto.setIdUser(user.getIdUser());
        userDto.setPasswordUser(user.getPasswordUser());
        userDto.setIdPerson(parse(user.getIdPerson()));
        userDto.setRoleUser(user.getRoleUser());
        userDto.setNameUser(user.getUserName());
        return userDto;
    }

    public static User parse(UserDto userDto) {
        User user = new User();
        user.setIdUser(userDto.getIdUser());
        user.setPasswordUser(userDto.getPasswordUser());
        user.setIdPerson(parse(userDto.getIdPerson()));
        user.setRoleUser(userDto.getRoleUser());
        user.setUserName(userDto.getNameUser());
        return user;
    }

    public static PartnerDto parse(Partner partner) {
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setIdPartner(partner.getIdPartner());
        partnerDto.setIdUserPartner(parse(partner.getIdUserPartner()));
        partnerDto.setAmountPartner(partner.getAmountPartner());
        partnerDto.setTypePartner(partner.getTypePartner());
        partnerDto.setCreationDatePartner(partner.getCreationDatePartner());
        return partnerDto;
    }

    public static Partner parse(PartnerDto partnerDto) {
        Partner partner = new Partner();
        partner.setIdPartner(partnerDto.getIdPartner());
        partner.setIdUserPartner(parse(partnerDto.getIdUserPartner()));
        partner.setAmountPartner(partnerDto.getAmountPartner());
        partner.setTypePartner(partnerDto.getTypePartner());
        partner.setCreationDatePartner(partnerDto.getCreationDatePartner());
        return partner;
    }

    public static Guest parse(GuestDto guestDto) {
        Guest guest = new Guest();
        guest.setIdGuest(guestDto.getIdGuest());
        guest.setUserIdGuest(parse(guestDto.getUserIdGuest()));
        guest.setPartnerIdGuest(parse(guestDto.getPartnerIdGuest()));
        guest.setStatusGuest(guestDto.getStatusGuest());
        return guest;
    }

    public static GuestDto parse(Guest guest) {
        GuestDto guestDto = new GuestDto();
        guestDto.setIdGuest(guest.getIdGuest());
        guestDto.setUserIdGuest(parse(guest.getUserIdGuest()));
        guestDto.setPartnerIdGuest(parse(guest.getPartnerIdGuest()));
        guestDto.setStatusGuest(guest.getStatusGuest());
        return guestDto;
    }

    public static Invoice parse(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setIdInvoice(invoiceDto.getIdInvoice());
        invoice.setIdPerson(parse(invoiceDto.getIdPerson()));
        invoice.setIdPartner(parse(invoiceDto.getIdPartner()));
        invoice.setCreationDateInvoice(invoiceDto.getCreationDateInvoice());
        invoice.setAmountInvoice(invoiceDto.getAmountInvoice());
        invoice.setStatusInvoice(invoiceDto.getStatusInvoice());
        return invoice;
    }

    public static InvoiceDto parse (Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setIdInvoice(invoice.getIdInvoice());
        invoiceDto.setIdPerson(parse(invoice.getIdPerson()));
        invoiceDto.setIdPartner(parse(invoice.getIdPartner()));
        invoiceDto.setAmountInvoice(invoice.getAmountInvoice());
        invoiceDto.setCreationDateInvoice(invoice.getCreationDateInvoice());
        invoiceDto.setStatusInvoice(invoice.getStatusInvoice());
        return invoiceDto;
    }

    public static InvoiceDetail parse(InvoiceDetailDto invoiceDetailDto) {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setIdInvoiceDetail(invoiceDetailDto.getIdInvoiceDetail());
        invoiceDetail.setIdInvoice(parse(invoiceDetailDto.getIdInvoice()));
        invoiceDetail.setItem(invoiceDetailDto.getItem());
        invoiceDetail.setDescriptionInvoiceDetail(invoiceDetailDto.getDescriptionInvoiceDetail());
        invoiceDetail.setAmountInvoiceDetail(invoiceDetailDto.getAmountInvoiceDetail());
        return invoiceDetail;
    }

    public static InvoiceDetailDto parse(InvoiceDetail invoiceDetail) {
        InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
        invoiceDetailDto.setIdInvoiceDetail(invoiceDetail.getIdInvoiceDetail());
        invoiceDetailDto.setIdInvoice(parse(invoiceDetail.getIdInvoice()));
        invoiceDetailDto.setItem(invoiceDetail.getItem());
        invoiceDetailDto.setDescriptionInvoiceDetail(invoiceDetail.getDescriptionInvoiceDetail());
        invoiceDetailDto.setAmountInvoiceDetail(invoiceDetail.getAmountInvoiceDetail());
        return invoiceDetailDto;
    }




}
