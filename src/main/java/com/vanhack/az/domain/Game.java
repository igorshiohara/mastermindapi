package com.vanhack.az.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This class represents the Game data
 * 
 * @author Igor K. Shiohara
 */
@XmlRootElement
public class Game implements Serializable {

	private static final long serialVersionUID = 6097584376269267857L;

	/**
	 * The pins to discover of the game
	 */
	private final List<Pin> pins;

	/**
	 * The number of the colors the game supports
	 */
	private final int codeLength;

	/**
	 * The game key to enable access for other users
	 */
	private final Game.Key gameKey;

	/**
	 * The numbers of guesses
	 */
	private final int numGuesses;

	/**
	 * All guess results. Used to get the history.
	 */
	private final List<GuessResults> guessResults;

	/**
	 * To handle if the game was resolved or not
	 */
	private final boolean solved;

	/**
	 * The session to handle the games
	 */
	private final Session session;
	
	/**
	 * When the game started. Starts in the first guess
	 */
	private final LocalDateTime timeGameStarted;
	
	/**
	 * Finish when the game is solved
	 */
	private final LocalDateTime timeGameFinished;
	
	private Game(final Builder builder) {
		this.pins = builder.pins;
		this.codeLength = builder.codeLength;
		this.gameKey = builder.gameKey;
		this.numGuesses = builder.numGuesses;
		this.guessResults = builder.guessResults;
		this.solved = builder.solved;
		this.session = builder.session;
		this.timeGameStarted = builder.timeGameStarted;
		this.timeGameFinished = builder.timeGameFinished;
	}

	public List<Pin> getPins() {
		return pins;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public Game.Key getGameKey() {
		return gameKey;
	}

	public int getNumGuesses() {
		return numGuesses;
	}

	public List<GuessResults> getGuessResults() {
		return guessResults;
	}

	public boolean isSolved() {
		return solved;
	}

	public Session getSession() {
		return session;
	}
	
	public LocalDateTime getTimeGameStarted() {
		return timeGameStarted;
	}
	
	public LocalDateTime getTimeGameFinished() {
		return timeGameFinished;
	}

	public static Builder copy(final Game original) {

        return new Builder(original);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codeLength;
		result = prime * result + ((gameKey == null) ? 0 : gameKey.hashCode());
		result = prime * result + ((guessResults == null) ? 0 : guessResults.hashCode());
		result = prime * result + numGuesses;
		result = prime * result + ((pins == null) ? 0 : pins.hashCode());
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		result = prime * result + (solved ? 1231 : 1237);
		result = prime * result + ((timeGameFinished == null) ? 0 : timeGameFinished.hashCode());
		result = prime * result + ((timeGameStarted == null) ? 0 : timeGameStarted.hashCode());
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
		Game other = (Game) obj;
		if (codeLength != other.codeLength)
			return false;
		if (gameKey == null) {
			if (other.gameKey != null)
				return false;
		} else if (!gameKey.equals(other.gameKey))
			return false;
		if (guessResults == null) {
			if (other.guessResults != null)
				return false;
		} else if (!guessResults.equals(other.guessResults))
			return false;
		if (numGuesses != other.numGuesses)
			return false;
		if (pins == null) {
			if (other.pins != null)
				return false;
		} else if (!pins.equals(other.pins))
			return false;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		if (solved != other.solved)
			return false;
		if (timeGameFinished == null) {
			if (other.timeGameFinished != null)
				return false;
		} else if (!timeGameFinished.equals(other.timeGameFinished))
			return false;
		if (timeGameStarted == null) {
			if (other.timeGameStarted != null)
				return false;
		} else if (!timeGameStarted.equals(other.timeGameStarted))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [pins=" + pins + ", codeLength=" + codeLength + ", gameKey=" + gameKey + ", numGuesses="
				+ numGuesses + ", guessResults=" + guessResults + ", solved=" + solved + ", session=" + session
				+ ", timeGameStarted=" + timeGameStarted + ", timeGameFinished=" + timeGameFinished + "]";
	}

	/**
	 * Represents a game key with its identifiers.
	 * 
	 * @author geiser
	 *
	 */
	@XmlRootElement
	public static class Key {

		private final String hash;

		private Key(String hash) {
			this.hash = hash;
		}

		/**
		 * Get the hash value.
		 * 
		 * @return hash value
		 */
		public String getHash() {
			return hash;
		}

		/**
		 * Create a key from an existing identifier.
		 * 
		 * @param uuid
		 * @return Key object
		 */
		public static Key fromExisting(String uuid) {
			return new Key(uuid);
		}

		/**
		 * Generates a new key.
		 * 
		 * @return Key object
		 */
		public static Key generate() {
			return new Key(UUID.randomUUID().toString());
		}

		@Override
		public int hashCode() {

			return new HashCodeBuilder().append(hash).build();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			return new EqualsBuilder().append(hash, other.hash).build();
		}

		@Override
		public String toString() {
			return "Key [hash=" + hash + "]";
		}

	}

	public static final class Builder {

		private List<Pin> pins;
		private int codeLength;
		private Game.Key gameKey;
		private int numGuesses;
		private List<GuessResults> guessResults;
		private boolean solved;
		private Session session;
		private LocalDateTime timeGameStarted;
		private LocalDateTime timeGameFinished;

		public Builder() {
			
		}
		
		public Builder(final Game original) {
			this.pins = original.getPins();
			this.codeLength = original.codeLength;
			this.gameKey = original.gameKey;
			this.numGuesses = original.numGuesses;
			this.guessResults = original.guessResults;
			this.solved = original.solved;
			this.session = original.session;
			this.timeGameStarted = original.timeGameStarted;
			this.timeGameFinished = original.timeGameFinished;
		}
		
		public Builder withPins(final List<Pin> pins) {
			this.pins = pins;
			return this;
		}

		public Builder withCodeLength(final int codeLength) {
			this.codeLength = codeLength;
			return this;
		}

		public Builder withGameKey(final Game.Key gameKey) {
			this.gameKey = gameKey;
			return this;
		}

		public Builder withNumGuesses(final int numGuesses) {
			this.numGuesses = numGuesses;
			return this;
		}

		public Builder withGuessResults(final List<GuessResults> guessResults) {
			this.guessResults = guessResults;
			return this;
		}

		public Builder withSolved(final boolean solved) {
			this.solved = solved;
			return this;
		}

		public Builder withSession(final Session session) {
			this.session = session;
			return this;
		}
		
		public Builder withTimeGameStarted(final LocalDateTime timeGameStarted) {
			this.timeGameStarted = timeGameStarted;
			return this;
		}
		
		public Builder withTimeGameFinished(final LocalDateTime timeGameFinished) {
			this.timeGameFinished = timeGameFinished;
			return this;
		}

		public Game build() {
			return new Game(this);
		}
	}

}
