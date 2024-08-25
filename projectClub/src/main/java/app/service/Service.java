package app.service;

import app.controller.Utils;
import app.dao.*;
import app.dao.interfaces.*;
import app.dto.*;
import app.service.interfaces.*;

import java.sql.SQLException;
import java.util.Objects;

public class Service implements LoginService, AdminService, UserService, PartnerService, GuestService {
	private UserDao userDao;
	private PersonDao personDao;
	private PartnerDao partnerDao;
	private GuestDao guestDao;

	public static UserDto user;
	public static PersonDto person;
	public static GuestDto guest;
	public static PartnerDto partner;

	public Service() {
		this.userDao = new UserDaoImplementation();
		this.personDao = new PersonDaoImplementation();
		this.partnerDao = new PartnerDaoImplementation();
		this.guestDao = new GuestDaoImplementation();
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
                partner = partnerDao.findByDocument(user);
			}
			case "invitado": {
				guest = guestDao.findByUserName(user);
			}
		}

		Utils.showMessage("Se ha iniciado sesion");
	}

	@Override
	public void logout() {
		user = null;
		Utils.showMessage("Se ha cerrado sesion");
	}

	@Override
	public void createAdmin(UserDto userDto, PersonDto personDto) throws Exception {
		this.createUser(userDto, personDto);
	}

	@Override
	public void createPartner(PartnerDto partnerDto, UserDto userDto, PersonDto personDto) throws Exception{
		this.createUser(userDto, personDto);
		userDto = userDao.findByUserName(userDto);
		this.partnerDao.createPartner(partnerDto, userDto);
	}

	@Override
	public void createGuest(GuestDto guestDto, UserDto userDto, PersonDto personDto) throws Exception {
		if (this.partnerDao.numberOfGuests(partner) <= 3 && Objects.equals(partner.getTypePartner(), "Regular")){
			throw new Exception("Ya no tiene cupos disponibles");
		} else {
			this.createUser(userDto, personDto);
			userDto = userDao.findByUserName(userDto);
			guestDto.setPartnerIdGuest(partner);
			this.guestDao.createGuest(guestDto, partner, userDto);
		}
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

}
