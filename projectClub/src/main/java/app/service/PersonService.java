package app.service;

import app.dto.PersonDto;
import app.dto.mapper.PersonMapper;
import app.entity.Person;
import app.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDto findById(long id) {
        Person person = personRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Persona no existe"));
        return PersonMapper.INSTANCE.ToPersonDto(person);
    }
}
