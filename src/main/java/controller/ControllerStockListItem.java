package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ControllerStockListItem extends AnchorPane {

    private final ChartController parentController;
    private final String acronym;
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
    private Label stockValue;
    @FXML
    private AnchorPane stockListItem;

    ControllerStockListItem (String acronym, ChartController parentController, boolean favorite){
        loadFXML();
        initializeLabels(acronym);
        initializeFavoriteButton();
        this.favorite = favorite;
        active = false;
        stockListItem.setOnMouseClicked(this::onClick);
        favoriteButton.setOnMouseClicked(this::onFavoriteClick);

        this.acronym = acronym;
        this.parentController = parentController;
    }

    private void initializeLabels(String acronym) {
        stockAcronym.setText(acronym);
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../MainViewCompanyEntity.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void togglePressed(){
        stockListItem.getStyleClass().clear();
        if (!active) {
            stockListItem.getStyleClass().add("pane_active");
        } else {
            stockListItem.getStyleClass().add("pane_normal");
        }
        active = !active;
    }

    @FXML
    private void onFavoriteHover(Event event){

    }

    private void initializeFavoriteButton(){
        String iconPath;
        if (favorite) {
            iconPath = "src/main/resources/Images/starActive.png";
        } else {
            iconPath = "src/main/resources/Images/starInactive.png";
        }
        Image imageToLoad = new Image(new File(iconPath).toURI().toString());
        favoriteImage = new ImageView(imageToLoad);
    }
    @FXML
    private void onFavoriteClick(Event event){
        String iconPath;

        if (favorite) {
            iconPath = "src/main/resources/Images/starInactive.png";
        } else {
            iconPath = "src/main/resources/Images/starActive.png";
        }
        Image imageToLoad = new Image(new File(iconPath).toURI().toString());
        favoriteImage = new ImageView(imageToLoad);
        favorite = !favorite;
    }

    @FXML
    private void onClick(Event event){
        /*if (parentController.withinCompanyLimit() || active) {
            parentController.stockListOnClick(this.acronym);
        }
        togglePressed();*/
    }
}
