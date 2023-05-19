package mid.data;



import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private int shouldBeExpelled; //Значение поля должно быть больше 0
    private int transferredStudents; //Значение поля должно быть больше 0

    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup( String name,
                      Coordinates coordinates,
                      ZonedDateTime creationDate,
                      int studentsCount, int shouldBeExpelled,
                      int transferredStudents,
                      Semester semesterEnum, Person groupAdmin) {
//        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.transferredStudents = transferredStudents;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;

    }

    public StudyGroup() {

    }



    @Override
    public int compareTo(StudyGroup o) {
        return this.studentsCount - o.studentsCount;
    }


    @Override
    public String toString() {
        return "{" +
                "\n\t id=" + id +
                ",\n\t name='" + name + '\'' +
                ",\n\t coordinates=" + coordinates +
                ",\n\t creationDate=" + creationDate +
                ",\n\t studentsCount=" + studentsCount +
                ",\n\t shouldBeExpelled=" + shouldBeExpelled +
                ",\n\t transferredStudents=" + transferredStudents +
                ",\n\t semesterEnum=" + semesterEnum +
                ",\n\t groupAdmin=" + groupAdmin +
                "\n\t}\n";
    }

    public boolean check() {
        return (name != null && coordinates != null && groupAdmin != null && semesterEnum != null);
    }
}

