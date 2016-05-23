package com.vanhack.az.domain.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GuessRequest {

	private String user;

	private String gameKey;

	private List<PinRequest> pins;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public List<PinRequest> getPins() {
		if (pins == null)
			pins = new ArrayList<PinRequest>();

		return pins;
	}

	public void setPins(List<PinRequest> pins) {
		this.pins = pins;
	}

}
