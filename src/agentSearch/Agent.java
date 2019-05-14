package agentSearch;

import search.AStarSearch;
import search.SearchMethod;

import java.util.ArrayList;

public class Agent<E extends State> {

    protected E environment;
    protected ArrayList<SearchMethod> searchMethods;
    protected SearchMethod searchMethod;
    protected ArrayList<Heuristic> heuristics;
    protected Heuristic heuristic;
    protected Solution solution;
    protected int numExpandedNodes;
    protected int maxFrontierSize;
    protected int numGeneratedNodes;

    public Agent(E environment) {
        this.environment = environment;
        searchMethods = new ArrayList<>();
        searchMethods.add(new AStarSearch());
        searchMethod = searchMethods.get(0);
        heuristics = new ArrayList<>();
    }

    public Solution solveProblem(Problem problem) {
        if (heuristic != null) {
            problem.setHeuristic(heuristic);
            heuristic.setProblem(problem);
        }
        solution = searchMethod.search(problem);
        this.numExpandedNodes += searchMethod.getStatistics().numExpandedNodes;
        this.maxFrontierSize = Math.max(searchMethod.getStatistics().numExpandedNodes, this.maxFrontierSize);
        this.numGeneratedNodes += searchMethod.getStatistics().numGeneratedNodes;
        return solution;
    }

    public void setSolution(Solution s) {
        this.solution = s;
    }

    public E executeSolution() {
        for (Action action : solution.getActions()) {
            environment.executeAction(action);
        }
        return environment;
    }

    public boolean hasSolution() {
        return solution != null;
    }

    public void stop() {
        getSearchMethod().stop();
    }

    public boolean hasBeenStopped() {
        return getSearchMethod().hasBeenStopped();
    }

    public E getEnvironment() {
        return environment;
    }

    public void setEnvironment(E environment) {
        this.environment = environment;
    }

    public SearchMethod[] getSearchMethodsArray() {
        SearchMethod[] sm = new SearchMethod[searchMethods.size()];
        return searchMethods.toArray(sm);
    }

    public SearchMethod getSearchMethod() {
        return searchMethod;
    }

    public void setSearchMethod(SearchMethod searchMethod) {
        this.searchMethod = searchMethod;
    }

    public Heuristic[] getHeuristicsArray() {
        Heuristic[] sm = new Heuristic[heuristics.size()];
        return heuristics.toArray(sm);
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public String getSearchReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(searchMethod + "\n");
        if (solution == null) {
            sb.append("No solution found\n");
        } else {
            sb.append("Solution cost: " + Double.toString(solution.getCost()) + "\n");
        }
        sb.append("Num of expanded nodes: " + numExpandedNodes + "\n");
        sb.append("Max frontier size: " + maxFrontierSize + "\n");
        sb.append("Num of generated nodes: " + numGeneratedNodes + "\n");

        return sb.toString();
    }

    public int getNumExpandedNodes() {
        return numExpandedNodes;
    }

    public int getMaxFrontierSize() {
        return maxFrontierSize;
    }

    public int getNumGeneratedNodes() {
        return numGeneratedNodes;
    }
}
