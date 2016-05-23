package com.vanhack.az.store;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.vanhack.az.domain.Game;
import com.vanhack.az.domain.Game.Key;

/**
 * In-memory implementation of a {@link GameStore}.
 * 
 * @author geiser
 *
 */
public class InMemoryGameStore implements GameStore {

	private Cache<Key, Game> games = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

	@Override
	public Game save(Key key, Game game) {

		games.put(key, game);
		return game;
	}

	@Override
	public Optional<Game> get(Key key) {

		return Optional.ofNullable(games.getIfPresent(key));
	}

}
