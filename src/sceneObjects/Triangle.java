package sceneObjects;

import java.awt.Color;

public class Triangle {
	private Point[] points;

	public Triangle(Point[] points) {
		this.points = points;
	}

	public Triangle(Point p1, Point p2, Point p3) {
		this(new Point[] { p1, p2, p3 });
	}

	/**
	 * Returns whether a point is on the triangle.
	 * 
	 * @param p The point to check.
	 * @return A boolean of whether it is on the triangle.
	 */
	public boolean isPointOnTriangle(Point p) {
		// 1: If the point is on the same plane as the triangle is - use normal eq of plane
		// 2: If all angles are from p1, the angle from p2 to p + p3 to p = p2 to p3
		// 3: Same as 2 but from another point so that the distance cannot be infinite
		// despite the angles from p1
		return true; // TODO
	}

	@Override
	public String toString() {
		return "[" + points[0].toString() + ", " + points[1].toString() + ", " + points[2].toString() + "]";
	}

	/**
	 * Gets the color of a spot on a triangle. Returns null if the point is not on
	 * the triangle.
	 * 
	 * @param p The point on the triangle.
	 * @return The color of the point.
	 */
	public Color pointColor(Point p) {
		if (isPointOnTriangle(p)) {
			return new Color(255, 255, 255); // TODO: Actually make this work
		} else
			return null;
	}
}
