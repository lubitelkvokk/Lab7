package server.collection.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.collection.converter.CustomConverter;
import mid.data.StudyGroup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;

public class Writer {



    Gson gson;

    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(StudyGroup.class, new CustomConverter());
        gson = builder.create();
    }


    public String writeInFile(String path, LinkedList<StudyGroup> studyGroups)  {
        try {
            File file = new File(path);
            if (!file.canWrite()) {
                throw new NoSuchFileException("Недостаточно прав для записи в файл");
            }
            BufferedWriter bf = new BufferedWriter(new FileWriter(System.getenv("KVOKKA")));
            bf.write(gson.toJson(studyGroups));
            bf.close();
            return ("Коллекция успешно сохранена!");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
