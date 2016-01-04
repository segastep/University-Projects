package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handle bookmark click events.
 * 
 * @author
 *
 */
public class BookmarkListener implements ActionListener {

	private JEditorPane editorPane;
	private JTextField txtUrl;
	private JMenuItem bookMarkMenu;
	private JButton btnBack;
	private JMenu historyMenu;

	public BookmarkListener(JEditorPane editorPane, JTextField txtUrl, JMenuItem bookMarkMenu, JButton btnBack,
			JMenu historyMenu) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
		this.bookMarkMenu = bookMarkMenu;
		this.btnBack = btnBack;
		this.historyMenu = historyMenu;
	}

	/**
	 * when click on the bookmark, loading clicked page and persist with
	 * history.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String url = this.bookMarkMenu.getText();
		// set URL
		this.txtUrl.setText(url);
		// add history
		JMenuItem historyMenuItem = new JMenuItem(url);
		historyMenu.add(historyMenuItem);

		// register history menu
		ActionListener historyListener = new HistoryListener(editorPane, txtUrl, historyMenuItem, btnBack);
		historyMenu.addActionListener(historyListener);

		// refresh history menu
		historyMenu.getPopupMenu().pack();
		historyMenu.repaint();
		try {
			// load page
			this.editorPane.setPage(url);
			// persist URL
			Utility.persistUrl(url);
			int currentPosition = Utility.getCurrentHistoryPosition();
			if (currentPosition > 0) {
				btnBack.setEnabled(true);
			} else {
				btnBack.setEnabled(false);
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Invalid URL:" + url, "Invalid URL", JOptionPane.ERROR_MESSAGE);
		}

	}
}