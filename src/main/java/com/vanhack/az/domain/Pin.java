package com.vanhack.az.domain;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import com.vanhack.az.domain.Pin.Builder;

/**
 * This class represent the pin of the game
 * 
 * @author Igor K. Shiohara
 *
 */
public class Pin implements Serializable {

	private static final long serialVersionUID = -3464629260556031961L;

	/**
	 * The color of the pin
	 */
	private final Color color;

	/**
	 * The position of the pin (1 to 8, if 8 is the max positions)
	 */
	private final Position position;

	private Pin(final Builder builder) {
		this.color = builder.color;
		this.position = builder.position;
	}

	public Color getColor() {
		return color;
	}

	public Position getPosition() {
		return position;
	}

	public Integer getPositionValue() {
		return position.getPos();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pin other = (Pin) obj;
		if (color != other.color)
			return false;
		if (position != other.position)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pin [color=" + color + ", position=" + position + "]";
	}

	public static final class Builder {

		private Color color;
		private Position position;

		public Builder withColor(final String color) {
			this.color = Color.valueFor(color);
			return this;
		}
		
		public Builder withColor(final Color color) {
			this.color = color;
			return this;
		}

		public Builder withPosition(final Integer position) {
			this.position = Position.valueFor(position);
			return this;
		}

		public Pin build() {
			validate();
			return new Pin(this);
		}

		private void validate() {
			if (color == null || position == null) {
				throw new IllegalArgumentException();
			}
		}

		public static Builder instance() {
			return new Builder();
		}

	}

}
