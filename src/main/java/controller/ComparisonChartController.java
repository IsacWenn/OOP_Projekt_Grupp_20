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

    public ComparisonChartController(AppController parentController){
        super(parentController);
        initializeAlgorithmComboBox();
    }

    @Override
    void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../ComparisonChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeAlgorithmComboBox() {
        algorithmComboBox.getItems().addAll(GraphModel.getGraphAlgorithmNames());
        algorithmComboBox.getSelectionModel().select(GraphModel.getGraphAlgorithmNames().get(0));
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            chartModel.updateAlgorithms(newVal);
            refreshChart();
        });
    }

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

    @Override
    public void refreshChart() {
        chart.refresh(chartModel.getGraphModels());
    }
}
