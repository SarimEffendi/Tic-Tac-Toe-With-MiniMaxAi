package project.Models;

import javafx.scene.layout.GridPane;
import project.Controllers.GameController;

public class Board {
    private Square[][] squares;

    public Board(GridPane gridPane, GameController gameController) {
        this.squares = new Square[GameController.RCCount][GameController.RCCount];

        for (int i = 0; i < GameController.RCCount; i++) {
            for (int j = 0; j < GameController.RCCount; j++) {
                Square square = new Square(i, j, gameController);
                squares[i][j] = square;
                gridPane.add(square, i, j);
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Square getSquare(int i, int j) {
        return squares[i][j];
    }

    public void setSquareMove(int i, int j, Player player) {
        getSquare(i, j).move(player);
    }

    public Player getSquareMove(int i, int j) {
        return getSquare(i, j).getMove();
    }

    public void printBoard() {
        System.out.println("board:");
        for (int i = 0; i < GameController.RCCount; i++) {
            for (int j = 0; j < GameController.RCCount; j++) {
                System.out.print(squares[j][i] + " | \t");
            }
            System.out.println();
        }
    }
}
