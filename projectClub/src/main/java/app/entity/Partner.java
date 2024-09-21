package app.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idPartner;

    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private User idUserPartner;

    @Column(name = "amount", nullable = false)
    private double amountPartner;

    @Column(name = "type", nullable = false)
    private String typePartner;

    @Column(name = "creation_date", nullable = false)
    private String creationDatePartner;

    @PrePersist
    protected void onCreate() {
        this.creationDatePartner = String.valueOf(LocalDateTime.now());
    }

}

