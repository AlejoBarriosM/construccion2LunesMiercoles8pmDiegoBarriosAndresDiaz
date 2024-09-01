package app.controller.validator;


public class InvoiceValidator extends CommonsValidator {

    public void validDesciption (String description) throws Exception{
        super.isValidString("La descripción del producto: ", description);
    }

    public Double validItemValue (String itemNumber) throws Exception{
        return super.isValidDouble("El valor del item: ", itemNumber);
    }

    public long validItemNumber (String itemValue) throws Exception{
        return super.isValidLong("El número del item: ", itemValue);
    }


}
