package com.vanhack.az.store;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.vanhack.az.domain.Game;
import com.vanhack.az.domain.Game.Key;


/**
 * 
 * @author geiser
 *
 */
public class InMemoryGameStoreTest {

	@Test
	public void testSaveAndGet() {

		InMemoryGameStore store = new InMemoryGameStore();

		Key key = Key.generate();
		Game game = new Game.Builder().withCodeLength(4).withPins(Lists.newArrayList()).build();
		
		store.save(key, game);
		
		Game loadedGame = store.get(key).get();
		
		Assert.assertEquals(game, loadedGame);

	}

}
