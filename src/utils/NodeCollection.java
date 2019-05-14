package utils;

import agentSearch.State;
import search.Node;

import java.util.Queue;

public interface NodeCollection extends Queue<Node> {
    public boolean containsState(State e);
}
