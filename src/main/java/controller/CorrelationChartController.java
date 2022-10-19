package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import model.bivariatealgorithms.BivariateComputer;
import model.graphmodel.GraphModel;

import java.io.IOException;
import java.util.ArrayList;

public class CorrelationChartController extends ChartController {

    private ArrayList<ControllerStockListItem> activeCompanies;
    private ArrayList<GraphModel> graphModels;
    @FXML
    private FlowPane keyFigureContainer;
    @FXML
    protected ComboBox<String> algorithmComboBox;

    public CorrelationChartController(AppController parentController){
        super(parentController);
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
        graphModels = new ArrayList<>();
    }

    @Override
    void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../CorrelationChart.fxml")));
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
            refreshChart();
        });
    }

    private void removeCompany(ControllerStockListItem item) {
        int index = chartModel.indexOf(item.getName());
        chartModel.remove(index);
        chart.remove(index);
        activeCompanies.remove(item);
        item.togglePressed();
        if (activeCompanies.size() < 2) {
            keyFigureContainer.getChildren().clear();
            graphModels.remove(index);
        }
    }

    private void addCompany(ControllerStockListItem item) {
        if (activeCompanies.size() >= 2) {
            return;
        }
        GraphModel newGraph = chartModel.add(item.getMIC(), item.getName(), algorithmComboBox.getValue());
        chart.add(newGraph);
        activeCompanies.add(item);
        graphModels.add(newGraph);
        item.togglePressed();
        if (activeCompanies.size() == 2 ) {
            populateKeyFigureContainer();
        }
    }

    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (item.isActive()) {
            removeCompany(item);
        } else {
            addCompany(item);
        }
    }

    @Override
    public void refreshChart() {
        chart.refresh(chartModel.getGraphModels());
    }

    private void populateKeyFigureContainer() {
        keyFigureContainer.getChildren().clear();
        for (String algorithm : BivariateComputer.getBivariateAlgorithmNames()) {
            keyFigureContainer.getChildren().add(new KeyFigureListItem(this, algorithm,
                    BivariateComputer.calculateKeyFigures(algorithm, graphModels.get(0).getValues(), graphModels.get(1).getValues())));
        }
    }
}

