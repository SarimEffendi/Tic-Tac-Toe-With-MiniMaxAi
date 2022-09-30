package project.Models;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import project.Controllers.GameController;

public class Square extends Button {
    private int row;
    private int col;
    GameController gameController;

    public Square(int row, int col, GameController gameController) {
        this.gameController = gameController;
        this.row = row;
        this.col = col;
        this.setText("");
        this.setFont(Font.font(35));
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setOnAction(event -> moveAction());
    }

    public void moveAction() {
        if (this.getText().equals("")) {
            System.out.println(gameController.currentTurn + " Move: " + row + " " + col);
            this.move(gameController.currentTurn);
            gameController.moveDone();
        }
    }

    public void move(Player player) {
        if (player == Player.X) {
            this.setText("X");
        } else if (player == Player.O) {
            this.setText("O");
        } else {
            this.setText("");
        }
    }

    public Player getMove() {
        if (this.getText() == "X") {
            return Player.X;
        } else if (this.getText() == "O") {
            return Player.O;
        } else {
            return null;
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Square[" + this.row + ", " + this.col + "] : " + this.getMove();
    }

}