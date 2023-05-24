package server.collection.db.services;

import mid.data.StudyGroup;
import mid.data.User;

import java.sql.SQLException;

public class GroupAndPersonService {


    public static void removeStudyGroupsWithGroupAdmin(User user, Integer id) throws SQLException {
        StudyGroupService studyGroupService = new StudyGroupService();
        studyGroupService.removeStudyGroupById(user, id);
        PersonService personService = new PersonService();
        personService.removePersonById(studyGroupService.getGroupAdminIdByStudyGroupId(id));
    }
}
