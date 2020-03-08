package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import sceneObjects.Camera;
import sceneObjects.Face;
import sceneObjects.Obj3d;
import sceneObjects.Point;
import sceneObjects.Ray;
import sceneObjects.Triangle;
import sceneObjects.Vector;

public class Renderer implements Runnable {

	private Scene scene;

	private DisplayCanvas canvas;
	private BufferStrategy buffer;
	private Thread thread;

	private final int FPSCAP = 120;
	private final double resScale = 0.3; // Resolution scaling

	private long lastMS = 0;

	public Renderer(DisplayCanvas canvas, Scene scene) {
		this.canvas = canvas;
		this.scene = scene;
	}

	/**
	 * Anything that needs initializing after adding to the JFrame
	 */
	public void start() {
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		thread = new Thread(this);
		thread.start();
	}

	public DisplayCanvas getCanvas() {
		return this.canvas;
	}

	private int getCanvasHeight() {
		return (int) (resScale * canvas.getHeight());
	}

	private int getCanvasWidth() {
		return (int) (resScale * canvas.getWidth());
	}

	@Override
	public void run() {
		Graphics graphics;
		while (true) {
			if (lastMS + 1000 / FPSCAP < System.currentTimeMillis()) {
				try {
					graphics = buffer.getDrawGraphics();
					render(graphics);
					graphics.dispose();
				} finally {
					buffer.show();
				}
				lastMS = System.currentTimeMillis();
			}
		}
	}

	/**
	 * Renders onto the graphics object based on the Renderer object's variables
	 * 
	 * @param g The Graphics object to render the image on
	 */
	public void render(Graphics g) {
		long timeStart = System.currentTimeMillis();
		// Create the array of Rays to be traced then rendered
		long rayAnglesStart = System.currentTimeMillis();
		Ray[] rs = rayAngles();
		System.out.println("Ray Angles:" + (System.currentTimeMillis() - rayAnglesStart) + "ms.");

		// Setup objects for tracing
		BufferedImage img = new BufferedImage(getCanvasWidth(), getCanvasHeight(), BufferedImage.TYPE_INT_RGB);
		ArrayList<Triangle> allTris = new ArrayList<Triangle>();

		// Triangulate the faces of the objects in the scene
		long triStart = System.currentTimeMillis();
		for (Obj3d obj : scene.getObjs()) {
			for (Face face : obj.getFaces()) {
				allTris.addAll(face.triangulate());
			}
		}
		System.out.println("Triangulation:" + (System.currentTimeMillis() - triStart) + "ms.");

		// Ray Tracing
		long rayTraceStart = System.currentTimeMillis();
		long interSum = 0;
		long closestSum = 0;
		long colorSum = 0;
		if (rs != null) {
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					Ray pixRay = rs[y * img.getWidth() + x];

					// Find all the Triangles and Points at which the ray intersects them
					long interStart = System.currentTimeMillis();
					HashMap<Triangle, Point> intersects = pixRay.allIntersects(allTris);
					interSum += System.currentTimeMillis() - interStart;

					// Find the intersect closest to the camera
					long closestStart = System.currentTimeMillis();
					Triangle closest = null;
					for (Entry<Triangle, Point> t : intersects.entrySet()) {
						if (closest == null || intersects.get(closest).distToPoint(pixRay.getPoint()) > t.getValue()
								.distToPoint(pixRay.getPoint())) {
							closest = t.getKey();
						}
					}
					closestSum += System.currentTimeMillis() - closestStart;

					// Render the color from the intersect (if exists)
					long colorStart = System.currentTimeMillis();
					if (closest != null) {
						img.setRGB(x, y, closest.pointColor(intersects.get(closest)).getRGB());
					} else {
						img.setRGB(x, y, Color.BLACK.getRGB());
					}
					colorStart += System.currentTimeMillis() - colorStart;
				}
			}
		}
		System.out.println("Ray Tracing:" + (System.currentTimeMillis() - rayTraceStart) + "ms.");
		System.out.println("	Intersects:" + interSum + "ms.");
		System.out.println("	Closest Intersect:" + closestSum + "ms.");
		System.out.println("	Color: " + colorSum + "ms.");

		// Display and scale up if rendering is scaled down
		long displayStart = System.currentTimeMillis();
		g.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		System.out.println("Display:" + (System.currentTimeMillis() - displayStart) + "ms.");
		System.out.println("Rendered in " + (System.currentTimeMillis() - timeStart) + "ms. Resolution: ("
				+ canvas.getWidth() + ", " + canvas.getHeight() + ") scaled " + resScale + " to (" + img.getWidth()
				+ ", " + img.getHeight() + ") | Camera Location: " + this.scene.getCamera().getLocation()
				+ " Camera Angle: Yaw=" + this.scene.getCamera().getYaw() + " Pitch="
				+ this.scene.getCamera().getPitch() + "\n");
	}

	/**
	 * Calculates the angles each ray needs to have based on the FOV of this
	 * Renderer's Scene's Camera and the resolution for this Renderer.
	 * 
	 * @return An array containing rays for each pixel of the scaled resolution.
	 */
	public Ray[] rayAngles() {
		Camera cam = scene.getCamera();
		Ray[] rays = new Ray[getCanvasHeight() * getCanvasWidth()];
		for (int y = 0; y < getCanvasHeight(); y++) {
			for (int x = 0; x < getCanvasWidth(); x++) {
				// Calculate the yaw and pitch angles of this ray
				double yaw = Math.toRadians(cam.getYaw()) + Math.atan((x - getCanvasWidth() / 2.0)
						/ (getCanvasWidth() / 2.0) * Math.tan(Math.toRadians(cam.getFOV()) / 2));
				double pitch = Math.toRadians(cam.getPitch() - 90) + Math.atan((y - getCanvasHeight() / 2.0)
						/ (getCanvasHeight() / 2.0) * Math.tan(Math.toRadians(cam.getFOV()) / 2));

				// Find the vector (length 1) of this ray from the angles
				double rz = Math.sin(pitch);
				double h = Math.cos(pitch);
				double rx = h * Math.cos(yaw);
				double ry = h * Math.sin(yaw);
				Vector v = new Vector(rx, ry, rz);

				// Add vector to its spot
				try {
					rays[y * getCanvasWidth() + x] = new Ray(cam.getLocation(), v);
				} catch (Exception e) {
					return null;
				}
			}
		}
		return rays;
	}

}
