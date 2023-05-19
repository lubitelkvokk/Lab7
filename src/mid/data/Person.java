package mid.data;


import lombok.Data;

import java.io.Serializable;
@Data
public class Person implements Serializable {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private long height; //Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 21, Длина строки должна быть не меньше 6, Поле не может быть null

    private Color eyeColor; //Поле не может быть null
    private Location location; //Поле не может быть null

    public Color getEyeColor() {
        return eyeColor;
    }



    public void setHeight(long height)  {

        this.height = height;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Person() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public String getName() {
        return name;
    }

    public long getHeight() {
        return height;
    }


    public String getPassportID() {
        return passportID;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setPassportID(String passportID)  {
        this.passportID = passportID;
    }

    @Override
    public String toString() {
        return "Person{" +
                "\n\t\t name='" + name + '\'' +
                ",\n\t\t height=" + height +
                ",\n\t\t passportID='" + passportID + '\'' +
                ",\n\t\t eyeColor=" + eyeColor +
                ", \n\t\t location=" + location +
                "\n\t\t}";
    }


}