package model.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * A serializable class containing the basic user data of a user.
 *
 * @author Isac
 */
public class UserInfo implements Serializable {

    /**
     * A {@link String} for the username of a user.
     */
    private String username;
    /**
     * A {@link String} for the password of a user.
     */
    private String password;
    /**
     * A {@link String} for the email address of a user.
     */
    private String email;

    /**
     * A {@link String} for the firstname of the user.
     */
    private String name;
    /**
     * A {@link String} for the lastname of the user.
     */
    private String lastname;
    /**
     * A {@link String} for a biography of the user.
     */
    private String bio;

    /**
     * A constructor of the UserInfo class.
     *
     * @param username A {@link String} of the username.
     * @param password A {@link String} of the password.
     * @param email A {@link String} of the email.
     * @param name A {@link String} of the firstname.
     * @param lastname A {@link String} of the lastname.
     * @param bio A {@link String} of the biography.
     */
    UserInfo(String username, String password, String email, String name, String lastname, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.bio = bio;
    }

    // Getters

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

    /**
     * A getter method for the email.
     *
     * @return A {@link String} of the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * A getter method for the firstname.
     *
     * @return A {@link String} of the firstname.
     */
    public String getName() {
        return name;
    }

    /**
     * A getter method for the lastname.
     *
     * @return A {@link String} of the lastname.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * A getter method for the biography.
     *
     * @return A {@link String} of the biography.
     */
    public String getBio() {
        return bio;
    }

    /**
     * A toString method implementation for the UserInfo class.
     *
     * @return A {@link String} representation of the class.
     */
    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", bio='" + bio + '\'' +
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
        UserInfo userInfo = (UserInfo) o;
        return username.equals(userInfo.username) && password.equals(userInfo.password)
                && email.equals(userInfo.email) && name.equals(userInfo.name)
                && lastname.equals(userInfo.lastname) && Objects.equals(bio, userInfo.bio);
    }

    /**
     * A method that uses the different instance attributes to create a unique hash.
     *
     * @return An {@link Integer} of that hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, name, lastname, bio);
    }
}
