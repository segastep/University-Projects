package org.ness;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * Handle adding bookmark button.
 * 
 * @author
 *
 */
public class BookMarkButtonListener implements ActionListener {

	private JEditorPane editorPane;
	private JTextField txtUrl;
	private JMenu menuBookMark;
	private JButton btnBack;
	private JMenu historyMenu;

	public BookMarkButtonListener(JEditorPane editorPane, JTextField txtUrl, JMenu menuBookMark, JButton btnBack,
			JMenu historyMenu) {
		this.editorPane = editorPane;
		this.txtUrl = txtUrl;
		this.menuBookMark = menuBookMark;
		this.btnBack = btnBack;
		this.historyMenu = historyMenu;
	}

	/**
	 * add new bookmark and refresh bookmark menu.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String bookmarkURL = this.txtUrl.getText();
		// validate URL
		if (bookmarkURL != null && !bookmarkURL.equals("")) {
			// write bookmark
			boolean isAdded = Utility.writeBookmarks(bookmarkURL);

			if (isAdded) {
				// add new bookmark item
				JMenuItem bookMarkMenu = new JMenuItem(bookmarkURL);
				menuBookMark.add(bookMarkMenu);

				// register bookmark menu
				ActionListener bookmarkListener = new BookmarkListener(editorPane, txtUrl, bookMarkMenu, btnBack,
						historyMenu);
				bookMarkMenu.addActionListener(bookmarkListener);

				// refresh bookmark menu
				menuBookMark.getPopupMenu().pack();
				menuBookMark.repaint();
			}
		}

	}
}
