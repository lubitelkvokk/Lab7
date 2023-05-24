package server.collection.db.dao;

import mid.data.StudyGroup;
import mid.data.User;

import java.sql.SQLException;
import java.util.LinkedList;

public interface StudyGroupDAO {


    StudyGroup getStudyGroupById(Integer id) throws SQLException;

    void addStudyGroup(StudyGroup studyGroup) throws SQLException;

    void removeStudyGroup(StudyGroup studyGroup);

    void removeStudyGroupById(User user, Integer id) throws SQLException;

    void updateStudyGroup(StudyGroup group) throws SQLException;


    LinkedList<StudyGroup> getAllStudyGroups() throws SQLException;
}
