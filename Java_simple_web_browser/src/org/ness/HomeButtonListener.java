package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handle home button.
 * 
 * @author
 *
 */
public class HomeButtonListener implements ActionListener {

	private JEditorPane editorPane;
	private JTextField txtUrl;

	public HomeButtonListener(JEditorPane editorPane, JTextField txtUrl) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
	}

	/**
	 * load home page.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String homePage = Utility.readHomePage();
		txtUrl.setText(homePage);
		try {
			// persist home page
			Utility.persistUrl(homePage);
			this.editorPane.setPage(homePage);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Can not load the home page", "Loading error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}

	}

}
