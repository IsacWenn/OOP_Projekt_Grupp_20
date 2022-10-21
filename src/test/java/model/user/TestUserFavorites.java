package model.user;

import model.util.Date;
import model.util.GraphRepresentation;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserFavorites {

    private UserFavorites userFavorites;
    private GraphRepresentation graphRepresentation;

    @BeforeEach
    public void initializeTestingVariables() throws IOException {
        userFavorites = new UserFavorites();
        graphRepresentation = new GraphRepresentation(
                new Date(2022, 9, 1).listIntervalTo(new Date(2022, 9, 30)),
                "Closing Price",
                new ArrayList<>(){{ add("AMZN"); }},
                "EUR"
        );
        userFavorites.addFavoriteGraph(graphRepresentation);
        userFavorites.addFavoriteCompany("AMZN");
    }

    @Test
    public void getFavoritesShouldReturnListOfFavorites() {
        List<GraphRepresentation> favorites = userFavorites.getFavoriteGraphs();
        assertEquals(1, favorites.size());
        assertEquals(graphRepresentation, favorites.get(0));
    }

    @Test
    public void equalsWithSameInstanceShouldReturnTrue() {
        UserFavorites fav = userFavorites;
        assertEquals(userFavorites, fav);
    }

    @Test
    public void equalsWithObjectNotOfTheSameTypeShouldReturnFalse() {
        assertNotEquals("Hej", userFavorites);
    }

    @Test
    public void equalsWithUserFavoritesNotContainingTheSameValuesShouldReturnFalse() {
        assertNotEquals(new UserFavorites(), userFavorites);
    }

    @Test
    public void hashCodeShouldReturnAnUniqueHashCodeByValues() {
        UserFavorites testUserFavorites1 = new UserFavorites();
        UserFavorites testUserFavorites2 = userFavorites;
        assertNotEquals(testUserFavorites1.hashCode(), userFavorites.hashCode());
        assertEquals(testUserFavorites2.hashCode(), userFavorites.hashCode());
    }

    @Test
    public void getFavoriteCompaniesShouldReturnListOfCompanies() {
        List<String> mics = userFavorites.getFavoriteCompanies();
        assertEquals(1, mics.size());
        assertEquals("AMZN", mics.get(0));
    }

    @Test
    public void addFavoriteGraphWithGraphRepShouldAddGraphRepToListOfGraphs() {
        assertEquals(1, userFavorites.getFavoriteGraphs().size());
        userFavorites.addFavoriteGraph(graphRepresentation);
        assertEquals(2, userFavorites.getFavoriteGraphs().size());
        assertEquals(graphRepresentation, userFavorites.getFavoriteGraphs().get(1));
    }

    @Test
    public void addFavoriteGraphWithVariablesShouldAddGraphRepToListOfGraphs() throws IOException {
        assertEquals(1, userFavorites.getFavoriteGraphs().size());
        List<Date> interval = new ArrayList<>(){{ add(new Date(2022, 1, 12)); }};
        String alg = "Daily Change";
        List<String> mics = new ArrayList<>(){{ add("T"); }};
        String preferredCurrency = "GBP";
        userFavorites.addFavoriteGraph(interval, alg, mics, preferredCurrency);
        assertEquals(2, userFavorites.getFavoriteGraphs().size());
        assertEquals(mics, userFavorites.getFavoriteGraphs().get(1).getCompanyMICs());
    }

    @Test
    public void addFavoriteCompanyShouldAddCompanyMicToList() {
        assertEquals(1, userFavorites.getFavoriteCompanies().size());
        userFavorites.addFavoriteCompany("MSFT");
        assertEquals(2, userFavorites.getFavoriteCompanies().size());
        assertEquals("MSFT", userFavorites.getFavoriteCompanies().get(1));
    }

    @Test
    public void removeFavoriteGraphShouldRemoveTheGivenGraphRepresentationGiven() {
        assertEquals(1, userFavorites.getFavoriteGraphs().size());
        userFavorites.removeFavoriteGraph(graphRepresentation);
        assertTrue(userFavorites.getFavoriteGraphs().isEmpty());
    }

    @Test
    public void removeFavoriteCompanyShouldRemoveGivenCompanyMIC() {
        assertEquals(1, userFavorites.getFavoriteCompanies().size());
        userFavorites.removeFavoriteCompany("AMZN");
        assertTrue(userFavorites.getFavoriteCompanies().isEmpty());
    }


}
