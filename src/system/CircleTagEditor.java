package system;

import javax.swing.JFrame;

import system.ui.CircleTagEditorWindow;

public class CircleTagEditor {
	public static CircleTagEditorWindow w = new CircleTagEditorWindow();

	public static void main(String[]args) {
		w.setSize(800, 600);
		w.setLocationRelativeTo(null);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		
	}
}
