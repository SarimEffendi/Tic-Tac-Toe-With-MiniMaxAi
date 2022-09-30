package project.Helpers;

import project.Controllers.GameController;
import project.Models.Board;
import project.Models.Player;
import project.Models.Square;
import project.Models.Winner;

public class AI {
    GameController gameController;

    public AI(GameController gameController) {
        this.gameController = gameController;
    }

    public Square getMove() {
        Board board = gameController.board;

        Square bestMove = null;
        float bestScore = -Integer.MAX_VALUE;
        for (int i = 0; i < GameController.RCCount; i++) {
            for (int j = 0; j < GameController.RCCount; j++) {
                if (board.getSquareMove(i, j) == null) {
                    board.setSquareMove(i, j, gameController.currentTurn);
                    float score = minimax(gameController.board, 1, false);
                    System.out.println("Check Move: " + i + " " + j + " Score: " + score);
                    board.setSquareMove(i, j, null);
                    if (score > bestScore) {
                        System.out.println(" -- Better score: " + score + " at " + i + "," + j);
                        bestScore = score;
                        bestMove = board.getSquare(i, j);
                    }
                }
            }
        }
        System.out.println();
        return bestMove;
    }

    //minimax
    //Complexity : O(b^m) 
    // where b is branching factor (or possible moves per turn) "3x3=9" 
    // and m is depth of tree so mostly 5 or 6
    public float minimax(Board board, int depth, boolean isMax) {
        // For default values of alpha and Beta
        return minimax(board, depth, isMax, -Float.MAX_VALUE, Float.MAX_VALUE);
    }

    public float minimax(Board board, int depth, boolean isMax, float alpha, float beta) {
        Winner winner = gameController.checkWinner();
        if (winner != null) {
            if (Winner.X == winner) {
                return (float) winner.toInt() + depth; // depth is bad for score
            } else {
                return (float) winner.toInt() - depth; // depth is bad for score
            }
        }

        if (isMax) { //Os Turn so maximize
            float bestScore = -Float.MAX_VALUE;
            for (int i = 0; i < GameController.RCCount; i++) {
                for (int j = 0; j < GameController.RCCount; j++) {
                    if (board.getSquareMove(i, j) == null) {
                        board.setSquareMove(i, j, Player.O);
                        float currentScore = minimax(board, depth + 1, !isMax, alpha, beta);
                        board.setSquareMove(i, j, null);
                        bestScore = Math.max(currentScore, bestScore);
                        alpha = Math.max(alpha, bestScore);
                        // Alpha Beta Pruning
                        if (beta <= alpha)
                            break;
                    }
                }
            }
            return bestScore;
        } else { //Xs turn so minimize
            float bestScore = Float.MAX_VALUE;
            for (int i = 0; i < GameController.RCCount; i++) {
                for (int j = 0; j < GameController.RCCount; j++) {
                    if (board.getSquareMove(i, j) == null) {
                        board.setSquareMove(i, j, Player.X);
                        float currentScore = minimax(board, depth + 1, !isMax, alpha, beta);
                        board.setSquareMove(i, j, null);
                        bestScore = Math.min(currentScore, bestScore);
                        beta = Math.min(beta, bestScore);
                        // Alpha Beta Pruning
                        if (beta <= alpha)
                            break;
                    }
                }
            }
            return bestScore;
        }

    }

}
