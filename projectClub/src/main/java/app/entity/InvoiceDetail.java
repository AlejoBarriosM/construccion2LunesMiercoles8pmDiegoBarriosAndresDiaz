package app.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "invoice_detail")
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idInvoiceDetail;

    @JoinColumn(name = "invoice_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Invoice idInvoice;

    @Column(name = "item", nullable = false)
    private long item;

    @Column(name = "description", nullable = false)
    private String descriptionInvoiceDetail;

    @Column(name = "amount", nullable = false)
    private double amountInvoiceDetail;

}