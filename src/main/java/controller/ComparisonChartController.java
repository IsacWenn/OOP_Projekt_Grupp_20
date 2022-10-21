package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import model.graphmodel.GraphModel;

import java.io.IOException;

/**
 * Class which allows several stocks at once to be viewed as graphs.
 *
 * @author Johan
 * @author Dennis
 */
public class ComparisonChartController extends ChartController {

    @FXML
    protected ComboBox<String> algorithmComboBox;

    /**
     * Generates a {@link ComparisonChartController}.
     * @param parentController the Parent Controller.
     */
    public ComparisonChartController(AppController parentController){
        super(parentController);
        initializeAlgorithmComboBox();
    }

    /**
     * Loads the FXML file.
     */
    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../ComparisonChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Initializes and fills the algorithm selector {@link ComboBox}.
     */
    private void initializeAlgorithmComboBox() {
        algorithmComboBox.getItems().addAll(GraphModel.getOrderedGraphAlgorithmNames());
        algorithmComboBox.getSelectionModel().select(GraphModel.getOrderedGraphAlgorithmNames().get(0));
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            chartModel.updateAlgorithms(newVal);
            refreshChart();
        });
    }

    /**
     * Handles what happens when a {@link ControllerStockListItem} is clicked upon, either adding it to the chart or
     * removing it.
     * @param item the {@link ControllerStockListItem} clicked upon.
     */
    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (item.isActive()) {
            int index = chartModel.indexOf(item.getName());
            chartModel.remove(index);
            chart.remove(index);
        } else {
            GraphModel newGraph = chartModel.add(item.getMIC(), item.getName(), algorithmComboBox.getValue());
            chart.add(newGraph);
        }
        item.togglePressed();
    }

    /**
     * Redraws the chart.
     */
    @Override
    public void refreshChart() {
        chart.refresh(chartModel.getGraphModels());
    }
}
