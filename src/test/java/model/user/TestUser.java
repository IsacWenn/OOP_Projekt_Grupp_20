package model.user;

import model.util.Date;
import model.util.GraphRepresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import javax.print.DocFlavor;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {

    private static List<User> actualUserList;
    private static GraphRepresentation graphRep;

    private List<User> userList;
    private User testUser;
    @BeforeAll
    public static void initializeTestingConditions() throws IOException {
        User.loadUsers();
        actualUserList = User.getUsers();
        User.resetUserList();
        User user = new User("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");

        graphRep = new GraphRepresentation(
                new ArrayList<>(){{ add(new Date(2022, 10, 15)); }},
                "Daily Closing",
                "AMZN",
                "USD"
        );
        user.addFavoriteGraph(graphRep);
        user.addFavoriteCompany("AMZN");
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
        assertEquals(graphRep, favorites.getFavoriteGraphs().get(0));
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
    public void hashCodeShouldReturnUniqueHashForThatSetOfInstanceVariables() {
        User dummyUser1 = new User("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        dummyUser1.addFavoriteGraph(graphRep);
        dummyUser1.addFavoriteCompany("AMZN");
        assertEquals(testUser.hashCode(), dummyUser1.hashCode());
    }

    @Test
    public void equalsWithEmptyOtherObjectTypeShouldReturnFalse() {
        assertNotEquals("User", testUser);
    }

    @Test
    public void equalsWithUserContainingSameValuesShouldReturnTrue() {
        User dummyUser1 = new User("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        dummyUser1.addFavoriteGraph(graphRep);
        dummyUser1.addFavoriteCompany("AMZN");
        assertEquals(testUser, dummyUser1);
    }

    @Test
    public void equalsWithUserNotContainingSameValuesShouldReturnFalse() {
        User dummyUser1 = new User("IsacWenn", "Password", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<");
        assertNotEquals(testUser, dummyUser1);
    }

    @Test
    public void getUserFavoriteGraphsShouldReturnListOfGraphRepresentations() {
        List<GraphRepresentation> graphRepList = testUser.getUserFavoriteGraphs();
        assertEquals(graphRep, graphRepList.get(0));
    }

    @Test
    public void getUserFavoriteCompaniesShouldReturnListOfStrings() {
        List<String> companies = testUser.getUserFavoriteCompanies();
        assertEquals("AMZN", companies.get(0));
    }

    @Test
    public void addFavoriteGraphWithValuesShouldAddGraphRepToFavorites() throws IOException {
        assertEquals(1, testUser.getUserFavoriteGraphs().size());
        List<Date> interval = new ArrayList<>(){{ add(new Date(2022, 1, 12)); }};
        String alg = "Daily Change";
        String mic = "T";
        String preferredCurrency = "GBP";
        testUser.addFavoriteGraph(interval, alg, mic, preferredCurrency);
        assertEquals(2, testUser.getUserFavoriteGraphs().size());
        assertInstanceOf(GraphRepresentation.class, testUser.getUserFavoriteGraphs().get(1));
    }

    @Test
    public void removeFavoriteCompanyShouldRemoveCompanyStringFromListOfFavorites() {
        assertEquals(1, testUser.getUserFavoriteCompanies().size());
        testUser.removeFavoriteCompany("AMZN");
        assertTrue(testUser.getUserFavoriteCompanies().isEmpty());
    }

    @Test
    public void removeFavoriteGraphShouldRemoveGraphRepFromListOfFavorites() {
        assertEquals(1, testUser.getUserFavoriteGraphs().size());
        testUser.removeFavoriteGraph(graphRep);
        assertTrue(testUser.getUserFavoriteGraphs().isEmpty());
    }

    @Test
    public void toStringShouldReturnStringRepresentationOfInstance() {
        User dummy = new User("", "", "", "", "", "");
        assertEquals("User{userInfo=UserInfo{username='', password='', email='', name='', lastname='', bio=''}," +
                " favorites=UserFavorites{favoriteGraphs=[], favoriteCompanies=[]}}", dummy.toString());
    }

    @Test
    public void creatingNewUserShouldLoginThatUser() {
        User.logoutActiveUser();
        assertNull(User.getActiveUser());
        assertTrue(User.loginUser("IsacWenn", "Lösenord"));
        assertEquals(testUser, User.getActiveUser());
    }

    @Test
    public void isLoggedInShouldReturnTrueIfThereIsAnActiveUser() {
        assertTrue(User.isLoggedIn());
    }

    @Test
    public void loginShouldFailIfIncorrectParametersAreGiven() {
        assertFalse(User.loginUser("Hej", "Lösenord"));
    }
}
