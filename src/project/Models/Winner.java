package project.Models;

public enum Winner {
    X, O, TIE;

    public int toInt() {
        if (this == O) {
            return 10;
        } else if (this == X) {
            return -10;
        } else {
            return 0;
        }
    }

    public Player toPlayer() {
        if (this == X) {
            return Player.X;
        } else if (this == O) {
            return Player.O;
        } else {
            return null;
        }
    }
}
