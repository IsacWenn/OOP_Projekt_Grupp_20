package model.user;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;
import model.util.GraphRepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A serializable class containing the different user selected favorites.
 *
 * @author Isac
 */
public class UserFavorites implements Serializable {

    /**
     * A {@link List} of {@link GraphRepresentation}s of the different favorites.
     */
    private List<GraphRepresentation> favorites;

    /**
     * A constructor for UserFavorites.
     */
    UserFavorites() {
        favorites = new ArrayList<>();
    }

    /**
     * A getter method for the favorites attribute.
     *
     * @return A {@link List} of {@link GraphRepresentation} of the favorites.
     */
    public List<GraphRepresentation> getFavorites() {
        return favorites;
    }

    /**
     * A toString method for the UserFavorites class.
     *
     * @return
     */
    @Override
    public String toString() {
        return "UserFavorites{" +
                favorites +
                '}';
    }

    /**
     * A method that adds a new favorite to {@link UserFavorites#favorites}.
     *
     * @param interval a {@link List} of {@link Date}s of the specified interval.
     * @param alg a {@link GraphRepresentation} for the algorithm.
     * @param mic a {@link String} for the company mic.
     */
    void addFavorite(List<Date> interval, GraphAlgorithms alg, String mic, String prefCurrency) {
        favorites.add(new GraphRepresentation(interval, alg, mic, prefCurrency));
    }

    /**
     * A method that adds a new favorite to {@link UserFavorites#favorites}.
     *
     * @param graphRep a {@link GraphRepresentation} to add to the list of favorites.
     */
    void addFavorite(GraphRepresentation graphRep) {
        favorites.add(graphRep);
    }

    /**
     * An implementation of the .equals() method of Java.
     *
     * @param o the {@link Object} to compare with.
     * @return a {@link Boolean} value of that comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFavorites that = (UserFavorites) o;
        return favorites.equals(that.favorites);
    }

    /**
     * An implementation of the .hashCode method that creates a unique hash based on the instance attributes of
     * the object
     *
     * @return a {@link Integer} value of that hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(favorites);
    }

    /**
     * A method that returns a {@link List} of {@link String}s of each MIC for the User favorites.
     *
     * @return a {@link List} of {@link String}s containing the company MICs.
     */
    public List<String> getFavoriteMICs() {
        List<String> micList = new ArrayList<>();
        for (GraphRepresentation gr : favorites)
            micList.add(gr.getCompanyMIC());
        return micList;
    }
}
