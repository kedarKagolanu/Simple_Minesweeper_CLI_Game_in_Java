package minesweeper;

public class Cell {
    private int value;
    private boolean isMine;
    private boolean isMarked;
    private boolean isEmpty;

    private boolean isExplored;

    String representingString;

    public Cell(int val) {
        this.value = val;
        isMine = false;
        isMarked = false;
        isExplored = false;
        isEmpty = true;

        representingString = ".";
    }

    public void incrementValue() {
        if(isMine ) {
            return;
        }
        value++;
        updateRepresentationString();
    }

    public void markOrUnmarkCell() {
        if(isMarked) {
            isMarked = false;
        } else {
            isMarked = true;
        }

        updateRepresentationString();
    }

    public void explore() {
        isExplored = true;
        updateRepresentationString();
    }

    void updateRepresentationString() {
        if(isExplored) {
            if(value == 0) {
                representingString = "/";
            } else if(isMine) {
                representingString = "X";
            } else {
                representingString = Integer.toString(getValue());
            }

        } else if(isMarked) {
            representingString = "*";
        } else {
            representingString = ".";
        }
    }

    public void setMine() {
        isMine = true;
        isEmpty = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isExplored() { return  isExplored; }

    @Override
    public String toString() {
        return representingString;
    }

}
