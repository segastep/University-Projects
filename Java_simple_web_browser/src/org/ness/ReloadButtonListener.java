package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handle reload button.
 * 
 * @author
 *
 */
public class ReloadButtonListener implements ActionListener {

	private JEditorPane editorPane;
	private JTextField txtUrl;

	public ReloadButtonListener(JEditorPane editorPane, JTextField txtUrl) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
	}

	/**
	 * reload current page.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String currentURL = txtUrl.getText();
		if (currentURL == null) {
			currentURL = Utility.readHomePage();
		}
		try {
			this.editorPane.setPage(currentURL);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Invalid URL", "Invalid URL", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

}
