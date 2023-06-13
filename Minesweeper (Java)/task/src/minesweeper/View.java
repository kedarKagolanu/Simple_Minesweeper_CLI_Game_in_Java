package minesweeper;

import java.util.HashMap;

public class View {

    private Modal modal;

    public View(Modal modal) {
        this.modal = modal;
    }

    public void printView() {
        System.out.println("\n |123456789|");
        System.out.println("-|---------|");
        for(int i=0;i<modal.rowCount;i++) {
            System.out.print((i+1)+"|");
            for(int j=0;j<modal.colCount;j++) {
                System.out.print(modal.cellField[i][j].toString());
            }
            System.out.println("|");
        }

        System.out.println("-|---------|");
    }

    public GameStatus getStatus() {
        return modal.status;
    }
}
