package catchBox;

import agentSearch.Action;

public class ActionUp extends Action<CatchState>{

    public ActionUp(){
        super(1);
    }

    @Override
    public void execute(CatchState state){
        state.moveUp();
        state.setAction(this);
    }

    @Override
    public boolean isValid(CatchState state){
        return state.canMoveUp();
    }
}