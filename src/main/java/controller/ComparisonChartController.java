package controller;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ComparisonChartController extends ChartController {

    public ComparisonChartController(AppController parentController){
        super(parentController);
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

}
