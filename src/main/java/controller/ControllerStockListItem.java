package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerStockListItem extends AnchorPane {
    private final ChartController parentController;
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
    ControllerStockListItem (String acronym, ChartController parentController){
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
        stockListItem.getStyleClass().clear();
        if (!active) {
            stockListItem.getStyleClass().add("pane_active");
        } else {
            stockListItem.getStyleClass().add("pane_normal");
        }
        active = !active;
    }

    @FXML
    private void onClick(Event event){
        if (parentController.withinCompanyLimit() && !active) {
            parentController.openStockView(this.acronym);
        } else if(active) {
            parentController.openStockView(acronym);
        }
        togglePressed();
    }
}
