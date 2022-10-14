package model.user;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {

    private static List<User> actualUserList;

    @BeforeAll
    public static void initializeTestingConditions() {
        User.loadUsers();
        actualUserList = User.getUsers();
    }

    @AfterAll
    public static void loadActualUserList() {
        User.resetUserList();
        actualUserList.forEach( user -> {
            new User(
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBio()
            );
        });
        User.saveUsers();
    }

    @BeforeAll
    public static void resetUserListToTestVariables() {
        User.loadUsers();
    }

    @Test
    public void constructingAUserShouldAddThatUserToListOfUsers() {
        List<User> userList = User.getUsers();
        assertEquals(1, userList.size());
        // assertEquals(user, userList.get(0));
        User.saveUsers();
    }

    @Test
    public void loadUsersShouldReturnListOfUsersSavedInFile() {
        List<User> ogUserList = User.getUsers();
        User dummyUser = new User("", "", "", "", "", "");
        List<User> newList = User.getUsers();
        assertEquals(2, newList.size());
        assertEquals(dummyUser, newList.get(1));
    }

}
