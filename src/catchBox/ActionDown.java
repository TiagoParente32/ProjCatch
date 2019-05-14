package catchBox;

import agentSearch.Action;

public class ActionDown extends Action<CatchState>{

    public ActionDown(){
        super(1);
    }

    @Override
    public void execute(CatchState state){
        state.moveDown();
        state.setAction(this);
    }

    @Override
    public boolean isValid(CatchState state){
        return state.canMoveDown();
    }
}