package com.vanhack.az.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.vanhack.az.exceptions.MastermindValidationException;

/**
 * This class represents a Session to handle each game
 * 
 * @author Igor K. Shiohara
 */
public class Session implements Serializable {
	
	private static final long serialVersionUID = -2002066704117191302L;

	/**
	 * A session id
	 */
	private final long id;
	
	/**
	 * Created time to register when the game starts and handle time limit of the game
	 */
	private final LocalDateTime createdTime;
	
	/**
	 * Users connected in this session
	 */
	private final List<String> users;
	
	private Session(Builder builder) {
		this.id = builder.id;
		this.createdTime = builder.createdTime;
		this.users = builder.users;
	}
	
	public long getId() {
		return id;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public List<String> getUsers() {
		return users;
	}

	public void addUser(final String user) {
		if (users.size() > 2) {
			throw new MastermindValidationException("This game is already full. No more than 2 player can enter in a game.");
		}
		users.add(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		Session other = (Session) obj;
		if (createdTime == null) {
			if (other.createdTime != null)
				return false;
		} else if (!createdTime.equals(other.createdTime))
			return false;
		if (id != other.id)
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", createdTime=" + createdTime + ", users=" + users + "]";
	}

	public static final class Builder {
		
		private long id;
		private LocalDateTime createdTime;
		private List<String> users;
		
		public Builder withId(final long id) {
			this.id = id;
			return this;
		}
		
		public Builder withCreatedTime(final LocalDateTime createdTime) {
			this.createdTime = createdTime;
			return this;
		}
		
		public Builder withUsers(final List<String> users) {
			this.users = users;
			return this;
		}
		
		public Session build() {
			return new Session(this);
		}
		
	}
	
}
