package server.collection.db.services;

import mid.data.StudyGroup;
import mid.data.User;
import server.exceptions.InputException;

import java.sql.SQLException;

public class GroupAndPersonService {


    public static void removeStudyGroupsWithGroupAdmin(User user, Integer id) throws SQLException, InputException {
        PersonService personService = new PersonService();
        personService.removeStudyGroupById(user, id);
    }


    public static void removeFirstGroup(User user) throws SQLException, InputException {
        StudyGroupService studyGroupService = new StudyGroupService();
        studyGroupService.removeFirst(user);
    }
}
