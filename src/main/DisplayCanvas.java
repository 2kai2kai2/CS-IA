package main;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayCanvas extends Canvas implements KeyListener {

	private static final long serialVersionUID = -8926375402783842403L;

	private Scene scene;

	public DisplayCanvas(Scene s) {
		this.scene = s;
		this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_W) {
			scene.getCamera().addPitch(-10);
		} else if (e.getKeyCode() == e.VK_A) {
			scene.getCamera().addYaw(-10);
		} else if (e.getKeyCode() == e.VK_S) {
			scene.getCamera().addPitch(10);
		} else if (e.getKeyCode() == e.VK_D) {
			scene.getCamera().addYaw(10);
		} else if (e.getKeyCode() == e.VK_I) {
			scene.getCamera().goForward(1);
		} else if (e.getKeyCode() == e.VK_K) {
			scene.getCamera().goForward(-1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
