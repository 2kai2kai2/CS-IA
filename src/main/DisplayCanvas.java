package main;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayCanvas extends Canvas implements KeyListener {

	private static final long serialVersionUID = -8926375402783842403L;

	private Scene scene;
	private Window window;

	public DisplayCanvas(Scene s, Window w) {
		this.scene = s;
		this.window = w;
		this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			scene.getCamera().addPitch(10);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			scene.getCamera().addYaw(-10);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			scene.getCamera().addPitch(-10);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			scene.getCamera().addYaw(10);
		} else if (e.getKeyCode() == KeyEvent.VK_I) { // Forward
			scene.getCamera().goForward(1);
		} else if (e.getKeyCode() == KeyEvent.VK_K) { // Backward
			scene.getCamera().goForward(-1);
		} else if (e.getKeyCode() == KeyEvent.VK_J) { // Left
			scene.getCamera().goRight(-1);
		} else if (e.getKeyCode() == KeyEvent.VK_L) { // Right
			scene.getCamera().goRight(1);
		} else if (e.getKeyCode() == KeyEvent.VK_U) { // Up
			scene.getCamera().goUp(1);
		} else if (e.getKeyCode() == KeyEvent.VK_M) { // Down
			scene.getCamera().goUp(-1);
		} else if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
			window.getRenderer().setResScale(window.getRenderer().getResScale() * 1.1);
		} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
			window.getRenderer().setResScale(window.getRenderer().getResScale() * 0.91);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
