package server.collection.db.dao;

import mid.data.Person;

import java.sql.SQLException;

public interface PersonDAO {

    void addPerson(Person person) throws SQLException;
    void removePerson(Person person) throws SQLException;
    Person getPersonByPassportId(String passport) throws SQLException;

    Person getPersonById(Integer id) throws SQLException;

}
