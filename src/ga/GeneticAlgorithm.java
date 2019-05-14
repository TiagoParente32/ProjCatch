package ga;

import ga.geneticOperators.Mutation;
import ga.geneticOperators.Recombination;
import ga.selectionMethods.SelectionMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm<I extends Individual, P extends Problem<I>> {

    public static Random random;
    private final int populationSize;
    private final int maxGenerations;
    private Population<I, P> population;
    private final SelectionMethod<I, P> selection;
    private final Recombination<I, P> recombination;
    private final Mutation<I, P> mutation;
    private int t;
    private boolean stopped;
    private I bestInRun;

    public GeneticAlgorithm(
            int populationSize,
            int maxGenerations,
            SelectionMethod<I, P> selection,
            Recombination<I, P> recombination,
            Mutation<I, P> mutation,
            Random rand) {

        random = rand;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.selection = selection;
        this.recombination = recombination;
        this.mutation = mutation;
    }

    public I run(P problem) {
        t = 0;
        population = new Population<>(populationSize, problem);
        bestInRun = population.evaluate();
        fireGenerationEnded(new GAEvent(this));

        while (!stopCondition(t)) {
            Population<I, P> populationAux = selection.run(population);
            recombination.run(populationAux);
            mutation.run(populationAux);
            population =  populationAux;
            I bestInGen = population.evaluate();
            computeBestInRun(bestInGen);
            t++;
            fireGenerationEnded(new GAEvent(this));
        }
        fireRunEnded(new GAEvent(this));
        return bestInRun;
    }

    private void computeBestInRun (I bestInGen){
        if (bestInGen.compareTo(bestInRun) > 0) {
            bestInRun = (I) bestInGen.clone();
        }
    }

    private boolean stopCondition(int t) {
        return stopped || t == maxGenerations;
    }

    public int getGeneration() {
        return t;
    }

    public I getBestInGeneration() {
        return population.getBest();
    }

    public double getAverageFitness() {
        return population.getAverageFitness();
    }

    public I getBestInRun() {
        return bestInRun;
    }

    public void stop() {
        stopped = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population size:" + populationSize + "\n");
        sb.append("Max generations:" + maxGenerations + "\n");
        sb.append("Selection:" + selection + "\n");
        sb.append("Recombination:" + recombination + "\n");
        sb.append("Mutation:" + mutation + "\n");
        return sb.toString();
    }

    //Listeners
    private final transient List<GAListener> listeners = new ArrayList<>(3);

    public synchronized void addGAListener(GAListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized void removeAGListener(GAListener listener) {
        if (listeners != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void fireGenerationEnded(GAEvent e) {
        for (GAListener listener : listeners) {
            listener.generationEnded(e);
        }
        if (e.isStopped()) {
            stop();
        }
    }

    public void fireRunEnded(GAEvent e) {
        for (GAListener listener : listeners) {
            listener.runEnded(e);
        }
    }
}
