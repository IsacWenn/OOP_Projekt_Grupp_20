package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.datahandling.DataHandler;
import model.util.Date;

import java.io.IOException;

public class ControllerStockListItem extends AnchorPane {
    private final LineGraphController parentController;
    private final String acronym;
    private boolean active;
    @FXML
    private Label stockAcronym;
    @FXML
    private Label stockName;
    @FXML
    private Label stockValue;
    @FXML
    private AnchorPane stockListItem;
    ControllerStockListItem (String acronym, LineGraphController parentController){
        loadFXML();
        initializeLabels(acronym);
        active = false;
        stockListItem.setOnMouseClicked(this::onClick);


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
        if (!active) {
            stockListItem.getStyleClass().clear();
            stockListItem.getStyleClass().add("pane_active");
        } else {
            stockListItem.getStyleClass().clear();
            stockListItem.getStyleClass().add("pane_normal");
        }
        active = !active;
    }

    @FXML
    private void onClick(Event event){
        parentController.openStockView(this.acronym);
        togglePressed();
    }
}
