package controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import model.graphmodel.GraphModel;
import model.graphmodel.keyfigures.KeyFigureAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class DetailedChartController extends ChartController{

    protected ControllerStockListItem activeCompany;
    protected ArrayList<String> activeAlgorithms;

    @FXML
    private CheckBox closingPriceBox;
    @FXML
    private CheckBox dailyChangeBox;
    @FXML
    private CheckBox dailyDeviationBox;
    @FXML
    private CheckBox linearRegressionBox;
    @FXML
    private FlowPane keyFigureContainer;

    public DetailedChartController(AppController parentController) {
        super(parentController);
        activeCompany = null;
        activeAlgorithms = new ArrayList<>();
        initializeCheckBoxes();
    }

    private void initializeCheckBoxes() {
        closingPriceBox.selectedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                closingPriceBoxPressed();
            }
        });
        dailyChangeBox.selectedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                dailyChangeBoxPressed();
            }
        });
        dailyDeviationBox.selectedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                dailyDeviationBoxPressed();
            }
        });
        linearRegressionBox.selectedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                linearRegressionBoxPressed();
            }
        });
        closingPriceBox.selectedProperty().set(true);
    }

    @Override
    void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../DetailedChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void closingPriceBoxPressed() {
        toggleAlgorithm(closingPriceBox, "Closing Price");
    }

    private void dailyChangeBoxPressed() {
        toggleAlgorithm(dailyChangeBox, "Daily Change");
    }

    private void dailyDeviationBoxPressed() {
        toggleAlgorithm(dailyDeviationBox, "Daily Deviation");
    }

    private void linearRegressionBoxPressed() {
        toggleAlgorithm(linearRegressionBox, "Linear Regression");
    }

    private void toggleAlgorithm(CheckBox checkBox, String algorithm) {
        if (activeAlgorithms.contains(algorithm)) {
            activeAlgorithms.remove(algorithm);
        } else {
            activeAlgorithms.add(algorithm);
        }

        if (chartModel.contains(algorithm)) {
            int index = chartModel.indexOf(algorithm);
            chartModel.remove(index);
            chart.remove(index);
        } else if (activeCompany != null && checkBox.isSelected()) {
            chartModel.add(activeCompany.getMIC(), algorithm, algorithm);
        }
        chart.refresh(chartModel.getGraphModels());
    }

    protected void removeFromChart() {
        activeCompany = null;
        chartModel.clearGraphModels();
        chart.clear();
        keyFigureContainer.getChildren().clear();
    }

    protected void addToChart(ControllerStockListItem item) {
        removeFromChart();
        activeCompany = item;
        for (String algorithm: activeAlgorithms) {
            chart.add(
                chartModel.add(
                    item.getMIC(),
                    algorithm,
                    algorithm
                )
            );
        }
        populateKeyFigureContainer();
    }

    public void stockListOnClick(ControllerStockListItem item) {
        if (activeCompany != null && activeCompany == item) {
            removeFromChart();
        } else {
            if (!item.isActive()) {
                if (activeCompany != null) {
                    activeCompany.togglePressed();
                }
                addToChart(item);
            } else {
                removeFromChart();
            }
        }
        item.togglePressed();
        chart.refresh(chartModel.getGraphModels());
    }

    private void populateKeyFigureContainer() {
        keyFigureContainer.getChildren().clear();
        for (String keyFig : GraphModel.getKeyFigureNames()) {
            keyFigureContainer.getChildren().add(new KeyFigureListItem(this, keyFig, chartModel.getGraphModels().get(0)));
        }
    }

    @Override
    public void refreshChart() {
        chart.refresh(chartModel.getGraphModels());
        populateKeyFigureContainer();
    }
}
