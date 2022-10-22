package model;

import model.datahandling.DataHandler;
import view.Observable;

import java.util.ArrayList;

public class AppModel {

    /**
     * A list of {@link Observable}s that can be notified.
     */
    private ArrayList<Observable> observerList;
    /**
     * Static variable holding the instance of {@link AppModel}.
     */
    private static AppModel instance = null;

    /**
     * A constructor for the class AppModel.
     */
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
