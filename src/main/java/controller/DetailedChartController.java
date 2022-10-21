package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import model.graphmodel.GraphModel;
import view.KeyFigureListItem;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class which allows for a detailed view of a select stock as a graph and allowing for multiple different sets of
 * data to be displayed any given stock.
 *
 * @author Johan
 * @author Dennis
 */
public class DetailedChartController extends ChartController{

    protected ControllerStockListItem activeCompany;
    protected ArrayList<String> activeAlgorithms;
    private GraphModel keyFigGraphModel;

    @FXML
    private MenuButton algorithmMenuButton;
    @FXML
    private FlowPane keyFigureContainer;
    @FXML
    protected ComboBox<String> currencyComboBox;

    /**
     * Generates a {@link DetailedChartController}.
     * @param parentController the Parent Controller.
     */
    public DetailedChartController(AppController parentController) {
        super(parentController);
        activeCompany = null;
        keyFigGraphModel = null;
        activeAlgorithms = new ArrayList<>();
        initializeAlgorithmComboBox();
    }

    /**
     * Loads the FXML file.
     */
    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../DetailedChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Initializes the Algorithm selector {@link ComboBox}.
     */
    private void initializeAlgorithmComboBox() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        for (String algorithm: GraphModel.getOrderedGraphAlgorithmNames()) {
            CheckBox checkBox = new CheckBox(algorithm);
            checkBox.selectedProperty().addListener((options, oldVal, newVal) -> {
                toggleAlgorithm(checkBox.getText());
            });
            checkBoxes.add(checkBox);
        }
        checkBoxes.get(0).setSelected(true);
        for (CheckBox checkBox: checkBoxes) {
            CustomMenuItem menuItem = new CustomMenuItem(checkBox);
            menuItem.setHideOnClick(false);
            algorithmMenuButton.getItems().add(menuItem);
        }
    }

    /**
     * Toggles whether the given algorithm is active or not.
     * @param algorithm the name of the algorithm to toggle.
     */
    private void toggleAlgorithm(String algorithm) {
        if (activeAlgorithms.contains(algorithm)) {
            activeAlgorithms.remove(algorithm);
        } else {
            activeAlgorithms.add(algorithm);
        }

        if (chartModel.contains(algorithm)) {
            int index = chartModel.indexOf(algorithm);
            chartModel.remove(index);
            chart.remove(index);
        } else if (activeCompany != null) {
            chartModel.add(activeCompany.getMIC(), algorithm, algorithm);
        }
        refreshChart();
    }

    /**
     * Clears the chart and relevant variables.
     */
    protected void clearChart() {
        activeCompany = null;
        keyFigGraphModel = null;
        chartModel.clearGraphModels();
        chart.clear();
    }

    /**
     * Adds the given stock to the chart.
     * @param item the {@link ControllerStockListItem} which contains information of the stock to be added.
     */
    protected void addToChart(ControllerStockListItem item) {
        clearChart();
        activeCompany = item;
        keyFigGraphModel = new GraphModel(item.getMIC(), "", chartModel.getStartDate(), chartModel.getEndDate());
        for (String algorithm: activeAlgorithms) {
            chart.add(
                chartModel.add(
                    item.getMIC(),
                    algorithm,
                    algorithm
                )
            );
        }
    }

    /**
     * Handles what happens when a {@link ControllerStockListItem} is clicked upon. Either adding it to the chart or
     * removing it from the chart.
     * @param item the {@link ControllerStockListItem} clicked upon.
     */
    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (activeCompany != null && activeCompany == item) {
            clearChart();
        } else {
            if (!item.isActive()) {
                if (activeCompany != null) {
                    activeCompany.togglePressed();
                }
                addToChart(item);
            } else {
                clearChart();
            }
        }
        item.togglePressed();
        refreshChart();
    }

    /**
     * Populates a container with relevant {@link KeyFigureListItem}s based on the currently displayed chart.
     */
    private void populateKeyFigureContainer() {
        keyFigureContainer.getChildren().clear();
        for (String keyFig : GraphModel.getOrderedKeyFigureNames()) {
            keyFigureContainer.getChildren().add(new KeyFigureListItem(keyFig, keyFigGraphModel));
        }
    }

    /**
     * Redraws the chart.
     */
    @Override
    public void refreshChart() {
        chart.refresh(chartModel.getGraphModels());
        if (keyFigGraphModel != null) {
            keyFigGraphModel.updateTimeInterval(chartModel.getStartDate(), chartModel.getEndDate());
            keyFigGraphModel.updateCurrency(getCurrency());
        }
        populateKeyFigureContainer();
    }
}
