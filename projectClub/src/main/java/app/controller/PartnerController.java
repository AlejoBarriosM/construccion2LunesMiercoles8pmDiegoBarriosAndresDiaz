package app.controller;

import app.controller.validator.PartnerValidator;
import app.dto.PartnerDto;
import app.service.Service;
import app.service.interfaces.PartnerService;

import javax.swing.*;

public class PartnerController extends UserController{

    private PartnerService service;
    private PartnerValidator partnerValidator;

    private final String[] TYPES = {"Regular", "VIP"};
    private final String ROLE = "socio";

    public PartnerController() {
        this.partnerValidator = new PartnerValidator();
        this.service = new Service();
    }



    private boolean options(int option) throws Exception{
        switch (option) {
            case 0: {
                //this.createUser();
                return true;
            }
            case 1: {
                System.out.println("Opción 1: fut: modificar");
                return true;
            }
            case 2: {
                System.out.println("Opción 2: fut: eliminar");
                return true;
            }
            case 3: {
                System.out.println("Opción 3: fut: salir");
                return false;
            }
            default: {
                System.out.println("Ingrese una opcion valida");
                return true;
            }
        }
    }




}
