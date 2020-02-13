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

	/**
	 * A Vector from edge from p2 to p1
	 * 
	 * @param edge
	 */
	public Vector(Edge edge) {
		this(edge.getP1().getX() - edge.getP2().getX(), edge.getP1().getY() - edge.getP2().getY(),
				edge.getP1().getZ() - edge.getP2().getZ());
	}

	public double magnitude() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
	}

	public double dotProduct(Vector other) {
		return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
	}

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

	public boolean equalsDir(Vector v) {
		return v.getX() / this.getX() == v.getY() / this.getY() && v.getY() / this.getY() == v.getZ() / this.getZ();
	}
}
