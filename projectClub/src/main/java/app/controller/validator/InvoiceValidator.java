package app.controller.validator;



public class InvoiceValidator extends CommonsValidator {

    public InvoiceValidator() {
        super();
    }


    public void validTotalAmount(String totalAmount) throws Exception{
        super.isValidDouble("El monto total: ", totalAmount);
    }


    public void validNumberItems (String numberItems) throws Exception{
        super.isValidInteger("Número de ítems:" , numberItems);
    }

    public void validDesciption (String description) throws Exception{
        super.isValidString("La descripción del producto: ", description);
    }

    public void validItemValue (String itemValue) throws Exception{
        super.isValidDouble("El valor del item: ",itemValue);
    }

}
