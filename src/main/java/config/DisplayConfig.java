package config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisplayConfig {

	private static class Resolution {
		@JsonProperty("width")
		private int width = 0;

		@JsonProperty("height")
		private int height = 0;

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}

	@JsonProperty("sourceResolution")
	private Resolution sourceResolution;

	@JsonProperty("targetResolution")
	private Resolution targetResolution;

	public Resolution getSourceResolution() {
		return sourceResolution;
	}

	public Resolution getTargetResolution() {
		return targetResolution;
	}

	public float getRelativeXFactor(){
		return ((float) targetResolution.getWidth() / (float) sourceResolution.getWidth());
	}

	public int applyXScale(int x){
		return Math.round((float)x * getRelativeXFactor());
	}

	public float getRelativeYFactor(){
		return ((float) targetResolution.getHeight() / (float) sourceResolution.getHeight());
	}

	public int applyYScale(int y){
		return Math.round((float)y * getRelativeYFactor());
	}
}
