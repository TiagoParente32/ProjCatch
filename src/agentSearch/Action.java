package agentSearch;


public abstract class Action <S extends State>{
    private final double cost;

    public Action(double cost){
        this.cost = cost;
    }

    public abstract void execute(S State);

    public abstract boolean isValid(S State);

    public double getCost(){
        return cost;
    }
}