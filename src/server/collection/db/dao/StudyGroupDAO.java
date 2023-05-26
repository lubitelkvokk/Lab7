package server.collection.db.dao;

import mid.data.StudyGroup;
import mid.data.User;
import server.exceptions.InputException;

import java.sql.SQLException;
import java.util.LinkedList;

public interface StudyGroupDAO {


    StudyGroup getStudyGroupById(Integer id) throws SQLException, InputException;

    void addStudyGroup(StudyGroup studyGroup) throws SQLException;

    void removeStudyGroup(StudyGroup studyGroup);

    void removeStudyGroupById(User user, Integer id) throws SQLException;


    void updateStudyGroup(User user, StudyGroup group) throws SQLException, InputException;

    LinkedList<StudyGroup> getAllStudyGroups() throws SQLException;
}
