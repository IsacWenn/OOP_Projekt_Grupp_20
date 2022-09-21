package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ControllerStockListItem extends AnchorPane {
    private final AppController parentController;
    private final String acronym;
    @FXML
    private Label stockAcronym;
    @FXML
    private Label stockName;
    @FXML
    private Label stockValue;
    @FXML
    private AnchorPane stockListItem;
    ControllerStockListItem (String acronym, AppController parentController){
        URL fxmlLocation = getClass().getResource("../MainViewCompanyEntity.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader((fxmlLocation));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        stockAcronym.setText(acronym);
        stockListItem.setOnMouseClicked(this::onClick);

        this.acronym = acronym;
        this.parentController = parentController;
    }
    @FXML
    private void onClick(Event event){
        parentController.openStockView(this.acronym);
    }
}
