package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handle bookmark click events.
 * 
 * @author
 *
 */
public class HistoryListener implements ActionListener {

	private JEditorPane editorPane;
	private JTextField txtUrl;
	private JMenuItem historyMenu;
	private JButton btnBack;

	public HistoryListener(JEditorPane editorPane, JTextField txtUrl, JMenuItem historyMenu, JButton btnBack) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
		this.historyMenu = historyMenu;
		this.btnBack = btnBack;
	}

	/**
	 * when click on the bookmark, loading clicked page and persist with
	 * history.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String url = this.historyMenu.getText();
		// set URL
		this.txtUrl.setText(url);
		try {
			// load page
			this.editorPane.setPage(url);
			// persist URL
			Utility.persistUrl(url);
			int currentPosition = Utility.getCurrentHistoryPosition();
			if(currentPosition > 0){
				btnBack.setEnabled(true);
			} else {
				btnBack.setEnabled(false);
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Invalid URL:" + url, "Invalid URL", JOptionPane.ERROR_MESSAGE);
		}

	}
}