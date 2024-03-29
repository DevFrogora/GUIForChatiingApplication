/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package swing;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Container;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				WindowFrame frame = new WindowFrame("hello");

				Container mainContainer = frame.getContentPane();
				mainContainer.setLayout(new BorderLayout());

				Panel2 panel2 = new Panel2();
				Panel3 panel3 = new Panel3();
				Panel1 panel1 = new Panel1();

				mainContainer.add(
						panel1.getPanel(panel3.getSendBtn(), panel3.getAutoreplaceSmiles(), frame.getLayeredPane()),
						BorderLayout.CENTER);
				mainContainer.add(panel2.getPanel(), BorderLayout.EAST);
				mainContainer.add(panel3.getPanel(), BorderLayout.SOUTH);

			}
		});

	}
}
