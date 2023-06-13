package minesweeper;

public class Modal {
    Cell[][] cellField;
    final int rowCount = 9;
    final int colCount = 9;

    int mineCellsCount = 0;
    int nonMineCellsCount = 0;
    int markedMineCellsCount = 0;
    int exploredNonMineCellsCount = 0;

    GameStatus status;

    int totalCells;

    public Modal() {
        cellField = new Cell[rowCount][colCount];

        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                cellField[i][j] = new Cell(0);
            }
        }

        totalCells = rowCount * colCount;
        status = GameStatus.RUNNING;
    }

    public boolean markedAllMines() {
        if(markedMineCellsCount == mineCellsCount)
            return true;
        else
            return false;
    }

    public boolean exploredAllNonMineCells() {
        if(exploredNonMineCellsCount == nonMineCellsCount)
            return true;
        else
            return false;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setMineCellsCount(int count) {
        mineCellsCount = count;
        nonMineCellsCount = totalCells - mineCellsCount;
    }
}
