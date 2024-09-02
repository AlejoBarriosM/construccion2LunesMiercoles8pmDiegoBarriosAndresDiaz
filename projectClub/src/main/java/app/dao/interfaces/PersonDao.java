package app.dao.interfaces;

import app.dto.PersonDto;

public interface PersonDao {

    boolean existsByDocument(PersonDto personDto) throws Exception;
    void createPerson(PersonDto personDto) throws Exception;
    void deletePerson(PersonDto personDto) throws Exception;
    PersonDto findByDocument(PersonDto personDto) throws Exception;
    PersonDto findById(Long id) throws Exception;

}
