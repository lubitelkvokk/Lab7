package server.collection.reader;


import server.exceptions.InputException;
import server.collection.checker.CollectionChecker;
import mid.data.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

public class Reader implements IReader {
    Scanner scanner;

    public Reader(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyGroup readElement(CollectionChecker collectionChecker) throws InputException {
        StudyGroup studyGroup = new StudyGroup();
        try {
            scanner.nextLine();
            studyGroup.setName(readName());
            readCoords(studyGroup);
            studyGroup.setCreationDate(ZonedDateTime.now());
            studyGroup.setStudentsCount(readSudentsCount());
            studyGroup.setShouldBeExpelled(readShouldBeExpelled());
            studyGroup.setTransferredStudents(readTransferredStudents());

            studyGroup.setSemesterEnum(readSemesterEnum());
            studyGroup.setGroupAdmin(readGroupAdmin(collectionChecker));

        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }

        return studyGroup;
    }

    @Override
    public String readLine() throws IOException {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }

    private Semester readSemesterEnum() {
        Semester semester = null;
        int x = 0;
        while (semester == null) {
            if (checkCtrlD()) {
                break;
            }
            String s = scanner.nextLine().trim();

            try {
                x = Integer.parseInt(s);
                semester = Semester.EIGHTH.getSemester(x);
            } catch (Exception e1) {
                semester = Semester.valueOf(s);
            }

        }
        return semester;
    }

    private int readSudentsCount() throws InputException {
        int studentsCount = 0;
        while (studentsCount <= 0) {
            if (checkCtrlD()) {
                break;
            }

            studentsCount = Integer.parseInt(scanner.nextLine().trim());
            if (studentsCount <= 0) {
                throw new InputException("Значение должно быть больше 0");
            }
        }
        return studentsCount;
    }

    private int readShouldBeExpelled() throws InputException {
        int shouldBeExpelled = 0;
        while (shouldBeExpelled <= 0) {
            if (checkCtrlD()) {
                break;
            }

            shouldBeExpelled = Integer.parseInt(scanner.nextLine().trim());
            if (shouldBeExpelled <= 0) {
                throw new InputException("Значение должно быть больше 0");
            }

        }
        return shouldBeExpelled;
    }

    private int readTransferredStudents() throws InputException {
        int transferredStudents = 0;
        boolean detect = false;
        while (transferredStudents <= 0) {
            if (checkCtrlD()) {
                break;
            }

            transferredStudents = Integer.parseInt(scanner.nextLine().trim());
            if (transferredStudents <= 0) {
                throw new InputException("Значение должно быть больше 0");
            }

        }
        return transferredStudents;
    }

    private Person readGroupAdmin(CollectionChecker collectionChecker) throws IOException {
        Person groupAdmin = new Person();
        groupAdmin.setName(readName());

        //Паспорт

        while (groupAdmin.getPassportID() == null) {
            if (checkCtrlD()) {
                break;
            }
            String passportID = readLine().trim();
            if (passportID.equals("")) {
                throw new InputException("Паспортные данные должны быть заполнены");
            }

            groupAdmin.setPassportID(passportID);


        }
        // Рост
        while (groupAdmin.getHeight() == 0) {

            if (checkCtrlD()) {
                break;
            }
            long height = Long.parseLong(scanner.nextLine().trim());
            groupAdmin.setHeight(height);

        }


        //цвет глаз
        while (groupAdmin.getEyeColor() == null) {

            if (checkCtrlD()) {
                break;
            }
            Color color = Color.valueOf(scanner.nextLine().trim());
            groupAdmin.setEyeColor(color);

        }

        //Данные локации
        Location location = new Location();
        boolean detector = false;
        long x = 0;
        while (!detector) {

            if (checkCtrlD()) {
                break;
            }
            String res = scanner.nextLine().trim();
            if (res.trim().equals("")) {
                break;
            }
            x = Long.parseLong(res);
            detector = true;

        }
        location.setX(x);

        while (location.getY() == null) {

            if (checkCtrlD()) {
                break;
            }
            String s = scanner.nextLine().trim();
            if (s.trim().equals("")) {
                throw new InputException("Значение не может быть null");
            }
            Float y = Float.parseFloat(s);
            location.setY(y);

        }
        detector = false;
        int z = 0;
        while (!detector) {

            if (checkCtrlD()) {
                break;
            }
            String res = scanner.nextLine().trim();

            if (res.trim().equals("")) {
                break;
            }
            z = Integer.parseInt(res);
            detector = true;


        }

        location.setZ(z);
        if (checkCtrlD()) {
            return groupAdmin;
        }

        String name = scanner.nextLine();
        if (name.trim().equals("")) {
            name = null;
        }

        location.setName(name);
        groupAdmin.setLocation(location);

        return groupAdmin;
    }

    private void readCoords(StudyGroup studyGroup) {
        Coordinates coordinates = new Coordinates();

        boolean detector = false;
        while (!detector) {

            if (checkCtrlD()) {
                break;
            }
            String res = scanner.nextLine();
            if (res.trim().equals("")) {
                detector = true;
                break;
            }
            long x = Long.parseLong(res.trim());
            coordinates.setX(x);
            detector = true;
        }
        while (coordinates.getY() == null) {
            if (checkCtrlD()) {
                break;
            }
            Double y = Double.parseDouble(scanner.nextLine().trim());
            coordinates.setY(y);
        }
        studyGroup.setCoordinates(coordinates);
    }


    public String readName() throws InputException {
        String name = "";
        while (name.equals("")) {
            if (checkCtrlD()) {
                break;
            }
            name = scanner.nextLine();
            if (name.trim().equals("")) {
                throw new InputException("Имя не может быть пустой строкой");
            }
        }
        return name;
    }

    @Override
    public String readCommand() {
        if (checkCtrlD()) {
            return "close";
        }
        String s = scanner.next();
        return s;
    }

    public boolean checkCtrlD() {
        if (!scanner.hasNextLine()) {
            return true;
        }
        return false;
    }

    public Stream<String> getStream() {
        return scanner.tokens();
    }


    @Override
    public Integer readId() throws IOException {
        return null;
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNextLine();
    }
}
