package app.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
@Table(name = "invoice_detail")
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idInvoiceDetail;

    @JoinColumn(name = "invoice_id")
    @OnDelete(action = CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    private Invoice idInvoice;

    @Column(name = "item", nullable = false)
    private long item;

    @Column(name = "description", nullable = false)
    private String descriptionInvoiceDetail;

    @Column(name = "amount", nullable = false)
    private double amountInvoiceDetail;

}