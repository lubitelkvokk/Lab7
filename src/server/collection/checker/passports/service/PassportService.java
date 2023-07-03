package server.collection.checker.passports.service;


import server.collection.checker.passports.storage.PassportStorage;
import server.exceptions.InputException;

public class PassportService {

    private PassportStorage passportStorage;

    public PassportService() {
        passportStorage = new PassportStorage();
    }

    public void add(String passport) throws InputException {
        if (passportStorage.hasEqualPassport(passport)){
            throw new InputException("notUniquePassportData");
        }
        passportStorage.add(passport);
    }


    public boolean checkPassport(String passport){
        return passportStorage.hasEqualPassport(passport);
    }

}
