package experiments;

import ga.GeneticAlgorithm;
import ga.Problem;

import java.util.ArrayList;
import java.util.List;

public class Experiment <E extends ExperimentsFactory, P extends Problem>{

    private final E factory;
    private final int numRuns;
    private GeneticAlgorithm ga;
    private final P problem;
    private final String experimentTextualRepresentation;
    private final String experimentHeader;
    private final String experimentValues;

    public Experiment(
            E factory,
            int numRuns,
            P problem,
            String experimentTextualRepresentation,
            String experimentHeader,
            String experimentValues) {
        this.factory = factory;
        this.numRuns = numRuns;
        this.problem = problem;
        this.experimentTextualRepresentation = experimentTextualRepresentation;
        this.experimentHeader = experimentHeader;
        this.experimentValues = experimentValues;
    }

    public void run() {
        for (int run = 0; run < numRuns; run++) {
            ga = factory.generateGAInstance(run + 1);
            ga.run(problem);
        }
        fireExperimentEnded();
    }

    public String getExperimentTextualRepresentation() {
        return experimentTextualRepresentation;
    }

    public String getExperimentHeader() {
        return experimentHeader;
    }

    public String getExperimentValues() {
        return experimentValues;
    }

    //listeners
    final private List<ExperimentListener> listeners = new ArrayList<>(10);

    public synchronized void addExperimentListener(ExperimentListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void fireExperimentEnded() {
        for (ExperimentListener listener : listeners) {
            listener.experimentEnded(new ExperimentEvent(this));
        }
    }
    
    @Override
    public String toString(){
        return experimentTextualRepresentation;
    }
}
