package catchBox;

public class Pair {
    private Cell cell1;
    private Cell cell2;
    private int value;

    public Pair(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return cell1.getLine() + "-" + cell1.getColumn() + " / " + cell2.getLine() + "-" + cell2.getColumn() + ": " + value + "\n";
    }
}
