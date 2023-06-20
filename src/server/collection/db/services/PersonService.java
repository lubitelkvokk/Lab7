package server.collection.db.services;

import mid.data.*;
import server.collection.db.dao.PersonDAO;
import server.collection.db.util.DbConnector;
import server.exceptions.InputException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonService implements PersonDAO {

    private Connection connection = DbConnector.getConnection();

    public PersonService() throws SQLException {
    }

    @Override
    public void addPerson(Person person) throws SQLException {
        String sql = "INSERT INTO person (name, passportid, eyecolor, \"locationName\", x, y, z, height) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getPassportID());
        preparedStatement.setString(3, person.getEyeColor().name());
        preparedStatement.setString(4, person.getLocation().getName());
        preparedStatement.setFloat(5, person.getLocation().getX());
        preparedStatement.setFloat(6, person.getLocation().getY());
        preparedStatement.setInt(7, person.getLocation().getZ());
        preparedStatement.setLong(8, person.getHeight());
        preparedStatement.executeUpdate();
    }

    @Override
    public void removePerson(Person person) throws SQLException {
        String sql = "DELETE FROM person where passportid=?  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, person.getPassportID());
        preparedStatement.executeUpdate();
    }

    @Override
    public Person getPersonByPassportId(String passport) throws SQLException {
        String sql = "SELECT * FROM person WHERE passportid=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, passport);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getPerson(resultSet);
    }

    @Override
    public Person getPersonById(Integer id) throws SQLException {
        String sql = "SELECT * FROM person WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getPerson(resultSet);
    }

    private Person getPerson(ResultSet resultSet) throws SQLException {
        Person result = new Person();
        result.setPassportID(resultSet.getString("passportId"));
//        System.out.println(resultSet.getString("eyeColor"));
        result.setEyeColor(Color.getColorByName(resultSet.getString("eyeColor")));
        result.setHeight(resultSet.getLong("height"));
        result.setName(resultSet.getString("name"));
        Location location = new Location();
        location.setX(resultSet.getFloat("x"));
        location.setY(resultSet.getFloat("y"));
        location.setZ(resultSet.getInt("z"));
        location.setName(resultSet.getString("locationName"));
        result.setLocation(location);
        return result;
    }

    public Integer getPersonIdByPassportId(String passport) throws SQLException {
        String sql = "SELECT * FROM person WHERE passportid=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, passport);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    public Integer getPersonIdByPerson(Person person) throws SQLException {
        String sql = "SELECT * FROM person WHERE passportid=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, person.getPassportID());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    public void removeStudyGroupById(User user, Integer id) throws SQLException, InputException {
        String sql = "DELETE  FROM person WHERE id IN (SELECT groupadmin FROM study_group WHERE id=? AND user_id=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, user.getId());
        if (preparedStatement.executeUpdate() == 0) {
            throw new InputException("Элемента из вашей коллекции с таким id не было найдено");
        }
        ;
    }

    public void removeGreater(User user, StudyGroup studyGroup) throws SQLException, InputException {
        String sql = "DELETE FROM person WHERE id IN (SELECT groupadmin FROM study_group WHERE user_id=? AND studentscount > ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setInt(2, studyGroup.getStudentsCount());
        if (preparedStatement.executeUpdate() == 0) {
            throw new InputException("removeGreaterElementsException");

        }
    }

    public void updatePersonById(User user, Person person, Integer id) throws SQLException, InputException {
        String sql = "UPDATE person SET " +
                "name=?," +
                "passportid=?," +
                "eyecolor=?," +
                " \"locationName\" =?," +
                "x=?," +
                "y=?," +
                "z=?," +
                "height=? " +
                "WHERE id in (SELECT groupadmin FROM study_group WHERE id=? AND user_id=? )";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(9, id);
        preparedStatement.setInt(10, user.getId());
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getPassportID());
        preparedStatement.setString(3, person.getEyeColor().name());
        preparedStatement.setString(4, person.getLocation().getName());
        preparedStatement.setDouble(5, person.getLocation().getX());
        preparedStatement.setDouble(6, person.getLocation().getY());
        preparedStatement.setInt(7, person.getLocation().getZ());
        preparedStatement.setLong(8, person.getHeight());
        if (preparedStatement.executeUpdate() == 0) {
            throw new InputException("Элемента с таким id не найдено");
        }
    }

    public void removeAllPeople(User user) throws SQLException {
        String sql = "DELETE FROM person WHERE id IN (SELECT groupadmin FROM study_group" +
                " WHERE user_id=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }
}
