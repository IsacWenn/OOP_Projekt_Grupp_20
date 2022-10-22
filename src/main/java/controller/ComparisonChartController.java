package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import model.datahandling.DataHandler;
import model.graphmodel.GraphModel;
import model.user.User;
import model.util.GraphRepresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which allows several stocks at once to be viewed as graphs.
 *
 * @author Johan
 * @author Dennis
 */
public class ComparisonChartController extends ChartController {

    List<String> activeCompanies;

    @FXML
    protected ComboBox<String> algorithmComboBox;

    /**
     * Generates a {@link ComparisonChartController}.
     * @param parentController the Parent Controller.
     */
    public ComparisonChartController(AppController parentController, List<String> favoriteCompanies){
        super(parentController, favoriteCompanies);
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
    }

    public ComparisonChartController(AppController parentController, List<String> favoriteCompanies, List<GraphRepresentation> graphsToLoad){
        super(parentController, favoriteCompanies);
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
        for (GraphRepresentation graph : graphsToLoad) {
            String graphName = DataHandler.getCompanyName(graph.getCompanyMIC());
            activeCompanies.add(graphName);
            GraphModel graphToAdd = new GraphModel(graph.getCompanyMIC(), graphName, graph.getStartingDate(),
                    graph.getEndDate(), graph.getAlgorithm(), graph.getConversionCurrency());
            graphModels.add(graphToAdd);
        }
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
        algorithmComboBox.getSelectionModel().select("Closing Price");
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            for (GraphModel graphModel : graphModels) {
                graphModel.updateAlgorithm(newVal);
            }
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
            int index = activeCompanies.indexOf(item.getName());
            activeCompanies.remove(index);
            graphModels.remove(index);
            chart.remove(index);
        } else {
            GraphModel newGraph = new GraphModel(item.getMIC(), item.getName(), startDate, endDate, algorithmComboBox.getValue(), getCurrency());
            activeCompanies.add(item.getName());
            graphModels.add(newGraph);
            chart.add(newGraph);
        }
        item.togglePressed();
    }

    /**
     * Redraws the chart.
     */
    @Override
    public void refreshChart() {
        chart.refresh(graphModels);
    }
}
