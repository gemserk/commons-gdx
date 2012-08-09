package com.gemserk.util.perf;

import com.gemserk.componentsengine.utils.RandomAccessMap;

public class PerfLogger {

	public static class PerfData {
		public FloatSlidingWindowArray data;
		public float current;
		private final float defaultValue;

		
		public PerfData(int windowSize) {
			this(windowSize,0);
		}
		
		public PerfData(int windowSize, float defaultValue) {
			this.defaultValue = defaultValue;
			data = new FloatSlidingWindowArray(windowSize);
		}

		public void update() {
			data.add(current);
			current=defaultValue;
		}

		public void clear() {
			data.clear();
		}
	}

	boolean enabled = false;
	RandomAccessMap<String, PerfData> perfDatas = new RandomAccessMap<String, PerfLogger.PerfData>();

	public PerfLogger(boolean enabled) {
		this.enabled = enabled;
	}

	public void update() {
		if (enabled) {
			for (int i = 0; i < perfDatas.size(); i++) {
				perfDatas.get(i).update();
			}
		}
	}

	/**
	 * 
	 * @param key
	 * @param perfData
	 * @return the perfData received as a parameter
	 */
	public PerfData register(String key, PerfData perfData) {
		perfDatas.put(key, perfData);
		return perfData;
	}

	public PerfData getPerfData(String key) {
		return perfDatas.get(key);
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void clear() {
		for (int i = 0; i < perfDatas.size(); i++) {
			perfDatas.get(i).clear();
		}
	}
}