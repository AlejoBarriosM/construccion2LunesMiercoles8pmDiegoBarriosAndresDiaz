package app.service;

import app.controller.Utils;
import app.dao.PartnerDaoImplementation;
import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dao.interfaces.*;
import app.dto.*;
import app.model.Partner;
import app.service.interfaces.AdminService;
import app.service.interfaces.LoginService;
import app.service.interfaces.PartnerService;
import app.service.interfaces.UserService;

import java.sql.SQLException;

public class Service implements LoginService, AdminService, UserService, PartnerService {
	private UserDao userDao;
	private PersonDao personDao;
	private PartnerDao partnerDao;
	public static UserDto user;
	public static PersonDto person;
	public static Partner partner;

	public Service() {
		this.userDao = new UserDaoImplementation();
		this.personDao = new PersonDaoImplementation();
		this.partnerDao = new PartnerDaoImplementation();
	}

	@Override
	public void login(UserDto userDto) throws Exception {
		UserDto validateDto = userDao.findByUserName(userDto);
		if (validateDto == null) {
			throw new Exception("Usuario no registrado");
		}
		if (!userDto.getPasswordUser().equals(validateDto.getPasswordUser())) {
			throw new Exception("Usuario o contraseña incorrecto");
		}
		userDto.setRoleUser(validateDto.getRoleUser());
		user = validateDto;
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
	public void createPartner(PartnerDto partnerDto) throws Exception{
		this.partnerDao.createPartner(partnerDto);
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

	private void createPerson(PersonDto personDto) throws Exception {
		if (this.personDao.existsByDocument(personDto)) {
			throw new Exception("Ya existe una persona con ese documento");
		}
		this.personDao.createPerson(personDto);
	}

}
