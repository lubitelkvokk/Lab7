package server.collection.reader;


import mid.data.StudyGroup;
import server.collection.checker.CollectionChecker;
import server.exceptions.InputException;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

public class ReaderS implements IReader {
    Scanner scanner;

    @Override
    public String readCommand() {
        return null;
    }

    @Override
    public StudyGroup readElement(CollectionChecker collectionChecker) throws InputException {
        return null;
    }

    @Override
    public String readLine() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public Stream<String> getStream() {
        return null;
    }

    @Override
    public Integer readId() throws IOException {
        return null;
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }
}
