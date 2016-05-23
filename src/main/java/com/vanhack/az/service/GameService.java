package com.vanhack.az.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vanhack.az.domain.Color;
import com.vanhack.az.domain.Game;
import com.vanhack.az.domain.Game.Key;
import com.vanhack.az.domain.Guess;
import com.vanhack.az.domain.GuessResults;
import com.vanhack.az.domain.Pin;
import com.vanhack.az.domain.Session;
import com.vanhack.az.domain.dto.JoinRequest;
import com.vanhack.az.exceptions.MastermindValidationException;
import com.vanhack.az.store.GameStore;
import com.vanhack.az.store.SessionStore;

import rx.Observable;

/**
 * @author Igor K. Shiohara
 * @author geiser
 */
public class GameService {

	/**
	 * Number of the colors able
	 */
	public static final int CODE_LENGTH = 8;

	private GameStore gameStore;
	private SessionStore sessionStore;

	@Inject
	public GameService(final GameStore gameStore, final SessionStore sessionStore) {
		this.gameStore = gameStore;
		this.sessionStore = sessionStore;
	}

	/**
	 * Create a new game
	 * 
	 * @param user
	 * @return Game
	 */
	public Observable<Game> createNewGame(String user) {
		Session session = sessionStore.save(user);

		Game game = new Game.Builder().withCodeLength(CODE_LENGTH).withPins(generateRandomPins())
				.withGameKey(Game.Key.generate()).withGuessResults(Lists.newArrayList()).withSession(session)
				.withSolved(false).build();

		gameStore.save(game.getGameKey(), game);

		return Observable.just(game);
	}

	/**
	 * Join a user in a game
	 * 
	 * @param user
	 *            User that is joining
	 * @param Session
	 *            the session of the game
	 */
	public void join(JoinRequest joinRequest) {
		final  Optional<Game> game = gameStore.get(Key.fromExisting(joinRequest.getGameKey()));
		checkGameExistent(joinRequest.getGameKey(), game);
		if (game.get().getTimeGameStarted() != null) {
			throw new MastermindValidationException("This game already started. It is possible the game creator started in a single player.");
		}
		
		final Optional<Session> session = sessionStore.get(joinRequest.getSessionId());
		if (!session.isPresent()) {
			throw new MastermindValidationException(
					"No sessions were found with this session id: " + joinRequest.getSessionId());
		}
		if (session.get().getUsers().size() >= 2) {
			throw new MastermindValidationException("This room is already full. Sorry.");
		}
		if (session.get().getUsers().contains(joinRequest.getUser())) {
			throw new MastermindValidationException("There is another user with this username. Please use other.");
		}
		session.get().addUser(joinRequest.getUser());
	}

	/**
	 * Do a guess
	 * 
	 * @param guessRequest
	 * @return
	 */
	public Observable<GuessResults> guess(final Guess guess) {
		final Optional<Game> original = gameStore.get(Key.fromExisting(guess.getGameKey()));

		checkGameExistent(guess.getGameKey(), original);
		
		if (CollectionUtils.isNotEmpty(original.get().getGuessResults()) && original.get().getSession().getUsers().size() > 1) {
			final String lastPlayer = lastGuessPlayer(original.get()).get();
			if (lastPlayer.equals(guess.getUser())) {
				throw new MastermindValidationException("It is not your turn.");
			}
		}	
		
		if ((original.get().getSession().getUsers().size() == 1 && original.get().getNumGuesses() > 10) || 
			(original.get().getSession().getUsers().size() > 1 && original.get().getNumGuesses() > 20)) {
			throw new MastermindValidationException("The limit of 10 tries was excedeed.");
		}
		
		final List<Pin> pins = guess.getPins();
		final int guessNum = original.get().getNumGuesses();
		final String user = guess.getUser();
		final Session session = original.get().getSession();

		Validator validator = new Validator();
		validator.validatePins(pins);
		validator.validateGuessLimit(guessNum);
		validator.validateUserInSession(user, session);

		final GuessResults guessResults = checkMatch(guess);
		original.get().getGuessResults().add(guessResults);

		updateGame(original, guessResults);

		return Observable.just(guessResults);
	}

