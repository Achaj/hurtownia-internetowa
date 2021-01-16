package main;

import main.entity.User;

public class TemporayUser {
    private static User user;

    public User getCurrentUser(){
        if (user==null){
            System.out.println("nie jesteś zalogowany");
        }
        return user;
    }
    public User setCurrentUser(User user1){
        user=user1;
        return user;
    }
}
