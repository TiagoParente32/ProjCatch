package agentSearch;


public abstract class Heuristic<P extends Problem, S extends State> {

    protected P problem;

    public Heuristic() {
    }

    public Heuristic(P problem) {
        this.problem = problem;
    }

    public abstract double compute(S state);

    public P getProblem() {
        return problem;
    }

    public void setProblem(P problem) {
        this.problem = problem;
    }
}