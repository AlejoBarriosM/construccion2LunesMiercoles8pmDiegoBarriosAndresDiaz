package app.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import java.time.LocalDateTime;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idInvoice;

    @JoinColumn(name = "person_id")
    @OnDelete(action = CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    private Person idPerson;

    @JoinColumn(name = "partner_id")
    @OnDelete(action = CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    private Partner idPartner;

    @Column(name = "creation_date", nullable = false)
    private String creationDateInvoice;

    @Column(name = "amount", nullable = false)
    private double amountInvoice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus statusInvoice;

    @PrePersist
    protected void onCreate() {
        this.creationDateInvoice = String.valueOf(LocalDateTime.now());
    }

}

