package model.user;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {

    private String username;
    private String password;
    private String email;

    private String name;
    private String lastname;
    private String bio;

    UserInfo(String username, String password, String email, String name, String lastname, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.bio = bio;
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBio() {
        return bio;
    }

}
