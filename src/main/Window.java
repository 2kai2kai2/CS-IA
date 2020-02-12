package main;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = -4810618286807932601L;
	private DisplayCanvas c;
	private Renderer r;

	public Window() {
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		c = new DisplayCanvas();
		r = new Renderer(c);
		this.add(c);
		this.setVisible(true);
		r.start();
	}
}
