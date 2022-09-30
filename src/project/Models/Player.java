package project.Models;

public enum Player {
    X, O;

    public Winner toWinner() {
        if (this == X) {
            return Winner.X;
        } else if (this == O) {
            return Winner.O;
        } else {
            return Winner.TIE;
        }
    }
}