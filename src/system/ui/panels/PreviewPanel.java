package system.ui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import marker.CircleMark;
import marker.CircleTag;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;

public abstract class PreviewPanel extends JPanel{
	private static final long serialVersionUID = 1106928782944576233L;
	private CircleTag marker;
	private Graphics2D g2d;
	
	private CircleMark selected_mark, pointed_mark;
	private Color color[] = {
			Color.black,
			Color.red,
			new Color(255,0,0,128),
			Color.red.darker()
	};
	private boolean showSelection=true;
	
	public PreviewPanel(){
		marker = new CircleTag() {
			int c;
			@Override
			public void drawCircleMark(Graphics2D g2d, CircleMark circle_mark) {
				if(showSelection) {
					c = 0;
					if(circle_mark == selected_mark) c+=1;
					if(circle_mark == pointed_mark) c+=2;

					g2d.setColor(color[c]);
				}
				super.drawCircleMark(g2d, circle_mark);
			}
		};
		marker.setMarkerBodyVisible(true);
		marker.setOrbitsVisible(true);
		marker.setScale(2);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				e.translatePoint(-tx, -ty);
				marker.forEachCircle(c -> {
					if(c.getPoint().distance(e.getPoint()) < c.getRadius()) {
						selected_mark = c;
						onSelectMark(c);
					}
				});
				e.translatePoint(tx, ty);
				getRootPane().repaint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				e.translatePoint(-tx, -ty);
				pointed_mark = null;
				marker.forEachCircle(c -> {
					if(c.getPoint().distance(e.getPoint()) < c.getRadius()) {
						pointed_mark = c;
					}
				});
				e.translatePoint(tx, ty);
				getRootPane().repaint();
			}
		});
		
	}
	
	//main functions
	public CircleTag getCircleTag() {
		return marker;
	}
	public CircleMark getSelectedCircleMark() {
		return selected_mark;
	}
	public abstract void onSelectMark(CircleMark c);
	
	//drawing function
	private int tx,ty,ts;
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ts = marker.getTagSize()/2;
		tx = (getWidth()/2) - ts;
		ty = (getHeight()/2) - ts;
		marker.draw(g2d, tx, ty);
		
		g2d.setColor(Color.black);
		if(marker.isMarkerValid())
			g2d.drawString("Valid", 5, getHeight() - 5);
		else
			g2d.drawString("Invalid", 5, getHeight() - 5);
	}
	
	//header calls
	public void addCircleMark() {
		CircleMark circle_mark = new CircleMark(1, CardinalDirection.E_, DiscreteSize.A, Polarity.HOLLOW);
		marker.addCircleMark(circle_mark);
		selected_mark = circle_mark;
		getRootPane().repaint();
	}
	public void removeCircleMark() {
		if(selected_mark != null) {
			marker.removeCircleMark(selected_mark);
			selected_mark = null;
			getRootPane().repaint();
		}
		else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
	public void showOrbits(boolean show) {
		marker.setOrbitsVisible(show);
		getRootPane().repaint();
	}
	public void showMarkerBody(boolean show) {
		marker.setMarkerBodyVisible(show);
		getRootPane().repaint();
	}
	public void showMarkerSelection(boolean show) {
		showSelection = show;
		getRootPane().repaint();
	}
	public void zoom(float zoom) {
		if(marker.getScale() < 1f) {
			Toolkit.getDefaultToolkit().beep();
			marker.setScale(1f);
			return;
		}
		marker.setScale(marker.getScale() + zoom);
		getRootPane().repaint();
	}
	
	
}
