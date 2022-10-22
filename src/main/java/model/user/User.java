package model.user;

import model.util.Date;
import model.util.GraphRepresentation;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * A static attribute of the active {@link User} in the application.
     */
    private static User activeUser = null;

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
     * An attribute which defines which type of chart the favorite graph represents.
     */
    private String favoriteChartType;

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
        activeUser = this;
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
        return userInfo.getUsername();
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
     * A getter method for a list of different favorite graphs.
     *
     * @return a {@link List} of {@link GraphRepresentation}s of the favorite graphs.
     */
    public List<GraphRepresentation> getUserFavoriteGraphs() {
        return userFavorites.getFavoriteGraphs();
    }

    /**
     * A getter method for a list of different favorite companies.
     *
     * @return a {@link List} of {@link String}s containing the MICs.
     */
    public List<String> getUserFavoriteCompanies() { return userFavorites.getFavoriteCompanies(); }

    /**
     * A method for adding a new company mic to the list of favorites.
     *
     * @param mic a {@link String} of the new company mic.
     */
    public void addFavoriteCompany(String mic) { userFavorites.addFavoriteCompany(mic); }

    /**
     * A method for adding a new graph to the list of favorites.
     *
     * @param from the starting {@link Date} of the graph.
     * @param to the end {@link Date} of the graph.
     * @param alg a {@link String} representing the chosen algorithm.
     * @param mic a {@link String} containing the company MIC.
     * @param prefCurrency a {@link String} containing the preferred currency.
     */
    public void addFavoriteGraph(Date from, Date to, String alg, String mic, String prefCurrency) {
        userFavorites.addFavoriteGraph(from, to, alg, mic, prefCurrency);
    }

    /**
     * Sets the chart type for the saved graph.
     * @param chartType the name of the saved chart type.
     */
    public void setFavoriteChartType(String chartType) {
        this.favoriteChartType = chartType;
    }

    /**
     * Clears the favorite graphs.
     */
    public void clearFavoriteGraphs(){
        userFavorites.clearFavoriteGraphs();
    }

    /**
     * A method for adding a new graph to the list of favorites.
     *
     * @param graphRep the {@link GraphRepresentation} to be added.
     */
    public void addFavoriteGraph(GraphRepresentation graphRep) { userFavorites.addFavoriteGraph(graphRep); }

    /**
     * A method for removing a favorite company.
     *
     * @param mic the {@link String} of the mic to remove.
     */
    public void removeFavoriteCompany(String mic) { userFavorites.removeFavoriteCompany(mic); }

    /**
     * A method for removing a favorite graph.
     *
     * @param graphRep the {@link GraphRepresentation} to remove.
     */
    public void removeFavoriteGraph(GraphRepresentation graphRep) { userFavorites.removeFavoriteGraph(graphRep); }

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
        List<User> savedUsers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            savedUsers = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        for (User user : savedUsers)
            if (!users.contains(user))
                users.add(user);
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

    /**
     * An implementation of .equals() of Java.
     *
     * @param o an {@link Object} to compare with.
     * @return a {@link Boolean} value of the comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userInfo, user.userInfo) && Objects.equals(userFavorites, user.userFavorites);
    }

    /**
     * An implementation of hashCode that creates a unique hash based on the instance attributes.
     *
     * @return An {@link Integer} hash of the different instance values.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userInfo, userFavorites);
    }

    /**
     * The method for user login in our application.
     *
     * @param username a {@link String} of the username.
     * @param password a {@link String} of the password.
     * @return the {@link Boolean} true if login was a success, otherwise returns false.
     */
    public static boolean loginUser(String username, String password) {
        User selected = null;
        for (User user : users)
            if (Objects.equals(user.getUserName(), username))
                selected = user;
        if (selected != null && Objects.equals(selected.getPassword(), password)) {
            activeUser = selected;
            return true;
        }
        return false;
    }

    /**
     * A method that checks if the application has a user logged in.
     *
     * @return a {@link Boolean} value of the question.
     */
    public static boolean isLoggedIn() { return activeUser != null; }

    /**
     * A getter method for the active user in the application.
     *
     * @return the active {@link User} in the application.
     */
    public static User getActiveUser() { return activeUser; }

    /**
     * A method that logs the active user out.
     */
    public static void logoutActiveUser() { activeUser = null; }

    /**
     * Fetches the favorite chart type.
     * @return {@link String} for favorite chart type.
     */
    public String getFavoriteChartType() {
        return favoriteChartType;
    }
}
