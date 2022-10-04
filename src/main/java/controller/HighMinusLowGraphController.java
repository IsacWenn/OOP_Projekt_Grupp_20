package controller;

import model.graphmanager.algorithms.AlgorithmFactory;
import model.graphmanager.algorithms.Graphables;

public class HighMinusLowGraphController extends GraphController {

    HighMinusLowGraphController(AppController parentController) {
        super(parentController);
        this.algorithm = AlgorithmFactory.create(Graphables.DAILYHIGHMINUSLOW);
    }
}









