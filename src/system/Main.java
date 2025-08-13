package system;

import javax.swing.JFrame;

import system.ui.CircleTagEditor;

public class Main {

	public static void main(String[]args) {
		CircleTagEditor w = new CircleTagEditor();
		w.setSize(800, 600);
		w.setLocationRelativeTo(null);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		
	}
}
