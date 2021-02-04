package swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.text.html.HTMLDocument;

public class AutoreplaceSmiles extends JTextPane {
    // static ImageIcon SMILE_IMG=createImage();

    public AutoreplaceSmiles() {
        super();
    }

    public void initListener() {
        String[] emojinamearr = new String[] { ":Blob_smile:", ":Poggers:" };
        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent event) {
                for (int i = 0; i < emojinamearr.length; i++)
                    getTheTextPaneListener(event, emojinamearr[i], i);

            }

            public void removeUpdate(DocumentEvent e) {
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    void getTheTextPaneListener(DocumentEvent event, String pattern, int imageIndex) {
        final DocumentEvent e = event;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (e.getDocument() instanceof StyledDocument) {
                    try {
                        StyledDocument doc = (StyledDocument) e.getDocument();
                        int start = Utilities.getRowStart(AutoreplaceSmiles.this, Math.max(0, e.getOffset() - 1));
                        int end = Utilities.getWordStart(AutoreplaceSmiles.this, e.getOffset() + e.getLength());
                        String text = doc.getText(start, end - start);

                        int i = text.indexOf(pattern);
                        while (i >= 0) {
                            final SimpleAttributeSet attrs = new SimpleAttributeSet(
                                    doc.getCharacterElement(start + i).getAttributes());
                            if (StyleConstants.getIcon(attrs) == null) {
                                StyleConstants.setIcon(attrs, createImage(imageIndex)); // create image
                                doc.remove(start + i, pattern.length());
                                doc.insertString(start + i, pattern, attrs);
                            }
                            i = text.indexOf(pattern, i + pattern.length());
                        }
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    ImageIcon createImage(int imageIndex) {
        String[] emojiurlarr = new String[] {
                "https://media.discordapp.net/attachments/569031398007242756/806476873043148850/smile_1.png",
                "https://media.discordapp.net/attachments/569031398007242756/806476565708931072/Poggies_2.png" };
        Image image = null;
        try {
            URL url = new URL(emojiurlarr[imageIndex]);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            InputStream urlStream = conn.getInputStream();
            image = ImageIO.read(urlStream);

        } catch (IOException e) {
            System.out.println("Something went wrong, sorry:" + e.toString());
            e.printStackTrace();
        }

        return new ImageIcon(getScaledImage(image, 20, 20));
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    void setEditorKit(WrappedHtmlEditorKit kit) {
        this.setEditorKit(kit);
    }

    void setDocument(HTMLDocument doc) {
        this.setDocument(doc);
    }
}