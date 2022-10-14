package model.chartmodel;

import model.graphmodel.GraphModel;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ChartModel {
    protected Date startDate;
    protected Date endDate;
    protected ArrayList<GraphModel> graphModels;

    public ChartModel() {
        initializeVariables();
    }
    protected void initializeVariables() {
        try {
            startDate = new Date(2021, 9, 26);
            endDate = new Date();
            graphModels = new ArrayList<>();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public GraphModel addToChart(String acronym, String name, GraphAlgorithms algorithm) {
        GraphModel graphModel = new GraphModel(acronym, name, startDate, endDate);
        graphModel.updateAlgorithm(algorithm);
        graphModels.add(graphModel);
        return graphModel;
    }

    public int removeFromChart(String name) {
        for (int i = 0; i < graphModels.size(); i++){
            if (graphModels.get(i).getName().equals(name)) {
                graphModels.remove(i);
                return i;
            }
        }
        return -1;
    }

    public boolean contains(String name) {
        for (GraphModel graphModel : graphModels) {
            if (graphModel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void updateAlgorithms(GraphAlgorithms algorithm) {
        for (GraphModel graphModel: graphModels) {
            graphModel.updateAlgorithm(algorithm);
        }
    }

    public void updateTimeInterval(LocalDate newStartDate, LocalDate newEndDate) throws IOException {
        Date tempStartDate = new Date(newStartDate.getYear(), newStartDate.getMonthValue(), newStartDate.getDayOfMonth());
        Date tempEndDate = new Date(newEndDate.getYear(), newEndDate.getMonthValue(), newEndDate.getDayOfMonth());
        if (tempStartDate.isBefore(tempEndDate) && tempEndDate.isAfter(tempStartDate)) {
            startDate = tempStartDate;
            endDate = tempEndDate;
        } else throw new IOException("Invalid date");
        updateGraphModelTimeInterval();
    }

    public void updateStartDate(LocalDate newDate) throws IOException {
        Date temp = new Date(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
        if (temp.isBefore(endDate)) {
            startDate = temp;
        } else throw new IOException("Invalid date");
        updateGraphModelTimeInterval();
    }

    public void updateEndDate(LocalDate newDate) throws IOException {
        Date temp = new Date(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
        if (temp.isAfter(startDate)) {
            endDate = temp;
        } else throw new IOException("Invalid date");
        updateGraphModelTimeInterval();
    }

    private void updateGraphModelTimeInterval() {
        for (GraphModel graphModel: graphModels) {
            graphModel.updateTimeInterval(startDate, endDate);
        }
    }

    public void clearGraphModels() {
        graphModels.clear();
    }

    public ArrayList<GraphModel> getGraphModels() {
        return graphModels;
    }
}
