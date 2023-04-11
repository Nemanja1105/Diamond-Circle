package Figures;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;

import Simulations.Timer;

public abstract class Figure {
	private static int globalId = 1;
	protected Integer id;
	protected Color color;
	protected boolean reachedToGoal;
	protected HashSet<Integer> traveledPath = new HashSet<>();
	protected int diamonds;
	protected int currentPosition;
	protected long startTime;
	protected long endTime;

	public Figure() {
		this.color = null;
		this.reachedToGoal = false;
		this.diamonds = 0;
		this.id = globalId++;
		this.currentPosition = 0;
		this.startTime = 0;
		this.endTime = 0;
	}

	public Figure(Color color) {
		this.color = color;
		this.reachedToGoal = false;
		this.diamonds = 0;
		this.id = globalId++;
		this.currentPosition = 0;
		this.startTime = 0;
		this.endTime = 0;
	}

	public Color getColor() {
		return this.color;
	}

	public int getCurrentPosition() {
		return this.currentPosition;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setStartTime(long value) {
		this.startTime = value;
	}

	public void setEndTime(long value) {
		this.endTime = value;
	}

	public LocalTime getMovementTime() {
		// return this.endTime.minusSeconds(this.startTime.getSecond());
		return LocalTime.ofSecondOfDay(this.endTime-this.startTime);
	}

	public boolean isReachedToGoal() {
		return this.reachedToGoal;
	}

	public HashSet<Integer> getTraveledPath() {
		return this.traveledPath;
	}

	public int getDiamonds() {
		return this.diamonds;
	}

	public int getId() {
		return this.id;
	}

	public void setColor(Color value) {
		this.color = value;
	}

	public void ReachedToGoal() {
		this.reachedToGoal = true;
	}

	public void addDiamond() {
		this.diamonds++;
	}

	public void resetDiamonds() {
		this.diamonds = 0;
	}

	public void setCurrentPosition(int value) {
		this.currentPosition = value;
	}

	public void addFieldToTraveledPath(int fieldNum) {
		this.traveledPath.add(fieldNum);
	}

	public static void resetGlobalId() {
		Figure.globalId = 1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Figura " + this.id + " (?, " + this.color.toString() + ") - " + "predjeni put (");
		int n = this.traveledPath.size();
		int i = 0;
		// for (int i = 0; i < n; i++)
		for (var path : this.traveledPath) {
			builder.append(path);
			if (i != n - 1)
				builder.append("-");
			i++;
		}
		builder.append(") - stigla do cilja (");
		if (this.reachedToGoal)
			builder.append("da)");
		else
			builder.append("ne)");
		//builder.append(" Vrijeme kretanja:"+Timer.getFormattedTime(this.getMovementTime()));
		//builder.append("\n");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
