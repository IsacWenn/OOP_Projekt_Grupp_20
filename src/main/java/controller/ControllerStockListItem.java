package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.datahandling.DataHandler;
import model.user.User;

import java.io.IOException;

/**
 * Class which functions as an intractable object within the concrete implementations of {@link ChartController}.
 *
 * Uses:    User
 *
 * Used by: ChartController, ComparisonChartController, CorrelationChartController
 *          DetailedChartController
 *
 * @author Johan
 * @author Dennis
 */
public class ControllerStockListItem extends AnchorPane {

    private final ChartController parentController;
    private final AppController appController;
    private final String acronym, name;
    private boolean active;
    private boolean favorite;


    @FXML
    private ImageView favoriteImage;
    @FXML
    private AnchorPane favoriteButton;
    @FXML
    private Label stockAcronym;
    @FXML
    private Label stockName;
    @FXML
    private AnchorPane stockListItem;

    /**
     * Creates a new instance of {@link ControllerStockListItem} and initializes its variables.
     * @param acronym the MIC of which the {@link ControllerStockListItem} is a representation of.
     * @param parentController the parent controller.
     * @param favorite whether the stock is marked as favorite or not upon creation.
     */
    ControllerStockListItem (String acronym, ChartController parentController,
                             AppController appController, boolean favorite){
        this.acronym = acronym;
        this.name = DataHandler.getCompanyName(acronym);

        loadFXML();
        initializeLabels(acronym);
        initializeFavoriteButton();
        this.favorite = favorite;
        active = false;
        stockListItem.setOnMouseClicked(this::onClick);
        favoriteButton.setOnMouseClicked(this::onFavoriteClick);

        this.parentController = parentController;
        this.appController = appController;
    }

    /**
     * Assigns relevant data to the {@link Label}s.
     * @param acronym the MIC.
     */
    private void initializeLabels(String acronym) {
        stockAcronym.setText(acronym);
        stockName.setText(name);
    }

    /**
     * Loads the FXML file.
     */
    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../StockListitem.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Toggles whether the {@link ControllerStockListItem} is set as active or not.
     */
    public void togglePressed(){
        stockListItem.getStyleClass().clear();
        if (!active) {
            stockListItem.getStyleClass().add("pane_active");
        } else {
            stockListItem.getStyleClass().add("pane_normal");
        }
        active = !active;
    }

    /**
     * Sets an {@link ImageView} to the appropriate image depending on the {@link ControllerStockListItem} is set as
     * favorite or not.
     */
    private void initializeFavoriteButton(){
        String iconPath;
        if (favorite) {
            iconPath = "../Images/starActive.png";
        } else {
            iconPath = "../Images/starInactive.png";
        }
        Image imageToLoad = new Image(getClass().getResource(iconPath).toExternalForm());
        favoriteImage.setImage(imageToLoad);
    }

    /**
     * Updates the favorite status of the {@link ControllerStockListItem}.
     * @param event the {@link Event} which triggered the method call.
     */
    @FXML
    private void onFavoriteClick(Event event){
        parentController.favoritize(acronym);
        appController.refreshFavorites();
        if (User.isLoggedIn()) {
            if (favorite) {
                User.getActiveUser().addFavoriteCompany(acronym);
            } else User.getActiveUser().removeFavoriteCompany(acronym);
        }
    }

    /**
     * Informs the parent controller that the {@link ControllerStockListItem} has been pressed.
     * @param event the event which triggered the method call.
     */
    @FXML
    private void onClick(Event event){
        parentController.stockListOnClick(this);
    }

    /**
     * Checks if the {@link ControllerStockListItem} is active.
     * @return whether the {@link ControllerStockListItem} is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the MIC of the {@link ControllerStockListItem}.
     * @return the MIC of the {@link ControllerStockListItem}.
     */
    public String getMIC() {
        return acronym;
    }

    /**
     * Returns the name of the {@link ControllerStockListItem}.
     * @return the name of the {@link ControllerStockListItem}.
     */
    public String getName() {
        return name;
    }

    public void setFavorite() {
        String iconPath;
        iconPath = "../Images/starActive.png";
        Image imageToLoad = new Image(getClass().getResource(iconPath).toExternalForm());
        favoriteImage.setImage(imageToLoad);
        favorite = true;
    }
    public void removeFavorite() {
        String iconPath;
        iconPath = "../Images/starInactive.png";
        Image imageToLoad = new Image(getClass().getResource(iconPath).toExternalForm());
        favoriteImage.setImage(imageToLoad);
        favorite = false;
    }
}
