package catchBox;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CatchProblemSearch<S extends CatchState> extends Problem<S> {
    //TODO this class might require the definition of additional methods and/or attributes
    protected LinkedList<Action> availableActions;
    private int linhaGoal;
    private int colunaGoal;

    public CatchProblemSearch(S initialCatchState, Cell goalPosition) {
        super(initialCatchState);
        availableActions = new LinkedList<>();

        availableActions.add(new ActionUp());
        availableActions.add(new ActionRight());
        availableActions.add(new ActionDown());
        availableActions.add(new ActionLeft());

        this.linhaGoal = goalPosition.getLine();
        this.colunaGoal = goalPosition.getColumn();

        //TODO
    }

    @Override
    public List<S> executeActions(S state) {
        //TODO
        //declarar uma lista de estados sucessores vazia
        List<S> sucessors = new ArrayList<S>();

        //para cada acao disponivel
        for (Action availableAction : availableActions) {
            // se o estado sucessor resultante da acao for valido ,
            if (availableAction.isValid(state)) {
                //obter o estado sucessor ,
                S sucessor = (S) state.clone();

                availableAction.execute(sucessor);
                //acrescentar o estado a lista
                sucessors.add(sucessor);
            }
        }

        //devolver a lista de estados sucessores
        return sucessors;
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }

    public boolean isGoal(S state) {
        //TODO
        if(this.linhaGoal == state.getLinhaCatch() && this.colunaGoal == state.getColunaCatch()){
            return true;
        }
        return false;
    }
}
