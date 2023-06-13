package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner in = new Scanner(System.in);
        Modal modal = new Modal();
        View view = new View(modal);
        Controller controller = new Controller(modal);

        controller.init();

        while(true) {

            if(view.getStatus() == GameStatus.WON) {
                view.printView();
                System.out.println("Congratulations! You found all the mines!");
                break;
            } else if(view.getStatus() == GameStatus.LOST) {
                view.printView();
                System.out.println("You stepped on a mine and failed!");
                break;
            }

            view.printView();

            controller.playerMove();
        }
    }
}
