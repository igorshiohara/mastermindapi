package com.vanhack.az.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinRequest {

	private String color;
	private Integer position;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
