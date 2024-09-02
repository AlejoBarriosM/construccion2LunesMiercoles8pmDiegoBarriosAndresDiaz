package app;


import app.controller.ControllerInterface;
import app.controller.LoginController;
import app.controller.Utils;

public class App {
    public static void main(String[] args) {
        ControllerInterface controller = new LoginController();
        try {
            controller.session();
        } catch (Exception e) {
            Utils.showError(e.getMessage());
        }
    }

}