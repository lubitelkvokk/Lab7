package server.collection.db.entities;

import lombok.Data;

@Data
public class User {

    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
