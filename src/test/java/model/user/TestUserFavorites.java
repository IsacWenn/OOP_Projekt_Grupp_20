package model.user;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.CurrencyConversionEnum;
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
                GraphAlgorithms.DAILYCLOSINGPRICE,
                "AMZN",
                CurrencyConversionEnum.USD_TO_EUR
        );
        userFavorites.addFavorite(graphRepresentation);
    }

    @Test
    public void getFavoritesShouldReturnListOfFavorites() {
        List<GraphRepresentation> favorites = userFavorites.getFavorites();
        assertEquals(1, favorites.size());
        assertEquals(graphRepresentation, favorites.get(0));
    }

    @Test
    public void toStringShouldReturnStringRepresentationOfUserFavoritesInstance() throws IOException {
        UserFavorites testUserFavorites = new UserFavorites();
        testUserFavorites.addFavorite(
                new ArrayList<>(){{ add(new Date(2022, 10, 14)); }},
                GraphAlgorithms.DAILYHIGHMINUSLOW,
                "MSFT",
                CurrencyConversionEnum.USD_TO_ISK
        );
        assertEquals("UserFavorites{[GraphRepresentation{interval=[14/10/2022], algorithm=DAILYHIGHMINUSLOW," +
                " companyMIC='MSFT', preferredCurrency=USD_TO_ISK}]'}", testUserFavorites.toString());
    }

    @Test
    public void addFavoriteUsingIndividualValuesShouldAddFavoriteCorrectly() {
        userFavorites.addFavorite(
                new ArrayList<>(){{ add(new Date()); }},
                GraphAlgorithms.DAILYHIGHMINUSLOW,
                "MSFT",
                CurrencyConversionEnum.USD_TO_ISK
        );
        List<GraphRepresentation> favorites = userFavorites.getFavorites();
        assertEquals(2, favorites.size());
        assertEquals("MSFT", favorites.get(1).getCompanyMIC());
    }

    @Test
    public void addFavoriteUsingGraphRepresentationShouldAddFavoriteCorrectly() {
        GraphRepresentation testGraph = new GraphRepresentation(
                new ArrayList<>(){{ add(new Date()); }},
                GraphAlgorithms.DAILYCLOSINGPRICE,
                "AAPL",
                CurrencyConversionEnum.USD_TO_DKK
        );
        userFavorites.addFavorite(testGraph);
        assertEquals(testGraph, userFavorites.getFavorites().get(1));
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
}
