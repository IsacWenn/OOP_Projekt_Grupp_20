package model.user;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;
import model.util.GraphRepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A serializable class containing the different user selected favorites.
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
                favorites + '\'' +
                '}';
    }

    /**
     * A method that adds a new favorite to {@link UserFavorites#favorites}.
     *
     * @param interval a {@link List} of {@link Date}s of the specified interval.
     * @param alg a {@link GraphRepresentation} for the algorithm.
     * @param mic a {@link String} for the company mic.
     */
    void addFavorite(List<Date> interval, GraphAlgorithms alg, String mic) {
        favorites.add(new GraphRepresentation(interval, alg, mic));
    }

    /**
     * A method that adds a new favorite to {@link UserFavorites#favorites}.
     *
     * @param graphRep a {@link GraphRepresentation} to add to the list of favorites.
     */
    void addFavorite(GraphRepresentation graphRep) {
        favorites.add(graphRep);
    }
}
