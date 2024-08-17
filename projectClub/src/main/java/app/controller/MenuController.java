package app.controller;

public class MenuController {

    public void menu() {

        String[] options = {"Opción 1", "Opción 2", "Opción 3", "Salir"};
        int choice = Utils.showMenu("Menú Principal", "Selecciona una opción:", options);


        switch (choice) {
            case 0:
                Utils.showMessage("Has seleccionado la Opción 1");
                break;
            case 1:
                Utils.showMessage("Has seleccionado la Opción 2");
                break;
            case 2:
                Utils.showMessage("Has seleccionado la Opción 3");
                break;
            case 3:
                Utils.showMessage("Saliendo del menú...");
                break;
            default:
                Utils.showMessage("Opción no válida.");
        }
    }
}
