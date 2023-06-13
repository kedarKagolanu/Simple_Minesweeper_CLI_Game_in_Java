package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Controller {
    private Modal modal;
    private int minesCount;

    public Controller(Modal modal) {
        this.modal = modal;
    }

    public void init() {
        Scanner in = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int inputMinesCount = in.nextInt();
        modal.setMineCellsCount(inputMinesCount);

        System.out.println(" |123456789|\n" +
                "-|---------|\n" +
                "1|.........|\n" +
                "2|.........|\n" +
                "3|.........|\n" +
                "4|.........|\n" +
                "5|.........|\n" +
                "6|.........|\n" +
                "7|.........|\n" +
                "8|.........|\n" +
                "9|.........|\n" +
                "-|---------|");

        System.out.println("Set/unset mines marks or claim a cell as free:");
        int colIndex = in.nextInt()-1;
        int rowIndex = in.nextInt()-1;
        String selectionType = in.next();

        if(selectionType.equals("free")) {
            fillRandomCells(inputMinesCount,rowIndex,colIndex);
            exploreCellAt(rowIndex,colIndex);
        } else {
            fillRandomCells(inputMinesCount,-1,-1);
            markOrUnMarkCellAt(rowIndex,colIndex);
        }

        updateStatus();

    }

    // here (rowIndex,colIndex) is the coordinate of cell,
    // which is to be avoided while adding mine randomly to cells in the field.
    public void fillRandomCells(int count,int rowIndex,int colIndex) {
        this.minesCount = count;
        Random rand = new Random();
        int tempRowIndex,tempColIndex;
        for(int i=0;i<count;i++) {
            while (true) {
                tempRowIndex = Math.abs(rand.nextInt() % modal.rowCount);
                tempColIndex = Math.abs(rand.nextInt() % modal.colCount);

                if(tempRowIndex == rowIndex && tempColIndex == colIndex) {
                    continue;
                }

                if(!(modal.cellField[tempRowIndex][tempColIndex].getValue() < 0)) {
                    insertMineAt(tempRowIndex,tempColIndex);
                    modal.mineCellsCount++;
                    break;
                }
            }
        }
    }

    private void insertMineAt(int rowIndex,int colIndex) {

        Cell cell = modal.cellField[rowIndex][colIndex];

        if(cell.isMine()) {
            return;
        } else {
            modal.cellField[rowIndex][colIndex].setMine();
        }

        //nested-for loop to increment the cells surrounding the mine.
        for(int i=rowIndex-1;i<rowIndex+2;i++) {
            for(int j=colIndex-1;j<colIndex+2;j++) {

                if(i < 0 || i >= 9 || j < 0 || j >= 9) {
                    continue;
                }

                if(i == rowIndex && j == colIndex) {
                    continue;
                }

                modal.cellField[i][j].incrementValue();
            }
        }
    }

    public void playerMove() {
        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print("\nSet/Unset mines marks (x and y coordinates): ");
            // x ordinate --> column number
            // y ordiante --> row nummber
            int colIndex = in.nextInt()-1;
            int rowIndex = in.nextInt()-1;
            String selectionType = in.next();

            if(rowIndex < 0 || rowIndex >= 9 || colIndex < 0 || colIndex >= 9) {
                continue;
            }

            Cell tempCellHolder = modal.cellField[rowIndex][colIndex];

            if(selectionType.equals("mine")) {
                markOrUnMarkCellAt(rowIndex,colIndex);
            } else if(selectionType.equals("free")) {
                exploreCellAt(rowIndex,colIndex);
            }

            updateStatus();

            break;
        }
    }

    private void markOrUnMarkCellAt(int rowIndex,int colIndex) {
        Cell cell = modal.cellField[rowIndex][colIndex];
        cell.markOrUnmarkCell();
        if(cell.isMine())
            modal.markedMineCellsCount++;
        else
            modal.markedMineCellsCount--;
    }

    private void exploreCellAt(int rowIndex,int colIndex) {
        Cell cell = modal.cellField[rowIndex][colIndex];
        //Checking if the cell is mine.
        if(cell.isMine()) {
            System.out.println("You stepped on a mine and failed!");
            modal.setStatus(GameStatus.LOST);
        }


        // incrementing the no of explored cells only if the cell is not explored before.
        if(!cell.isExplored()) {
            modal.exploredNonMineCellsCount++;
        }
        cell.explore();


        //exploring all the possible cells around the cell.
        for(int i=rowIndex-1;i<rowIndex+2;i++) {
            for(int j=colIndex-1;j<colIndex+2;j++) {

                if(i < 0 || i >= 9 || j < 0 || j >= 9) {
                    continue;
                }

                if(i == rowIndex && j == colIndex) {
                    continue;
                }

                Cell tempCellHolder = modal.cellField[i][j];
                if(!tempCellHolder.isMine() && !tempCellHolder.isExplored())
                    exploreCellAt(i,j);
            }
        }

    }

    private void updateStatus() {
        if(modal.markedAllMines() || modal.exploredAllNonMineCells()) {
            modal.setStatus(GameStatus.WON);
        }
    }
}
