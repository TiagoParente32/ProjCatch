package agentSearch;

import search.Node;

import java.util.LinkedList;

public class Solution {
    private final Problem problem;
    private final LinkedList<Action> actions;

    public Solution(Problem problem, Node goalNode) {
        this.problem = problem;
        Node node = goalNode;
        actions = new LinkedList<>();
        while (node.getParent() != null) {
            actions.addFirst(node.getState().getAction());
            node = node.getParent();
        }
    }

    public double getCost() {
        return problem.computePathCost(actions);
    }

    public LinkedList<Action> getActions() {
        return actions;
    }

    public void updateActions(LinkedList<Action> actions) {
        this.actions.addAll(actions);
    }

}