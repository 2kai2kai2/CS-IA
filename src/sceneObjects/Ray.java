package sceneObjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Ray {

	private Point origin;
	private Vector vector;

	public Ray(Point origin, Vector vector) {
		this.origin = origin;
		this.vector = vector;
	}

	public Point getPoint() {
		return origin;
	}

	public Vector getVector() {
		return vector;
	}

	public boolean isPointOnLine(Point p) {
		double dX = p.getX() - origin.getX();
		double dY = p.getY() - origin.getY();
		double dZ = p.getZ() - origin.getZ();

		// First - The point is on a line represented by the vector
		// Second - The point is not behind the vector origin (rays are infinite but
		// have a starting point)
		return dX / vector.getX() == dY / vector.getY() && dY / vector.getY() == dZ / vector.getZ()
				&& dX / vector.getX() >= 0;
	}

	public Point triIntersect(Triangle t) {
		return new Point(1, 1, 1); // TODO
	}

	/**
	 * Gets the intersects for all the Triangles given, based off triIntersect.
	 * 
	 * @param tris An ArrayList of Triangles to search for intersects with.
	 * @return A HashMap of the Triangles that have intersects and the points at
	 *         which they intersect. Excludes Triangles with no intersects.
	 */
	public HashMap<Triangle, Point> allIntersects(ArrayList<Triangle> tris) {
		HashMap<Triangle, Point> intersects = new HashMap<Triangle, Point>();
		for (Triangle t : tris) {
			Point intersect = triIntersect(t);
			if (intersect != null) {
				intersects.put(t, intersect);
			}
		}
		return intersects;
	}
}
