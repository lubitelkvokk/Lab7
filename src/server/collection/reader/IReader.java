package server.collection.reader;

import server.exceptions.InputException;
import server.collection.checker.CollectionChecker;
import mid.data.StudyGroup;
import java.io.IOException;
import java.util.stream.Stream;

public interface IReader {

    String readCommand() ;

    StudyGroup readElement(CollectionChecker collectionChecker) throws InputException;

    String readLine() throws IOException;

    void close() throws IOException;

    public Stream<String> getStream();

    Integer readId() throws IOException;

    boolean hasNext();
}
