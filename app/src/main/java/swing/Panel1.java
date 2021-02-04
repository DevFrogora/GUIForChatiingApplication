package swing;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

class Panel1 {

	public JPanel getPanel(JButton btn_send, AutoreplaceSmiles textPane_msg, JLayeredPane layeredPane) {
		JPanel p = new ScrollControl().createAndShowUI(btn_send, textPane_msg, layeredPane);

		return p;
	}
}