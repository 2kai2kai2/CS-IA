package sceneObjects;

public class Camera {
	
	private Point location;
	private double yaw;
	private double pitch;
	private double FOV;
	
	public Camera(Point location) {
		this.location = location;
		this.yaw = 0; // 0 <= yaw < 360
		this.pitch = 90; // 0 <= pitch <= 180 (straight down -> straight up)
		this.FOV = 60;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw % 360;
	}
	
	public void addYaw(double change) {
		setYaw(getYaw() + change);
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = Math.min(Math.max(pitch, 0), 180);
	}
	
	public void addPitch(double change) {
		setYaw(getYaw() + change);
	}

	public double getFOV() {
		return FOV;
	}

	public void setFOV(double fov) {
		FOV = fov;
	}
}
