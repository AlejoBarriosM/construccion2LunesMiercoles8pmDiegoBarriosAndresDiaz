package app.controller.validator;

public class PartnerValidator extends CommonsValidator {

    public Double validAmount(String amount) throws Exception{
        return super.isValidDouble("Los fondos ingresados", amount);
    }



}
