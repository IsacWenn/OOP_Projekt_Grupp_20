package controller;

import model.graphmanager.algorithms.AlgorithmFactory;
import model.graphmanager.algorithms.Graphables;

public class ClosingPriceGraphController extends GraphController {

    ClosingPriceGraphController(AppController parentController) {
        super(parentController);
        this.algorithm = AlgorithmFactory.create(Graphables.DAILYCLOSINGPRICE);
    }
}









