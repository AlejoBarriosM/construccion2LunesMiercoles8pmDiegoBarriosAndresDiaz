package app.service;

import app.controller.Utils;
import app.dao.*;
import app.dao.interfaces.*;
import app.dto.*;
import app.service.interfaces.*;

import java.sql.SQLException;
import java.util.Map;

public class Service implements LoginService, AdminService, UserService, PartnerService, GuestService, InvoiceService {

	private final UserDao userDao;
	private final PersonDao personDao;
	private final PartnerDao partnerDao;
	private final GuestDao guestDao;
	private final InvoiceDao invoiceDao;

	public static UserDto user;
	public static PersonDto person;
	public static GuestDto guest;
	public static PartnerDto partner;

	private final String STATUSINVOICE = "Debe";
	private final long AMOUNT = 50000;
	private final String TYPE = "Regular";
	private final String STATUS = "Activo";
	private final int CANTVIP = 5;
	private static int maxAmount;

    public Service() {
		this.userDao = new UserDaoImplementation();
		this.personDao = new PersonDaoImplementation();
		this.partnerDao = new PartnerDaoImplementation();
		this.guestDao = new GuestDaoImplementation();
		this.invoiceDao = new InvoiceDaoImplementation();
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	@Override
	public void login(UserDto userDto) throws Exception {
		user = userDao.findByUserName(userDto);
		if (user == null) {
			throw new Exception("Usuario no registrado");
		}
		if (!userDto.getPasswordUser().equals(user.getPasswordUser())) {
			throw new Exception("Usuario o contraseña incorrecto");
		}

		userDto.setRoleUser(user.getRoleUser());
		person = personDao.findById(user.getIdPerson().getIdPerson());
		user.setIdPerson(person);

		switch (user.getRoleUser()){
			case "socio":{
                partner = partnerDao.findByIdUser(user);
				maxAmount = (partner.getTypePartner().equals("VIP") ? 5000000 : 1000000);
				break;
			}
			case "invitado": {
				guest = guestDao.findByUserId(user);
				partner = partnerDao.findByIdUser(guest.getPartnerIdGuest().getIdUserPartner());
				break;
			}
		}

		Utils.showMessage("Se ha iniciado sesión");
	}

	@Override
	public boolean logout() {
		user = null;
		partner = null;
		guest = null;
		Utils.showMessage("Se ha cerrado sesión");
		return false;
	}

	@Override
	public void createAdmin(UserDto userDto, PersonDto personDto) throws Exception {
		this.createUser(userDto, personDto);
	}

	@Override
	public void createPartner(UserDto userDto, PersonDto personDto) throws Exception{
		PartnerDto partnerDto = new PartnerDto();
		this.createUser(userDto, personDto);
		userDto = userDao.findByUserName(userDto);
		partnerDto.setIdUserPartner(userDto);
		partnerDto.setAmountPartner(AMOUNT);
		partnerDto.setTypePartner(TYPE);
		this.partnerDao.createPartner(partnerDto, userDto);
	}

	@Override
	public void increaseAmount(PartnerDto partnerDto, Double amount) throws Exception {

		if ((partnerDto.getAmountPartner() + amount) <= maxAmount) {
			this.partnerDao.increaseAmount(partnerDto, amount);
			partner = partnerDao.findByIdUser(user);
			Utils.showMessage("Se ha incrementado el monto: " + partner.getAmountPartner());
			return;
		}
		throw new Exception("Monto supera los topes");
	}

	@Override
	public void changeSubscription(PartnerDto partnerDto) throws Exception {
		if (partnerDao.cantTypeSubscription("VIP") >= CANTVIP){
			throw new Exception("No hay cupos VIP disponibles");
		}else {
			partnerDao.changeSubscription(partnerDto, "Pendiente");
			Utils.showMessage("Se he registrado la suscripción");
		}
	}

	@Override
	public boolean pendingInvoices(PartnerDto partnerDto) throws Exception {
        return partnerDao.pendingInvoices(partnerDto);
	}

	@Override
	public void unsubscribe(PartnerDto partnerDto) throws Exception {
		partnerDao.unsubscribe(partnerDto);
	}

	@Override
	public Map<Long, PartnerDto> pendingSubscriptions() throws Exception {
		return partnerDao.pendingSubscriptions();
	}

	@Override
	public void approvalVIP(PartnerDto partnerDto, Boolean approve) throws Exception {
		if (approve){
			partnerDao.changeSubscription(partnerDto, "VIP");
		} else {
			partnerDao.changeSubscription(partnerDto, "Regular");
		}
	}

	@Override
	public void createGuest(GuestDto guestDto, UserDto userDto, PersonDto personDto) throws Exception {
		if (partner.getTypePartner().equals("Regular") && this.partnerDao.numberOfGuests(partner) >= 3 ) {
			throw new Exception("Ya no tiene cupos disponibles");
        }
		this.createUser(userDto, personDto);
		userDto = userDao.findByUserName(userDto);
		guestDto.setUserIdGuest(userDto);
		guestDto.setPartnerIdGuest(partner);
		guestDto.setStatusGuest(STATUS);
		this.guestDao.createGuest(guestDto, partner, userDto);
	}

	@Override
	public boolean pendingInvoices(GuestDto guestDto) throws Exception {
		return guestDao.pendingInvoices(guestDto);
	}

	@Override
	public void upgradeToPartner(GuestDto guestDto) throws Exception {
		UserDto userDto = userDao.findByUserName(guestDto.getUserIdGuest());
		PartnerDto partnerDto = new PartnerDto();
		partnerDto.setIdUserPartner(userDto);
		partnerDto.setAmountPartner(AMOUNT);
		partnerDto.setTypePartner(TYPE);
		this.partnerDao.createPartner(partnerDto, userDto);
		this.guestDao.changeType(guestDto);
		this.guestDao.upgradeToPartner(guestDto);
	}

	private void createPerson(PersonDto personDto) throws Exception {
		if (this.personDao.existsByDocument(personDto)) {
			throw new Exception("Ya existe una persona con ese documento");
		}
		this.personDao.createPerson(personDto);
	}

	private void createUser(UserDto userDto, PersonDto personDto) throws Exception {
		this.createPerson(personDto);
		personDto = personDao.findByDocument(personDto);
		userDto.setIdPerson(personDto);
		if (this.userDao.existsByUserName(userDto)) {
			this.personDao.deletePerson(userDto.getIdPerson());
			throw new Exception("El nombre de usuario ya está siendo usado");
		}
		try {
			this.userDao.createUser(userDto);
		} catch (SQLException e) {
			this.personDao.deletePerson(userDto.getIdPerson());
		}
	}

	@Override
	public void validateInvoice(InvoiceDto invoiceDto, Map<Long, InvoiceDetailDto> items) throws Exception {
		if (invoiceDto.getAmountInvoice() < maxAmount) {
			createInvoice(invoiceDto, items);
		} else throw new Exception("El valor de la factura no puede exceder $ " + maxAmount);
	}

	@Override
	public void createInvoice(InvoiceDto invoiceDto, Map<Long, InvoiceDetailDto> items) throws Exception {
		invoiceDto.setIdPerson(user.getIdPerson());
        invoiceDto.setStatusInvoice(STATUSINVOICE);
		InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();

		switch (user.getRoleUser()){
			case "socio":{
				invoiceDto.setIdPartner(partner);
				break;
			}
			case "invitado": {
				invoiceDto.setIdPartner(guest.getPartnerIdGuest());
				break;
			}
		}

		invoiceDto.setIdInvoice(this.invoiceDao.createInvoice(invoiceDto));
		for (Map.Entry<Long, InvoiceDetailDto> entry : items.entrySet()){
			invoiceDetailDto.setIdInvoice(invoiceDto);
			invoiceDetailDto.setItem(entry.getKey());
			invoiceDetailDto.setDescriptionInvoiceDetail(entry.getValue().getDescriptionInvoiceDetail());
			invoiceDetailDto.setAmountInvoiceDetail(entry.getValue().getAmountInvoiceDetail());
			this.invoiceDao.createDetailInvoice(invoiceDetailDto);
		}
	}

	@Override
	public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices() throws Exception {
		return invoiceDao.showAllInvoices(null);
	}

	@Override
	public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoicesByPartner(PartnerDto partnerDto) throws Exception {
		return invoiceDao.showAllInvoices(partnerDto.getIdPartner());
	}

	@Override
	public boolean payInvoices(PartnerDto partnerDto) throws Exception {
		partner = partnerDao.findById(partnerDto.getIdPartner());
		Map<InvoiceDto, Map<Long, InvoiceDetailDto>> invoices = invoiceDao.showAllInvoices(partner.getIdPartner());

		for (Map.Entry<InvoiceDto, Map<Long, InvoiceDetailDto>> invoiceEntry : invoices.entrySet()) {
			InvoiceDto invoiceDto = invoiceEntry.getKey();
			double amount = partner.getAmountPartner();

			if (detailInvoice(partnerDto, amount, invoiceDto)) break;
		}
		return true;
	}

	private boolean detailInvoice(PartnerDto partnerDto, double amount, InvoiceDto invoiceDto) throws Exception {
		if (amount >= invoiceDto.getAmountInvoice()) {
			invoiceDao.payInvoice(invoiceDto);
			partnerDao.decreaseAmount(partner, invoiceDto.getAmountInvoice());
			partner.setAmountPartner(amount - invoiceDto.getAmountInvoice());
			Utils.showMessage("Factura " + invoiceDto.getIdInvoice() + " pagada"
					+ "\nNuevo saldo: " + partner.getAmountPartner());
		} else {
			partner = partnerDao.findById(partnerDto.getIdPartner());
			Utils.showMessage("Saldo insuficiente"
					+ "\nSaldo: " + partner.getAmountPartner());
			return true;
		}
		return false;
	}


}

