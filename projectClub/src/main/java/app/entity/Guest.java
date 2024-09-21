package app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idGuest;

    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private User userIdGuest;

    @JoinColumn(name = "partner_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Partner partnerIdGuest;

    @Column(name = "status", nullable = false)
    private String statusGuest;

}