	/**
	 * Retrieves whose turn it is
	 * 
	 * @param gameKey
	 * @return The user turn
	 */
	public Observable<String> status(final String gameKey) {
		final Optional<Game> game = gameStore.get(Key.fromExisting(gameKey));
		checkGameExistent(gameKey, game);
		
		String player;
		if (game.get().getSession().getUsers().size() == 1) { //Singleplayer
			player = game.get().getSession().getUsers().get(0);
		} else {
			player = nextGuessPlayer(game.get()).orElse(StringUtils.EMPTY);
		}	
			
		return Observable.just(player);
	}

	/**
	 * Retrieves the history of the {@link GuessResults}
	 * 
	 * @param gameKey
	 * @param user
	 * @return
	 */
	public Observable<List<GuessResults>> history(final String gameKey, final Optional<String> user) {
		final Optional<Game> game = gameStore.get(Key.fromExisting(gameKey));
		checkGameExistent(gameKey, game);
		if (user.isPresent()) {
			return Observable.just(game.get().getGuessResults().stream()
					.filter(u -> u.getGuess().getUser().equalsIgnoreCase(user.get())).collect(Collectors.toList()));
		}
		return Observable.just(game.get().getGuessResults());
	}
	
	/**
	 * Retrieves the game info
	 * @param gameKey
	 * @return Game
	 */
	public Observable<Game> gameInfo(final String gameKey) {
		Optional<Game> game = gameStore.get(Key.fromExisting(gameKey));
		checkGameExistent(gameKey, game);
		return Observable.just(game.get());
	}

	private Optional<String> lastGuessPlayer(final Game game) {
		final Optional<GuessResults> lastGuessResults = game.getGuessResults().parallelStream().reduce((a, b) -> b);
		if (lastGuessResults.isPresent()) {
			return Optional.of(lastGuessResults.get().getGuess().getUser());
		}
		return Optional.empty();
	}
	
	private Optional<String> nextGuessPlayer(final Game game) {
		final Optional<String> lastGuessPlayer = lastGuessPlayer(game);
		Optional<GuessResults> otherUser = game.getGuessResults().parallelStream().filter(r -> !r.getGuess().getUser().equalsIgnoreCase(lastGuessPlayer.get())).findAny();
		if (otherUser.isPresent()) {
			return Optional.of(otherUser.get().getGuess().getUser());
		}
		return Optional.empty();
	}

	private GuessResults checkMatch(final Guess guessRequest) {

		final Game game = gameStore.get(Key.fromExisting(guessRequest.getGameKey())).get();
		final List<Pin> correctPins = game.getPins();
		final List<Pin> guessPins = guessRequest.getPins();

		Set<Pin> near = Sets.newHashSet();
		Set<Pin> exact = Sets.newHashSet();

		for (Pin guessPin : guessPins) {
			for (Pin correctPin : correctPins) {
				if (correctPin.getColor().equals(guessPin.getColor())) {
					if (correctPin.getPosition() == guessPin.getPosition()) {
						exact.add(guessPin);
					} else {
						near.add(guessPin);
					}
				}
			}
		}

		int nearWithoutExact = (int) near.stream().filter(nearPin -> {
			for (Pin exactPin : exact) {
				if (exactPin.getColor().equals(nearPin.getColor())) {
					return false;
				}
			}
			return false;
		}).count();

		return new GuessResults.Builder().withGuess(guessRequest).withExact(exact.size()).withNear(nearWithoutExact)
				.withSolved(exact.size() == CODE_LENGTH).build();
	}

	private List<Pin> generateRandomPins() {

		Color[] colors = Color.values();

		List<Pin> pins = Lists.newArrayList();
		Random r = new Random();

		while (pins.size() < CODE_LENGTH) {
			Color color = colors[r.nextInt(CODE_LENGTH)];
			pins.add(new Pin.Builder().withColor(color).withPosition(pins.size() + 1).build());
		}

		return pins;
	}

	private void updateGame(final Optional<Game> original, final GuessResults guessResults) {
		final Game.Builder builder = new Game.Builder(original.get());
		builder.withNumGuesses(original.get().getNumGuesses() + 1);
		builder.withTimeGameStarted(LocalDateTime.now());
		if (guessResults.isSolved()) {
			builder.withSolved(true);
			builder.withTimeGameFinished(LocalDateTime.now());
		}
		gameStore.save(original.get().getGameKey(), builder.build());
	}
	
	private void checkGameExistent(final String gameKey, final Optional<Game> game) {
		if (!game.isPresent()) {
			throw new MastermindValidationException("No game was found for this gameKey: " + gameKey);
		}
	}
}
