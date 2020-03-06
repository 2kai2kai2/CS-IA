package sceneObjects;

public class Point {
	private double x;
	private double y;
	private double z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	/**
	 * Represents the point as a Vector from (0, 0, 0) to this point.
	 * 
	 * @return A vector representation of this point
	 */
	public Vector asOriginVector() {
		return new Vector(x, y, z);
	}

	/**
	 * Gets the distance between this point and a given point
	 * 
	 * @param point The given Point to find the distance to
	 * @return A double representing the distance between the two points
	 */
	public double distToPoint(Point point) {
		return Math.sqrt(Math.pow(this.getX() - point.getX(), 2) + Math.pow(this.getY() - point.getY(), 2)
				+ Math.pow(this.getZ() - point.getZ(), 2));
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Point && Math.abs(((Point) o).getX() - this.getX()) < 0.00001
				&& Math.abs(((Point) o).getY() - this.getY()) < 0.00001
				&& Math.abs(((Point) o).getZ() - this.getZ()) < 0.00001;
	}
}
