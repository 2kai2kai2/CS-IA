package main;

import java.io.File;
import java.util.ArrayList;

import sceneObjects.Camera;
import sceneObjects.Obj3d;
import sceneObjects.Point;

public class Scene {

	private ArrayList<Obj3d> objs;
	private Camera camera;

	public Scene() {
		objs = new ArrayList<Obj3d>();
		objs.add(Obj3d.newObj(new File("C:\\Users\\2kai2\\OneDrive\\Documents\\GitHub\\IA\\src\\untitled.obj")));
		camera = new Camera(new Point(0, 0, 0));
	}

	public Camera getCamera() {
		return camera;
	}

	public ArrayList<Obj3d> getObjs() {
		return objs;
	}
}
