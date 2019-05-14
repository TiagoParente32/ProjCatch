package search;

import agentSearch.State;

public class Node implements Comparable<Node> {

    private State state;
    private double cost;
    private double f;
    private Node parent;
    private int depth;

    public Node(State state) {
        this.state = state;
    }

    public Node(State state, Node parent) {
        this(state, parent, 0, 0);
    }    
    
    public Node(State state, Node parent, double cost, double f) {
        this.state = state;
        this.cost = cost;
        this.f = f;
        this.parent = parent;
        this.depth = parent.depth + 1;
    }

    public State getState() {
        return state;
    }

    public double getG() {
        return cost;
    }

    public double getF() {
        return f;
    }

    public Node getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int compareTo(Node other) {
        return (f < other.f) ? -1 : (f == other.f) ? 0 : 1;
    }

    public boolean isCycle(State state) {
        Node aux = this;
        do{
            if (state.equals(aux.getState())) {
                return true;
            }
            aux = aux.getParent();
        }while (aux != null);

        return false;
    }
    
    @Override
    public String toString(){
        return "" + f;
    }
}