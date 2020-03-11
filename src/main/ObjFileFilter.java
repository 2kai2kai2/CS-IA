package main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ObjFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getPath().substring(f.getPath().length() - 4).equalsIgnoreCase(".obj");
	}

	@Override
	public String getDescription() {
		return ".obj Files";
	}

}
