package swing;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.awt.event.*;
import java.util.Date;
import javax.swing.text.*;

import javax.swing.text.html.HTMLDocument;

class EmojiPanel {
	private JTextComponent textComponent;
	DefaultCaret globcaret;
	WrappedHtmlEditorKit kit;
	HTMLDocument doc;
	JPanel center;
	EmojiPanel emojipanelcustomclass;
	JPanel emojipanel;
	String username;
	String emoteStr;

	void setDataOfTransferedCaret(JTextComponent textComponent, DefaultCaret globcaret, WrappedHtmlEditorKit kit,
			HTMLDocument doc, EmojiPanel emojipanelcustomclass, JPanel center, String username) {
		this.textComponent = textComponent;
		this.globcaret = globcaret;
		this.kit = kit;
		this.doc = doc;
		this.emojipanelcustomclass = emojipanelcustomclass;
		this.center = center;
		this.username = username;
	}

	EmojiPanel() {

	}

	public JPanel getPanel(JPanel center) {
		emojipanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		emojipanel.setBackground(Color.yellow);
		emojipanel.setBounds(100, 200, center.getWidth() - 100 - 20, center.getHeight() - 200 - 3);

		int size = 2;
		JButton[] btn_emotes = new JButton[size];
		String[] emojiurlarr = new String[] {
				"https://media.discordapp.net/attachments/569031398007242756/806476873043148850/smile_1.png",
				"https://media.discordapp.net/attachments/569031398007242756/806476565708931072/Poggies_2.png" };
		String[] emojinamearr = new String[] { ":Blob_smile:", ":Poggers:" };

		for (int i = 0; i < size; i++) {
			btn_emotes[i] = new JButton();
			btn_emotes[i].setPreferredSize(new Dimension(40, 40));
			btn_emotes[i].setToolTipText(emojinamearr[i]);
			Image image = null;
			try {
				URL url = new URL(emojiurlarr[i]);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0");
				conn.connect();
				InputStream urlStream = conn.getInputStream();
				image = ImageIO.read(urlStream);
				btn_emotes[i].setIcon(new ImageIcon(image));
			} catch (IOException e) {
				System.out.println("Something went wrong, sorry:" + e.toString());
				e.printStackTrace();
			}
		}

		for (int i = 0; i < size; i++) {
			emojipanel.add(btn_emotes[i]);
		}

		for (int i = 0; i < size; i++) {
			this.emoteStr = emojiurlarr[i];
			btn_emotes[i].addActionListener(new ActionListener() {
				String privateSavedEmoji = emoteStr;

				public void actionPerformed(ActionEvent ae) {
					JButton myButton = (JButton) ae.getSource();
					System.out.println(myButton.getToolTipText());
					try {
						pasteEmojiInTextPane(privateSavedEmoji);
						// pasteEmojiInTextPane("https://media.discordapp.net/attachments/761245357144932353/806199786222911528/smileblob.png");
					} catch (Exception bad) {
					}

				}
			});
		}

		return emojipanel;
	}

	void pasteEmojiInTextPane(String stremojiurl) throws BadLocationException, IOException {
		Date now = new Date();
		kit.insertHTML(doc, doc.getLength(),
				"<font color='red'><u><b>" + this.username + "<b></u></font> : "
						+ "<font  style='background-color:#000000; color:#ffffff; font-size: 6px;'>" + now.toString()
						+ "</font><br>" + "<img src=" + stremojiurl + " alt=\"smileBlob\" width=\"40\" height=\"30\">",
				0, 0, null);
		globcaret.setDot(textComponent.getDocument().getLength());
		globcaret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
		if (emojipanel.isVisible()) {
			emojipanel.setVisible(false);
		} else {
			emojipanel.setVisible(true);
		}
		emojipanel.setBounds(100, 200, center.getWidth() - 100 - 20, center.getHeight() - 200 - 3);
		center.revalidate();
	}
}