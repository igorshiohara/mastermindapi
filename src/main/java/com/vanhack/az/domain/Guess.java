package com.vanhack.az.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * This class represents each Guess in a specific game
 * 
 * @author Igor K. Shiohara
 *
 */
public class Guess implements Serializable {

	private static final long serialVersionUID = 8645373069470520797L;

	/**
	 * An user that did the guess
	 */
	private final String user;

	/**
	 * The pins that the user used for this guess
	 */
	private final List<Pin> pins;

	/**
	 * The game key
	 */
	private final String gameKey;

	public Guess(Builder builder) {
		this.user = builder.user;
		this.pins = builder.pins;
		this.gameKey = builder.gameKey;
	}

	public String getUser() {
		return user;
	}

	public List<Pin> getPins() {
		return pins;
	}

	public String getGameKey() {
		return gameKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pins == null) ? 0 : pins.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Guess other = (Guess) obj;
		if (pins == null) {
			if (other.pins != null)
				return false;
		} else if (!pins.equals(other.pins))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Guess [user=" + user + ", colors=" + pins + ", gameKey=" + gameKey + "]";
	}

	public static final class Builder {

		private String user;
		private List<Pin> pins;
		private String gameKey;

		public Builder withUser(final String user) {
			this.user = user;
			return this;
		}

		public Builder withPins(final List<Pin> pins) {
			this.pins = pins;
			return this;
		}

		public Builder withGameKey(final String gameKey) {
			this.gameKey = gameKey;
			return this;
		}

		public Guess build() {
			validate();
			return new Guess(this);
		}

		private void validate() {
			if(StringUtils.isBlank(user) || StringUtils.isBlank(gameKey) || pins == null) {
				throw new IllegalArgumentException();
			}
		}

		public static Builder instance() {
			return new Builder();
		}

	}

}
