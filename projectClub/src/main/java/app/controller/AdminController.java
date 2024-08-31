package app.controller;


import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.service.Service;
import jdk.jshell.execution.Util;

import java.util.HashMap;
import java.util.Map;


public class AdminController extends UserController implements ControllerInterface {

	private final String[] OPTIONS = {"Usuarios", "Socios", "Historial Facturas", "Promoción VIP", "Cerrar Sesion"};
	private Service service;

	public AdminController() {
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
				showAllInvoices();
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

	private void showAllInvoices() throws Exception {
		Map<InvoiceDto, Map<Long, InvoiceDetailDto>> invoices = service.showAllInvoices();
		StringBuilder messageBuilder = new StringBuilder();
		Map<Long, String> fields = new HashMap<>();

		for (Map.Entry<InvoiceDto, Map<Long, InvoiceDetailDto>> invoiceEntry : invoices.entrySet()) {
			messageBuilder.setLength(0);
			InvoiceDto invoiceDto = invoiceEntry.getKey();
			Map<Long, InvoiceDetailDto> invoiceDetailDto  = invoiceEntry.getValue();

			messageBuilder.append(invoiceDto.toString()).append("\n");
			messageBuilder.append(showAllDetailInvoice(invoiceDetailDto)).append("\n");
			messageBuilder.append("-------------------------------------------------------------------------------");

			fields.put(invoiceDto.getIdInvoice(),messageBuilder.toString());
		}

		Utils.createPanelWithScroll(fields, "Listado Facturas");
	}

	private String showAllDetailInvoice(Map<Long, InvoiceDetailDto> invoiceDetailDto){
		StringBuilder messageBuilder = new StringBuilder();
		for (Map.Entry<Long, InvoiceDetailDto> detailEntry : invoiceDetailDto.entrySet()) {
			InvoiceDetailDto detail = detailEntry.getValue();
			messageBuilder.append(detail.toString()).append("\n");
		}
		return messageBuilder.toString();
	}



}
