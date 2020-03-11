package sceneObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Obj3d {

	private ArrayList<Point> points;
	private ArrayList<Face> faces;
	private String fileName;

	private ArrayList<Material> materials;
	private double x, y, z;

	public Obj3d() {
		this.points = new ArrayList<Point>();
		this.faces = new ArrayList<Face>();
		this.materials = new ArrayList<Material>();
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<Point> getPoints() {
		ArrayList<Point> translatedPoints = new ArrayList<Point>();

		for (Point p : this.points) {
			translatedPoints.add(new Point(p.getX() + getX(), p.getY() + getY(), p.getZ() + getX()));
		}

		return translatedPoints;
	}

	public void addPoint(Point point) {
		this.points.add(point);
	}

	public Point getPoint(double x, double y, double z) {
		for (Point p : this.getPoints()) {
			if (p.getX() == x && p.getY() == y && p.getZ() == z)
				return p;
		}
		return null;
	}

	public ArrayList<Face> getFaces() {
		ArrayList<Face> translatedFaces = new ArrayList<Face>();

		for (Face f : faces)
			translatedFaces.add(f.asTranslated(getX(), getY(), getZ()));

		return translatedFaces;
	}

	public void addFace(Face face) {
		this.faces.add(face);
	}

	public ArrayList<Material> getMaterials() {
		return materials;
	}

	public void addMaterial(Material material) {
		this.materials.add(material);
	}

	public Material getMaterial(String name) {
		for (Material m : getMaterials()) {
			if (m.name.equals(name))
				return m;
		}
		return new Material();
	}

	/**
	 * Parses an Obj3d from a .obj file.
	 * 
	 * @param f The .obj file to parse into an Obj3d object.
	 * @return An Obj3d object containing the object from the .obj file.
	 */
	public static Obj3d newObj(File f) {
		Obj3d obj = new Obj3d();
		obj.setFileName(f.getName());
		try {
			Scanner s = new Scanner(f);
			String currentMat = "";
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
				if (words[0].equals("mtllib")) {
					String fileName = "";
					for (int i = 1; i < words.length; i++) {
						fileName += words[i] + " ";
					}
					readMTL(new File(f.getPath().substring(0, f.getPath().indexOf(f.getName())) + fileName.strip()),
							obj);
				} else if (words[0].equals("v")) { // Vertex
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
					obj.addFace(new Face(facePoints, obj.getMaterial(currentMat)));
				} else if (words[0].equals("usemtl")) {
					currentMat = words[1];
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static void readMTL(File f, Obj3d obj) {
		try {
			@SuppressWarnings({ "unused", "resource" })
			Scanner s = new Scanner(f);
			Material mat = null;
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
				if (words[0].equalsIgnoreCase("newmtl")) {
					if (mat != null)
						obj.addMaterial(mat);
					mat = new Material(words[1]);
				} else if (words[0].equals("Ka")) {
					if (words[1].equals("spectral") || words[1].equals("xyz")) {

					} else {
						mat.ambientR = (int) (255 * Double.parseDouble(words[1]));
						if (words.length > 2)
							mat.ambientG = (int) (255 * Double.parseDouble(words[2]));
						else
							mat.ambientG = 0;
						if (words.length > 3)
							mat.ambientB = (int) (255 * Double.parseDouble(words[3]));
						else
							mat.ambientB = 0;
					}
				} else if (words[0].equals("Kd")) {
					if (words[1].equals("spectral") || words[1].equals("xyz")) {

					} else {
						mat.diffuseR = (int) (255 * Double.parseDouble(words[1]));
						if (words.length > 2)
							mat.diffuseG = (int) (255 * Double.parseDouble(words[2]));
						else
							mat.diffuseG = 0;
						if (words.length > 3)
							mat.diffuseB = (int) (255 * Double.parseDouble(words[3]));
						else
							mat.diffuseB = 0;
					}
				} else if (words[0].equals("Ks")) {
					if (words[1].equals("spectral") || words[1].equals("xyz")) {

					} else {
						mat.specularR = (int) (255 * Double.parseDouble(words[1]));
						if (words.length > 2)
							mat.specularG = (int) (255 * Double.parseDouble(words[2]));
						else
							mat.specularG = 0;
						if (words.length > 3)
							mat.specularB = (int) (255 * Double.parseDouble(words[3]));
						else
							mat.specularB = 0;
					}
				} else if (words[0].equals("Tf")) {
					if (words[1].equals("spectral") || words[1].equals("xyz")) {

					} else {
						mat.transmissionR = Double.parseDouble(words[1]);
						if (words.length > 2)
							mat.transmissionG = Double.parseDouble(words[2]);
						else
							mat.transmissionG = 0;
						if (words.length > 3)
							mat.transmissionB = Double.parseDouble(words[3]);
						else
							mat.transmissionB = 0;
					}
				} else if (words[0].equals("illum")) {
					mat.illumination = Integer.parseInt(words[1]);
				} else if (words[0].equals("d")) {
					if (words[1].equals("-halo")) {
						mat.dissolve = 0; // Figure out the halo thing???
					} else {
						mat.dissolve = Double.parseDouble(words[1]);
					}
				} else if (words[0].equals("Ns")) {
					mat.specularExp = Double.parseDouble(words[1]);
				} else if (words[0].equals("sharpness")) {
					mat.sharpness = Double.parseDouble(words[1]);
				} else if (words[0].equals("Ni")) {
					mat.opticalDensity = Double.parseDouble(words[1]);
				}
			}
			if (mat != null)
				obj.addMaterial(mat);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
