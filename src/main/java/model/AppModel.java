package model;

import model.bivariatealgorithms.BivariateAlgorithmCollection;
import model.datahandling.DataHandler;
import model.graphmodel.graphalgorithms.GraphAlgorithmCollection;
import model.graphmodel.keyfigures.KeyFigureCollection;
import view.Observable;

import java.util.ArrayList;

public class AppModel {

    private ArrayList<Observable> observerList;
    private static AppModel instance = null;
    private DataHandler dataHandler;

    public static void main(String[] args) {

    }

    private AppModel() {
        observerList = new ArrayList<>();
    }

    public static AppModel getInstance() {
        if (instance == null) instance = new AppModel();
        GraphAlgorithmCollection.init();
        KeyFigureCollection.init();
        BivariateAlgorithmCollection.init();
        return instance;
    }

    public void addObserver(Observable observable) { observerList.add(observable); }

    public void notifyObservers() {
        for (Observable observable : observerList) observable.updateView();
    }

}
