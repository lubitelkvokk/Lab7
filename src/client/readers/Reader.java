package client.readers;


import client.exeptions.InputException;
import mid.data.*;
import mid.data.exceptions.WorldBorderException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

public class Reader implements IReader {
    Scanner scanner;
    public Reader(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyGroup readElement(boolean hasId) throws InputException {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName(readName());
        readCoords(studyGroup);
        studyGroup.setCreationDate(ZonedDateTime.now());
        studyGroup.setStudentsCount(readSudentsCount());
        studyGroup.setShouldBeExpelled(readShouldBeExpelled());
        studyGroup.setTransferredStudents(readTransferredStudents());
        studyGroup.setSemesterEnum(readSemesterEnum());
        studyGroup.setGroupAdmin(readGroupAdmin());
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
            if (checkCtrlD()){
                break;
            }
            String s = scanner.nextLine().trim();
            try {
                try {
                    x = Integer.parseInt(s);
                    semester = Semester.EIGHTH.getSemester(x);
                } catch (Exception e1) {
                    semester = Semester.valueOf(s);
                }
            } catch (Exception e) {
//                System.out.println("Семестр введен неверно");
            }
        }
        return semester;
    }

    private int readSudentsCount() {
        int studentsCount = 0;
        while (studentsCount <= 0) {
            if (checkCtrlD()){
                break;
            }
            try {
                studentsCount = Integer.parseInt(scanner.nextLine().trim());
                if (studentsCount <= 0) {
                    throw new InputException("Значение должно быть больше 0");
                }
            } catch (InputException e) {
//                System.out.println(e.getMessage());
            } catch (Exception e) {
//                System.out.println("Значение должно быть числовым");
            }

        }
        return studentsCount;
    }

    private int readShouldBeExpelled() {
        int shouldBeExpelled = 0;
        while (shouldBeExpelled <= 0) {
            if (checkCtrlD()){
                break;
            }
            try {
                shouldBeExpelled = Integer.parseInt(scanner.nextLine().trim());
                if (shouldBeExpelled <= 0) {
                    throw new InputException("Значение должно быть больше 0");
                }
            } catch (InputException e) {
//                System.out.println(e.getMessage());
            } catch (Exception e) {
//                System.out.println("Значение должно быть числовым");
            }
        }
        return shouldBeExpelled;
    }

    private int readTransferredStudents() {
        int transferredStudents = 0;
        boolean detect = false;
        while (transferredStudents <= 0) {
            if (checkCtrlD()){
                break;
            }
            try {
                transferredStudents = Integer.parseInt(scanner.nextLine().trim());
                if (transferredStudents <= 0) {
                    throw new InputException("Значение должно быть больше 0");
                }
            } catch (InputException e) {
//                System.out.println(e.getMessage());
            } catch (Exception e) {
//                System.out.println("Значение должно быть числовым");
            }
        }
        return transferredStudents;
    }

    private Person readGroupAdmin() {
        Person groupAdmin = new Person();
        groupAdmin.setName(readName());

        //Паспорт

        while (groupAdmin.getPassportID() == null) {
            try {
                if (checkCtrlD()){
                    break;
                }
                String passportID = readLine().trim();
                if (passportID.equals("")){
                    throw new InputException("Паспортные данные должны быть заполнены");
                }
//                if (!collectionChecker.checkPasportID(passportID)) {
//                    throw new CrossingPasportIDException();
//                }
                groupAdmin.setPassportID(passportID);

            } catch (Exception e) {
//                System.out.println(e.getMessage());
            }
        }
        // Рост
        while (groupAdmin.getHeight() == 0) {
            try {
                if (checkCtrlD()){
                    break;
                }
                long height = Long.parseLong(scanner.nextLine().trim());
                groupAdmin.setHeight(height);
            } catch (Exception e) {
//                System.out.println("Значение должно быть числовым");
            }
        }


        //цвет глаз
        while (groupAdmin.getEyeColor() == null) {
            try {
                if (checkCtrlD()){
                    break;
                }
                Color color = Color.valueOf(scanner.nextLine().trim());
                groupAdmin.setEyeColor(color);
            } catch (Exception e) {
//                System.out.println("Ошибка ввода");
            }
        }

        //Данные локации
        Location location = new Location();
        boolean detector = false;
        long x = 0;
        while (!detector) {
            try {
                if (checkCtrlD()){
                    break;
                }
                String res = scanner.nextLine().trim();
                if (res.trim().equals("")) {
                    break;
                }
                x = Long.parseLong(res);
                detector = true;
            } catch (Exception e) {
//                System.out.println("Координаты должны быть целочисленными");
            }
        }
        location.setX(x);

        while (location.getY() == null) {
            try {
                if (checkCtrlD()){
                    break;
                }
                String s = scanner.nextLine().trim();
                if (s.trim().equals("")) {
                    throw new InputException("Значение не может быть null");
                }
                Float y = Float.parseFloat(s);
                location.setY(y);
            } catch (InputException e) {
//                System.out.println(e.getMessage());
            } catch (Exception e) {
//                System.out.println("Значение должно быть целочисленным");
            }
        }

        detector = false;
        int z = 0;
        while (!detector) {
            try {
                if (checkCtrlD()){
                    break;
                }
                String res = scanner.nextLine().trim();
                if (res.trim().equals("")) {
                    break;
                }
                z = Integer.parseInt(res);
                detector = true;
            } catch (Exception e) {
//                System.out.println("Координаты должны быть целочисленными");
            }
        }
        location.setZ(z);
        if (checkCtrlD()){
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
            try {
                if (checkCtrlD()){
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
            } catch (Exception e) {
//                System.out.println("Координаты должны быть целочисленными");
            }
        }
        while (coordinates.getY() == null) {
            try {
                if (checkCtrlD()){

                    break;
                }
                Double y = Double.parseDouble(scanner.nextLine().trim());
                coordinates.setY(y);
            } catch (Exception e) {
//                System.out.println("Координаты должны быть целочисленными");
            }
        }
        studyGroup.setCoordinates(coordinates);
    }


    public String readName() {
        String name = "";
        while (name.equals("")) {
            try {
                if (checkCtrlD()){
                    break;
                }
                name = scanner.nextLine();
                if (name.trim().equals("")) {
                    throw new InputException("Имя не может быть пустой строкой");
                }
            } catch (Exception e) {
//                System.out.println(e.getMessage());
            }
        }
        return name;
    }

    @Override
    public String readCommand() {

        return scanner.next();
    }

    public boolean checkCtrlD(){
        if (!scanner.hasNextLine()){
            return true;
        }
        return false;
    }

    public Stream<String> getStream(){
        return scanner.tokens();
    }

    @Override
    public String readFromFile(String path) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
        return new String(bufferedInputStream.readAllBytes(), "UTF-8");
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}
