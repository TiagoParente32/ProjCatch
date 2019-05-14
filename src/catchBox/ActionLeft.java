package catchBox;

import agentSearch.Action;

public class ActionLeft extends Action<CatchState>{

    public ActionLeft(){
        super(1);
    }

    @Override
    public void execute(CatchState state){
        state.moveLeft();
        state.setAction(this);
    }

    @Override
    public boolean isValid(CatchState state){
        return state.canMoveLeft();
    }
}
