package server.collection.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import mid.data.StudyGroup;
import server.exceptions.InputException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.logging.Logger;

public class ParsingCollection {

    private static final Logger logger = Logger.getLogger(ParsingCollection.class.getName());

    Gson gson;

    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(StudyGroup.class, new CustomConverter());
        gson = builder.create();
    }

    public LinkedList<StudyGroup> parseCollectionFromFile(String path) throws IOException {
        if (path == null) {
            throw new NullPointerException("Переменной окружения с таким названием не существует");
        }
        File file = new File(path);
        try {
            if (!file.canRead()) {
                throw new InputException("Недостаточно прав для чтения из файла");
            }
        } catch (InputException e) {
            logger.warning(e.getMessage());
            System.exit(1);
        }

        String s = readFromFile(path);

        LinkedList<StudyGroup> studyGroups = new LinkedList<>();
        Type t = new TypeToken<LinkedList<StudyGroup>>() {
        }.getType();
        try {
            studyGroups = gson.fromJson(s, t); //TODO Как происходит каст? Почему без бубна TreeMap? https://www.baeldung.com/gson-list
        } catch (Exception e) {
            logger.warning("Неверный формат JSON");
        }
        return studyGroups;
    }

    public String readFromFile(String path) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
        return new String(bufferedInputStream.readAllBytes(), "UTF-8");
    }


}
