package org.ness;

import java.awt.Frame;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

/**
 * Handle hyperlinks.
 * 
 * @author
 *
 */
class ActivatedHyperlinkListener implements HyperlinkListener {

	private Frame frame;
	private JEditorPane editorPane;
	private JTextField txtUrl;
	private JButton btnBack;
	private JButton btnForward;

	public ActivatedHyperlinkListener(Frame frame, JEditorPane editorPane, JTextField txtUrl, JButton btnBack,
			JButton btnForward) {
		this.frame = frame;
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
		this.btnBack = btnBack;
		this.btnForward = btnForward;
	}

	/**
	 * handle hyperlink click event.
	 */
	public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
		HyperlinkEvent.EventType type = hyperlinkEvent.getEventType();
		final String url = hyperlinkEvent.getURL().toString();
		txtUrl.setText(url);
		if (type == HyperlinkEvent.EventType.ENTERED) {
			System.out.println("URL: " + url);
		} else if (type == HyperlinkEvent.EventType.ACTIVATED) {
			Runnable runner = new Runnable() {
				public void run() {
					// Retain reference to original
					Document doc = editorPane.getDocument();
					try {
						editorPane.setPage(url);
						Utility.persistUrl(url);
						int historySize = Utility.getHistorySize();
						int currentPosition = Utility.getCurrentHistoryPosition();
						if (historySize > 0) {
							btnBack.setEnabled(true);
							if (historySize > currentPosition + 1) {
								btnForward.setEnabled(true);
							}
						}
					} catch (IOException ioException) {
						JOptionPane.showMessageDialog(frame, "Error following link", "Invalid link",
								JOptionPane.ERROR_MESSAGE);
						editorPane.setDocument(doc);
					}
				}
			};
			SwingUtilities.invokeLater(runner);
		}
	}
}
