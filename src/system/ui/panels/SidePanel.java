package system.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import marker.CircleMark;
import marker.CircleTag;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;

public abstract class SidePanel extends JPanel{
	private static final long serialVersionUID = 3633574605735472837L;
	private SizesPanel sizes_panel;
	private OrbitsPanel orbits_panel;
	private DirectionsPanel directions_panel;
	private PolarityPanel polarity_panel;

	public SidePanel(CircleTag circle_tag) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		sizes_panel = new SizesPanel();
		orbits_panel = new OrbitsPanel(circle_tag.getOrbits());
		directions_panel = new DirectionsPanel();
		polarity_panel = new PolarityPanel();
		
		add(sizes_panel, BorderLayout.NORTH);
		add(orbits_panel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(directions_panel);
		panel.add(polarity_panel);
		add(panel, BorderLayout.SOUTH);
		
	}
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		sizes_panel.setEnabled(enabled);
		orbits_panel.setEnabled(enabled);
		directions_panel.setEnabled(enabled);
		polarity_panel.setEnabled(enabled);
	}

	//main functions
	public void updateAtributes(CircleMark c) {
		sizes_panel.setSelectedSize(c.getDiscreteSize());
		orbits_panel.setSelectedOrbit(c.getOrbit());
		directions_panel.setSelectedDirection(c.getCardinalDirection());
		polarity_panel.selectPolarity(c.getPolarity());
	}
	public abstract void onNextSize(DiscreteSize size);
	public abstract void onNextOrbit(int orbit);
	public abstract void onNextDirection(CardinalDirection direction);
	public abstract void onNextPolarity(Polarity polarity);
	
	private class SizesPanel extends JPanel {
		private static final long serialVersionUID = 8605662557403581238L;
		private JButton btn[];

		public SizesPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBorder(BorderFactory.createTitledBorder("Discrete Sizes: "));

			JPanel panel = new JPanel(new GridLayout(1, 7));
			DiscreteSize sizes[] = DiscreteSize.values();
			
			btn = new JButton[sizes.length];
			for(int b=0; b<btn.length; b++) {
				final DiscreteSize size = sizes[b];
				btn[b] = new JButton(sizes[b].name());
				btn[b].addActionListener(e -> {
					onNextSize(size);
					setSelectedSize(size);
				});
				panel.add(btn[b]);
			}			
			add(panel);
		}
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			for(JButton b: btn) {
				b.setEnabled(enabled);
			}
			setSelectedSize(null);
		}
		public void setSelectedSize(DiscreteSize discrete_size) {
			int selected = (discrete_size!=null) ? discrete_size.ordinal() : -1;
			
			Color color;
			for(int b=0; b<btn.length; b++) {
				if(b==selected)
					color = Color.black;
				else
					color = new Color(184, 207, 229);
				
				btn[b].setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(color),
						BorderFactory.createEmptyBorder(4, 16, 4, 16)
				));
			}
		}
	}
	private class OrbitsPanel extends JPanel {
		private static final long serialVersionUID = -8009824956721798315L;
		private JRadioButton btn_orbits[];
		
		public OrbitsPanel(int orbits) {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBorder(BorderFactory.createTitledBorder("Orbital Positions: "));
			
			JPanel panel = new JPanel(new GridLayout(orbits, 1));
			ButtonGroup group = new ButtonGroup();

			btn_orbits = new JRadioButton[orbits];
			for(int o=orbits-1; o>=0; o--) {
				final int orbit = o + 1;
				
				btn_orbits[o] = new JRadioButton("Orbit " + (o+1));
				btn_orbits[o].addActionListener(e -> onNextOrbit(orbit));
				panel.add(btn_orbits[o]);
				
				group.add(btn_orbits[o]);
			}
			
			add(panel);
		}
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			for(JRadioButton bo: btn_orbits) {
				bo.setEnabled(enabled);
			}
		}
		public void setSelectedOrbit(int orbit) {
			btn_orbits[orbit - 1].setSelected(true);
		}
	}
	private class DirectionsPanel extends JPanel{
		private static final long serialVersionUID = -754857760719550473L;
		private JButton btn[];
		
		public DirectionsPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBorder(BorderFactory.createTitledBorder("Cardinal Directions: "));
			
			JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));
			CardinalDirection directions[] = CardinalDirection.values();
			
			btn = new JButton[directions.length];
			for(int b=0; b<btn.length; b++) {
				if(b == 4) panel.add(new JPanel());

				final CardinalDirection direction = directions[b];
				btn[b] = new JButton(direction.name());
				btn[b].addActionListener(e -> {
					onNextDirection(direction);
					setSelectedDirection(direction);
				});
				panel.add(btn[b]);
			}
			
			add(panel);
		}
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			for(JButton b: btn) {
				b.setEnabled(enabled);
			}
			setSelectedDirection(null);
		}
		public void setSelectedDirection(CardinalDirection cardinal_direction) {
			int selected = (cardinal_direction!=null) ? cardinal_direction.ordinal() : -1;
			
			Color color;
			for(int b=0; b<btn.length; b++) {
				if(b==selected)
					color = Color.black;
				else
					color = new Color(184, 207, 229);
				
				btn[b].setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(color),
						BorderFactory.createEmptyBorder(4, 16, 4, 16)
				));
			}
		}
	}
	private class PolarityPanel extends JPanel{
		private static final long serialVersionUID = -3443201774396749388L;
		private JRadioButton hollow, solid;
		
		public PolarityPanel() {
			setLayout(new FlowLayout());
			
			JPanel panel = new JPanel(new GridLayout(2,  1));
			ButtonGroup group = new ButtonGroup();
			
			hollow = new JRadioButton("Hollow");
			solid = new JRadioButton("Solid");

			hollow.addActionListener(e -> onNextPolarity(Polarity.HOLLOW));
			solid.addActionListener(e -> onNextPolarity(Polarity.SOLID));
			
			panel.add(hollow);
			panel.add(solid);

			group.add(hollow);
			group.add(solid);
			
			add(panel);
		}
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			hollow.setEnabled(enabled);
			solid.setEnabled(enabled);
		}
		public void selectPolarity(Polarity polarity) {
			switch(polarity) {
			case HOLLOW:
				hollow.setSelected(true);
				break;
			case SOLID:
				solid.setSelected(true);
				break;			
			}
		}
	}
}
