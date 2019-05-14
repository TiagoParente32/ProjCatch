package catchBox;

import agentSearch.Action;

public class ActionRight extends Action<CatchState>{

    public ActionRight(){
        super(1);
    }

    @Override
    public void execute(CatchState state){
        state.moveRight();
        state.setAction(this);
    }

    @Override
    public boolean isValid(CatchState state){
        return state.canMoveRight();
    }
}