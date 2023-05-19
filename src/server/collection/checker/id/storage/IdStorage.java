package server.collection.checker.id.storage;

import java.util.LinkedList;
import java.util.Optional;

public class IdStorage {

    LinkedList<Integer> listId;

    public IdStorage() {
        listId = new LinkedList<>();
    }

    public LinkedList<Integer> getListId() {
        return listId;
    }

    public boolean checkId(Integer id) {
        Optional<Integer> box = listId.stream().filter(s -> s.equals(id)).findFirst();
        return box.isPresent();
    }

    public void add(Integer id) {
        listId.add(id);
    }
}
