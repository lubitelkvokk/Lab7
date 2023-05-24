package mid.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
        
    }
}
