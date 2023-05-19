package mid.data;



import lombok.Data;

import java.io.Serializable;
@Data
public class Coordinates implements Serializable {
    private long x;
    private Double y; //Значение поля должно быть больше -909, Поле не может быть null

    public Coordinates(long x, Double y)  {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {

    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}