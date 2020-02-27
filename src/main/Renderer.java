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
		return (int) (resScale * canvas.getHeight());
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

	public void render(Graphics g) {
		long timeStart = System.currentTimeMillis();
		Ray[] rs = rayAngles();
		BufferedImage img = new BufferedImage(getCanvasWidth(), getCanvasHeight(), BufferedImage.TYPE_INT_RGB);
		ArrayList<Triangle> allTris = new ArrayList<Triangle>();
		for (Obj3d obj : scene.getObjs()) {
			for (Face face : obj.getFaces()) {
				allTris.addAll(face.triangulate());
			}
		}
		if (rs != null) {
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					Ray pixRay = rs[y * img.getWidth() + x];
					HashMap<Triangle, Point> intersects = pixRay.allIntersects(allTris);
					// Find the intersect closest to the camera
					Triangle closest = null;
					for (Entry<Triangle, Point> t : intersects.entrySet()) {
						if (closest == null || intersects.get(closest).distToPoint(pixRay.getPoint()) > t.getValue()
								.distToPoint(pixRay.getPoint())) {
							closest = t.getKey();
						}
					}
					// Render the color from the intersect (if exists)
					if (closest != null) {
						img.setRGB(x, y, closest.pointColor(intersects.get(closest)).getRGB());
					} else {
						img.setRGB(x, y, Color.BLACK.getRGB());
					}
				}
			}
		}
		// Display and scale up if rendering is scaled down
		g.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		System.out.println("Rendered in " + (System.currentTimeMillis() - timeStart) + "ms.");
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
				double yaw = Math.toRadians(cam.getYaw() + cam.getFOV() * ((double) x / getCanvasWidth() - 0.5));
				double pitch = Math.toRadians(cam.getPitch()
						+ (cam.getFOV() * getCanvasHeight() / getCanvasWidth()) * ((double) y / getCanvasHeight() - 0.5) - 90);
				// Find the vector (length 1) of this ray from the angles
				Vector v = new Vector(Math.acos(yaw), Math.asin(yaw), Math.asin(pitch));
				// Add vector to its spot
				try {
					rays[y * getCanvasWidth() + x] = new Ray(cam.getLocation(), v);
				} catch (Exception e) {
					return null;
				}
			}
		}
		/*
		for (Ray r : rays) {
			System.out.println(r);
		}*/
		return rays;
	}

}
