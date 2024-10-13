package app.service;

import app.entity.Person;
import app.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void updateById(long idPerson, Object fields) {
        Person person = personRepository.findById(idPerson).orElseThrow(()-> new EntityNotFoundException("Persona no existe"));
        Map<String, Object> fieldsMap = (Map<String, Object>) fields;
        fieldsMap.forEach((k, v) -> {
            switch (k) {
                case "documentPerson":
                    if (personRepository.existsByDocumentPerson(((Integer) v).longValue())) {
                        throw new EntityNotFoundException("Documento ya existe");
                    }
                    person.setDocumentPerson(((Integer) v).longValue());
                    break;
                case "namePerson":
                    person.setNamePerson((String) v);
                    break;
                case "cellphonePerson":
                    person.setCellphonePerson(((Integer) v).longValue());
                    break;
                default:
                    throw new IllegalArgumentException("Campo invalido: " + k);
            }
        });
        personRepository.save(person);
    }

    public void deleteById(long idPerson) {
        Person person = personRepository.findById(idPerson).orElseThrow(()-> new EntityNotFoundException("Persona no existe"));
        try {
            personRepository.deleteById(person.getIdPerson());
        } catch (Exception e) {
            throw new EntityNotFoundException("No se puede eliminar la persona " + e);
        }
    }
}
