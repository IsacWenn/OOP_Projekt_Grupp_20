package model;

import view.Observable;

import java.util.ArrayList;

public class AppModel {

    private ArrayList<Observable> observerList;

    public static void main(String[] args) {

    }

    public AppModel() {
        observerList = new ArrayList<>();
    }

    public void addObserver(Observable observable) { observerList.add(observable); }

    public void notifyObservers() {
        for (Observable observable : observerList) observable.updateView();
    }

}
