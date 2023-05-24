package client.readers;


import client.UserAuth;
import client.exeptions.InputException;
import mid.data.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

public class ReaderS implements IReader {
    Scanner scanner;

    public ReaderS(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyGroup readElement(boolean hasId) throws IOException, NullPointerException {
        StudyGroup studyGroup = new StudyGroup();
        if (hasId) {
            int id = Integer.parseInt(readLine().trim());
            studyGroup.setId(id);
        } else {
            scanner.nextLine();
        }
        studyGroup.setName(readName("Введите название группы: "));
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
    public String readLine()  {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }

    private Semester readSemesterEnum() throws InputException, NullPointerException {
        Semester semester = null;
        int x = 0;
        while (semester == null) {
            System.out.printf("Введите один из предложенных семестров" + '\n');
            System.out.printf("\t SECOND(2),\n" +
                    "\t FOURTH(4),\n" +
                    "\t FIFTH(5),\n" +
                    "\t SEVENTH(7),\n" +
                    "\t EIGHTH(8); \n");
            try {
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
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
                    System.out.printf("Семестр введен неверно" + '\n');
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            }
        }
        return semester;
    }

    private int readSudentsCount() throws InputException {
        int studentsCount = 0;
        while (studentsCount <= 0) {

            System.out.print("Введите количество студентов в группе: ");
            if (checkCtrlD()) {
                throw new NullPointerException("Прекращение ввода");
            }
            try {
                studentsCount = Integer.parseInt(scanner.nextLine().trim());
                if (studentsCount <= 0) {
                    throw new InputException("Значение должно быть больше 0");
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (InputException e) {
                System.out.printf(e.getMessage() + '\n');
            } catch (Exception e) {
                System.out.printf("Значение должно быть числовым" + '\n');
            }
        }
        return studentsCount;
    }

    private int readShouldBeExpelled() throws InputException {
        int shouldBeExpelled = 0;
        while (shouldBeExpelled <= 0) {

            System.out.print("Введите количество студентов, которых следует исключить: ");
            if (checkCtrlD()) {
                throw new NullPointerException("Прекращение ввода");
            }
            try {
                shouldBeExpelled = Integer.parseInt(scanner.nextLine().trim());
                if (shouldBeExpelled <= 0) {
                    throw new InputException("Значение должно быть больше 0");
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (InputException e) {
                System.out.printf(e.getMessage() + '\n');
            } catch (Exception e) {
                System.out.printf("Значение должно быть числовым" + '\n');
            }
        }
        return shouldBeExpelled;
    }

    private int readTransferredStudents() throws InputException {
        int transferredStudents = 0;
        boolean detect = false;
        while (transferredStudents <= 0) {

            System.out.print("Введите количество переведенных студентов: ");
            if (checkCtrlD()) {
                throw new NullPointerException("Прекращение ввода");
            }
            try {
                transferredStudents = Integer.parseInt(scanner.nextLine().trim());
                if (transferredStudents <= 0) {
                    throw new InputException("Значение должно быть больше 0");
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (InputException e) {
                System.out.printf(e.getMessage() + '\n');
            } catch (Exception e) {
                System.out.printf("Значение должно быть числовым" + '\n');
            }
        }
        return transferredStudents;
    }

    private Person readGroupAdmin() throws InputException {
        Person groupAdmin = new Person();
        groupAdmin.setName(readName("Введите имя лидера группы: "));

        //Паспорт

        while (groupAdmin.getPassportID() == null) {
            try {
                System.out.print("Введите паспортные данные: ");
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
                }
                String passportID = readLine().trim();
                if (passportID.equals("")) {
                    throw new InputException("Паспортные данные должны быть заполнены");
                }
//                if (!collectionChecker.checkPasportID(passportID)) {
//                    throw new CrossingPasportIDException();
//                }
                groupAdmin.setPassportID(passportID);

            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (Exception e) {
                System.out.printf(e.getMessage() + '\n');
            }
        }
        // Рост
        while (groupAdmin.getHeight() == 0) {
            try {
                System.out.print("Введите рост лидера группы: ");
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
                }
                long height = Long.parseLong(scanner.nextLine().trim());
                groupAdmin.setHeight(height);
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (Exception e) {
                System.out.printf("Значение должно быть числовым" + '\n');
            }
        }


        //цвет глаз
        while (groupAdmin.getEyeColor() == null) {
            System.out.printf("Введите один из предложенных цвет глаз: " + '\n');
            System.out.println("\t RED,\n" +
                    "\t BLACK,\n" +
                    "\t BLUE,\n" +
                    "\t ORANGE");
            try {
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
                }
                Color color = Color.valueOf(scanner.nextLine().trim());
                groupAdmin.setEyeColor(color);
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (Exception e) {
                System.out.printf("Ошибка ввода" + '\n');
            }
        }

        System.out.printf("Введите координаты локации" + '\n');
        //Данные локации
        Location location = new Location();
        boolean detector = false;
        long x = 0;
        while (!detector) {
            try {
                System.out.print("\t x: ");
                if (checkCtrlD()) {
                    break;
                }
                String res = scanner.nextLine().trim();
                if (res.trim().equals("")) {
                    break;
                }
                x = Long.parseLong(res);
                detector = true;
            } catch (Exception e) {
                System.out.printf("Координаты должны быть целочисленными" + '\n');
            }
        }
        location.setX(x);

        while (location.getY() == null) {
            System.out.print("\t y: ");
            try {
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
                }
                String s = scanner.nextLine().trim();
                if (s.trim().equals("")) {
                    throw new InputException("Значение не может быть null");
                }
                Float y = Float.parseFloat(s);
                location.setY(y);
            } catch (InputException e) {
                System.out.printf(e.getMessage() + '\n');
            } catch (Exception e) {
                System.out.printf("Значение должно быть целочисленным" + '\n');
            }
        }

        detector = false;
        int z = 0;
        while (!detector) {
            try {
                System.out.print("\t z: ");
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
                }
                String res = scanner.nextLine().trim();
                if (res.trim().equals("")) {
                    break;
                }
                z = Integer.parseInt(res);
                detector = true;
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (Exception e) {
                System.out.printf("Координаты должны быть целочисленными" + '\n');
            }
        }
        location.setZ(z);
        System.out.print("Введите название локации: ");
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

    private void readCoords(StudyGroup studyGroup) throws InputException {
        System.out.printf("Введите координаты группы: " + '\n');
        Coordinates coordinates = new Coordinates();

        boolean detector = false;
        while (!detector) {
            try {
                System.out.print("\t x: ");
                if (checkCtrlD()) {
                    throw new NullPointerException("Прерывание ввода");
                }
                String res = scanner.nextLine();
                if (res.trim().equals("")) {
                    detector = true;
                    break;
                }
                long x = Long.parseLong(res.trim());
                coordinates.setX(x);
                detector = true;
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (Exception e) {
                System.out.printf("Координаты должны быть целочисленными" + '\n');
            }
        }
        while (coordinates.getY() == null) {
            try {
                System.out.print("\t y: ");
                if (checkCtrlD()) {
                    break;
                }
                Double y = Double.parseDouble(scanner.nextLine().trim());
                coordinates.setY(y);
            } catch (Exception e) {
                System.out.printf("Координаты должны быть целочисленными" + '\n');
            }
        }

        studyGroup.setCoordinates(coordinates);
    }


    public String readName(String msg) throws InputException {
        String name = "";
        while (name.equals("")) {
            try {
                System.out.print(msg);
                if (checkCtrlD()) {
                    throw new NullPointerException("Прекращение ввода");
                }
                name = scanner.nextLine().trim();
                if (name.trim().equals("")) {
                    throw new InputException("Имя не может быть пустой строкой");
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Прекращение ввода");
            } catch (Exception e) {
                System.out.printf(e.getMessage() + '\n');
            }
        }

        return name;
    }

    @Override
    public String readCommand() {

        return scanner.next();
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
    public String readFromFile(String path) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
        return new String(bufferedInputStream.readAllBytes(), "UTF-8");
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }


    @Override
    public User readUser() {
        String login = "";
        readLine();
        while (login.length() < 8) {
            try {
                System.out.print("Введите логин: ");
                login = readLine().trim();
                if (login.length() < 8) {
                    throw new InputException("Длина имени пользователя должен состоять из минимум 8 символов");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        String password = "";
        while (password.length() < 8) {
            try {
                System.out.print("Введите пароль: ");
                password = readLine().trim();
                if (password.length() < 8) {
                    throw new InputException("Длина пароля должна состоять из минимум 8 символов");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return new User(login, password);
    }


}
