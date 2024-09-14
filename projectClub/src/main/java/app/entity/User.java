package app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personId", nullable = false)
    private Person idPerson;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String passwordUser;

    @Column(name = "role", nullable = false)
    private String roleUser;

    public User() { }

}
