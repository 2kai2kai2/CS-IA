package sceneObjects;

public class Triangle {
	private Point[] points;

	public Triangle(Point[] points) {
		this.points = points;
	}

	public Triangle(Point p1, Point p2, Point p3) {
		this(new Point[] { p1, p2, p3 });
	}

	@Override
	public String toString() {
		return "[" + points[0].toString() + ", " + points[1].toString() + ", " + points[2].toString() + "]";
	}
}
