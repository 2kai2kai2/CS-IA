package sceneObjects;

public class Material {
	public String name;
	public int ambientR, ambientG, ambientB; // Ka
	public int diffuseR, diffuseG, diffuseB; // Kd
	public int specularR, specularG, specularB; // Ks
	public double transmissionR, transmissionG, transmissionB; // Tf
	public int illumination; // illum
	public double dissolve; // d -halo
	public double specularExp; // Ns
	public double sharpness; // sharpness
	public double opticalDensity; // Ni

	public Material(String name) {
		this.name = name;
	}

	public Material() {
		this.name = "blank";
		this.ambientR = 255;
		this.ambientG = 255;
		this.ambientB = 255;
		this.diffuseR = 255;
		this.diffuseG = 255;
		this.diffuseB = 255;
		this.specularR = 255;
		this.specularG = 255;
		this.specularB = 255;
		this.transmissionR = 0;
		this.transmissionG = 0;
		this.transmissionB = 0;
		this.illumination = 0;
		this.dissolve = 0;
		this.specularExp = 0;
		this.sharpness = 0;
		this.opticalDensity = 0;
	}
}
