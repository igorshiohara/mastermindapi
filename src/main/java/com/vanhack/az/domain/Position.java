package com.vanhack.az.domain;

public enum Position {

	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);

	private final Integer pos;

	Position(Integer pos) {
		this.pos = pos;
	}

	public Integer getPos() {
		return pos;
	}

	public static Position valueFor(Integer position) {
		switch (position) {
		case 1:
			return ONE;

		case 2:
			return TWO;

		case 3:
			return THREE;

		case 4:
			return FOUR;

		case 5:
			return FIVE;

		case 6:
			return SIX;

		case 7:
			return SEVEN;

		case 8:
			return EIGHT;

		default:
			throw new IllegalArgumentException();
		}
	}

}
