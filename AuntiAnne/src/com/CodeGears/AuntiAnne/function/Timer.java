package com.CodeGears.AuntiAnne.function;

public class Timer {

	private int duration;
	private int elapse;
	private long currentTime;
	private long previousTime;
	private boolean active;

	public Timer() {
		active = false;
	}
	
	public void increaseDuration(int time){
		duration += time;
	}

	public int getDuration() {
		return duration;
	}

	public int getElapse() {
		return elapse;
	}

	public void start() {
		currentTime = System.currentTimeMillis();
		active = true;
	}

	public void update() {
		if (active) {
			previousTime = currentTime;
			currentTime = System.currentTimeMillis();
			elapse = (int) (currentTime - previousTime);
			duration += elapse;
		}
	}

	public void stop() {
		duration = 0;
		elapse = 0;
		active = false;
	}

	public void pause() {
		active = false;
	}
}
