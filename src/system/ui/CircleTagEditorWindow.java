package system.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import marker.CircleMark;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;
import system.ui.panels.HeaderPanel;
import system.ui.panels.IDPanel;
import system.ui.panels.PreviewPanel;
import system.ui.panels.SidePanel;

public class CircleTagEditorWindow extends JFrame{
	private static final long serialVersionUID = -5807390463809400638L;
	private HeaderPanel headerPanel;
	private PreviewPanel preview_panel;
	private SidePanel side_panel;
	private IDPanel id_panel;

	public CircleTagEditorWindow() {
		setTitle("CircleTag - Editor");
		setLayout(new BorderLayout());
		
		headerPanel = new HeaderPanel() {
			private static final long serialVersionUID = 8097717967088268204L;
			@Override
			public void onAddCircle() {
				preview_panel.addCircleMark();
				side_panel.setEnabled(true);
				side_panel.updateAtributes(preview_panel.getSelectedCircleMark());
				id_panel.setID(preview_panel.getCircleTag().getTagId());
			}
			@Override
			public void onRemoveCircle() {
				preview_panel.removeCircleMark();
				side_panel.setEnabled(false);
				id_panel.setID(preview_panel.getCircleTag().getTagId());
			}
			@Override
			public void onShowOrbits(boolean show) {
				preview_panel.showOrbits(show);
			}
			@Override
			public void onShowMarkerBody(boolean show) {
				preview_panel.showMarkerBody(show);
			}
			@Override
			public void onShowMarkerSelection(boolean show) {
				preview_panel.showMarkerSelection(show);
			}
			@Override
			public void onZoomIn() {
				preview_panel.zoom(0.1f);
			}
			@Override
			public void onZoomOut() {
				preview_panel.zoom(-0.1f);
			}
		};
		
		preview_panel = new PreviewPanel() {
			private static final long serialVersionUID = -3394730924680560413L;
			@Override
			public void onSelectMark(CircleMark c) {
				side_panel.setEnabled(true);
				side_panel.updateAtributes(c);
			}
		};
		
		side_panel = new SidePanel(preview_panel.getCircleTag()) {
			private static final long serialVersionUID = 2308933764510718420L;
			@Override
			public void onNextSize(DiscreteSize size) {
				preview_panel.getSelectedCircleMark().setDiscreteSize(size);
				id_panel.setID(preview_panel.getCircleTag().getTagId());
				getRootPane().repaint();
			}
			@Override
			public void onNextOrbit(int orbit) {
				preview_panel.getSelectedCircleMark().setOrbit(orbit);
				id_panel.setID(preview_panel.getCircleTag().getTagId());
				getRootPane().repaint();
			}
			@Override
			public void onNextDirection(CardinalDirection direction) {
				if(direction == null) {
					preview_panel.getSelectedCircleMark().setOrbit(0);
					side_panel.updateAtributes(preview_panel.getSelectedCircleMark());
				}
				else {
					preview_panel.getSelectedCircleMark().setDirection(direction);
				}
				id_panel.setID(preview_panel.getCircleTag().getTagId());
				getRootPane().repaint();
			}
			@Override
			public void onNextPolarity(Polarity polarity) {
				preview_panel.getSelectedCircleMark().setPolarity(polarity);
				id_panel.setID(preview_panel.getCircleTag().getTagId());
				getRootPane().repaint();
			}
		};
		side_panel.setEnabled(false);
		
		id_panel = new IDPanel();
		
		add(headerPanel, BorderLayout.NORTH);
		add(preview_panel, BorderLayout.CENTER);
		add(side_panel, BorderLayout.EAST);
		add(id_panel, BorderLayout.SOUTH);
		
	}
	
}
