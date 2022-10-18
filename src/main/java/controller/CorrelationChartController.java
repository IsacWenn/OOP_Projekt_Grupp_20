package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import model.graphmodel.GraphModel;

import java.io.IOException;
import java.util.ArrayList;

public class CorrelationChartController extends ChartController {

    protected ArrayList<ControllerStockListItem> activeCompanies;
    @FXML
    protected ComboBox<String> algorithmComboBox;

    public CorrelationChartController(AppController parentController){
        super(parentController);
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
    }

    @Override
    void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../LineChart.fxml")));
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
        algorithmComboBox.getSelectionModel().select("Closing Price");
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            chartModel.updateAlgorithms(newVal);
            chart.refresh(chartModel.getGraphModels());
        });
    }

    private void removeCompany(ControllerStockListItem item) {
        int index = chartModel.indexOf(item.getName());
        chartModel.remove(index);
        chart.remove(index);
        activeCompanies.remove(item);
        item.togglePressed();
    }

    private void addCompany(ControllerStockListItem item) {
        if (activeCompanies.size() >= 2) {
            return;
        }
        GraphModel newGraph = chartModel.add(item.getMIC(), item.getName(), algorithmComboBox.getValue());
        chart.add(newGraph);
        activeCompanies.add(item);
        item.togglePressed();
    }

    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (item.isActive()) {
            removeCompany(item);
        } else {
            addCompany(item);
        }
    }
}
