package app.controller;


import app.controller.validator.UserValidator;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.LoginService;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class LoginController implements ControllerInterface{

    private UserValidator userValidator;
    private LoginService service;
    private Map<String,ControllerInterface> roles;

    public LoginController() {
        this.userValidator= new UserValidator();
        this.service=new Service();
        ControllerInterface adminController = new AdminController();
        this.roles= new HashMap<String,ControllerInterface>();
        roles.put("administrador", adminController);
    }

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menu();
        }
    }

    private boolean menu() {
        try {
            this.login();
            return true;
        } catch (Exception e) {
            Utils.showError(e.getMessage());
            return true;
        }
    }

    private void login() throws Exception {

        JTextField usernameField = new JTextField(30);
        JPasswordField passwordField = new JPasswordField(30);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Usuario:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15)); // Espacio entre los campos
        panel.add(new JLabel("Contraseña:"));
        panel.add(passwordField);

        if (Utils.showConfirmDialog(panel, "Inicio Sesión")){
            userValidator.validUserName(usernameField.getText());
            userValidator.validPassword(new String(passwordField.getPassword()));
            UserDto userDto = new UserDto();
            userDto.setPasswordUser(new String(passwordField.getPassword()));
            userDto.setNameUser(usernameField.getText());
            this.service.login(userDto);
            if(roles.get(userDto.getRoleUser())==null) {
                throw new Exception ("Rol invalido");
            }
            roles.get(userDto.getRoleUser()).session();
        } else {
            exit(0);
        }
    }

}
