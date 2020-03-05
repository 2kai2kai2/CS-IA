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
		// 1: If the point is on the same plane as the triangle
		// 2: Check sum of angles (<=180) between the vectors from the triangle vertexes
		// and the point.
		// If the angle is 360, then it is in the triangle, if it is not then it is
		// outside

		// Check point is on plane
		Vector vNormal = planeNormal();
		Vector vPoint = p.asOriginVector();
		Vector vPlanePoint = getPoint(0).asOriginVector();
		if (Math.abs(vPoint.dotProduct(vNormal) - vPlanePoint.dotProduct(vNormal)) < 0.00001) {
			Vector pToA = new Vector(p, points[0]);
			Vector pToB = new Vector(p, points[1]);
			Vector pToC = new Vector(p, points[2]);
			return Math.abs(pToA.angleWithVector(pToB) + pToB.angleWithVector(pToC) + pToC.angleWithVector(pToA)
					- Math.PI * 2) < 0.00001;
		} else
			return false;
	}

	/**
	 * Gets the normal Vector to this plane
	 * 
	 * @return The normal Vector
	 */
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
