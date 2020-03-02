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

	/**
	 * Changes the yaw of the Camera.
	 * 
	 * @param change The amount in degrees to rotate the camera. May be negative.
	 */
	public void addYaw(double change) {
		setYaw(getYaw() + change);
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = Math.min(Math.max(pitch, 0), 180);
	}

	/**
	 * Changes the pitch of the Camera.
	 * 
	 * @param change The amount in degrees to rotate the camera. May be negative.
	 */
	public void addPitch(double change) {
		setPitch(getPitch() + change);
	}

	/**
	 * Moves the camera's location by a given distance in the direction it is facing
	 * 
	 * @param distance The distance to move the camera
	 */
	public void goForward(double distance) {
		double z = distance * Math.sin(Math.toRadians(this.getPitch() - 90));
		double h = distance * Math.cos(Math.toRadians(this.getPitch() - 90));
		double x = h * Math.cos(Math.toRadians(this.getYaw()));
		double y = h * Math.sin(Math.toRadians(this.getYaw()));
		this.setLocation(
				new Point(this.getLocation().getX() + x, this.getLocation().getY() + y, this.getLocation().getZ() + z));
	}

	public double getFOV() {
		return FOV;
	}

	public void setFOV(double fov) {
		FOV = fov;
	}
}
