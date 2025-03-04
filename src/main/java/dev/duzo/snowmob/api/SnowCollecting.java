package dev.duzo.snowmob.api;

public interface SnowCollecting {
	int getSnowLevel();

	int getMaxSnow();

	/**
	 * Set the snow level of the entity
	 *
	 * @param level the snow level
	 * @return true if the snow level was set, false otherwise
	 */
	boolean setSnowLevel(int level);

	default boolean addSnow(int amount) {
		return setSnowLevel(getSnowLevel() + amount);
	}

	default void clearSnow() {
		setSnowLevel(0);
	}
}
