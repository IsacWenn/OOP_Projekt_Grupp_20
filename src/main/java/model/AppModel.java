package model;

import model.datahandling.DataHandler;
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
        return instance;
    }

    public void addObserver(Observable observable) { observerList.add(observable); }

    public void notifyObservers() {
        for (Observable observable : observerList) observable.updateView();
    }

}
