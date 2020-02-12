package sceneObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Obj3d {

	private ArrayList<Point> points;
	private ArrayList<Edge> edges;
	private ArrayList<Face> faces;

	public Obj3d() {
		this.points = new ArrayList<Point>();
		this.edges = new ArrayList<Edge>();
		this.faces = new ArrayList<Face>();
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void addPoint(Point point) {
		this.points.add(point);
	}

	public Point getPoint(double x, double y, double z) {
		for (Point p : this.points) {
			if (p.getX() == x && p.getY() == y && p.getZ() == z)
				return p;
		}
		return null;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	public ArrayList<Face> getFaces() {
		return faces;
	}

	public void addFace(Face face) {
		this.faces.add(face);
		System.out.println(face.toString());
	}

	public static Obj3d newObj(File f) {
		Obj3d obj = new Obj3d();
		try {
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine().strip();
				// Ignore comments
				if (line.startsWith("#"))
					continue;
				// Split the line into each word
				String[] words = line.split(" ");
				// Clean each word
				for (int i = 0; i < words.length; i++)
					words[i] = words[i].strip();
				// Parse the line
				if (words[0].equals("v")) { // Vertex
					obj.addPoint(new Point(Double.parseDouble(words[1]), Double.parseDouble(words[2]),
							Double.parseDouble(words[3])));
				} else if (words[0].equals("f")) { // Face
					ArrayList<Point> facePoints = new ArrayList<Point>();
					for (int i = 1; i < words.length; i++) {
						int pointnum = Integer.parseInt(words[i].split("/")[0]); // Get the point's index
						if (pointnum > 0) { // This means that the index of the point is from the top
							facePoints.add(obj.getPoints().get(pointnum - 1));
						} else { // This means that the index of the point is from the bottom
							facePoints.add(obj.getPoints().get(obj.getPoints().size() - pointnum));
						}
					}
					obj.addFace(new Face(facePoints));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
