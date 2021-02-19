package swing;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.text.StyledEditorKit;

class Panel3 {
	JLabel name;
	AutoreplaceSmiles ars;
	JButton btn_send;
	JButton btn_attach;
	String emojiurlarr[];

	Panel3() {
		name = new JLabel("Name:");
		ars = new AutoreplaceSmiles();
		btn_send = new JButton("");
		btn_attach = new JButton("");
		btn_send.setPreferredSize(new Dimension(20, 20));
		btn_attach.setPreferredSize(new Dimension(20, 20));
	}

	public JButton getSendBtn() {
		return btn_send;
	}

	public AutoreplaceSmiles getAutoreplaceSmiles() {
		return ars;
	}

	public JPanel getPanel() {
		String iconSadBlobPath = "/frameIcon/emoji_smile.png";
		String iconattachmentPath = "/frameIcon/attachmentIcon.png";
		JPanel mainBoxPanel = new JPanel();
		mainBoxPanel.setLayout(new BoxLayout(mainBoxPanel, BoxLayout.Y_AXIS));
		JPanel textBoxPanel = new JPanel(new FlowLayout());
		textBoxPanel.add(name);
		textBoxPanel.add(btn_attach);

		JPanel msgBoxPanel = new JPanel(new BorderLayout());
		msgBoxPanel.setPreferredSize(new Dimension(250, 30));
		// adding the scrollTextpane

		ars.setEditorKit(new StyledEditorKit());
		ars.initListener();
		JScrollPane scroll = new JScrollPane(ars);
		// WrappedHtmlEditorKit kit = new WrappedHtmlEditorKit();
		// ars.setEditorKit(kit);

		msgBoxPanel.add(scroll, BorderLayout.CENTER);
		textBoxPanel.add(msgBoxPanel);
		textBoxPanel.add(btn_send);
		System.out.println(Paths.get(".").toAbsolutePath());
		btn_send.setIcon(new ImageIcon(getResourceImage(iconSadBlobPath)));
		btn_attach.setIcon(new ImageIcon(getResourceImage(iconattachmentPath)));

		JPanel textBoxPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		textBoxPanel2.add(new JLabel("User :"));
		textBoxPanel2.add(new JLabel("Typing ..."));

		mainBoxPanel.add(textBoxPanel);
		mainBoxPanel.add(textBoxPanel2);

		return mainBoxPanel;
	}

	Image getResourceImage(String path) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(path));

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return img;
	}

}