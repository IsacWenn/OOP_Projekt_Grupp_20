package model.user;

import model.util.GraphRepresentation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A serializable class for our users in our system
 *
 * @author Isac
 */
public class User implements Serializable {

    /**
     * A static attribute {@link String} holding the file path for our user file.
     */
    private static String filePath = "src/main/resources/users.dat";

    /**
     * A static {@link List} of User:s holding all initialized Users.
     */
    private static List<User> users = new ArrayList<>();

    /**
     * An attribute containing all the standard User information.
     */
    private UserInfo userInfo;

    /**
     * An attribute containing the different favorite graph representations.
     */
    private UserFavorites userFavorites;

    /**
     * A constructor for the class User.
     *
     * @param username A {@link String} containing the Username of a user.
     * @param password A {@link String} containing the password of a user.
     * @param email    A {@link String} containing the email of a user.
     * @param name     A {@link String} containing the firstname of a user.
     * @param lastname A {@link String} containing the lastname of a user.
     * @param bio      A {@link String} containing a biography of a user.
     */
    public User(String username, String password, String email, String name, String lastname, String bio) {
        this.userInfo = new UserInfo(username, password, email, name, lastname, bio);
        this.userFavorites = new UserFavorites();
        users.add(this);
    }

    // Getters

    /**
     * A getter for the userInfo attribute.
     *
     * @return the {@link UserInfo} attribute of the user instance.
     */
    UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * A getter for the userFavorites attribute.
     *
     * @return the {@link UserFavorites} attribute of the user instance.
     */
    UserFavorites getUserFavoriteInstance() {
        return userFavorites;
    }

    /**
     * A getter method for the Username.
     *
     * @return a {@link String} of the username.
     */
    public String getUserName() {
        return userInfo.getName();
    }

    /**
     * A getter method for the password.
     *
     * @return a {@link String} of the password.
     */
    public String getPassword() {
        return userInfo.getPassword();
    }

    /**
     * A getter method for the email address.
     *
     * @return a {@link String} of the email.
     */
    public String getEmail() {
        return userInfo.getEmail();
    }

    /**
     * A getter method for the firstname of the user.
     *
     * @return a {@link String} of the firstname.
     */
    public String getFirstName() {
        return userInfo.getName();
    }

    /**
     * A getter method for the lastname of the user.
     *
     * @return a {@link String} of the lastname.
     */
    public String getLastName() {
        return userInfo.getLastname();
    }

    /**
     * A getter method for the biography of the user.
     *
     * @return a {@link String} of the bio.
     */
    public String getBio() {
        return userInfo.getBio();
    }

    /**
     * A getter method for the list of users.
     *
     * @return a {@link List} of {@link User}s containing all the different users in the system.
     */
    public static List<User> getUsers() {
        return users;
    }

    /**
     * A getter method for a list of different favorites.
     *
     * @return a {@link List} of {@link GraphRepresentation}s of the favorite graphs.
     */
    public List<GraphRepresentation> getUserFavorites() {
        return userFavorites.getFavorites();
    }

    /**
     * A method that sets the List {@link User#users} to an empty {@link ArrayList}.
     */
    static void resetUserList() {
        users = new ArrayList<>();
    }

    /**
     * A method loading all the Users of the file in {@link User#filePath} to the {@link User#users}.
     */
    public static void loadUsers() {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * A method saving all the current Users in the {@link User#users} list to the file given in {@link User#filePath}.
     */
    public static void saveUsers() {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * A toString method for the User class.
     *
     * @return a {@link String} representation of the User class.
     */
    @Override
    public String toString() {
        return "User{" +
                "userInfo=" + userInfo +
                ", favorites=" + userFavorites +
                '}';
    }
    
}
