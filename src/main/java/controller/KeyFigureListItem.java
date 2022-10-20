package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;

import java.io.IOException;

public class KeyFigureListItem extends AnchorPane {
    @FXML
    private Label keyFigLabel;
    @FXML
    private Label keyFigVal;
    private final ChartController parentController;

    public KeyFigureListItem(ChartController parentController, String keyFig, GraphModel graphModel) {
        this.parentController = parentController;
        loadFXML();
        keyFigLabel.setText(keyFig + ":");
        if (graphModel != null) {
            keyFigVal.setText(String.format("%.2f", graphModel.getKeyFigureValue(keyFig)));
        } else {
            keyFigVal.setText("-");
        }

    }
    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../KeyFigureItem.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
