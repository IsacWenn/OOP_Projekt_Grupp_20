package model.user;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private static String filePath = "src/main/resources/users.dat";
    private static List<User> users;

    private UserInfo userInfo;
    private UserFavorites favorites;

    public User(String username, String password, String email, String name, String lastname, String bio) {
        this.userInfo = new UserInfo(username, password, email, name, lastname, bio);
        this.favorites = new UserFavorites();
    }

    UserInfo getUserInfo() {
        return userInfo;
    }

    UserFavorites getFavorites() {
        return favorites;
    }

    public static void loadUsers() {
        try ( FileInputStream fis = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fis); ){
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userInfo=" + userInfo +
                ", favorites=" + favorites +
                '}';
    }

    public static void saveUsers() {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {


        users = new ArrayList<>();
        users.add(new User("Isac" ,"4", "i@j.se",
                "Isac", "Ingvast Wennerström", ""));
        users.add(new User("Carl", "1", "c@j.se",
                "Carl", "Odqvist", ""));

    }

}
