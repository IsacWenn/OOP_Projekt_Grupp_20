package controller;

import model.graphmanager.algorithms.AlgorithmFactory;
import model.graphmanager.algorithms.Graphables;

public class DailyChangeGraphController extends GraphController {

    DailyChangeGraphController(AppController parentController) {
        super(parentController);
        this.algorithm = AlgorithmFactory.create(Graphables.DAILYCHANGE);
    }

}









