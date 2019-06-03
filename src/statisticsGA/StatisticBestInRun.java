package statisticsGA;

import experiments.Experiment;
import experiments.ExperimentEvent;
import ga.*;

import java.io.File;

import static catchBox.CatchExperimentsFactory.pathToFiles;
import static catchBox.CatchExperimentsFactory.puzzleNumber;

public class StatisticBestInRun<I extends Individual, P extends Problem<I>> implements GAListener {

    private I bestInExperiment;

    public StatisticBestInRun(String experimentHeader) {
        File file = new File(pathToFiles + "statistic_best_per_experiment_fitness_" + puzzleNumber +".xls");
        if(!file.exists()){
            utils.FileOperations.appendToTextFile(pathToFiles+ "statistic_best_per_experiment_fitness_" + puzzleNumber +".xls", experimentHeader + "\t" + "Fitness:" + "\r\n");
        }
    }

    @Override
    public void generationEnded(GAEvent e) {
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<I, P> ga = e.getSource();
        if (bestInExperiment == null || ga.getBestInRun().compareTo(bestInExperiment) > 0) {
            bestInExperiment = (I) ga.getBestInRun().clone();
        }
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {

        String experimentTextualRepresentation = ((Experiment) e.getSource()).getExperimentTextualRepresentation();
        String experimentHeader = ((Experiment) e.getSource()).getExperimentHeader();
        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        utils.FileOperations.appendToTextFile(pathToFiles + "statistic_best_per_experiment_fitness_" + puzzleNumber +".xls", experimentConfigurationValues + "\t" + bestInExperiment.getFitness() + "\r\n");
        utils.FileOperations.appendToTextFile(pathToFiles+ "statistic_best_per_experiment_" + puzzleNumber + ".txt", "\r\n\r\n" + experimentTextualRepresentation + "\r\n" + bestInExperiment);
    }
}
