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

	/**
	 * Triangulates the face into Triangles. This is necessary because the face may
	 * not be on a single plane and often will not contain simple polygons.
	 * Triangles should be more simple to find intersects for.
	 * 
	 * The method finds which point has the smallest average distance to all other
	 * points, then draws triangles from all other points to this point. This should
	 * work in all simple cases if not all cases.
	 * 
	 * @return An ArrayList of Triangles that make up this face.
	 */
	public ArrayList<Triangle> triangulate() {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		ArrayList<Double> sumDistance = new ArrayList<Double>();

		// Get the sum of the distances to all other points for each point
		for (int i = 0; i < points.size(); i++) {
			double sum = 0.0;
			for (Point dp : points) {
				sum += points.get(i).distToPoint(dp);
				// The distance from a point to itself is 0.0 so it doesn't matter.
			}
			sumDistance.add(sum);
		}

		// Find out which point is the closest to all other points
		int maxIndex = 0;
		for (int i = 0; i < sumDistance.size(); i++) {
			if (sumDistance.get(maxIndex) > sumDistance.get(i))
				maxIndex = i;
		}

		// Go through each other point and add valid triangles with the center point as
		// the third vertex.
		for (int i = 0; i < points.size(); i++) {
			if (maxIndex == i || maxIndex == (i + 1) % points.size()) {
				// Ignore the center point and the one right before the center point so that we
				// don't have a triangle with the center point twice
			} else {
				// Add the triangle with this point, the next point, and the center point
				triangles.add(new Triangle(points.get(i), points.get((i + 1) % points.size()), points.get(maxIndex)));
			}
		}

		return triangles;
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
