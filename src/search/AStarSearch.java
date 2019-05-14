package search;

import agentSearch.State;

import java.util.List;

public class AStarSearch extends InformedSearch {

    //f = g + h
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        for (State s : successors) {
            double g = parent.getG() + s.getAction().getCost();
            if (!(frontier.containsState(s) || explored.contains(s))) {
                frontier.add(new Node(s, parent, g, g + heuristic.compute(s)));
            }
        }
    }

    @Override
    public String toString() {
        return "A* search";
    }
}
