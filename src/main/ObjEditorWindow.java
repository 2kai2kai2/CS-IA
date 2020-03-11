package main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sceneObjects.Obj3d;

public class ObjEditorWindow extends JFrame implements ChangeListener, ActionListener {

	private static final long serialVersionUID = 3045211619228780737L;

	private Obj3d obj;
	private ObjPane pane;
	private JSpinner spinnerX, spinnerY, spinnerZ;
	private JButton delete;

	public ObjEditorWindow(Obj3d obj, ObjPane pane) {
		this.obj = obj;
		this.pane = pane;

		this.setSize(400, 200);
		this.setLayout(new FlowLayout());
		this.setTitle(obj.getFileName());

		this.spinnerX = new JSpinner(new SpinnerNumberModel(obj.getX(), -100, 100, 0.1));
		this.spinnerY = new JSpinner(new SpinnerNumberModel(obj.getY(), -100, 100, 0.1));
		this.spinnerZ = new JSpinner(new SpinnerNumberModel(obj.getZ(), -100, 100, 0.1));
		this.delete = new JButton("Delete");

		spinnerX.addChangeListener(this);
		spinnerY.addChangeListener(this);
		spinnerZ.addChangeListener(this);
		delete.addActionListener(this);

		this.add(new JLabel("X:"));
		this.add(spinnerX);
		this.add(new JLabel("Y:"));
		this.add(spinnerY);
		this.add(new JLabel("Z:"));
		this.add(spinnerZ);
		this.add(delete);

		this.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		obj.setX((double) spinnerX.getValue());
		obj.setY((double) spinnerY.getValue());
		obj.setZ((double) spinnerZ.getValue());
		pane.update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == delete) {
			pane.delete();
			this.dispose();
		}
	}
}
