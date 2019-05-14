package catchBox;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CatchState extends State implements Cloneable {
    //TODO this class might require the definition of additional methods and/or attributes

    protected int[][] matrix;
    private int colunaCatch;
    private int linhaCatch;
    private int linhaGoal;
    private int colunaGoal;
    private int steps;
    private int numBox;
    //4=porta
    //3=obstaculo
    //2=objetos
    //1=agente

    public CatchState(int[][] matrix, int colunaCatch, int linhaCatch, int linhaGoal, int colunaGoal,int steps,int numBox) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];

            }
        }

        this.colunaCatch = colunaCatch;
        this.linhaCatch = linhaCatch;
        this.linhaGoal = linhaGoal;
        this.colunaGoal = colunaGoal;
        this.steps = steps;
    }

    public CatchState(int[][] matrix) {
        //TODO
        this.matrix = new int[matrix.length][matrix.length];
        this.numBox = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if(matrix[i][j] == Properties.BOX){
                    numBox++;
                }

            }
        }
        this.colunaCatch = 0;
        this.linhaCatch = 0;
        this.linhaGoal = 0;
        this.colunaGoal = 0;
        //this.goal = setGoal();

        //throw new UnsupportedOperationException("Not Implemented Yet");
    }

    public void executeAction(Action action) {
        action.execute(this);
        // TODO
        fireUpdatedEnvironment();
        //nao alterei nada ainda mas tirei a exception
        //throw new UnsupportedOperationException("Not Implemented Yet"); // delete after implementing
    }

    public boolean canMoveUp() {
        //TODO
        if(linhaCatch-1<0){
            return false;
        }
        if(matrix[linhaCatch-1][colunaCatch]== Properties.WALL){
            return false;
        }
        return true;
    }

    public boolean canMoveRight() {
        //TODO
        if(colunaCatch+1>=matrix[0].length){
            return false;
        }
        if(matrix[linhaCatch][colunaCatch+1]== Properties.WALL){
            return false;
        }
        //return  catchAgentSearch.getCellCatch().getColumn()+1 < matrix[0].length && matrix[catchAgentSearch.getCellCatch().getLine()][catchAgentSearch.getCellCatch().getColumn()+1]!= Properties.WALL ;
        //throw new UnsupportedOperationException("Not Implemented Yet");
        return true;
    }

    public boolean canMoveDown() {
        //TODO
        if(linhaCatch+1>=matrix.length){
            return false;
        }
        if(matrix[linhaCatch+1][colunaCatch]== Properties.WALL){
            return false;
        }
        //return catchAgentSearch.getCellCatch().getLine()+1 < matrix.length && matrix[catchAgentSearch.getCellCatch().getLine()+1][catchAgentSearch.getCellCatch().getColumn()]!= Properties.WALL ;
        //throw new UnsupportedOperationException("Not Implemented Yet");
        return true;
    }

    public boolean canMoveLeft() {
        //TODO
        if(colunaCatch-1<0){
            return false;
        }
        if(matrix[linhaCatch][colunaCatch-1] == Properties.WALL){
            return false;
        }
        //return catchAgentSearch.getCellCatch().getColumn()-1 >= 0 && matrix[catchAgentSearch.getCellCatch().getLine()][catchAgentSearch.getCellCatch().getColumn()-1]!= Properties.WALL ;
        //throw new UnsupportedOperationException("Not Implemented Yet");
        return true;
    }

    public void moveUp() {
        //TODO
        matrix[linhaCatch][colunaCatch] = Properties.EMPTY;
        if(matrix[linhaCatch-1][colunaCatch] == Properties.BOX){
            numBox --;
        }
        matrix[linhaCatch-1][colunaCatch] = Properties.CATCH;
        setCellCatch(linhaCatch-1,colunaCatch);
        steps++;
    }

    public void moveRight() {
        //TODO
        matrix[linhaCatch][colunaCatch] = Properties.EMPTY;
        if(matrix[linhaCatch][colunaCatch+1] == Properties.BOX){
            numBox --;
        }
        matrix[linhaCatch][colunaCatch+1] = Properties.CATCH;
        setCellCatch(linhaCatch,colunaCatch+1);
        steps++;
    }

    public void moveDown() {
        //TODO
        matrix[linhaCatch][colunaCatch] = Properties.EMPTY;
        if(matrix[linhaCatch+1][colunaCatch] == Properties.BOX){
            numBox --;
        }
        matrix[linhaCatch+1][colunaCatch] = Properties.CATCH;
        setCellCatch(linhaCatch+1,colunaCatch);
        steps++;
    }

    public void moveLeft() {
        //TODO
        matrix[linhaCatch][colunaCatch] = Properties.EMPTY;
        if(matrix[linhaCatch][colunaCatch-1] == Properties.BOX){
            numBox --;
        }
        matrix[linhaCatch][colunaCatch-1] = Properties.CATCH;
        setCellCatch(linhaCatch,colunaCatch-1);
        steps++;
    }

    public int getNumBox() {
        //TODO
        return  numBox;
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /*function heuristic(node) =
    dx = abs(node.x - goal.x)
    dy = abs(node.y - goal.y)
    return D * (dx + dy)
    */
    public double computeDistances(Cell goal,Cell catchCell) {
        double h = 0;

            h += Math.abs(catchCell.getLine() - goal.getLine())
                + Math.abs(catchCell.getColumn() - goal.getColumn());

        return h;
    }

    public void setCellCatch(int line, int column) {
        //TODO
        this.linhaCatch = line;
        this.colunaCatch = column;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setGoal(int line, int column) {
        //TODO
        this.linhaGoal = line;
        this.colunaGoal = column;
    }


    public int getSteps() {
        //TODO
        return steps;
    }

    public int getSize() {
        return matrix.length;
    }

    public int getColunaCatch() {
        return colunaCatch;
    }

    public int getLinhaCatch() {
        return linhaCatch;
    }

    public int getLinhaGoal() {
        return linhaGoal;
    }

    public int getColunaGoal() {
        return colunaGoal;
    }

    public Color getCellColor(int line, int column) {
        switch (matrix[line][column]) {
            case Properties.BOX:
                return Properties.COLORBOX;
            case Properties.CATCH:
                return Properties.COLORCATCH;
            case Properties.DOOR:
                return Properties.COLORDOOR;
            case Properties.WALL:
                return Properties.COLORWALL;
            default:
                return Properties.COLOREMPTY;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CatchState)) {
            return false;
        }

        CatchState o = (CatchState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public CatchState clone() {
        //TODO
        CatchState catchState = new CatchState(matrix,colunaCatch,linhaCatch,linhaGoal,colunaGoal,steps,numBox);
        return catchState;
    }

    //Listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

}
