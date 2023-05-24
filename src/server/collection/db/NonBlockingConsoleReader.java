package server.collection.db;

import mid.data.*;
import server.collection.db.services.PersonService;
import server.collection.db.services.StudyGroupService;
import server.collection.db.services.user.UserService;

import javax.management.timer.Timer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;

public class NonBlockingConsoleReader {
    public static void main(String[] args) throws Exception {
//        UserService userService = new UserService();
//        ;
//        System.out.println(userService.loginUser(new User("VASYAAAAA", "bibaboba")));
        PersonService personService = new PersonService();
        Person person = new Person();
        person.setHeight(123);
        person.setName("malchik");
        person.setLocation(new Location(1F, 2.0F, 3, "forest"));
        person.setEyeColor(Color.BLACK);
        person.setPassportID("12345678976543");
        personService.addPerson(person);
        StudyGroupService studyGroupService = new StudyGroupService();
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setUser(new User("vasya", "bibaboba"));
        studyGroup.setGroupAdmin(person);
        studyGroup.setCreationDate(ZonedDateTime.now());
        studyGroup.setStudentsCount(123);
        studyGroup.setSemesterEnum(Semester.EIGHTH);
        studyGroup.setTransferredStudents(123);
        studyGroup.setName("newGROUP");
        studyGroup.setShouldBeExpelled(123);
        studyGroup.setCoordinates(new Coordinates(1,2.0));
        studyGroupService.addStudyGroup(studyGroup);
//        System.out.println(studyGroupService.getAllStudyGroups());
    }
}
