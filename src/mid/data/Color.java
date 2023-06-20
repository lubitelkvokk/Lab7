package mid.data;

import java.io.Serializable;

public enum Color implements Serializable {
    RED("red"),
    BLACK("black"),
    BLUE("blue"),
    ORANGE("orange");

    private String name;

    Color(String name) {
        this.name = name;
    }

    public static Color getColorByName(String name) {
        for (Color color : Color.values()) {
            if (color.name.equals(name.toLowerCase())) {
//                System.out.println(color);
                return color;
            }
        }
        return null;
    }
}