package model.bivariatealgorithms;

import model.util.Date;

import java.io.IOException;
import java.util.*;

public class SpearmanCorrelation implements BivariateAlgorithms {

    /**
     * A method for calculating the Pearson correlation coefficient between two data sets.
     * @param series1 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @param series2 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @return a {@link Double} that is the calculated correlation coefficient.
     */
    @Override
    public double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2, Set<Date> commonDates) {

        List<Map.Entry<Date, Number>> list1 = new LinkedList<Map.Entry<Date, Number>>(series1.entrySet());
        List<Map.Entry<Date, Number>> list2 = new LinkedList<Map.Entry<Date, Number>>(series2.entrySet());

        sortByValue(list1);
        sortByValue(list2);

        HashMap<Date, Number> rankSeries1 = new HashMap<>();
        HashMap<Date, Number> rankSeries2 = new HashMap<>();
        int numberOfDays = commonDates.size();

        for (int i = 0; i < numberOfDays; i++) {
            rankSeries1.put(list1.get(i).getKey(), i);
            rankSeries2.put(list2.get(i).getKey(), i);
        }
        double sumOfRankDifferenceSquared = 0;
        for (Date date : commonDates) {
            double deltaRank = rankSeries1.get(date).doubleValue() - rankSeries2.get(date).doubleValue();
            sumOfRankDifferenceSquared += Math.pow(deltaRank, 2);
        }

        return 1 - 6 * sumOfRankDifferenceSquared / (numberOfDays * (Math.pow(numberOfDays, 2) - 1));
    }

    /**
     * A method for ordering key-value-pairs based on values represented as a {@link Number}.
     * @param list1 A {@link List} containing the sorted key-value-pairs.
     */
    private void sortByValue(List<Map.Entry<Date, Number>> list1) {
        list1.sort(Comparator.comparingDouble(o -> o.getValue().doubleValue()));
    }

    public static void main(String[] args) throws IOException {
        Map<Date, Number> hm1 = new HashMap<>();
        Map<Date, Number> hm2 = new HashMap<>();

        hm1.put(new Date(2020,1,1), 1);
        hm1.put(new Date(2020,2,2), 10);
        hm1.put(new Date(2020,4,2), 30);
        hm1.put(new Date(2020,8,2), 50);
        hm1.put(new Date(2020,12,2), 1000);

        hm2.put(new Date(2020,1,1), 5);
        hm2.put(new Date(2020,2,2), 4);
        hm2.put(new Date(2020,4,2), 3);
        hm2.put(new Date(2020,8,2), 2);
        hm2.put(new Date(2020,12,2), 1);

        SpearmanCorrelation sp = new SpearmanCorrelation();
        System.out.println(sp.calculateKeyFigure(hm1,hm2, hm1.keySet()));
    }
}


