package catchBox;

import agentSearch.Heuristic;

public class HeuristicCatch extends Heuristic<CatchProblemSearch, CatchState> {

    @Override
    public double compute(CatchState state) {
        //TODO
        return state.computeDistances(new Cell(state.getLinhaGoal(),state.getColunaGoal()),new Cell(state.getLinhaCatch(),state.getColunaCatch()));
    }

    @Override
    public String toString() {
        //TODO
        return "Distance between two poins";
    }
}