package app.controller;


import app.service.Service;


public class AdminController extends UserController implements ControllerInterface {

	private final String[] OPTIONS = {"Usuarios", "Socios", "Historial Facturas", "Promoción VIP", "Cerrar Sesion"};
	private Service service;
	private InvoiceController invoiceController;

	public AdminController() {
		this.service = new Service();
		this.invoiceController = new InvoiceController();
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
				super.setRole("administrador");
				super.session();
				return true;
			}
			case 1: {
				super.setRole("socio");
				super.session();
				return true;
			}
			case 2: {
				this.invoiceController.showAllInvoices(null);
				return true;
			}
			case 3: {
				return Utils.showYesNoDialog("Promoción VIP");
			}
			case 4: {
				return Utils.showYesNoDialog("¿Desea cerrar sesión?") || (this.service.logout());
			}
			default: {
				Utils.showError("Ingrese una opcion valida");
				return true;
			}
			}
	}


}
