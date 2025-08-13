package system.ui.panels;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IDPanel extends JPanel{
	private static final long serialVersionUID = 3695174457646509528L;
	private int id;
	private JLabel lbl_id;
	
	public IDPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		lbl_id = new JLabel("ID: " + id);
		add(lbl_id);
		
	}
	public void setID(int id) {
		this.id = id;
		lbl_id.setText("ID: " + id);
	}
	public int getID() {
		return id;
	}
}
