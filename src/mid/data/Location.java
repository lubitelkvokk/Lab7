package mid.data;

import lombok.Data;

import java.io.Serializable;
@Data
public class Location implements Serializable {
    private float x;
    private Float y; //Поле не может быть null
    private int z;
    private String name; //Длина строки не должна быть больше 797, Поле может быть null

    public Location(float x, Float y, int z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Location() {

    }

    @Override
    public String toString() {
        return "Location{" +
                "\n\t\t\t x=" + x +
                ",\n\t\t\t y=" + y +
                ",\n\t\t\t z=" + z +
                ",\n\t\t\t name='" + name + '\'' +
                "\n\t\t\t}";
    }
}
