package swing;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;

class Panel2 {

	public JPanel getPanel() {
		JButton jbO = new JButton("Users");
		JButton jbl = new JButton("Final dfdsdhsakjdhsakjhdkj");
		JButton jb2 = new JButton("2021");
		JButton jb3 = new JButton("Exam");

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(BorderLayout.NORTH, jbO);
		panel.add(BorderLayout.CENTER, jbl);
		panel.add(BorderLayout.SOUTH, jb2);
		// panel.add(BorderLayout.EAST, jb3);

		return panel;
	}
}