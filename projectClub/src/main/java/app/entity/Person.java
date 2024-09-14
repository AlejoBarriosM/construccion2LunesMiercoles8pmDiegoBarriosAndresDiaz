package app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idPerson;

    @Column(name = "document", nullable = false)
    private Long documentPerson;

    @Column(name = "name", nullable = false)
    private String namePerson;

    @Column(name = "cellphone", nullable = false)
    private Long cellphonePerson;

    public Person() { }

}
