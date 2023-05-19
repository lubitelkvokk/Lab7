package client.readers;

import mid.data.StudyGroup;

import java.io.IOException;
import java.util.stream.Stream;

public interface IReader {

    String readCommand() throws IOException;

    StudyGroup readElement(boolean hasId) throws IOException;

    String readLine() throws IOException;

    void close() throws IOException;

    public Stream<String> getStream();

    public String readFromFile(String path) throws IOException;

    boolean hasNextLine();
}
