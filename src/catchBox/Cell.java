package catchBox;

public class Cell {
    private final int line;
    private final int column;

    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return line == cell.line &&
                column == cell.column;
    }

    @Override
    public String toString() {
        return this.line + "-" + this.column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}