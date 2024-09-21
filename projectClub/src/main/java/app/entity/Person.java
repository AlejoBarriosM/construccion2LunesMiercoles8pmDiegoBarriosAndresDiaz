package app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idPerson;

    @Column(name = "document", nullable = false, unique = true)
    private Long documentPerson;

    @Column(name = "name", nullable = false)
    private String namePerson;

    @Column(name = "cellphone", nullable = false)
    private Long cellphonePerson;

}
