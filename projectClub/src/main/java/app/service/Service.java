package app.service;

import app.controller.Utils;
import app.dao.*;
import app.dao.interfaces.*;
import app.dto.*;
import app.service.interfaces.*;

import java.sql.SQLException;
import java.util.Map;

public class Service implements LoginService, AdminService, UserService, PartnerService, GuestService, InvoiceService {

	private UserDao userDao;
	private PersonDao personDao;
	private PartnerDao partnerDao;
	private GuestDao guestDao;
	private InvoiceDao invoiceDao;

	public static UserDto user;
	public static PersonDto person;
	public static GuestDto guest;
	public static PartnerDto partner;

	private final long AMOUNT = 50000;
	private final String TYPE = "Regular";
	private final String STATUS = "Activo";
	private final String STATUSINVOICE = "Debe";

	public Service() {
		this.userDao = new UserDaoImplementation();
		this.personDao = new PersonDaoImplementation();
		this.partnerDao = new PartnerDaoImplementation();
		this.guestDao = new GuestDaoImplementation();
		this.invoiceDao = new InvoiceDaoImplementation();
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
			}
			case "invitado": {
				guest = guestDao.findByUserId(user);
			}
		}

		Utils.showMessage("Se ha iniciado sesion");
	}

	@Override
	public boolean logout() {
		user = null;
		partner = null;
		guest = null;
		Utils.showMessage("Se ha cerrado sesion");
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
		int maxAmount = partnerDto.getTypePartner().equals("VIP") ? 5000000 : 1000000;

		if ((partnerDto.getAmountPartner() + amount) <= maxAmount) {
			this.partnerDao.increaseAmount(partnerDto, amount);
			partner = partnerDao.findByIdUser(user);
			Utils.showMessage("Se ha incrementado el monto: " + partner.getAmountPartner());
			return;
		}
		throw new Exception("Monto supera los topes");
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
	public void createInovice(InvoiceDto invoiceDto, Map<Long, InvoiceDetailDto> items) throws Exception {
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
		return invoiceDao.showAllInvoices();
	}
}
