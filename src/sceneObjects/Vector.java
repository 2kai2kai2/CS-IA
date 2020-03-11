package sceneObjects;

public class Vector {
	private double x;
	private double y;
	private double z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	 * The magnitude of this vector
	 * 
	 * @return A double representing the magnitude of this vector
	 */
	public double magnitude() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
	}

	/**
	 * The dot product of this vector and a given vector.
	 * 
	 * @param other The other vector.
	 * @return A double representing the dot product.
	 */
	public double dotProduct(Vector other) {
		return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
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
		return obj instanceof Vector && Math.abs(((Vector) obj).getX() - this.getX()) < 0.00001
				&& Math.abs(((Vector) obj).getY() - this.getY()) < 0.00001
				&& Math.abs(((Vector) obj).getZ() - this.getZ()) < 0.00001;
	}

	/**
	 * Finds if this vector and another vector have the same direction.
	 * 
	 * @param v The other vector with which to compare.
	 * @return A boolean with the result.
	 */
	public boolean equalsDir(Vector v) {
		return Math.abs(v.getX() / this.getX() - v.getY() / this.getY()) < 0.00001
				&& Math.abs(v.getY() / this.getY() - v.getZ() / this.getZ()) < 0.00001;
	}

	/**
	 * Finds the angle between this vector and another vector. Represented in
	 * radians.
	 * 
	 * @param v The other vector with which to compare.
	 * @return A double representing the angle in radians between the vectors.
	 */
	public double angleWithVector(Vector v) {
		return Math.acos(dotProduct(v) / (magnitude() * v.magnitude()));
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
