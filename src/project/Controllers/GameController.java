package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import project.Main;
import project.Helpers.AI;
import project.Models.Board;
import project.Models.GameMode;
import project.Models.GameModel;
import project.Models.Player;
import project.Models.Square;
import project.Models.Winner;

public class GameController implements Initializable {
    @FXML
    private GridPane boardGridPane;

    @FXML
    private Label gameState;

    @FXML
    private Button resetButton;
    @FXML
    private Button menuButton;

    public final static int RCCount = 3; // Row and Columns Count

    public Player currentTurn;
    public Board board;
    private AI aiPlayer;

    private String userid;
    private String opponentid;
    private GameMode gameMode;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.userid = Main.getRouteParameters().get("userid");
        this.gameMode = GameMode.fromString(Main.getRouteParameters().get("mode"));

        System.out.println("Your User id: " + this.userid + "\n");
        System.out.println("Game Mode : " + this.gameMode + "\n");
        if (this.gameMode == GameMode.ONE_PLAYER) {
            this.aiPlayer = new AI(this);
        } else {
            this.opponentid = Main.getRouteParameters().get("userid2");
            System.out.println("Opponent User id: " + this.opponentid + "\n");
        }

        System.out.println("Setup Tic Tac Toe board");
        this.currentTurn = Player.X; //First Turn is always X which is the player 1 (userid)
        this.board = new Board(boardGridPane, this);
        // Create the grid Constraints
        for (int i = 0; i < RCCount; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            this.boardGridPane.getColumnConstraints().add(cc);

            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            this.boardGridPane.getRowConstraints().add(rc);
        }
    }

    public void resetBoard() {
        for (int i = 0; i < RCCount; i++) {
            for (int j = 0; j < RCCount; j++) {
                board.setSquareMove(i, j, null);
            }
        }
        currentTurn = Player.X;
        gameState.setText("");
        boardGridPane.setDisable(false);
        resetButton.setDisable(true);
        menuButton.setDisable(true);
    }

    public void moveDone() {
        Winner winner = checkWinner();
        if (winner != null) {
            System.out.println("Winner: " + winner);
            System.out.println("Game Over");
            if (winner == Winner.TIE) {
                if (gameMode == GameMode.ONE_PLAYER) {
                    GameModel.addSPRecord(userid, "TIE");
                } else if (gameMode == GameMode.TWO_PLAYER) {
                    GameModel.addTPRecord(userid, opponentid, "TIE");
                }
                gameState.setText("You Tied! Try Again");
            } else {
                if (gameMode == GameMode.ONE_PLAYER) {
                    if (winner == Winner.X) {
                        GameModel.addSPRecord(userid, userid);
                    } else if (winner == Winner.O) {
                        GameModel.addSPRecord(userid, "COMP");
                    }
                } else if (gameMode == GameMode.TWO_PLAYER) {
                    if (winner == Winner.X) {
                        GameModel.addTPRecord(userid, opponentid, userid);
                    } else if (winner == Winner.O) {
                        GameModel.addTPRecord(userid, opponentid, opponentid);
                    }
                }
                gameState.setText(winner + " Wins!");
            }
            resetButton.setDisable(false);
            menuButton.setDisable(false);
            boardGridPane.setDisable(true);
        } else {
            if (currentTurn == Player.X) {
                currentTurn = Player.O;
                if (this.gameMode == GameMode.ONE_PLAYER) {
                    Square bestSquare = aiPlayer.getMove();
                    System.out.println("Best Move: " + bestSquare);
                    bestSquare.moveAction();
                }
            } else {
                currentTurn = Player.X;
            }
        }
    }

    public Winner checkWinner() {
        // printGameState();

        //Vertical Check
        // System.out.print("Checking Columns: ");
        for (int col = 0; col < RCCount; col++) {
            boolean check = true;
            Player lastMove = board.getSquareMove(col, 0); //First element of each Column
            for (int row = 0; row < RCCount; row++) {
                Square square = board.getSquare(col, row);
                if (lastMove != null && square.getMove() == lastMove) {
                    lastMove = square.getMove();
                } else {
                    check = false;
                }
            }
            // System.out.println("Vertical Check: " + check + ", lastMove: " + lastMove);
            if (check && lastMove != null) {
                return lastMove.toWinner();
            }
        }

        //Horizontal Check
        // System.out.print("Checking Rows: ");
        for (int row = 0; row < RCCount; row++) {
            boolean check = true;
            Player lastMove = board.getSquareMove(0, row); //First element of each row
            for (int col = 0; col < RCCount; col++) {
                Square square = board.getSquare(col, row);
                if (lastMove != null && square.getMove() == lastMove) {
                    lastMove = square.getMove();
                } else {
                    check = false;
                }
            }
            // System.out.println("Horizontal Check: " + check + ", lastMove: " + lastMove);
            if (check) {
                return lastMove.toWinner();
            }
        }

        //! Diagonal Checks

        //Left Diagonal
        {
            int col = 0;
            int row = 0;
            boolean check = true;
            Player lastMove = board.getSquareMove(col, row); //First element
            while (col < RCCount && row < RCCount) {
                Square square = board.getSquare(col, row);
                if (lastMove != null && square.getMove() == lastMove) {
                    lastMove = square.getMove();
                    col++;
                    row++;
                } else {
                    check = false;
                    break;
                }
            }
            if (check) {
                return lastMove.toWinner();
            }
        }

        //Right Diagonal
        {
            int col = 0;
            int row = RCCount - 1;
            boolean check = true;
            Player lastMove = board.getSquareMove(col, row); //First element

            while (col < RCCount && row >= 0) {
                Square square = board.getSquare(col, row);
                if (lastMove != null && square.getMove() == lastMove) {
                    lastMove = square.getMove();
                    col++;
                    row--;
                } else {
                    check = false;
                    break;
                }
            }
            if (check) {
                return lastMove.toWinner();
            }
        }
        if (isTied()) {
            return Winner.TIE;
        } else {
            return null;
        }
    }

    private boolean isTied() {
        for (int i = 0; i < RCCount; i++) {
            for (int j = 0; j < RCCount; j++) {
                if (board.getSquareMove(j, i) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printGameState() {
        System.out.println("Game State: " + currentTurn);
        board.printBoard();
    }

    @FXML
    private void toMenu() throws IOException {
        Main.gotoPage("mainPage");
    }
}
