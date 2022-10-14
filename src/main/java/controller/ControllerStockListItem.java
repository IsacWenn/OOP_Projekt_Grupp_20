package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.datahandling.DataHandler;

import java.io.IOException;

public class ControllerStockListItem extends AnchorPane {

    private final ChartController parentController;
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
    private Label stockValue;
    @FXML
    private AnchorPane stockListItem;

    ControllerStockListItem (String acronym, ChartController parentController, boolean favorite){
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
    }

    private void initializeLabels(String acronym) {
        stockAcronym.setText(acronym);
        stockName.setText(name);
        /*try{
            stockValue.setText(DataHandler.getLatestDayData(acronym).values().toString());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }*/
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
    @FXML
    private void onFavoriteClick(Event event){
        String iconPath;

        if (favorite) {
            iconPath = "../Images/starInactive.png";
        } else {
            iconPath = "../Images/starActive.png";
        }
        Image imageToLoad = new Image(getClass().getResource(iconPath).toExternalForm());
        favoriteImage.setImage(imageToLoad);
        parentController.favoritize(acronym);
        favorite = !favorite;
    }

    @FXML
    private void onClick(Event event){
        parentController.stockListOnClick(this);
    }

    public boolean isActive() {
        return active;
    }

    public String getMIC() {
        return acronym;
    }

    public String getName() {
        return name;
    }
}
