package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import sceneObjects.Obj3d;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4810618286807932601L;
	private DisplayCanvas c;
	private Scene s;
	private Renderer r;
	private JMenuBar menuBar;
	private JMenu objectMenu;
	private JMenuItem addItem;
	private JMenuItem listItems;

	private ObjListWindow objList;

	public Window() {
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("3d Renderer");

		this.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());

		s = new Scene();
		c = new DisplayCanvas(s, this);
		r = new Renderer(c, s);

		this.objList = null;

		this.menuBar = new JMenuBar();
		this.objectMenu = new JMenu("Objects");
		this.addItem = new JMenuItem("Add");
		this.addItem.addActionListener(this);
		this.listItems = new JMenuItem("List");
		this.listItems.addActionListener(this);

		this.setJMenuBar(this.menuBar);
		this.menuBar.add(this.objectMenu);
		this.objectMenu.add(this.addItem);
		this.objectMenu.add(this.listItems);

		this.add(c);
		this.setVisible(true);
		r.start();
	}

	public DisplayCanvas getCanvas() {
		return c;
	}

	public Scene getScene() {
		return s;
	}

	public Renderer getRenderer() {
		return r;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.addItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new ObjFileFilter());
			int result = fileChooser.showOpenDialog(this.addItem);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					Obj3d newObj = Obj3d.newObj(fileChooser.getSelectedFile());
					s.getObjs().add(newObj);
					if (objList != null && this.objList.isShowing())
						objList.addObj(newObj);
				} catch (Exception except) {
					JOptionPane.showMessageDialog(this.addItem, "Your selected file could not be successfully parsed.");
				}
			}
		} else if (e.getSource() == this.listItems) {
			if (objList == null || !objList.isShowing())
				objList = new ObjListWindow(s);
		}
	}
}
