package swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import java.awt.Container;
import javax.swing.JLayeredPane;

class WindowFrame implements WindowListener {
  String iconSadBlobPath = "/frameIcon/sadblob.png";
  String iconSmileBlobPath = "/frameIcon/smileblob.png";
  JFrame window = new JFrame("Window State Listener");

  public WindowFrame() {
    window.addWindowListener(this);
    window.setSize(600, 600);
    window.setLocationRelativeTo(null);
    window.setIconImage(new ImageIcon(getClass().getResource(iconSmileBlobPath)).getImage());
    window.setResizable(false);
    window.setVisible(true);
  }

  public void add(JComponent component) {
    window.add(component);
  }

  public WindowFrame(String msg) {
    window = new JFrame(msg);
    window.addWindowListener(this);
    window.setSize(500, 500);
    window.setLocationRelativeTo(null);
    window.setResizable(false);
    window.setIconImage(new ImageIcon(getClass().getResource(iconSmileBlobPath)).getImage());
    window.setVisible(true);
  }

  public void windowClosing(WindowEvent e) {
    System.out.println("Closing");
    window.dispose();
    System.exit(0);
  }

  public void windowOpened(WindowEvent e) {
    System.out.println("Opened");
  }

  public void windowClosed(WindowEvent e) {
    System.out.println("Closed");
  }

  public void windowIconified(WindowEvent e) {
    System.out.println("Iconified");

  }

  public void windowDeiconified(WindowEvent e) {
    System.out.println("Deiconified");
  }

  public void windowActivated(WindowEvent e) {
    window.setIconImage(new ImageIcon(getClass().getResource(iconSmileBlobPath)).getImage());
    System.out.println("Activated");
  }

  public void windowDeactivated(WindowEvent e) {
    window.setIconImage(new ImageIcon(getClass().getResource(iconSadBlobPath)).getImage());
    System.out.println("Deactivated");
  }

  public Container getContentPane() {
    return (Container) window.getContentPane();
  }

  public JLayeredPane getLayeredPane() {
    return window.getLayeredPane();
  }

  public void pack() {
    window.pack();
  }

}