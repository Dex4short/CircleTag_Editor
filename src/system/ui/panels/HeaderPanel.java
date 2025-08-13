package system.ui.panels;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public abstract class HeaderPanel extends JPanel {
	private static final long serialVersionUID = 6719129745457134409L;
	private MenuPanel menu_panel;
	private ZoomPanel zoom_panel;
	
	
	public HeaderPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createRaisedBevelBorder());
		
		menu_panel = new MenuPanel();
		zoom_panel = new ZoomPanel();
		
		add(menu_panel, BorderLayout.CENTER);
		add(zoom_panel, BorderLayout.EAST);
		
	}
	private class MenuPanel extends JPanel{
		private static final long serialVersionUID = 485244594050859237L;
		
		public MenuPanel(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			
			JButton
			btn_add_circle = new JButton(" + "),
			btn_remove_circle = new JButton(" - ");
			
			JRadioButton
			btn_show_orbits = new JRadioButton("Show Orbits"),
			btn_show_marker_body = new JRadioButton("Show Marker Body"),
			btn_show_marker_selection = new JRadioButton("Show Marker Selection");
			
			btn_add_circle.addActionListener( event -> onAddCircle());
			btn_remove_circle.addActionListener(event -> onRemoveCircle());
			btn_show_orbits.addActionListener(event -> onShowOrbits(btn_show_orbits.isSelected()));
			btn_show_marker_body.addActionListener(event -> onShowMarkerBody(btn_show_marker_body.isSelected()));
			btn_show_marker_selection.addActionListener(Event -> onShowMarkerSelection(btn_show_marker_selection.isSelected()));

			btn_show_orbits.setSelected(true);
			btn_show_marker_body.setSelected(true);
			btn_show_marker_selection.setSelected(true);
			
			add(btn_add_circle);
			add(btn_remove_circle);
			add(btn_show_orbits);
			add(btn_show_marker_body);
			add(btn_show_marker_selection);
			
		}
	}
	private class ZoomPanel extends JPanel{
		private static final long serialVersionUID = 4336165945888586125L;
		
		public ZoomPanel(){
			setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			JButton
			zoom_in = new JButton("zoom in"),
			zoom_out = new JButton("zoom out");
			
			zoom_in.addActionListener(event -> onZoomIn());
			zoom_out.addActionListener(event -> onZoomOut());
			
			add(zoom_in);
			add(zoom_out);
		}
	}
	
	//button calls
	public abstract void onAddCircle();
	public abstract void onRemoveCircle();
	public abstract void onShowOrbits(boolean show);
	public abstract void onShowMarkerBody(boolean show);
	public abstract void onShowMarkerSelection(boolean show);
	public abstract void onZoomIn();
	public abstract void onZoomOut();
	
}
