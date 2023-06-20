package server.collection.db.services;

import mid.data.*;
import server.collection.db.dao.StudyGroupDAO;
import server.collection.db.util.DbConnector;
import server.exceptions.InputException;

import java.sql.*;
import java.time.ZoneId;
import java.util.LinkedList;

public class StudyGroupService implements StudyGroupDAO {

    private Connection connection = DbConnector.getConnection();

    public StudyGroupService() throws SQLException {
    }

    @Override
    public StudyGroup getStudyGroupById(Integer id) throws SQLException, InputException {
        String sql = "SELECT * FROM study_group WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            throw new InputException("Такой элемент не найден");
        }
        resultSet.next();
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(id);
        PersonService personService = new PersonService();
        studyGroup.setGroupAdmin(personService.getPersonById(resultSet.getInt("groupadmin")));
        studyGroup.setName(resultSet.getString("name"));
        studyGroup.setCoordinates(new Coordinates(
                resultSet.getInt("x"),
                resultSet.getDouble("y")));
        studyGroup.setSemesterEnum(Semester.getSemester(resultSet.getInt("semesterenum")));
        studyGroup.setCreationDate(resultSet.getTimestamp("creationdate").toInstant().atZone(ZoneId.systemDefault()));
        studyGroup.setShouldBeExpelled(resultSet.getInt("shouldbeexpelled"));
        studyGroup.setTransferredStudents(resultSet.getInt("transferredstudents"));
        studyGroup.setStudentsCount(resultSet.getInt("studentscount"));
        return studyGroup;
    }

    @Override
    public void addStudyGroup(StudyGroup studyGroup) throws SQLException {
        String sql = "INSERT INTO study_group (name," +
                " x," +
                " y," +
                " creationdate," +
                " studentscount," +
                " shouldbeexpelled," +
                " transferredstudents," +
                " semesterenum," +
                " groupadmin," +
                "user_id) " +
                "values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, studyGroup.getName());
        preparingStatement(studyGroup, preparedStatement);
        PersonService personService = new PersonService();
        preparedStatement.setInt(9, personService.getPersonIdByPerson(studyGroup.getGroupAdmin()));
        preparedStatement.setInt(10, studyGroup.getUser().getId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        studyGroup.setId(resultSet.getInt(1));
    }

    @Override
    public void removeStudyGroup(StudyGroup studyGroup) {

    }

    @Override
    public void removeStudyGroupById(User user, Integer id) throws SQLException {
        String sql = "DELETE FROM study_group WHERE id=? AND user_id=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, user.getId());
        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Элемента с таким id не найдено или он вам не принадлежит");
        }
    }

    @Override
    public void updateStudyGroup(User user, StudyGroup group) throws SQLException, InputException {
        String sql = "UPDATE study_group SET " +
                "name=?," +
                "x=?," +
                "y=?," +
                "creationdate=?," +
                "studentscount=?," +
                "shouldbeexpelled=?," +
                "transferredstudents=?," +
                "semesterenum=?" +
                "WHERE id=? and user_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, group.getName());

        preparingStatement(group, preparedStatement);
        StudyGroupService studyGroupService = new StudyGroupService();
        StudyGroup previousGroup = studyGroupService.getStudyGroupById(group.getId());
        Integer personId = previousGroup.getId();
        PersonService personService = new PersonService();
        personService.updatePersonById(user, group.getGroupAdmin(), personId);
        preparedStatement.setInt(9, group.getId());
        preparedStatement.setInt(10, user.getId());
        if (preparedStatement.executeUpdate() == 0) {
            throw new InputException("ownerError");
        }

    }

    @Override
    public LinkedList<StudyGroup> getAllStudyGroups() throws SQLException {
        String sql = "SELECT * FROM study_group JOIN person p on p.id = study_group.groupadmin JOIN \"user\" u on u.id = study_group.user_id";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        LinkedList<StudyGroup> result = new LinkedList<>();
        while (resultSet.next()) {
            StudyGroup studyGroup = new StudyGroup();
            studyGroup.setId(resultSet.getInt(1));
            studyGroup.setName(resultSet.getString(2));
            Coordinates coordinates = new Coordinates(resultSet.getLong(3),
                    resultSet.getDouble(4));
            studyGroup.setCoordinates(coordinates);
            studyGroup.setSemesterEnum(Semester.getSemester(resultSet.getInt("semesterenum")));
            studyGroup.setCreationDate(resultSet.getTimestamp("creationdate").toInstant().atZone(ZoneId.systemDefault()));
            studyGroup.setShouldBeExpelled(resultSet.getInt("shouldbeexpelled"));
            studyGroup.setTransferredStudents(resultSet.getInt("transferredstudents"));
            studyGroup.setStudentsCount(resultSet.getInt("studentscount"));

            Person person = new Person();
            person.setName(resultSet.getString(12));
            person.setPassportID(resultSet.getString("passportid"));
            person.setEyeColor(Color.getColorByName(resultSet.getString("eyecolor")));
            Location location = new Location();
            location.setName(resultSet.getString("locationname"));
            location.setX(resultSet.getFloat(17));
            location.setY(resultSet.getFloat(18));
            location.setZ(resultSet.getInt(19));
            person.setLocation(location);
            person.setHeight(resultSet.getLong(20));
            User user = new User();
            user.setLogin(resultSet.getString("login"));
            studyGroup.setUser(user);
            studyGroup.setGroupAdmin(person);
            result.add(studyGroup);
        }
        return result;
    }

    private void preparingStatement(StudyGroup group, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(2, group.getCoordinates().getX());
        preparedStatement.setDouble(3, group.getCoordinates().getY());
        preparedStatement.setTimestamp(4, Timestamp.from(group.getCreationDate().toInstant()));
        preparedStatement.setInt(5, group.getStudentsCount());
        preparedStatement.setInt(6, group.getShouldBeExpelled());
        preparedStatement.setInt(7, group.getTransferredStudents());
        preparedStatement.setInt(8, group.getSemesterEnum().getNumber());
    }


    public Integer getGroupAdminIdByStudyGroupId(Integer id) throws SQLException, InputException {
        String sql = "SELECT groupadmin from study_group WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
            throw new InputException("missingElement");
        }
        resultSet.next();
        return resultSet.getInt(1);
    }


    public void removeFirst(User user) throws SQLException, InputException {
        String sql = "DELETE FROM person WHERE id IN (SELECT groupadmin FROM study_group WHERE user_id=? LIMIT 1)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());

        if (preparedStatement.executeUpdate() == 0) {
            throw new InputException("emptyCollectionException");
        }


    }

}
