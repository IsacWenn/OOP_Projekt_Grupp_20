package model.user;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {

    private static List<User> actualUserList;

    @BeforeAll
    public static void initializeTestingConditions() {
        User.loadUsers();
        actualUserList = User.getUsers();
        User.resetUserList();
        User user = new User("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        User.saveUsers();
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

    @BeforeEach
    public void resetUserListToTestVariables() {
        User.loadUsers();
        System.out.println(User.getUsers());
    }

    @Test
    public void constructingAUserShouldAddThatUserToListOfUsers() {
        User dummyUser = new User("", "", "", "", "", "");
        List<User> userList = User.getUsers();
        assertEquals(2, userList.size());
        assertEquals(dummyUser, userList.get(1));
    }

    @Test
    public void loadUsersShouldReturnListOfUsersSavedInFile() {
        List<User> ogUserList = new ArrayList<>(User.getUsers());
        User dummyUser = new User("", "", "", "", "", "");
        List<User> newList = User.getUsers();
        assertEquals(2, newList.size());
        assertEquals(dummyUser, newList.get(1));
        User.loadUsers();
        assertEquals(1, User.getUsers().size());
        assertEquals(ogUserList.get(0), User.getUsers().get(0));
    }

}
