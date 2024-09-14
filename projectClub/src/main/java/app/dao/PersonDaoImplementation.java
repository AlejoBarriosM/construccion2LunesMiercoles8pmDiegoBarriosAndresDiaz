package app.dao;

import app.dao.interfaces.PersonDao;
import app.dto.PersonDto;
import app.helpers.Helper;
import app.entity.Person;
import app.repository.PersonRepository;


public class PersonDaoImplementation implements PersonDao {

    private PersonRepository personRepository;

    @Override
    public boolean existsByDocument(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        return personRepository.existsByDocumentPerson(person.getDocumentPerson());
    }

    @Override
    public void createPerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        personRepository.save(person);
    }

    @Override
    public void deletePerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        personRepository.delete(person);
    }

    @Override
    public PersonDto findByDocument(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        return Helper.parse(personRepository.findByDocumentPerson(person.getDocumentPerson()));
    }

    @Override
    public PersonDto findById(Long id) throws Exception {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new Exception("Persona no encontrada"));
        return Helper.parse(person);
    }

}

