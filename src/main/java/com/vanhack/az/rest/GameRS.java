package com.vanhack.az.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanhack.az.domain.Game;
import com.vanhack.az.domain.Guess;
import com.vanhack.az.domain.GuessResults;
import com.vanhack.az.domain.Pin;
import com.vanhack.az.domain.dto.GuessRequest;
import com.vanhack.az.domain.dto.JoinRequest;
import com.vanhack.az.domain.dto.PinRequest;
import com.vanhack.az.service.GameService;

import rx.Observable;

/**
 * 
 * This class contains the core apis for this game
 * 
 * @author Igor K. Shiohara
 *
 */
@Path("api/")
public class GameRS {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameRS.class);

	private GameService gameService;

	@Inject
	public GameRS(final GameService gameService) {

		this.gameService = gameService;
	}

	/**
	 * This service is responsible to create a new game by an user
	 * 
	 * @param user
	 * @return Game
	 */
	@POST
	@Path("/new_game/{user}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewGame(@PathParam("user") final String user) {
		Game game = gameService
				.createNewGame(user)
				.doOnCompleted(
						() -> LOGGER.info("The game was create with sucess!"))
				.doOnError(
						error -> LOGGER
								.error("Error ocurred while trying to create a new game",
										error)).toBlocking().single();

		return Response.ok(game).build();
	}

	/**
	 * This service is responsible to join a user in a specific game already
	 * created
	 * 
	 * @param a
	 *            JoinRequest with user and session
	 */
	@POST
	@Path("/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response join(final JoinRequest joinRequest) {
		gameService.join(joinRequest);
		return Response.ok(joinRequest.getUser() + " entered in the game with sessionId " + joinRequest.getSessionId()).build();
	}

	/**
	 * This service is responsible to do a guess
	 * 
	 * @param GuessRequest
	 *            with user, gameKey and colors
	 * @return GuessResults
	 */
	@POST
	@Path("/guess")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guess(final GuessRequest guessRequest) {

		Guess.Builder builder = extractGuessParameters(guessRequest);

		GuessResults results = gameService
				.guess(builder.build())
				.doOnCompleted(
						() -> LOGGER.info("The guess was sent with sucess!"))
				.doOnError(
						error -> LOGGER.error(
								"Error ocurred while trying to send a guess",
								error)).toBlocking().single();
		
		return Response.ok(results).build();
	}

	/**
	 * Return whose turn it is
	 * 
	 * @param gameKey
	 * @return User turn
	 */
	@GET
	@Path("/status/{gameKey}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response status(@PathParam("gameKey") final String gameKey) {
		String response;
		
		Observable<String> observable = gameService.status(gameKey);
		
		if (StringUtils.isEmpty(observable.toBlocking().single())) {
			response = "Nobody played yet, anyone can start";
		} else {
			final String status = observable
								.doOnCompleted(() -> LOGGER.info("The status was executed with sucess!"))
								.doOnError(error -> LOGGER.error("Error ocurred while trying to get the status", error))
							.toBlocking().single();
			response = "It is time to " + status + " make the move.";
		}
		return Response.ok(response).build();
	}

	/**
	 * Return the history of the game
	 * 
	 * @param gameKey
	 * @return History game
	 */
	@GET
	@Path("/history/{gameKey}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response history(@PathParam("gameKey") final String gameKey,
			@QueryParam("byUser") final String user) {
		List<GuessResults> result = gameService.history(gameKey, Optional.ofNullable(user))
					.doOnCompleted(() -> LOGGER.info("The history was executed with sucess!"))
					.doOnError(error -> LOGGER.error("Error ocurred while trying to get the history", error))
				.toBlocking().single();
		return Response.ok(result).build();
	}
	
	/**
	 * Return the history of the game
	 * 
	 * @param gameKey
	 * @return History game
	 */
	@GET
	@Path("/game/{gameKey}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gameInfo(@PathParam("gameKey") final String gameKey,
			@QueryParam("byUser") final String user) {
		Game game = gameService.gameInfo(gameKey)
					.doOnCompleted(() -> LOGGER.info("The game info was executed with sucess!"))
					.doOnError(error -> LOGGER.error("Error ocurred while trying to get the game info", error))
				.toBlocking().single();
		return Response.ok(game).build();
	}
	
	private Guess.Builder extractGuessParameters(final GuessRequest guessRequest) {
		Guess.Builder builder = Guess.Builder.instance();
		Pin.Builder pinBuilder = Pin.Builder.instance();

		List<Pin> pins = new ArrayList<Pin>(guessRequest.getPins().size());

		for (PinRequest pinRequest : guessRequest.getPins()) {
			pinBuilder.withColor(pinRequest.getColor()).withPosition(
					pinRequest.getPosition());

			pins.add(pinBuilder.build());
		}

		builder.withGameKey(guessRequest.getGameKey())
				.withUser(guessRequest.getUser()).withPins(pins);
		return builder;
	}

}
