package app.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idGuest;

    @JoinColumn(name = "user_id")
    @OnDelete(action = CASCADE)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User userIdGuest;

    @JoinColumn(name = "partner_id")
    @OnDelete(action = CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    private Partner partnerIdGuest;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GuestStatus statusGuest;

}

