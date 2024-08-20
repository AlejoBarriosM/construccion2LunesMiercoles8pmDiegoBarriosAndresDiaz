package app.helpers;

import app.dto.InvoiceDto;
import app.model.Invoice;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Partner;
import app.model.Person;
import app.model.User;

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
    public static Partner parse(PartnerDto partnerDto) {
        Partner partner = new Partner();
        partner.setIdPartner(partnerDto.getIdPartner());
        partner.setIdUserPartner(partnerDto.getIdUserPartner());
        partner.setAmountPartner(partnerDto.getAmountPartner());
        partner.setTypePartner(partnerDto.getTypePartner());
        partner.setCreationDatePartner(partnerDto.getCreationDatePartner());
        return partner;
    }


    public static PartnerDto parse(Partner partner) {
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setIdPartner(partner.getIdPartner());
        partnerDto.setIdUserPartner(partner.getIdUserPartner());
        partnerDto.setAmountPartner(partner.getAmountPartner());
        partnerDto.setTypePartner(partner.getTypePartner());
        partnerDto.setCreationDatePartner(partner.getCreationDatePartner());
        return partnerDto;
    }

    public static Invoice parse(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setIdPerson(invoiceDto.getIdPerson());
        invoice.setIdPartner(invoiceDto.getIdPartner());
        invoice.setAmountInvoice(invoiceDto.getTotalAmount());
        invoice.setStatusInvoice(invoice.getStatusInvoice());
        return invoice;
    }


    public static InvoiceDto parse (Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setIdPerson(invoice.getIdPerson());
        invoiceDto.setIdPartner(invoiceDto.getIdPartner());
        invoiceDto.setTotalAmount(invoice.getAmountInvoice());
        invoiceDto.setStatusInvoice(invoice.getStatusInvoice());
        return invoiceDto;



    }



}
