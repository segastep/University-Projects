package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.IDN;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handle URLs.
 * 
 * @author
 *
 */
public class URLListener implements ActionListener {

	private JEditorPane editorPane;
	private JButton btnBack;
	private JButton btnForward;
	private JTextField txtUrl;
	private JMenu historyMenu;

	public URLListener(JEditorPane editorPane, JButton btnBack, JButton btnForward, JTextField txtUrl,JMenu historyMenu) {
		this.editorPane = editorPane;
		this.btnBack = btnBack;
		this.btnForward = btnForward;
		this.txtUrl = txtUrl;
		this.historyMenu = historyMenu;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String url = txtUrl.getText().toLowerCase();
		System.out.println(url);
		try {
			editorPane.setPage(url);
			// persist url
			Utility.persistUrl(url);
			
			//add history
			JMenuItem historyMenuItem = new JMenuItem(url);
			historyMenu.add(historyMenuItem);
			
			// register history menu
			ActionListener historyListener = new HistoryListener(editorPane, txtUrl, historyMenuItem, btnBack);
			historyMenu.addActionListener(historyListener);

			// refresh history menu
			historyMenu.getPopupMenu().pack();
			historyMenu.repaint();
			
			int historySize = Utility.getHistorySize();
			int currentPosition = Utility.getCurrentHistoryPosition();
			if (historySize > 0) {
				btnBack.setEnabled(true);
				if (historySize > currentPosition + 1) {
					btnForward.setEnabled(true);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Invalid URL: " + url, "Invalid URL",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

}

