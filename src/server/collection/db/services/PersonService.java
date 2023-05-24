package server.collection.db.services;

import mid.data.Color;
import mid.data.Location;
import mid.data.Person;
import server.collection.db.dao.PersonDAO;
import server.collection.db.util.DbConnector;

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
        result.setEyeColor(Color.valueOf(resultSet.getString("eyeColor")));
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


    public void removePersonById(Integer id) throws SQLException {
        String sql = "DELETE  FROM person WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }
}
