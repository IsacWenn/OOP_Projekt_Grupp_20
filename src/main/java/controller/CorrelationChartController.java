package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import model.bivariatealgorithms.BivariateComputer;
import model.datahandling.DataHandler;
import model.graphmodel.GraphModel;
import model.user.User;
import model.util.GraphRepresentation;
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

    private List<String> activeCompanies;
    @FXML
    private FlowPane keyFigureContainer;
    @FXML
    protected ComboBox<String> algorithmComboBox;

    /**
     * Constructor for an empty Correlation Chart Controller.
     * @param parentController the Parent Controller.
     * @param favoriteCompanies list of favorite companies.
     */
    public CorrelationChartController(AppController parentController, List<String> favoriteCompanies){
        super(parentController, favoriteCompanies);
        activeCompanies = new ArrayList<>();
        graphModels = new ArrayList<>();
        initializeAlgorithmComboBox();
        refreshChart();
    }

    /**
     * Constructor for a filled Correlation Chart Controller.
     * @param parentController the Parent Controller.
     * @param favoriteCompanies list of favorite companies.
     * @param graphsToLoad list of graphs to load.
     */
    public CorrelationChartController(AppController parentController, List<String> favoriteCompanies,
                                      List<GraphRepresentation> graphsToLoad){
        super(parentController, favoriteCompanies);
        activeCompanies = new ArrayList<>();
        graphModels = new ArrayList<>();
        initializeAlgorithmComboBox();
        algorithmComboBox.setValue(graphsToLoad.get(0).getAlgorithm());
        currencyComboBox.setValue(graphsToLoad.get(0).getConversionCurrency());
        startDate = graphsToLoad.get(0).getStartingDate();
        endDate = graphsToLoad.get(0).getEndDate();
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
     * Removes a company from the chart and clears the relevant variables.
     * @param item the {@link ControllerStockListItem} contains information of the stock to be removed.
     */
    private void removeCompany(ControllerStockListItem item) {
        int index = activeCompanies.indexOf(item.getMIC());
        graphModels.remove(index);
        chart.remove(index);
        activeCompanies.remove(index);
        item.togglePressed();
        if (activeCompanies.size() < 2) {
            keyFigureContainer.getChildren().clear();
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
        GraphModel newGraph = new GraphModel(item.getMIC(), item.getName(), startDate, endDate,algorithmComboBox.getValue(), getCurrency());
        chart.add(newGraph);
        activeCompanies.add(item.getMIC());
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
        chart.refresh(graphModels);
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

    @Override
    public void saveGraph() {
        if (User.isLoggedIn()) {
            User.getActiveUser().clearFavoriteGraphs();
            User.getActiveUser().setFavoriteChartType("Correlation Chart");
            for (String company : activeCompanies) {
                User.getActiveUser().addFavoriteGraph(new GraphRepresentation(startDate, endDate,
                        algorithmComboBox.getValue(), company, getCurrency()));
            }
            saveLabel.setText("Chart saved successfully!");
        }  else {
            saveLabel.setText("You must be logged in to save.");
        }
    }
}

