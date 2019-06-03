package statisticsGA;

import experiments.Experiment;
import experiments.ExperimentEvent;
import ga.*;
import utils.Maths;

import java.io.File;

import static catchBox.CatchExperimentsFactory.pathToFiles;
import static catchBox.CatchExperimentsFactory.puzzleNumber;

public class StatisticBestAverage<E extends Individual, P extends Problem<E>> implements GAListener  {
    
    private final double[] values;
    private int run;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        values = new double[numRuns];
        File file = new File(pathToFiles + "statistic_average_fitness_" + puzzleNumber +".xls");
        if(!file.exists()){
            utils.FileOperations.appendToTextFile(pathToFiles + "statistic_average_fitness_" + puzzleNumber +".xls", experimentHeader + "\t" + "Average:" + "\t" + "StdDev:" + "\r\n");
        }
    }

    @Override
    public void generationEnded(GAEvent e) {    
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<E, P> ga = e.getSource();
        values[run++] = ga.getBestInRun().getFitness();
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {
        double average = Maths.average(values);
        double sd = Maths.standardDeviation(values, average);

        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        utils.FileOperations.appendToTextFile(pathToFiles + "statistic_average_fitness_" + puzzleNumber +".xls", experimentConfigurationValues + "\t" + average + "\t" + sd + "\r\n");
    }
}
