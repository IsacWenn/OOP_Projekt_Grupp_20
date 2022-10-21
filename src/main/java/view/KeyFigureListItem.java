package view;

import controller.ChartController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;

import java.io.IOException;

/**
 * Class which functions as a container which displays key figures.
 *
 * @author Johan
 * @author Dennis
 */
public class KeyFigureListItem extends AnchorPane {
    @FXML
    private Label keyFigLabel;

    /**
     * Generates a container for displaying key figures based on a given {@link String} and {@link GraphModel}.
     *
     * @param name the name of the field.
     * @param graphModel the {@link GraphModel} which contains the key figures to be displayed.
     */
    public KeyFigureListItem(String name, GraphModel graphModel) {
        loadFXML();
        String keyFigVal;
        if (graphModel != null) {
             keyFigVal = String.format("%.2f", graphModel.getKeyFigureValue(name));
        } else {
            keyFigVal = "-";
        }
        keyFigLabel.setText(name + ": " + keyFigVal);
    }

    /**
     * Generates a container for displaying key figures based on a given {@link String} and {@link Double}.
     * @param name the name of the field.
     * @param data the data to be displayed.
     */
    public KeyFigureListItem(String name, double data) {
        loadFXML();
        String keyFigVal;
        if (data != 0) {
            keyFigVal = String.format("%.2f", data);
        } else {
            keyFigVal = "-";
        }
        keyFigLabel.setText(name + ": " + keyFigVal);
        this.setPrefSize(250, 30);
    }

    /**
     * Loads the FXML file for the container.
     */
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
