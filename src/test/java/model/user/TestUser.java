package model.user;

import model.util.GraphRepresentation;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestUser {

    private static List<User> actualUserList;

    private List<User> userList;
    private User testUser;

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
        User.resetUserList();
        User.loadUsers();
        userList = User.getUsers();
        testUser = userList.get(0);
    }

    @Test
    public void constructingAUserShouldAddThatUserToListOfUsers() {
        User dummyUser = new User("", "", "", "", "", "");
        userList = User.getUsers();
        assertEquals(2, userList.size());
        assertEquals(dummyUser, userList.get(1));
    }


    @Test
    public void getUserInfoShouldReturnUserInfo() {
        UserInfo info = testUser.getUserInfo();
        assertEquals("Isac", info.getName());
    }

    @Test
    public void getUserFavoriteInstanceShouldReturnUserInfo() {
        UserFavorites favorites = testUser.getUserFavoriteInstance();
        assertTrue(favorites.getFavorites().isEmpty());
    }

    @Test
    public void getUserNameShouldReturnUserName() {
        assertEquals("IsacWenn", testUser.getUserName());
    }

    @Test
    public void getPasswordShouldReturnPassword() {
        assertEquals("Lösenord", testUser.getPassword());
    }

    @Test
    public void getEmailShouldReturnEmail() {
        assertEquals("i@grupp20.se", testUser.getEmail());
    }

    @Test
    public void getFirstNameShouldReturnFirstName() {
        assertEquals("Isac", testUser.getFirstName());
    }

    @Test
    public void getLastNameShouldReturnLastName() {
        assertEquals("Ingvast Wennerström", testUser.getLastName());
    }

    @Test
    public void getBioShouldReturnBio() {
        assertEquals("python <<<<<<", testUser.getBio());
    }

    @Test
    public void getUserFavoritesShouldReturnListOfFavorites() {
        List<GraphRepresentation> favorites = testUser.getUserFavorites();
        assertTrue(favorites.isEmpty());
    }

    @Test
    public void hashCodeShouldReturnUniqueHashForThatSetOfInstanceVariables() {
        User dummyUser1 = new User("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        assertEquals(testUser.hashCode(), dummyUser1.hashCode());
    }

    @Test
    public void toStringShouldReturnStringRepresentationOfUser() {
        assertEquals("User{userInfo=UserInfo{username='IsacWenn', password='Lösenord', email='i@grupp20.se'," +
                " name='Isac', lastname='Ingvast Wennerström', bio='python <<<<<<'}," +
                " favorites=UserFavorites{[]}}", testUser.toString());
    }

    @Test
    public void equalsWithEmptyOtherObjectTypeShouldReturnFalse() {
        assertNotEquals("User", testUser);
    }

    @Test
    public void equalsWithUserContainingSameValuesShouldReturnTrue() {
        User dummyUser1 = new User("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        assertEquals(testUser, dummyUser1);
    }

    @Test
    public void equalsWithUserNotContainingSameValuesShouldReturnFalse() {
        User dummyUser1 = new User("IsacWenn", "Password", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        assertNotEquals(testUser, dummyUser1);
    }
}
