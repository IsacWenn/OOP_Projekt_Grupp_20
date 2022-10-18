package model.chartmodel;

import model.graphmodel.GraphModel;
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

    public GraphModel add(String acronym, String name, String algorithm) {
        GraphModel graphModel = new GraphModel(acronym, name, startDate, endDate, algorithm);
        graphModels.add(graphModel);
        return graphModel;
    }

    public void remove(int i) {
        graphModels.remove(i);
    }

    public int indexOf(String name) {
        for (int i = 0; i < graphModels.size(); i++){
            if (graphModels.get(i).getName().equals(name)) {
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

    public void updateAlgorithms(String algorithm) {
        for (GraphModel graphModel: graphModels) {
            graphModel.updateAlgorithm(algorithm);
        }
    }

    public void updateCurrencies(String currency) {
        for (GraphModel graphModel: graphModels) {
            graphModel.updateCurrency(currency);
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
