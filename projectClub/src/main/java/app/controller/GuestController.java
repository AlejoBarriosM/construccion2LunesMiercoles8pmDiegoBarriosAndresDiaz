package app.controller;


import app.service.Service;

public class GuestController extends UserController implements ControllerInterface{

    private final String[] OPTIONS = {"Cambio Tipo Socio", "Consumos", "Cerrar Sesion"};

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menuGuest();
        }
    }

    private boolean menuGuest() {
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
                return Utils.showYesNoDialog("Historial Facturas");
            }
            case 3: {
                return Utils.showYesNoDialog("Promoción VIP");
            }
            case 4: {
                return Utils.showYesNoDialog("¿Desea cerrar sesión?");
            }
            default: {
                Utils.showError("Ingrese una opcion valida");
                return true;
            }
        }
    }

}
