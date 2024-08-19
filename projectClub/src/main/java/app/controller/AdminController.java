package app.controller;


import app.service.Service;


public class AdminController extends UserController {

	private PartnerController partnerController;

	private final String ROLE = "administrador";
	private final String[] OPTIONS = {"Usuarios", "Socios", "Salir"};



	public AdminController() {
		this.partnerController = new PartnerController();
		super.setRole(ROLE);
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
			super.menuUser();
			return true;
		}
		case 1: {
			//partnerController.menu();

			return true;
		}
		case 2: {
			System.out.println("2");
			return false;
		}
		default: {
			Utils.showError("ingrese una opcion valida");
			return true;
		}
		}
	}

}
