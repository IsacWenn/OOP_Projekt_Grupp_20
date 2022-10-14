package model.user;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserInfo {

    private UserInfo userInfo;

    @BeforeEach
    public void initializeTestingVariables() {
        userInfo = new UserInfo("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<<");
    }

    @Test
    public void getUserNameShouldReturnUserName() {
        assertEquals("IsacWenn", userInfo.getUsername());
    }

    @Test
    public void getPasswordShouldReturnPassword() {
        assertEquals("Lösenord", userInfo.getPassword());
    }

    @Test
    public void getEmailShouldReturnEmail() {
        assertEquals("i@grupp20.se", userInfo.getEmail());
    }

    @Test
    public void getNameShouldReturnFirstname() {
        assertEquals("Isac", userInfo.getName());
    }

    @Test
    public void getLastnameShouldReturnLastname() {
        assertEquals("Ingvast Wennerström", userInfo.getLastname());
    }

    @Test
    public void getBioShouldReturnBio() {
        assertEquals("python <<<<<<<", userInfo.getBio());
    }

    @Test
    public void toStringShouldReturnStringRepresentationOfUserInfo() {
        assertEquals("UserInfo{username='IsacWenn', password='Lösenord', " +
                "email='i@grupp20.se', name='Isac', lastname='Ingvast Wennerström'" +
                ", bio='python <<<<<<<'}", userInfo.toString());
    }

    @Test
    public void equalsWithSameObjectShouldReturnTrue() {
        UserInfo dummyInfo = userInfo;
        assertEquals(userInfo, dummyInfo);
    }

    @Test
    public void equalsWithNonComparableObjectShouldReturnFalse() {
        assertNotEquals("Hej", userInfo);
    }

    @Test
    public void equalsWithUserInfoWithDifferentValuesShouldReturnFalse() {
        UserInfo dummyInfo = new UserInfo("Carl", "", "", "", "", "");
        assertNotEquals(userInfo, dummyInfo);
    }

    @Test
    public void hashCodeShouldReturnHashOfValues() {
        UserInfo dummyInfo1 = new UserInfo("IsacWenn", "Lösenord", "i@grupp20.se", "Isac",
                "Ingvast Wennerström", "python <<<<<<<");
        UserInfo dummyInfo2 = new UserInfo("Carl", "", "", "", "", "");

        assertNotEquals(dummyInfo2.hashCode(), userInfo.hashCode());
        assertEquals(dummyInfo1.hashCode(), userInfo.hashCode());
    }
}
