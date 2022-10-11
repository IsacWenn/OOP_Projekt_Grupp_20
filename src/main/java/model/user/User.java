package model.user;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private static List<User> users;

    private UserInfo userInfo;
    private UserFavorites favorites;

    public User(String username, String password, String email, String name, String lastname, String bio) {
        this.userInfo = new UserInfo(username, password, email, name, lastname, bio);
        this.favorites = new UserFavorites();
    }

    public static List<User> loadUsers() {

    }

    public static void saveUsers() {

    }
}
