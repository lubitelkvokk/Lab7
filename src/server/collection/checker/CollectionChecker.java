package server.collection.checker;

import mid.data.StudyGroup;
import server.collection.checker.id.service.IdService;
import server.collection.checker.passports.service.PassportService;
import server.exceptions.InputException;

import java.util.LinkedList;


public class CollectionChecker {

    private PassportService passportService;

    private IdService idService;

    public CollectionChecker() {
        passportService = new PassportService();
        idService = new IdService();
    }

    public CollectionChecker(LinkedList<StudyGroup> studyGroups) throws InputException {
        this();
        setStorage(studyGroups);
    }

    public void setStorage(LinkedList<StudyGroup> studyGroups) throws InputException {
        for (StudyGroup studyGroup : studyGroups) {
            addId(studyGroup.getId());
            addPassport(studyGroup.getGroupAdmin().getPassportID());
        }
    }


    public boolean checkId(Integer id)  {
        return idService.checkId(id);
    }

    public Integer getFreeId() throws InputException {
        Integer id = idService.getFreeId();
        addId(id);
        return id;
    }

    public boolean checkPasportID(String passport)  {
        return passportService.checkPassport(passport);
    }

    public void addId(Integer id) throws InputException {
        idService.add(id);
    }

    public void addPassport(String passport) throws InputException {
        passportService.add(passport);
    }

}
