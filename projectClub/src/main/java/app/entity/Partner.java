package app.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import java.time.LocalDateTime;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
@Table(name = "partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idPartner;

    @JoinColumn(name = "user_id")
    @OnDelete(action = CASCADE)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User idUserPartner;

    @Column(name = "amount", nullable = false)
    private double amountPartner;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Subscription typePartner;

    @Column(name = "creation_date", nullable = false)
    private String creationDatePartner;

    @PrePersist
    protected void onCreate() {
        this.creationDatePartner = String.valueOf(LocalDateTime.now());
    }

}

