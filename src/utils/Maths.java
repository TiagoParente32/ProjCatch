package utils;

import java.util.ArrayList;

public class Maths {

    public static double average(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    public static double standardDeviation(double[] array, double mean) {
        double sum = 0;
        for (double value : array) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(1 / (double) array.length * sum);
    }

    public static double standardDeviation(ArrayList<Double> array, double mean) {
        double sum = 0;
        for (Double value : array) {
            sum += Math.pow(value.doubleValue() - mean, 2);
        }
        return Math.sqrt(1 / (double) array.size() * sum);
    }
}
