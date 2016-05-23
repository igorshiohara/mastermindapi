package com.vanhack.az.domain;

public enum Color {

	RED("R"), BLUE("B"), GREEN("G"), YELLOW("Y"), ORANGE("O"), PURPLE("P"), CYAN(
			"C"), MAGENTA("M");

	private final String color;

	Color(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public static Color valueFor(String color) {
		switch (color) {
		case "R":
			return RED;
		case "B":
			return BLUE;
		case "G":
			return GREEN;
		case "Y":
			return YELLOW;
		case "O":
			return ORANGE;
		case "P":
			return PURPLE;
		case "C":
			return CYAN;
		case "M":
			return MAGENTA;

		default:
			throw new IllegalArgumentException();
		}
	}

}
