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
     * Constructor for an empty Comparison Chart Controller.
     *
     * @param parentController the Parent Controller.
     */
    public ComparisonChartController(AppController parentController, List<String> favoriteCompanies) {
        super(parentController, favoriteCompanies);
        updateStockList();
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
    }

    /**
     * Constructor for a filled Comparison Chart Controller.
     * @param parentController the Parent Controller.
     * @param favoriteCompanies list of favorite companies.
     * @param graphsToLoad list of graphs to load.
     */
    public ComparisonChartController(AppController parentController, List<String> favoriteCompanies, List<GraphRepresentation> graphsToLoad) {
        super(parentController, favoriteCompanies);
        updateStockList();
        initializeAlgorithmComboBox();
        activeCompanies = new ArrayList<>();
        algorithmComboBox.setValue(graphsToLoad.get(0).getAlgorithm());
        startDate = graphsToLoad.get(0).getStartingDate();
        endDate = graphsToLoad.get(0).getEndDate();
        currencyComboBox.setValue(graphsToLoad.get(0).getConversionCurrency());
        for (GraphRepresentation graph : graphsToLoad) {
            activeCompanies.add(graph.getCompanyMIC());
            stockListItemMap.get(graph.getCompanyMIC()).togglePressed();
            String name = stockListItemMap.get(graph.getCompanyMIC()).getName();
            GraphModel graphToAdd = new GraphModel(graph.getCompanyMIC(), name, startDate, endDate,
                    algorithmComboBox.getValue(), graph.getConversionCurrency());
            graphModels.add(graphToAdd);
        }
        refreshChart();
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
     *
     * @param item the {@link ControllerStockListItem} clicked upon.
     */
    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (item.isActive()) {
            int index = activeCompanies.indexOf(item.getMIC());
            activeCompanies.remove(index);
            graphModels.remove(index);
            chart.remove(index);
        } else {
            GraphModel newGraph = new GraphModel(item.getMIC(), item.getName(), startDate, endDate, algorithmComboBox.getValue(), getCurrency());
            activeCompanies.add(item.getMIC());
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

    @Override
    public void saveGraph() {
        User.getActiveUser().clearFavoriteGraphs();
        User.getActiveUser().setFavoriteChartType("Comparison Chart");
        for (String company : activeCompanies) {
            User.getActiveUser().addFavoriteGraph(new GraphRepresentation(startDate, endDate,
                    algorithmComboBox.getValue(), company, getCurrency()));
        }
    }
}
