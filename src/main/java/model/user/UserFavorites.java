package model.user;

import model.graphmodel.GraphModel;
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
     * A {@link List} of {@link GraphRepresentation}s of the favorite graphs.
     */
    private List<GraphRepresentation> favoriteGraphs;

    /**
     * A {@link List} of {@link String}s of the favorite company MICs.
     */
    private List<String> favoriteCompanies;

    /**
     * A constructor for UserFavorites.
     */
    UserFavorites() {
        favoriteGraphs = new ArrayList<>();
        favoriteCompanies = new ArrayList<>();
    }

    /**
     * A getter method for the favorite graphs.
     *
     * @return A {@link List} of {@link GraphRepresentation} of the favorite graphs.
     */
    public List<GraphRepresentation> getFavoriteGraphs() {
        return favoriteGraphs;
    }

    /**
     * A getter method for the favorite company MICs
     *
     * @return A {@link List} of {@link String}s of the MICs.
     */
    public List<String> getFavoriteCompanies() { return favoriteCompanies; }

    /**
     * A toString method for the UserFavorites class.
     *
     * @return a {@link String} representation of the UserFavorite object.
     */
    @Override
    public String toString() {
        return "UserFavorites{" +
                "favoriteGraphs=" + favoriteGraphs +
                ", favoriteCompanies=" + favoriteCompanies +
                '}';
    }

    /**
     * A method that adds a new favorite to {@link UserFavorites#favoriteGraphs}.
     *
     * @param from the starting {@link Date} for the graph.
     * @param to the end {@link Date} for the graph.
     * @param alg a {@link String} for the algorithm.
     * @param mic a {@link String} of the company mic.
     * @param prefCurrency a {@link String} of the preferred currency.
     */
    void addFavoriteGraph(Date from, Date to, String alg, String mic, String prefCurrency) {
        favoriteGraphs.add(new GraphRepresentation(from, to, alg, mic, prefCurrency));
    }

    /**
     * A method that adds a new favorite to {@link UserFavorites#favoriteGraphs}.
     *
     * @param graphRep a {@link GraphRepresentation} to add to the list of favorites.
     */
    void addFavoriteGraph(GraphRepresentation graphRep) {
        favoriteGraphs.add(graphRep);
    }

    /**
     * A method that adds a new company to {@link UserFavorites#favoriteCompanies}.
     *
     * @param mic a {@link String} to add to the list.
     */
    void addFavoriteCompany(String mic) { favoriteCompanies.add(mic); }

    /**
     * A method that removes a favorite graph from {@link UserFavorites#favoriteGraphs}.
     *
     * @param graphRep a {@link GraphRepresentation} to remove from the list.
     */
    void removeFavoriteGraph(GraphRepresentation graphRep) { favoriteGraphs.remove(graphRep); }

    /**
     * A method that removes a company from {@link UserFavorites#favoriteCompanies}.
     *
     * @param mic a {@link String} to remove from the list.
     */
    void removeFavoriteCompany(String mic) { favoriteCompanies.remove(mic); }

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
        UserFavorites favorites = (UserFavorites) o;
        return Objects.equals(favoriteGraphs, favorites.favoriteGraphs)
                && Objects.equals(favoriteCompanies, favorites.favoriteCompanies);
    }

    /**
     * An implementation of the .hashCode method that creates a unique hash based on the instance attributes of
     * the object
     *
     * @return a {@link Integer} value of that hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(favoriteGraphs, favoriteCompanies);
    }
}