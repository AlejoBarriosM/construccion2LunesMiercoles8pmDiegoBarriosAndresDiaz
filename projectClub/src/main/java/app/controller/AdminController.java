package app.controller;


import app.controller.validator.PartnerValidator;
import app.dto.PartnerDto;
import app.service.Service;
import app.service.interfaces.PartnerService;

import javax.swing.*;


public class AdminController extends UserController implements ControllerInterface {

	private PartnerService service;
	private PartnerValidator partnerValidator;

	private final String[] TYPES = {"Regular", "VIP"};
	private final String[] OPTIONS = {"Usuarios", "Socios", "Cerrar Sesion"};

	public AdminController() {
		this.partnerValidator = new PartnerValidator();
		this.service = new Service();
	}

	@Override
	public void session() throws Exception {
		boolean session = true;
		while (session) {
			session = menuAdmin();
		}
	}

	private boolean menuAdmin() {
		try {
        	int option = Utils.showMenu("Menú Principal", "Bienvenido " + Service.user.getNameUser() + "\nSelecciona una opción:", OPTIONS);
			return options(option);
		} catch (Exception e) {
			Utils.showError(e.getMessage());
			return true;
		}
	}

	private boolean options(int option) throws Exception{
		switch (option) {
		case 0: {
			super.menuUser("administrador");
			return true;
		}
		case 1: {
			super.menuUser("socio");
			return true;
		}
		case 2: {
            return Utils.showYesNoDialog("¿Desea cerrar sesión?");
		}
		default: {
			Utils.showError("Ingrese una opcion valida");
			return true;
		}
		}
	}



}
