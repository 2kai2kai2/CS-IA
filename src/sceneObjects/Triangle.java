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

	public Point getPoint(int pointNum) {
		return points[pointNum];
	}

	/**
	 * Returns whether a point is on the triangle.
	 * 
	 * @param p The point to check.
	 * @return A boolean of whether it is on the triangle.
	 */
	public boolean isPointOnTriangle(Point p) {
		// 1: If the point is on the same plane as the triangle is - use normal eq of
		// plane
		// 2: If all angles are from p1, the angle from p2 to p + p3 to p = p2 to p3
		// 3: Same as 2 but from another point so that the distance cannot be infinite
		// despite the angles from p1
		Vector vNormal = planeNormal();
		Vector vPoint = p.asOriginVector();
		Vector vPlanePoint = getPoint(0).asOriginVector();

		// Check point is on plane
		if (vPoint.dotProduct(vNormal) == vPlanePoint.dotProduct(vNormal)) {
			// Angle a = p2-p1-p3; Angle b = p2-p1-p; Angle c = p3-p1-p
			// If 0 <= b and b <= a and 0 <= c and c <= a then it is in the cone from p1.
			Vector p1p2 = new Vector(points[0], points[1]);
			Vector p1p3 = new Vector(points[0], points[2]);
			Vector p1p = new Vector(points[0], p);
			if (0 <= p1p2.angleWithVector(p1p) && p1p2.angleWithVector(p1p) <= p1p2.angleWithVector(p1p3)
					&& 0 <= p1p3.angleWithVector(p1p) && p1p3.angleWithVector(p1p) <= p1p2.angleWithVector(p1p3)) {
				// Angle a = p1-p2-p3; Angle b = p1-p2-p; Angle c = p3-p2-p
				// If 0 <= b and b <= a and 0 <= c and c <= a then it is in the cone from p2 and
				// therefore is in the triangle.
				Vector p2p1 = new Vector(points[1], points[0]);
				Vector p2p3 = new Vector(points[1], points[2]);
				Vector p2p = new Vector(points[1], p);
				return (0 <= p2p1.angleWithVector(p2p) && p2p1.angleWithVector(p2p) <= p2p1.angleWithVector(p2p3)
						&& 0 <= p2p3.angleWithVector(p2p) && p2p3.angleWithVector(p2p) <= p2p1.angleWithVector(p2p3));
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Vector planeNormal() {
		return new Vector(points[0].getX() - points[1].getX(), points[0].getY() - points[1].getY(),
				points[0].getZ() - points[1].getZ())
						.crossProduct(new Vector(points[0].getX() - points[2].getX(),
								points[0].getY() - points[2].getY(), points[0].getZ() - points[2].getZ()));
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
