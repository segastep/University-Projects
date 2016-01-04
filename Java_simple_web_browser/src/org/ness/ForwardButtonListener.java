package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handle forward button.
 * 
 * @author
 *
 */
public class ForwardButtonListener implements ActionListener {
	private JEditorPane editorPane;
	private JTextField txtUrl;
	private JButton btnForward;
	private JButton btnBack;

	public ForwardButtonListener(JEditorPane editorPane, JTextField txtUrl, JButton btnForward, JButton btnBack) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
		this.btnForward = btnForward;
		this.btnBack = btnBack;
	}

	/**
	 * load next url from persistent history.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String forwardPage = Utility.getForwardURL(btnForward, btnBack);
		txtUrl.setText(forwardPage);
		try {
			this.editorPane.setPage(forwardPage);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Can not load next url.", "Loading error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}

	}

}
