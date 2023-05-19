package server.collection.reader;

import mid.data.StudyGroup;
import server.collection.checker.CollectionChecker;
import server.exceptions.InputException;

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
