package model.user;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;
import model.util.GraphRepresentation;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    /**
     * A {@link String} for the username of a user.
     */
    private final String username;
    /**
     * A {@link String} for the password of a user.
     */
    private final String password;
    /**
     * A {@link List} of {@link GraphRepresentation}s of the different favorites.
     */
    private List<GraphRepresentation> favorites;

    private static User curUser;

    /**
     * A constructor of the UserV2 class.
     *
     * @param username A {@link String} of the username.
     * @param password A {@link String} of the password.
     */
    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * A getter method for the username
     *
     * @return A {@link String} of the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * A getter method for the password.
     *
     * @return A {@link String} of the password.
     */
    public String getPassword() {
        return password;
    }

    public static User getCurrentUser() {
        return curUser;
    }

    private static void setCurrentUser(User user) {
        curUser = user;
    }

    /**
     * A getter method for the favorites attribute.
     *
     * @return A {@link List} of {@link GraphRepresentation} of the favorites.
     */
    public List<GraphRepresentation> getFavorites() {
        return favorites;
    }

    /**
     * A method that adds a new favorite to {@link User#favorites}.
     *
     * @param interval a {@link List} of {@link Date}s of the specified interval.
     * @param alg a {@link GraphRepresentation} for the algorithm.
     * @param mic a {@link String} for the company mic.
     */
    void addFavorite(List<Date> interval, GraphAlgorithms alg, String mic, String prefCurrency) {
        favorites.add(new GraphRepresentation(interval, alg, mic, prefCurrency));
    }

    /**
     * A method that adds a new favorite to {@link User#favorites}.
     *
     * @param graphRep a {@link GraphRepresentation} to add to the list of favorites.
     */
    void addFavorite(GraphRepresentation graphRep) {
        favorites.add(graphRep);
    }

    public static void signup(String username, String password) {
        if (UsersCollection.userExists(username)) {
            return;
        }
        User user = new User(username, password);
        UsersCollection.addUser(user);
        setCurrentUser(user);
    }

    public static void login(String username, String password) {
        User user = UsersCollection.getUser(username, password);
        if (user != null) {
            setCurrentUser(user);
        }
    }

    public static void logout() {
        setCurrentUser(null);
    }

    public static boolean isLoggedIn() {
        return curUser != null;
    }

    /**
     * A toString method implementation for the UserV2 class.
     *
     * @return A {@link String} representation of the class.
     */
    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * An implementation of the .equals() method in Java.
     *
     * @param o An {@link Object} to compare with.
     * @return A {@link Boolean} value of that comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password);
    }

    /**
     * A method that uses the different instance attributes to create a unique hash.
     *
     * @return An {@link Integer} of that hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
