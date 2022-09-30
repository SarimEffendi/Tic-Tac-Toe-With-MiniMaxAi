package project.Models;

public enum GameMode {
	TWO_PLAYER,
	ONE_PLAYER;

	public static GameMode fromString(String input) {
		if (input.equals("TWO_PLAYER")) {
			return TWO_PLAYER;
		} else if (input.equals("ONE_PLAYER")) {
			return ONE_PLAYER;
		} else {
			return null;
		}
	}

}
