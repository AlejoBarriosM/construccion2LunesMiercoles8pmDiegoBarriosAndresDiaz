package app.controller;


import app.dto.PartnerDto;
import app.service.Service;

import java.util.Map;


public class AdminController extends UserController implements ControllerInterface {

	private final String[] OPTIONS = {"Usuarios", "Socios", "Historial Facturas", "Promoción VIP", "Cerrar Sesión"};
	private final Service service;
	private final InvoiceController invoiceController;

	public AdminController() {
		this.service = new Service();
		this.invoiceController = new InvoiceController();
	}

	@Override
	public void session(){
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
				this.approvalVIP();
				return true;
			}
			case 4: {
				return Utils.showYesNoDialog("¿Desea cerrar sesión?") && (this.service.logout());
			}
			default: {
				Utils.showError("Ingrese una opción valida");
				return true;
			}
		}
	}

	private void approvalVIP() throws Exception {
		StringBuilder messageBuilder = new StringBuilder();
		Map<Long, PartnerDto> pendingSubscriptions;
		pendingSubscriptions = service.pendingSubscriptions();
		if (!pendingSubscriptions.isEmpty()) {
			approvalList(pendingSubscriptions, messageBuilder);
		}else {
			throw new Exception("No hay ascensos pendientes");
		}

	}

	private void approvalList(Map<Long, PartnerDto> pendingSubscriptions, StringBuilder messageBuilder) throws Exception {
		for (Map.Entry<Long, PartnerDto> entry : pendingSubscriptions.entrySet()){
			messageBuilder.setLength(0);
			PartnerDto partnerDto = entry.getValue();
			messageBuilder.append("Solicitud de usuario: ").append(partnerDto.getIdUserPartner().getIdPerson().getNamePerson());
			messageBuilder.append("\nMonto facturas pagadas: ").append(partnerDto.getAmountPartner());
			messageBuilder.append("\nFecha de ingreso: ").append(partnerDto.getCreationDatePartner());
            messageBuilder.append("\n\n¿Desea aprobar el ascenso?\n\n");
			service.approvalVIP(partnerDto, Utils.showYesNoDialog(messageBuilder.toString()));
		}
	}


}
