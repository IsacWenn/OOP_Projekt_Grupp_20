package model.user;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersCollection {

    /**
     * A static attribute {@link String} holding the file path for our user file.
     */
    private static final String filePath = "src/main/resources/users.dat";

    /**
     * A static {@link List} of User:s holding all initialized Users.
     */
    private static final List<User> users = loadUsers();

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUser(String username, String password) {
        for (User user : users) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public static boolean userExists(String username) {
        return getUser(username) != null;
    }

    /**
     * A method loading all the Users of the file in {@link UsersCollection#filePath} to the {@link UsersCollection#users}.
     */
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            users.addAll((List<User>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(users.size());
        return users;
    }

    /**
     * A method saving all the current Users in the {@link UsersCollection#users} list to the file given in {@link UsersCollection#filePath}.
     */
    public static void saveUsers() {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
