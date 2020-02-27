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
		Vector vPlane = t.getPoint(0).asOriginVector();
		Vector vPlaneNormal = t.planeNormal();
		Vector vLine = getPoint().asOriginVector();
		Vector vLineDir = getVector();

		if (vLineDir.dotProduct(vPlaneNormal) == 0) { // This means that the line is a part of the plane.
			return null;
		}

		double lineScale = (vPlane.dotProduct(vPlaneNormal) - vLine.dotProduct(vPlaneNormal))
				/ vLineDir.dotProduct(vPlaneNormal);

		if (lineScale < 0) { // The triangle intersect is behind the camera
			return null;
		}
		Point point = new Point(vLine.getX() + vLineDir.getX() * lineScale, vLine.getY() + vLineDir.getY() * lineScale,
				vLine.getZ() + vLineDir.getZ() * lineScale);
		if (t.isPointOnTriangle(point)) {
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
