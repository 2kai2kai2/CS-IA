package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {

	private DisplayCanvas canvas;
	private BufferStrategy buffer;
	private Thread thread;

	private final int FPSCAP = 120;

	private long lastMS = 0;

	public Renderer(DisplayCanvas canvas) {
		this.canvas = canvas;
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
		g.drawRect(100, 100, 100, 100);
	}

}
