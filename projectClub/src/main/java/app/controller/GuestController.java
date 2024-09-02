package app.controller;


import app.dto.GuestDto;
import app.service.Service;
import app.service.interfaces.GuestService;

public class GuestController extends UserController implements ControllerInterface{

    private final String[] OPTIONS = {"Cambio Tipo Socio", "Consumos", "Cerrar Sesión"};
    private final Service service;
    private final GuestService guestService;
    private GuestDto guestDto;
    private final InvoiceController invoiceController;

    public GuestController() {
        this.service = new Service();
        this.guestService = new Service();
        this.invoiceController = new InvoiceController();
    }

    @Override
    public void session() {
        boolean session = true;
        while (session) {
            session = menuGuest();
        }
    }

    private boolean menuGuest() {
        this.guestDto = Service.guest;

        try {
            int option = Utils.showMenu("Menú Principal", "Bienvenido " + Service.user.getNameUser() + "\nSelecciona una opción:", OPTIONS);
            return options(option);
        } catch (Exception e) {
            Utils.showError(e.getMessage());
            return true;
        }
    }

    private boolean options(int option) throws Exception{
        return switch (option) {
            case 0 -> upgradeToPartner(guestDto);
            case 1 -> {
                this.invoiceController.session();
                yield true;
            }
            case 2 -> Utils.showYesNoDialog("¿Desea cerrar sesión?") && (this.service.logout());
            default -> {
                Utils.showError("Ingrese una opción valida");
                yield true;
            }
        };
    }

    private boolean upgradeToPartner(GuestDto guestDto) throws Exception {
        if (!guestService.pendingInvoices(guestDto)){
            guestService.upgradeToPartner(guestDto);
            Utils.showMessage("¡Felicidades! eres un nuevo socio.\n\nSe cerrará sesión, ingrese de nuevo.");
            return false;
        } else {
            Utils.showError("Tiene facturas pendientes");
        }
        return true;
    }

}
