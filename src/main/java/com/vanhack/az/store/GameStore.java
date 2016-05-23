package com.vanhack.az.store;

import java.util.Optional;

import com.vanhack.az.domain.Game;
import com.vanhack.az.domain.Game.Key;

/**
 * Methods to keep record of games.s
 * 
 * @author geiser
 *
 */
public interface GameStore {

	/**
	 * Saves the game data.
	 * 
	 * @param key
	 *            game key
	 * @param game
	 *            game object
	 * @return persisted game object
	 */
	Game save(Key key, Game game);

	/**
	 * Retrieves a game based on the key.
	 * 
	 * @param key
	 *            game key
	 * @return the game object
	 */
	Optional<Game> get(Key key);

}
