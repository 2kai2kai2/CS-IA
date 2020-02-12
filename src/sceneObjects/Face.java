package sceneObjects;

import java.util.ArrayList;

public class Face {

	private ArrayList<Point> points;

	public Face(ArrayList<Point> points) {
		this.points = points;
	}

	public boolean isValid() {
		return true; // TODO
	}

	@Override
	public String toString() {
		String str = "[" + points.get(0).toString();
		for (int i = 1; i < points.size(); i++) {
			str += ", " + points.get(i).toString();
		}
		return str + "]";
	}
}
