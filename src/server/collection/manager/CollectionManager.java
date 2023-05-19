package server.collection.manager;

import mid.data.StudyGroup;
import mid.fabrics.commands.instance.Command;
import mid.logger.ColorLogger;
import server.collection.checker.CollectionChecker;
import server.collection.writer.Writer;
import server.exceptions.InputException;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class CollectionManager {

    private LinkedList<StudyGroup> studyGroups;
    private final CollectionChecker collectionChecker;

    private final ZonedDateTime zonedDateTime;
    private final Command[] allowedCommands;

    public CollectionManager(Command[] commands, LinkedList<StudyGroup> studyGroups) throws InputException {

        this.allowedCommands = commands;
        zonedDateTime = ZonedDateTime.now();
        // фиктивно формируем коллекцию
        this.studyGroups = studyGroups;
        this.collectionChecker = new CollectionChecker(studyGroups);
    }

    public String add(StudyGroup studyGroup) throws InputException {
        // Проверяем уникальность паспортных данных
        try {
            collectionChecker.addPassport((studyGroup.getGroupAdmin().getPassportID()));
            studyGroup.setId(collectionChecker.getFreeId());
        } catch (InputException e) {
            throw new InputException(e.getMessage());
        }
        studyGroups.add(studyGroup);
        return "Элемент успешно добавлен";
    }

    public String addIfMin(StudyGroup studyGroup) throws InputException {
        if (Collections.min(studyGroups).compareTo(studyGroup) > -1) {
            return add(studyGroup);
        } else {
            throw new InputException("Данный элемент не минимальный");
        }
    }

    public String removeGreater(StudyGroup studyGroup) throws InputException {
        if (studyGroups.removeIf(x -> x.compareTo(studyGroup) > 0)) {
            return "Удаление прошло успешно";
        } else {
            throw new InputException("Больших элементов не найдено");
        }
    }

    public String update(StudyGroup studyGroup) {
        studyGroups.stream().filter(x -> studyGroup.getId().equals(x.getId())).findFirst()
                .map(x -> {
                    studyGroups.set(studyGroups.indexOf(x), studyGroup);
                    return "Обновление прошло успешно";
                });
        return "Группы с таким id не найдено";
    }

    public String removeById(Integer id) {

        studyGroups.stream().filter(x -> x.getId().equals(id)).findFirst().
                map(x -> {
                    studyGroups.remove(x);
                    return "Удаление прошло успешно";
                });

        throw new NullPointerException("Элемент с таким id не найден");
    }

    public Integer averageOfShouldBeExpelled() {
        long sum = studyGroups.stream()
                .mapToLong(StudyGroup::getShouldBeExpelled)
                .sum();
        return (int) ((double) sum / studyGroups.size());
    }

    public String clear() {
        studyGroups = new LinkedList<>();
        return ColorLogger.logInfo("Коллекция успешно очищена!");
    }

    public String exit() {
        return "Данный метод недоступен пользователю";
    }

    public String help() {
        return ColorLogger.logInfo(
                Arrays.stream(allowedCommands)
                        .map(Command::getDescription)
                        .map((String str) -> (ColorLogger.logInfo(str + "\n")))
                        .collect(Collectors.joining()));
    }

    public String info() {
        return ("Тип коллекции: " +
                studyGroups.getClass().getSimpleName() + "\n" +
                "Количество элементов в коллекции: " +
                studyGroups.size() + "\nДата инициализации: "
                + zonedDateTime);
    }

    public StudyGroup minByName() {
        StudyGroup minByName = null;
        if (studyGroups.isEmpty()) {
            throw new NullPointerException("Коллекция пустая");
        }
        return studyGroups.stream()
                .min(Comparator.comparing(StudyGroup::getName))
                .orElse(null);
    }

    public String removeFirst() throws InputException {
        if (studyGroups.size() == 0) {
            throw new InputException("Коллекция уже пустая");
        } else {
            studyGroups.remove(0);
            return ("Первый элемент коллекции удален.");
        }
    }

    public String save() {
        Writer writer = new Writer();
        writer.writeInFile(System.getenv("KVOKKA"), studyGroups);
        return "Коллекция успешно сохранена";
    }

    public LinkedList<StudyGroup> show() {
        return studyGroups;
    }

    public Integer sumOfShouldBeExpelled() {
        int sum = 0;
        for (StudyGroup x : studyGroups) {
            sum += x.getShouldBeExpelled();
        }
        return sum;

    }
}

