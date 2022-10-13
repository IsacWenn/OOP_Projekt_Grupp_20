package model.util;

import model.graphmodel.graphalgorithms.GraphAlgorithms;

import java.util.List;

public class GraphRepresentation {

    private List<Date> interval;

    private GraphAlgorithms algorithm;

    private String companyMIC;

    public GraphRepresentation(List<Date> interval, GraphAlgorithms alg, String companyMIC) {
        this.interval = interval;
        this.algorithm = alg;
        this.companyMIC = companyMIC;
    }

    // Getters

    public List<Date> getInterval() {
        return interval;
    }

    public GraphAlgorithms getAlgorithm() {
        return algorithm;
    }

    public String getCompanyMIC() {
        return companyMIC;
    }

    

}
