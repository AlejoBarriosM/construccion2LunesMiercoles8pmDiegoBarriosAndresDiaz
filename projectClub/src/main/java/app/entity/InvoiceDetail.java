package app.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "invoiceDetail")
@Data
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idInvoiceDetail;

    @JoinColumn(name = "invoiceid")
    @OneToOne(cascade = CascadeType.ALL)
    private Invoice idInvoice;

    @Column(name = "item", nullable = false)
    private long item;

    @Column(name = "description", nullable = false)
    private String descriptionInvoiceDetail;

    @Column(name = "amount", nullable = false)
    private double amountInvoiceDetail;

    public InvoiceDetail() { }

}