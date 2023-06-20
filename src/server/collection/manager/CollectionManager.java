package server.collection.manager;

import mid.data.StudyGroup;
import mid.data.User;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.commands.instance.with_content.with_group.*;
import mid.fabrics.commands.instance.with_content.with_int.*;
import mid.fabrics.commands.instance.without_content.*;
import mid.logger.ColorLogger;
import server.collection.checker.CollectionChecker;
import server.collection.db.services.GroupAndPersonService;
import server.collection.db.services.PersonService;
import server.collection.db.services.StudyGroupService;
import server.collection.writer.Writer;
import server.exceptions.InputException;
import server.handler.client_processing.UserAuth;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CollectionManager {
    private static final Logger logger = Logger.getLogger(CollectionManager.class.getName());


    private Lock studyGroupsLock = new ReentrantLock();
    private LinkedList<StudyGroup> studyGroups;
    private final CollectionChecker collectionChecker;

    private final ZonedDateTime zonedDateTime;
    private final Command[] allowedCommands;

    private static CollectionManager collectionManager = null;

    private CollectionManager() throws InputException {

        this.allowedCommands = new Command[]{new AddCommand(), new AddIfMinCommand(),
                new UpdateCommand(), new RemoveGreaterCommand(), new RemoveByIdCommand(),
                new AverageOfShouldBeExpelledCommand(), new SumExpelledCommand(), new InfoCommand(),
                new MinByNameCommand(), new ShowCommand(), new ClearCommand(),
                new RemoveFirstCommand(), new HelpCommand()};
        zonedDateTime = ZonedDateTime.now();

        try {
            this.studyGroups = new StudyGroupService().getAllStudyGroups();
        } catch (Exception e) {
            studyGroups = new LinkedList<>();
            logger.warning(e.getMessage());
            throw new InputException("В настоящее время база данных не отвечает");
        } finally {
            this.collectionChecker = new CollectionChecker(studyGroups);
        }
    }

    public static synchronized CollectionManager getInstance() throws InputException {
        if (collectionManager == null) {
            collectionManager = new CollectionManager();
        }
        return collectionManager;
    }

    public synchronized String add(User user, StudyGroup studyGroup) throws InputException {
        // Проверяем уникальность паспортных данных
        try {
            collectionChecker.addPassport((studyGroup.getGroupAdmin().getPassportID()));
//            studyGroup.setId(collectionChecker.getFreeId());

            PersonService personService = new PersonService();
            personService.addPerson(studyGroup.getGroupAdmin());
            user.setPassword("");
            studyGroup.setUser(user);
            StudyGroupService studyGroupService = new StudyGroupService();
            studyGroupService.addStudyGroup(studyGroup);
//            studyGroups.add(studyGroup);
        } catch (InputException e) {
            throw new InputException(e.getMessage());
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            throw new InputException("Ошибка при добавлении");
        }

        try {
            studyGroupsLock.lock();
            studyGroups.add(studyGroup);
        } finally {
            studyGroupsLock.unlock();
        }
        return "addResult";
    }

    public synchronized String addIfMin(User user, StudyGroup studyGroup) throws InputException {
        if (studyGroups.stream().noneMatch(x -> x.compareTo(studyGroup) > 0)) {
            return add(user, studyGroup);
        } else {
            throw new InputException("addIfMinException");
        }
    }

    public String removeGreater(User user, StudyGroup studyGroup) throws InputException {

        studyGroupsLock.lock();
        try {
            PersonService personService = new PersonService();
            personService.removeGreater(user, studyGroup);
        } catch (SQLException e) {
            throw new InputException("databaseError");
        } finally {
            studyGroupsLock.unlock();
        }
        if (studyGroups.removeIf(x -> x.compareTo(studyGroup) > 0)) {
            collectionChecker.setStorage(studyGroups);
            return "removeGreaterElementsInformation";
        } else {
            throw new InputException("removeGreaterElementsException");
        }
    }


    // TODO
    public String update(User user, StudyGroup studyGroup) throws InputException {

        studyGroupsLock.lock();
        try {
            try {
                StudyGroupService studyGroupService = new StudyGroupService();
                studyGroupService.updateStudyGroup(user, studyGroup);
            } catch (InputException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw new InputException("databaseError");
            }
            studyGroup.setUser(user);
            // TODO В этом месте происходит замена, но почему-то программа доходит до исключения
            return studyGroups.stream().filter(x -> studyGroup.getId().equals(x.getId())).findFirst()
                    .map(x -> {
                        studyGroups.set(studyGroups.indexOf(x), studyGroup);
                        return "editingStudyGroupInformation";
                    }).get();
        } finally {
            studyGroupsLock.unlock();
        }
//        throw new InputException("Группы с таким id не найдено");
    }

    public synchronized String removeById(User user, Integer id) throws InputException {
        try {
            PersonService personService = new PersonService();
            personService.removeStudyGroupById(user, id);
            for (StudyGroup x : studyGroups) {
                if (x.getId().equals(id)) {
                    studyGroups.remove(x);
                    return "Удаление прошло успешно";
                }
            }
            throw new InputException("Такой элемент не найден");
        } catch (SQLException e) {
            throw new InputException(e.getMessage());
        }
    }

    public synchronized Integer averageOfShouldBeExpelled(User user) {
        long sum = studyGroups.stream()
                .mapToLong(StudyGroup::getShouldBeExpelled)
                .sum();
        return (int) ((double) sum / studyGroups.size());
    }

    public synchronized String clear(User user) throws InputException {
        try {
            PersonService personService = new PersonService();
            personService.removeAllPeople(user);
        } catch (SQLException e) {
            throw new InputException("databaseError");
        }

        List<StudyGroup> groupsToRemove = studyGroups.stream()
                .filter(x -> x.getUser().getLogin().equals(user.getLogin()))
                .collect(Collectors.toList());

        studyGroups.removeAll(groupsToRemove);
        collectionChecker.setStorage(studyGroups);
        return "clearResult";
    }

    public synchronized String exit() {
        return "Данный метод недоступен пользователю";
    }

    public synchronized String help(User user) {
        return ColorLogger.logInfo(
                Arrays.stream(allowedCommands)
                        .map(Command::getDescription)
                        .map((String str) -> (ColorLogger.logInfo(str + "\n")))
                        .collect(Collectors.joining()));
    }

    public synchronized String info(User user) {
        return ("Тип коллекции: " +
                studyGroups.getClass().getSimpleName() + "\n" +
                "Количество элементов в коллекции: " +
                studyGroups.size() + "\nДата инициализации: "
                + zonedDateTime);
    }

    public synchronized StudyGroup minByName(User user) {
        StudyGroup minByName = null;
        if (studyGroups.isEmpty()) {
            throw new NullPointerException("emptyCollectionException");
        }
        return studyGroups.stream()
                .min(Comparator.comparing(StudyGroup::getName))
                .orElse(null);
    }

    public synchronized String removeFirst(User user) throws InputException {
        try {
            GroupAndPersonService.removeFirstGroup(user);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InputException("deleteException");
        }
        if (studyGroups.size() == 0) {
            throw new InputException("emptyCollectionException");
        } else {
            studyGroups.remove(0);
            collectionChecker.setStorage(studyGroups);
            return ("firstElementDeletingInformation");
        }
    }

    public synchronized String save(User user) {
        Writer writer = new Writer();
        writer.writeInFile(System.getenv("KVOKKA"), studyGroups);
        return "Коллекция успешно сохранена";
    }

    public synchronized LinkedList<StudyGroup> show(User user) {
        return studyGroups;
    }

    public synchronized Integer sumOfShouldBeExpelled(User user) {
        int sum = 0;
        for (StudyGroup x : studyGroups) {
            sum += x.getShouldBeExpelled();
        }
        return sum;

    }
}

