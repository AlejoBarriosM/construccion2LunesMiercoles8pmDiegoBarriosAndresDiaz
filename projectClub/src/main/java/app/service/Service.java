package app.service;

import app.controller.Utils;
import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dao.interfaces.*;
import app.dto.*;
import app.service.interfaces.AdminService;
import app.service.interfaces.LoginService;
import app.service.interfaces.UserService;

import java.sql.Date;
import java.sql.SQLException;

public class Service implements LoginService, AdminService, UserService {
	private UserDao userDao;
	private PersonDao personDao;
	public static UserDto user;


	public Service() {
		this.userDao = new UserDaoImplementation();
		this.personDao = new PersonDaoImplementation();
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

	private void createUser(UserDto userDto, PersonDto personDto) throws Exception {
		this.createPerson(personDto);
		personDto = personDao.findByDocument(personDto);
		userDto.setIdPerson(personDto);
		if (this.userDao.existsByUserName(userDto)) {
			this.personDao.deletePerson(userDto.getIdPerson());
			throw new Exception("USERNAME ya está siendo usado");
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


//	@Override
//	public void createClinicalHistory(ClinicalHistoryDto clinicalHistoryDto) throws Exception {
//		PetDto petDto = petDao.findById(clinicalHistoryDto.getPetId());
//		if (petDto == null) {
//			throw new Exception("no existe una mascota con ese id");
//		}
//		clinicalHistoryDto.setPetId(petDto);
//		clinicalHistoryDto.setVeterinarian(user);
//		OrderDto orderDto = this.createOrder(clinicalHistoryDto);
//		clinicalHistoryDto.setOrderId(orderDto);
//		clinicalHistoryDao.createClinicalHistory(clinicalHistoryDto);
//	}
//
//	@Override
//	public void createPet(PetDto petDto) throws Exception {
//		PersonDto personDto = personDao.findByDocument(petDto.getOwnerId());
//		if (personDto == null) {
//			throw new Exception("no existe un dueño registrado con esa cedula");
//		}
//		petDto.setOwnerId(personDto);
//		petDao.createPet(petDto);

//	}
//	@Override
//	public void createOwner(PersonDto personDto) throws Exception {
//		this.createPerson(personDto);
//	}
//
//	@Override
//	public void createSeller(UserDto userDto) throws Exception {
//		this.createUser(userDto);
//	}
//
//	@Override
//	public void createVeterinarian(UserDto userDto) throws Exception {
//		this.createUser(userDto);

//	}

//	private OrderDto createOrder(ClinicalHistoryDto clinicalHistoryDto) throws Exception {
//		OrderDto orderDto = new OrderDto();
//		orderDto.setDate(new Date(clinicalHistoryDto.getDate()));
//		orderDto.setPetId(clinicalHistoryDto.getPetId());
//		orderDto.setOwnerId(clinicalHistoryDto.getPetId().getOwnerId());
//		orderDto.setMedicine(clinicalHistoryDto.getMedicine());
//		orderDto.setDose(clinicalHistoryDto.getDose());
//		orderDto.setVeterinarian(clinicalHistoryDto.getVeterinarian());
//		orderDao.createOrder(orderDto);
//		return orderDto;
//	}

}
