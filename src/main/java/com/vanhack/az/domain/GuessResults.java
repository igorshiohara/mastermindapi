package com.vanhack.az.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a domain class that represents all guess results. Example: This guess had 1 exact and 5 near with the colors RPYGOGOP
 * 
 * @author Igor K. Shiohara
 */
@XmlRootElement
public class GuessResults implements Serializable{

	private static final long serialVersionUID = -4486516675227289341L;

	/**
	 * The number of exact guess
	 */
	private final int exact;
	
	/**
	 * The number of near guess
	 */
	private final int near;
	
	/**
	 * The Guess : Colors and user
	 */
	private final Guess guess;
	
	/**
	 * Return true if the player win with this guess
	 */
	private final boolean solved;
	
	private GuessResults(Builder builder) {
		this.exact = builder.exact;
		this.near = builder.near;
		this.guess = builder.guess;
		this.solved = builder.solved;
	}

	public int getExact() {
		return exact;
	}

	public int getNear() {
		return near;
	}

	public Guess getGuess() {
		return guess;
	}
	
	public boolean isSolved() {
		return solved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + exact;
		result = prime * result + ((guess == null) ? 0 : guess.hashCode());
		result = prime * result + near;
		result = prime * result + (solved ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GuessResults other = (GuessResults) obj;
		if (exact != other.exact)
			return false;
		if (guess == null) {
			if (other.guess != null)
				return false;
		} else if (!guess.equals(other.guess))
			return false;
		if (near != other.near)
			return false;
		if (solved != other.solved)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GuessResults [exact=" + exact + ", near=" + near + ", guess=" + guess + ", solved=" + solved + "]";
	}

	public static final class Builder {

		private int exact;
		private int near;
		private Guess guess;
		private boolean solved;

		public Builder withExact(final int exact) {
			this.exact = exact;
			return this;
		}
		
		public Builder withNear(final int near) {
			this.near = near;
			return this;
		}
		
		public Builder withGuess(final Guess guess) {
			this.guess = guess;
			return this;
		}
		
		public Builder withSolved(final boolean solved) {
			this.solved = solved;
			return this;
		}
		
		public GuessResults build() {
			return new GuessResults(this);
		}
		
	}
	
}
