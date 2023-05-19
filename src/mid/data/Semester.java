package mid.data;

import lombok.Data;

import java.io.Serializable;
public enum Semester implements Serializable {
    SECOND(2),
    FOURTH(4),
    FIFTH(5),
    SEVENTH(7),
    EIGHTH(8);

    private int number;

    Semester(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Semester getSemester(int number) {
        for (Semester x : Semester.values()) {
            if (number == x.getNumber()) {
                return x;
            }
        }
        return null;
    }

}