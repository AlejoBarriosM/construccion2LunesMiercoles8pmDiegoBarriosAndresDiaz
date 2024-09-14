package app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idInvoice;

    @JoinColumn(name = "personId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Person idPerson;

    @JoinColumn(name = "partnerId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Partner idPartner;

    @Column(name = "creationDate", nullable = false)
    private String creationDateInvoice;

    @Column(name = "amount", nullable = false)
    private double amountInvoice;

    @Column(name = "status", nullable = false)
    private String statusInvoice;

    @PrePersist
    protected void onCreate() {
        this.creationDateInvoice = String.valueOf(LocalDateTime.now());
    }

    public Invoice() {  }


}

