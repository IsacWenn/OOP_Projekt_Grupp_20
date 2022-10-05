package controller;

import model.graphmanager.algorithms.AlgorithmFactory;
import model.graphmanager.algorithms.Graphables;

public class LinearRegressionGraphController extends GraphController {

    LinearRegressionGraphController(AppController parentController) {
        super(parentController);
        this.algorithm = AlgorithmFactory.create(Graphables.LINEARREGRESSION);
    }
}









