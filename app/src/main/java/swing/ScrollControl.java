package swing;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.util.Random;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;

class ScrollControl implements AdjustmentListener
{	
	JButton btn_send_emoji;
    private JScrollBar scrollBar;
    private JTextComponent textComponent;
    private int previousExtent = -1;
	DefaultCaret globcaret;
	public ScrollControl(){}

    public void ScrollControler(JScrollPane scrollPane)
    {	
		
        Component view = scrollPane.getViewport().getView();

        if (! (view instanceof JTextComponent))
            throw new IllegalArgumentException("Scrollpane must contain a JTextComponent");

        textComponent = (JTextComponent)view;

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.addAdjustmentListener( this );
    }

    @Override
    public void adjustmentValueChanged(final AdjustmentEvent e)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                checkScrollBar(e);
            }
        });
    }

    public void checkScrollBar(AdjustmentEvent e)
    {


        JScrollBar scrollBar = (JScrollBar)e.getSource();
        BoundedRangeModel model = scrollBar.getModel();
        int value = model.getValue();
        int extent = model.getExtent();
        int maximum = model.getMaximum();
        DefaultCaret caret = (DefaultCaret)textComponent.getCaret();


		globcaret=caret;
        if (previousExtent != extent)
        {

            if (extent < previousExtent
            &&  caret.getUpdatePolicy() == DefaultCaret.UPDATE_WHEN_ON_EDT)
            {
                scrollBar.setValue( maximum );
            }

            previousExtent = extent;
            return;
        }

        int bottom = textComponent.getInsets().bottom;

        if (value + extent + bottom < maximum)
        {	
            if (caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE)
                caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        }
        else
        {
            if (caret.getUpdatePolicy() != DefaultCaret.UPDATE_WHEN_ON_EDT)
            {
                caret.setDot(textComponent.getDocument().getLength());
                caret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
            }
        }
    }

    public JPanel createAndShowUI(JButton btn_send_emoji, AutoreplaceSmiles textFieldMsg,JLayeredPane layeredPane)
    {
		
        JPanel center = new JPanel( new GridLayout() );
        String text = "1\n2\n3\n4\n5\n6\n7\n8\n9\n0\n";
		this.btn_send_emoji= btn_send_emoji;
		
        final JTextPane textPane = new JTextPane();
        textPane.setText( text );

        textPane.setEditable( false );
		textPane.setContentType("text/html");
        center.add( this.createScrollPane( textPane ) );
        System.out.println(textPane.getInsets());
		layeredPane.add(center);
		
		EmojiPanel emojipanelcustomclass=new EmojiPanel();
		JPanel emojipanel = emojipanelcustomclass.getPanel(center);  
		layeredPane.add(emojipanel, new Integer(3));  
		
		WrappedHtmlEditorKit kit = new WrappedHtmlEditorKit();
		HTMLDocument doc = new HTMLDocument();
		textPane.setEditorKit(kit);
		textPane.setDocument(doc);
		
		String username = "&lt;@"+"Username"+">";
		String [] emojiurlarr ={"https://media.discordapp.net/attachments/761245357144932353/806199786222911528/smileblob.png"};

		center.setPreferredSize(center.getPreferredSize());
		textFieldMsg.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode()==10) {
					try
					{	
						//makeStringToSend(textFieldMsg.getText());
						Date now =new Date();
						kit.insertHTML(doc, doc.getLength(), "<font color='red'><u><b>"+username+"<b></u></font> : "+"<font  style='background-color:#000000; color:#ffffff; font-size: 6px;'>"+ now.toString()+"</font><br>"+makeStringToSend(textFieldMsg.getText()) , 0, 0, null);
						textFieldMsg.setText("");
						globcaret.setDot(textComponent.getDocument().getLength());
						globcaret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT); 
						// + now.toString()
					}
					catch (BadLocationException e1) {}
					catch(IOException eo){}
				}
			}
		});
		
		emojipanelcustomclass.setDataOfTransferedCaret(textComponent,globcaret,kit,doc,emojipanelcustomclass,center,username);
		
        this.btn_send_emoji.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
					globcaret.setDot(textComponent.getDocument().getLength());
					globcaret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
					if(emojipanel.isVisible()){
						emojipanel.setVisible(false);
					}else{
						emojipanel.setVisible(true);
					}
					emojipanel.setBounds(100,200, center.getWidth() -100 -20,center.getHeight() -200 -3 ); 			
					center.revalidate();
            }
        });
		
        center.setPreferredSize(center.getPreferredSize());
		return center;
    }

    public JComponent createScrollPane(JComponent component)
    {
       JScrollPane scrollPane = new JScrollPane(component);
	   scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       ScrollControler( scrollPane );

        return scrollPane;
    }
	
	public void textPaneCarretListener(JTextPane textPane ,String username){
		Random rand = new Random();

		textPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if(e.getMark() == e.getDot()){
                    Highlighter hl = textPane.getHighlighter();
                    // hl.removeAllHighlights();
                    String pattern = username;
                    String text = textPane.getText(); 
                    int index = text.indexOf(pattern);
					
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					Color randomColor = new Color(r, g, b);
					
                    while(index >= 0){
                        try {                
                            // Object o = hl.addHighlight(index, index + pattern.length(), DefaultHighlighter.DefaultPainter);
                            Object o = hl.addHighlight(index, index + pattern.length(), new DefaultHighlighter.DefaultHighlightPainter(randomColor));
                            index = text.indexOf(pattern, index + pattern.length());
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
	}
	
	String makeStringToSend(String textpanestr){
		String [] emojiurlarr =new String[] {"https://media.discordapp.net/attachments/569031398007242756/806476873043148850/smile_1.png",
		"https://media.discordapp.net/attachments/569031398007242756/806476565708931072/Poggies_2.png"};
		String [] emojinamearr =new String[] {":Blob_smile:",":Poggers:"};
		String replaceString=null;
		replaceString =textpanestr;
		for(int i=0; i < emojinamearr.length; i++){
			while(replaceString.contains(emojinamearr[i])){
			replaceString=replaceString.replace(emojinamearr[i],"<img src="+emojiurlarr[i]+" alt=\"smileBlob\" width=\"30\" height=\"20\">");
			}
		}
		return replaceString;
	}
	

}