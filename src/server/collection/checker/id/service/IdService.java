package server.collection.checker.id.service;


import server.exceptions.InputException;
import server.collection.checker.id.storage.IdStorage;

public class IdService {


    private IdStorage idStorage;

    public IdService() {
        idStorage = new IdStorage();
    }


    public Integer getFreeId() {
        Integer id = (int) (Math.random() * Integer.MAX_VALUE);
        while (idStorage.checkId(id)) {
            id = (int) (Math.random() * Integer.MAX_VALUE);
        }
        return id;
    }


    public void add(Integer id) throws InputException {
        if (idStorage.checkId(id)) {
            throw new InputException("Пересечение ID");
        }
        idStorage.add(id);
    }

    public boolean checkId(Integer id){
        return idStorage.checkId(id);
    }
}
