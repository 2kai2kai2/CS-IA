package sceneObjects;

public class Edge implements Line {
	private Point p1;
	private Point p2;

	public Edge(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public double magnitude() {
		return this.p1.distToPoint(this.p2);
	}

	public boolean containsPoint(Point point) {
		return point.distToPoint(this.p1) + point.distToPoint(this.p2) == this.magnitude();
	}
}
