package app.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idUser;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "person_id", nullable = false)
    private Person idPerson;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String passwordUser;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roleUser;

}

