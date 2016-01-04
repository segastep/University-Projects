package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handlr back button events.
 * 
 * @author
 *
 */
public class BackButtonListener implements ActionListener {
	
	private JEditorPane editorPane;
	private JTextField txtUrl;
	private JButton btnBack;
	private JButton btnForward;

	public BackButtonListener(JEditorPane editorPane, JTextField txtUrl, JButton btnBack, JButton btnForward) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
		this.btnBack = btnBack;
		this.btnForward = btnForward;

	}

	/**
	 * load previous page and update current position in the session.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//get back page
		String backPage = Utility.getPreviousURL(btnBack,btnForward);
		try {
			this.editorPane.setPage(backPage);
			this.txtUrl.setText(backPage);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Can not load previous url.", "Loading error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();	}

	}
}
