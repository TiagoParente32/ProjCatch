package catchBox;

import ga.Problem;

import java.util.LinkedList;

public class CatchProblemForGA implements Problem<CatchIndividual> {
    //TODO this class might require the definition of additional methods and/or attributes

    private LinkedList<Cell> cellsBoxes;
    private LinkedList<Pair> pairs;
    private Cell cellCatch;
    private Cell door;

    public CatchProblemForGA(LinkedList<Cell> cellsBoxes,LinkedList<Pair> pairs,Cell cellCatch,Cell door){
        //TODO
        this.cellCatch = cellCatch;
        this.pairs = pairs;
        this.cellCatch = cellCatch;
        this.door = door;

        //throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public CatchIndividual getNewIndividual() {
        //TODO
        return new CatchIndividual(this,this.cellsBoxes.size());
    }

    @Override
    public String toString() {
        //TODO
        StringBuilder sb = new StringBuilder();
        for (Pair pair : pairs) {
            sb.append(pair.toString()+" ");
        }
        return sb.toString();
    }

    public LinkedList<Cell> getCellsBoxes() {
        return cellsBoxes;
    }
}
