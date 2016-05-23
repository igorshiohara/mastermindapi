package com.vanhack.az.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.vanhack.az.domain.Game;
import com.vanhack.az.domain.Game.Key;
import com.vanhack.az.domain.Guess;
import com.vanhack.az.domain.GuessResults;
import com.vanhack.az.domain.Pin;
import com.vanhack.az.domain.Session;
import com.vanhack.az.service.GameService;

import rx.Observable;

/**
 * Tests all game services
 * 
 * @author Igor K. Shiohara
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

	@Mock
	GameStore gameStore;

	@Mock
	SessionStore sessionStore;

	@InjectMocks
	GameService gameService;

	Session session = new Session.Builder().withId(1).withUsers(Lists.newArrayList("test")).build();

	@Test
	public void createGameTest() {
		when(sessionStore.save(Mockito.anyString())).thenReturn(session);
		
		Observable<Game> newGame = gameService.createNewGame("Test1");
		Game game = newGame.toBlocking().single();

		// Game
		assertEquals(8, game.getCodeLength());
		assertEquals(8, game.getPins().size());
		assertNotNull(game.getGameKey().getHash());
		assertTrue(game.getGuessResults().isEmpty());
		assertEquals(0, game.getNumGuesses());
		assertFalse(game.isSolved());
		assertNotNull(game.getSession());

		// Session
		assertFalse(game.getSession().getUsers().isEmpty());
		assertTrue(game.getSession().getUsers().contains("test"));
	}

	@Test
	public void testGuessHalfExactHafNear() {

		Game game = new Game.Builder().withCodeLength(GameService.CODE_LENGTH)
				.withPins(Lists.newArrayList(new Pin.Builder().withColor("R").withPosition(1).build(),
						new Pin.Builder().withColor("B").withPosition(2).build(),
						new Pin.Builder().withColor("R").withPosition(3).build(),
						new Pin.Builder().withColor("B").withPosition(4).build(),
						new Pin.Builder().withColor("R").withPosition(5).build(),
						new Pin.Builder().withColor("B").withPosition(6).build(),
						new Pin.Builder().withColor("R").withPosition(7).build(),
						new Pin.Builder().withColor("B").withPosition(8).build()))
				.withGameKey(Game.Key.generate()).withGuessResults(Lists.newArrayList()).withSession(session)
				.withSolved(false).build();

		when(gameStore.get(any(Key.class))).thenReturn(Optional.of(game));

		Guess guess = new Guess.Builder().withGameKey(game.getGameKey().getHash()).withUser("test")
				.withPins(Lists.newArrayList(new Pin.Builder().withColor("R").withPosition(1).build(),
						new Pin.Builder().withColor("R").withPosition(2).build(),
						new Pin.Builder().withColor("R").withPosition(3).build(),
						new Pin.Builder().withColor("R").withPosition(4).build(),
						new Pin.Builder().withColor("R").withPosition(5).build(),
						new Pin.Builder().withColor("R").withPosition(6).build(),
						new Pin.Builder().withColor("R").withPosition(7).build(),
						new Pin.Builder().withColor("R").withPosition(8).build()))
				.build();

		GuessResults guessResults = gameService.guess(guess).toBlocking().single();

		assertEquals(4, guessResults.getExact());
		assertEquals(0, guessResults.getNear());
		assertEquals(false, guessResults.isSolved());
	}

	@Test
	public void testGuessMatchAndGameSolved() {

		Game game = new Game.Builder().withCodeLength(GameService.CODE_LENGTH)
				.withPins(Lists.newArrayList(new Pin.Builder().withColor("R").withPosition(1).build(),
						new Pin.Builder().withColor("R").withPosition(2).build(),
						new Pin.Builder().withColor("R").withPosition(3).build(),
						new Pin.Builder().withColor("R").withPosition(4).build(),
						new Pin.Builder().withColor("R").withPosition(5).build(),
						new Pin.Builder().withColor("R").withPosition(6).build(),
						new Pin.Builder().withColor("R").withPosition(7).build(),
						new Pin.Builder().withColor("R").withPosition(8).build()))
				.withGameKey(Game.Key.generate()).withGuessResults(Lists.newArrayList()).withSession(session)
				.withSolved(false).build();

		when(gameStore.get(any(Key.class))).thenReturn(Optional.of(game));

		Guess guess = new Guess.Builder().withGameKey(game.getGameKey().getHash()).withUser("test")
				.withPins(Lists.newArrayList(new Pin.Builder().withColor("R").withPosition(1).build(),
						new Pin.Builder().withColor("R").withPosition(2).build(),
						new Pin.Builder().withColor("R").withPosition(3).build(),
						new Pin.Builder().withColor("R").withPosition(4).build(),
						new Pin.Builder().withColor("R").withPosition(5).build(),
						new Pin.Builder().withColor("R").withPosition(6).build(),
						new Pin.Builder().withColor("R").withPosition(7).build(),
						new Pin.Builder().withColor("R").withPosition(8).build()))
				.build();

		GuessResults guessResults = gameService.guess(guess).toBlocking().single();

		assertEquals(8, guessResults.getExact());
		assertEquals(0, guessResults.getNear());
		assertEquals(true, guessResults.isSolved());
	}

}
