package com.vanhack.az.store;

import java.util.Optional;

import com.vanhack.az.domain.Session;

/**
 * Handle the Session store
 * 
 * @author Igor K. Shiohara
 */
public interface SessionStore {
	
	/**
	 * Saves the session data.
	 * 
	 * @param User to save in session
	 * @return persisted session object
	 */
	Session save(final String user);

	/**
	 * Retrieves a session based on the session id.
	 * 
	 * @param id session id
	 * @return the session object
	 */
	Optional<Session> get(final long id);

}
