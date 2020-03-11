package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import sceneObjects.Obj3d;

public class ObjPane extends JLayeredPane implements ActionListener {

	private static final long serialVersionUID = 8650651743903491086L;

	private Obj3d obj;
	private JLabel locLabel;
	private JButton modButton;

	private ObjEditorWindow mod;
	private ObjListWindow window;

	public ObjPane(Obj3d obj, ObjListWindow window) {
		super();
		this.setObj(obj);
		this.window = window;

		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createTitledBorder(obj.getFileName()));
		this.setMaximumSize(new Dimension(400, 100));
		this.setPreferredSize(new Dimension(360, 100));

		this.locLabel = new JLabel();
		this.modButton = new JButton("Modify");
		this.modButton.addActionListener(this);

		this.add(locLabel);
		this.add(modButton);

		this.update();
	}

	public Obj3d getObj() {
		return obj;
	}

	public void setObj(Obj3d obj) {
		this.obj = obj;
	}

	/**
	 * Updates the Location data for this pane
	 */
	public void update() {
		this.locLabel.setText("Location: (" + this.obj.getX() + ", " + this.obj.getY() + ", " + this.obj.getZ() + ")");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.modButton) {
			if (mod == null || !mod.isShowing())
				mod = new ObjEditorWindow(getObj(), this);
		}
	}

	/**
	 * Calls the removal of this object from the list and from the scene
	 */
	public void delete() {
		this.window.removeObj(getObj());
	}
}
