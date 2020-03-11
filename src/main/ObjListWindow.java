package main;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import sceneObjects.Obj3d;

public class ObjListWindow extends JFrame {

	private static final long serialVersionUID = -797449133043354365L;

	private Scene s;
	ArrayList<ObjPane> panes;

	public ObjListWindow(Scene scene) {
		this.s = scene;
		panes = new ArrayList<ObjPane>();

		this.setSize(400, 800);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Object List");
		this.setLayout(new FlowLayout());

		addObj(s.getObjs());

		this.setVisible(true);
	}

	/**
	 * Adds an object to the window list
	 * 
	 * @param obj The object to add
	 */
	public void addObj(Obj3d obj) {
		this.panes.add(new ObjPane(obj, this));
		this.add(getObjPane(obj));
		this.revalidate();
		this.repaint();
	}

	/**
	 * Adds all objects on a list to the window list
	 * 
	 * @param obj The ArrayList of objects to add
	 */
	public void addObj(ArrayList<Obj3d> obj) {
		for (Obj3d o : obj)
			addObj(o);
	}

	public ObjPane getObjPane(Obj3d obj) {
		for (ObjPane pane : panes) {
			if (pane.getObj() == obj)
				return pane;
		}
		return null;
	}

	/**
	 * Removes an object from a the object list and from the scene
	 * 
	 * @param obj The object to remove
	 */
	public void removeObj(Obj3d obj) {
		for (int i = panes.size() - 1; i >= 0; i--) {
			if (panes.get(i).getObj() == obj) {
				this.remove(panes.get(i));
				this.revalidate();
				this.repaint();
				panes.remove(i);
			}
		}
		s.getObjs().remove(obj);
	}
}
