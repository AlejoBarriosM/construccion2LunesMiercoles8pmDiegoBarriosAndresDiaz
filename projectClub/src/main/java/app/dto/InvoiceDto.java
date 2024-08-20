
package app.dto;
import java.util.Date;

public class InvoiceDto {
    
    private int id;
    private int idPerson;
    private int idPartner;
    private Date date;
    private double totalAmount;
    private String statusInvoice;


    public InvoiceDto(){
    }
    public int getIdPerson(){return idPerson;}

    public void setIdPerson(int idPerson){this.idPerson = idPerson;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(String statusInvoice) {
        this.statusInvoice = statusInvoice;
    }
    
    
    
    
    
}
