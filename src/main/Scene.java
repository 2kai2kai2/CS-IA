package main;

import java.util.ArrayList;

import sceneObjects.Camera;
import sceneObjects.Obj3d;
import sceneObjects.Point;

public class Scene {

	private ArrayList<Obj3d> objs;
	private Camera camera;

	public Scene() {
		objs = new ArrayList<Obj3d>();
		camera = new Camera(new Point(-10, 0, 0));
	}

	public Camera getCamera() {
		return camera;
	}

	public ArrayList<Obj3d> getObjs() {
		return objs;
	}
}
