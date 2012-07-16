package com.gemserk.util.logs;

/**
 * Allows to log stuff using a fixed time period between logs.
 */
public abstract class PeriodicLogger {

	long period;
	long startTime;

	public PeriodicLogger() {
		this(1000000000);
	}

	/**
	 * Constructs a new PeriodicLogger specifying the period between log.
	 * 
	 * @param period
	 *            The period in nano seconds.
	 */
	public PeriodicLogger(long period) {
		this.period = period;
		startTime = System.nanoTime();
	}

	/**
	 * Logs the current frames per second to the console.
	 */
	public void update() {
		if (System.nanoTime() - startTime > period) {
			log();
			startTime = System.nanoTime();
		}
	}

	protected abstract void log();

}
