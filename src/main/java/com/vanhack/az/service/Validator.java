package com.vanhack.az.service;

import java.util.List;

import com.vanhack.az.domain.Pin;
import com.vanhack.az.domain.Session;
import com.vanhack.az.exceptions.MastermindValidationException;

/**
 * 
 * All validation in this class
 * 
 * @author Igor K. Shiohara
 *
 */
public class Validator {
	
	private static final int MAX_GUESSES = 10;

	/**
	 * Checks whether the user is in session
	 */
	public void validateUserInSession(final String user, final Session session){
		if (!session.getUsers().contains(user)) {
			throw new MastermindValidationException("User not found in this session.");
		}
	}
	
	/**
	 * Check if the retry limit was exceeded
	 */
	public void validateGuessLimit(int numGuesses) {
		if (numGuesses >= MAX_GUESSES) {
			throw new MastermindValidationException("The number of the guesses was excedeed.");
		}
	}
	
	/**
	 * Check if the positions are correct and if the colors are correct too
	 */
	public void validatePins(final List<Pin> pins){
		
	}

}
