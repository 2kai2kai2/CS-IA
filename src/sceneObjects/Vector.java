package sceneObjects;

public class Vector {
	private double x;
	private double y;
	private double z;

	private static final double round = Math.pow(10, 9);

	public Vector(double x, double y, double z) {
		this.x = Math.round(x * round) / round;
		this.y = Math.round(y * round) / round;
		this.z = Math.round(z * round) / round;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Vector(Point p1, Point p2) {
		this(p1.getX() - p2.getX(), p1.getY() - p2.getY(), p1.getZ() - p2.getZ());
	}

	/**
	 * A Vector from edge from p2 to p1
	 * 
	 * @param edge
	 */
	public Vector(Edge edge) {
		this(edge.getP1(), edge.getP2());
	}

	/**
	 * The magnitude of this vector
	 * 
	 * @return A double representing the magnitude of this vector
	 */
	public double magnitude() {
		double rawVal = Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
		return Math.round(rawVal * round) / round;
	}

	/**
	 * The dot product of this vector and a given vector.
	 * 
	 * @param other The other vector.
	 * @return A double representing the dot product.
	 */
	public double dotProduct(Vector other) {
		double rawVal = this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
		return Math.round(rawVal * round) / round;
	}

	/**
	 * The cross product of this vector and a given vector, where this vector is
	 * first. The resultant vector will be perpendicular to both vectors.
	 * 
	 * @param other The other vector.
	 * @return A vector representing the cross product.
	 */
	public Vector crossProduct(Vector other) {
		return new Vector(this.getY() * other.getZ() - this.getZ() * other.getY(),
				this.getZ() * other.getX() - this.getX() * other.getZ(),
				this.getX() * other.getY() - this.getY() * other.getX());
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vector && ((Vector) obj).getX() == this.getX() && ((Vector) obj).getY() == this.getY()
				&& ((Vector) obj).getZ() == this.getZ();
	}

	/**
	 * Finds if this vector and another vector have the same direction.
	 * 
	 * @param v The other vector with which to compare.
	 * @return A boolean with the result.
	 */
	public boolean equalsDir(Vector v) {
		return v.getX() / this.getX() == v.getY() / this.getY() && v.getY() / this.getY() == v.getZ() / this.getZ();
	}

	/**
	 * Finds the angle between this vector and another vector. Represented in
	 * radians.
	 * 
	 * @param v The other vector with which to compare.
	 * @return A double representing the angle in radians between the vectors.
	 */
	public double angleWithVector(Vector v) {
		double rawVal = Math.acos(dotProduct(v) / (magnitude() * v.magnitude()));
		return Math.round(rawVal * round) / round;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
