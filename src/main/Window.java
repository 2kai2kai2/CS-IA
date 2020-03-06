package main;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = -4810618286807932601L;
	private DisplayCanvas c;
	private Scene s;
	private Renderer r;

	public Window() {
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s = new Scene();
		c = new DisplayCanvas(s, this);
		r = new Renderer(c, s);
		this.add(c);
		this.setVisible(true);
		r.start();
	}

	public DisplayCanvas getCanvas() {
		return c;
	}

	public Scene getScene() {
		return s;
	}

	public Renderer getRenderer() {
		return r;
	}
}
