package client;


import mid.data.User;

public class UserAuth {

    private static User user = null;


    public static void setUser(User data){
        user = data;
    }

    public static User getUser(){
        return user;
    };
}
