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

	/**
	 * Gets the point of origin for this Ray
	 * 
	 * @return A Point representing the origin of the Ray
	 */
	public Point getPoint() {
		return origin;
	}

	/**
	 * Gets a Vector representing the direction of this Ray
	 * 
	 * @return A vector representing the direction of this Ray
	 */
	public Vector getVector() {
		return vector;
	}

	/**
	 * Checks whether or not a point is in this ray
	 * 
	 * @param p The point to check
	 * @return Whether or not the point is in the ray
	 */
	public boolean isPointOnLine(Point p) {
		double dX = p.getX() - origin.getX();
		double dY = p.getY() - origin.getY();
		double dZ = p.getZ() - origin.getZ();

		// First - The point is on a line represented by the vector
		// Second - The point is not behind the vector origin (rays are infinite but
		// have a starting point)
		return Math.abs(dX / vector.getX() - dY / vector.getY()) < 0.00001
				&& Math.abs(dY / vector.getY() - dZ / vector.getZ()) < 0.00001 && dX / vector.getX() >= 0;
	}

	/**
	 * Finds the point at which this ray intersects the given triangle
	 * 
	 * @param t The Triangle to find the intersection with
	 * @return The point of intersection, or null if there is none.
	 */
	public Point triIntersect(Triangle t) {
		Vector vPlane = t.getPoint(0).asOriginVector();
		Vector vPlaneNormal = t.planeNormal();
		Vector vLine = getPoint().asOriginVector();
		Vector vLineDir = getVector();

		if (Math.abs(vLineDir.dotProduct(vPlaneNormal)) < 0.00001) { // This means that the line is a part of the plane.
			return null;
		}

		double lineScale = (vPlane.dotProduct(vPlaneNormal) - vLine.dotProduct(vPlaneNormal))
				/ vLineDir.dotProduct(vPlaneNormal);
		Point point = new Point(vLine.getX() + vLineDir.getX() * lineScale, vLine.getY() + vLineDir.getY() * lineScale,
				vLine.getZ() + vLineDir.getZ() * lineScale);
		if (t.isPointOnTriangle(point)) {
			// Check if the point is behind the camera
			if (lineScale < 0) {
				return null;
			} else
				return point;
		} else { // Point crosses the plane but not through the triangle
			return null;
		}
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

	@Override
	public String toString() {
		return origin.toString() + " " + vector.toString();
	}
}
