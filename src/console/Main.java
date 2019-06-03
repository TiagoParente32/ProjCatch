package console;

import agentSearch.Solution;
import catchBox.*;
import experiments.Experiment;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Main {




    public static void main(String[] args) {

        CatchExperimentsFactory experimentsFactory = null;

        try {
            experimentsFactory = new CatchExperimentsFactory(new File("./config1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int[][] matrix = CatchAgentSearch.readInitialStateFromFile(new File(experimentsFactory.getFile()));
            CatchAgentSearch agentSearch = new CatchAgentSearch(new CatchState(matrix));

            LinkedList<Pair> pairs = agentSearch.getPairs();
            for (Pair p : pairs) {
                CatchState state = ((CatchState) agentSearch.getEnvironment()).clone();
                state.setGoal(p.getCell2().getLine(), p.getCell2().getColumn());
                state.setCellCatch(p.getCell1().getLine(), p.getCell1().getColumn());
                CatchProblemSearch problem = new CatchProblemSearch(state, p.getCell2());
                Solution s = agentSearch.solveProblem(problem);
                p.setValue((int) s.getCost());
            }

            while (experimentsFactory.hasMoreExperiments()) {
                try {

                    Experiment experiment = experimentsFactory.nextExperiment(agentSearch.getInitialBox(), pairs, agentSearch.getCellCatch(), agentSearch.getDoor());
                    experiment.run();

                } catch (IOException e1) {
                    e1.printStackTrace(System.err);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

}
