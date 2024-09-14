package app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "guest")
@Data
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idGuest;

    @JoinColumn(name = "userid")
    @OneToOne(cascade = CascadeType.ALL)
    private User userIdGuest;

    @JoinColumn(name = "partnerId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Partner partnerIdGuest;

    @Column(name = "status", nullable = false)
    private String statusGuest;

    public Guest() {
    }

}

