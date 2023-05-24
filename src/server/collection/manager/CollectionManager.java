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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CollectionManager {
    private static final Logger logger = Logger.getLogger(CollectionManager.class.getName());

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
        studyGroups.add(studyGroup);
        return "Элемент успешно добавлен";
    }

    public synchronized String addIfMin(User user, StudyGroup studyGroup) throws InputException {
        if (Collections.min(studyGroups).compareTo(studyGroup) > -1) {

            return add(user, studyGroup);
        } else {
            throw new InputException("Данный элемент не минимальный");
        }
    }

    public synchronized String removeGreater(User user, StudyGroup studyGroup) throws InputException {
        if (studyGroups.removeIf(x -> x.compareTo(studyGroup) > 0)) {
            return "Удаление прошло успешно";
        } else {
            throw new InputException("Больших элементов не найдено");
        }
    }

    public synchronized String update(User user, StudyGroup studyGroup) {
        studyGroups.stream().filter(x -> studyGroup.getId().equals(x.getId())).findFirst()
                .map(x -> {
                    studyGroups.set(studyGroups.indexOf(x), studyGroup);
                    return "Обновление прошло успешно";
                });
        return "Группы с таким id не найдено";
    }

    public synchronized String removeById(User user, Integer id) throws InputException {

        try {


            GroupAndPersonService.removeStudyGroupsWithGroupAdmin(user, id);
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

    public synchronized String clear(User user) {
        studyGroups = new LinkedList<>();

        return ColorLogger.logInfo("Коллекция успешно очищена!");
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
            throw new NullPointerException("Коллекция пустая");
        }
        return studyGroups.stream()
                .min(Comparator.comparing(StudyGroup::getName))
                .orElse(null);
    }

    public synchronized String removeFirst(User user) throws InputException {
        if (studyGroups.size() == 0) {
            throw new InputException("Коллекция уже пустая");
        } else {
            studyGroups.remove(0);
            return ("Первый элемент коллекции удален.");
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

