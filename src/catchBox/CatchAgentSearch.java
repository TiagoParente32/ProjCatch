package catchBox;

import agentSearch.Agent;
import agentSearch.State;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class CatchAgentSearch<S extends State> extends Agent<S> {

    protected S initialEnvironment;
    private static LinkedList<Pair> pairs;
    private static LinkedList<Cell> boxes;
    private static Cell cellCatch;
    private static Cell door;

    public CatchAgentSearch(S environment) {
        super(environment);
        initialEnvironment = environment;
        heuristics.add(new HeuristicCatch());
        heuristic = heuristics.get(0);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Pairs:\n");
        for (Pair p : pairs) {
            str.append(p);
        }
        return str.toString();
    }

    public static int[][] readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);

        cellCatch = null;
        door = null;
        int dim = scanner.nextInt();
        boxes = new LinkedList<>();
        scanner.nextLine();
        int[][] matrix = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrix[i][j] = scanner.nextInt();
                if (matrix[i][j] == Properties.CATCH)
                    cellCatch = new Cell(i, j);
                else if (matrix[i][j] == Properties.BOX)
                    boxes.add(new Cell(i, j));
                else if (matrix[i][j] == Properties.DOOR)
                    door = new Cell(i, j);
            }
            scanner.nextLine();
        }
        pairs = new LinkedList<>();
        for (Cell b : boxes) {
            pairs.add(new Pair(cellCatch, b));
            pairs.add(new Pair(b, door));
        }

        for (int i = 0; i < boxes.size() - 1; i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                pairs.add(new Pair(boxes.get(i), boxes.get(j)));
            }
        }
        return matrix;
    }

    public LinkedList<Pair> getPairs() {
        return pairs;
    }

    public LinkedList<Cell> getInitialBox() {
        return boxes;
    }

    public static Cell getCellCatch() {
        return cellCatch;
    }

    public static Cell getDoor() {
        return door;
    }
}
