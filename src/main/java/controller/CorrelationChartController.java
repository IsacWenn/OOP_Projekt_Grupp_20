package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import model.bivariatealgorithms.BivariateComputer;
import model.graphmodel.GraphModel;
import view.KeyFigureListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which allows two stocks to be compared and viewed as graphs as well as generating and displaying key figures
 * for correlation between chosen stocks.
 *
 * @author Johan
 * @author Dennis
 */
public class CorrelationChartController extends ChartController {

    private ArrayList<ControllerStockListItem> activeCompanies;
    private ArrayList<GraphModel> graphModels;
    @FXML
    private FlowPane keyFigureContainer;
    @FXML
    protected ComboBox<String> algorithmComboBox;

    /**
     * Generates a {@link CorrelationChartController}.
     * @param parentController the Parent Controller.
     */
    public CorrelationChartController(AppController parentController, List<String> favouriteCompanies){
        super(parentController, favouriteCompanies);
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
        graphModels = new ArrayList<>();
        populateKeyFigureContainer();
    }

    /**
     * Loads the FXML file.
     */
    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../CorrelationChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Initializes the algorithm selector {@link ComboBox}.
     */
    private void initializeAlgorithmComboBox() {
        algorithmComboBox.getItems().addAll(GraphModel.getGraphAlgorithmNames());
        algorithmComboBox.getSelectionModel().select("Closing Price");
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            chartModel.updateAlgorithms(newVal);
            refreshChart();
        });
    }

    /**
     * Removes a company from the chart and clears the relevant variables.
     * @param item the {@link ControllerStockListItem} contains information of the stock to be removed.
     */
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
        populateKeyFigureContainer();
    }

    /**
     * Adds a company to the chart and updates the relevant variables.
     * @param item the {@link ControllerStockListItem} which contains information of the stock to be added.
     */
    private void addCompany(ControllerStockListItem item) {
        if (activeCompanies.size() >= 2) {
            return;
        }
        GraphModel newGraph = chartModel.add(item.getMIC(), item.getName(), algorithmComboBox.getValue());
        chart.add(newGraph);
        activeCompanies.add(item);
        graphModels.add(newGraph);
        item.togglePressed();
        populateKeyFigureContainer();
    }

    /**
     * Handles what happens when a {@link ControllerStockListItem} is clicked upon, either adding it to the chart or
     * removing it.
     * @param item the {@link ControllerStockListItem} clicked upon.
     */
    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (item.isActive()) {
            removeCompany(item);
        } else {
            addCompany(item);
        }
    }

    /**
     * Redraws the chart.
     */
    @Override
    public void refreshChart() {
        chart.refresh(chartModel.getGraphModels());
        populateKeyFigureContainer();
    }

    /**
     * Populates a container with relevant {@link KeyFigureListItem}s based on the currently displayed charts.
     */
    private void populateKeyFigureContainer() {
        keyFigureContainer.getChildren().clear();
        if (activeCompanies.size() == 2) {
            for (String algorithm : BivariateComputer.getBivariateAlgorithmNames()) {
                keyFigureContainer.getChildren().add(new KeyFigureListItem(algorithm,
                        BivariateComputer.calculateKeyFigures(algorithm, graphModels.get(0).getValues(),
                                graphModels.get(1).getValues())));
            }
        } else {
            for (String algorithm : BivariateComputer.getBivariateAlgorithmNames()) {
                keyFigureContainer.getChildren().add(new KeyFigureListItem(algorithm, 0));
            }
        }
    }
}

