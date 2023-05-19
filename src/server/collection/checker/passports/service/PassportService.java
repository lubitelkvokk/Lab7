package server.collection.checker.passports.service;


import server.exceptions.InputException;
import server.collection.checker.passports.storage.PassportStorage;

public class PassportService {

    private PassportStorage passportStorage;

    public PassportService() {
        passportStorage = new PassportStorage();
    }

    public void add(String passport) throws InputException {
        if (passportStorage.hasEqualPassport(passport)){
            throw new InputException("Введены не уникальные паспортные данные");
        }
        passportStorage.add(passport);
    }


    public boolean checkPassport(String passport){
        return passportStorage.hasEqualPassport(passport);
    }

}
