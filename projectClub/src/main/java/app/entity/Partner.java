package app.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "partner")
@Data
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPartner;

    @JoinColumn(name = "userid")
    @OneToOne(cascade = CascadeType.ALL)
    private User idUserPartner;

    @Column(name = "amount", nullable = false)
    private double amountPartner;

    @Column(name = "type", nullable = false)
    private String typePartner;

    @Column(name = "creationDate", nullable = false)
    private String creationDatePartner;

    @PrePersist
    protected void onCreate() {
        this.creationDatePartner = String.valueOf(LocalDateTime.now());
    }

    public Partner() {
    }

}

