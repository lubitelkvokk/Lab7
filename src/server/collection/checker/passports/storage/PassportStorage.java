package server.collection.checker.passports.storage;


import server.exceptions.InputException;

import java.util.LinkedList;
import java.util.Optional;

public class PassportStorage {

    private LinkedList<String> passports;

    public PassportStorage() {
        passports = new LinkedList<>();
    }

    public boolean hasEqualPassport(String passport) {
        Optional<String> samePassport = passports.stream().filter(s -> s.equals(passport)).findFirst();
        if (samePassport.isPresent()) {
            return true;
        }
        return false;
    }

    public LinkedList<String> getPassports() {
        return passports;
    }

    public void add(String passport) throws InputException {
        passports.add(passport);
    }

}
