package org.ness;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkListener;

/**
 * WebBorwser class contains GUI and startup functionalities.
 * 
 * @author
 *
 */
public class WebBrowser {
	// browser title
	private static final String WEB_BROWSER_TITLE = "simple web browser";
	// default home page - this string is not used unless the configuration file is not found !
	//It is just as an insurance 
	private static final String DEFAULT_HOME_PAGE = "http://www.ncl.ac.uk";

	JFrame frame = new JFrame();
	JEditorPane editorPane = new JEditorPane();
	JPanel panel = new JPanel();
	JPanel toolBar = new JPanel();
	private JTextField txtURL = new JTextField("", 30);
	private JLabel lblStatus = new JLabel(" ");

	// buttons
	private JButton btnBack = new JButton("Back");
	private JButton btnForward = new JButton("Forward");
	private JButton btnReload = new JButton("Reload");
	private JButton btnHome = new JButton("Home");
	private JButton btnBookMark = new JButton("Bookmark");

	// menu
	private JMenuBar menubar = new JMenuBar();
	private JMenu bookmarkMenu = new JMenu("BookMarks");
	private JMenu historyMenu = new JMenu("History");

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		WebBrowser webBrowser = new WebBrowser();
		webBrowser.runBrowser();
	}

	/**
	 * Initialize web browser
	 */
	public void runBrowser() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add history
		menubar.add(historyMenu);

		// load bookmarks
		menubar.add(bookmarkMenu);
		List<String> bookmarks = Utility.readBookmarks();

		JMenuItem bookMarkMenu = null;
		ActionListener bookmarkListener = null;
		for (String str : bookmarks) {
			bookMarkMenu = new JMenuItem(str);
			bookmarkMenu.add(bookMarkMenu);

			// register bookmarks menu for each link
			bookmarkListener = new BookmarkListener(editorPane, txtURL, bookMarkMenu, btnBack, historyMenu);
			bookMarkMenu.addActionListener(bookmarkListener);
		}

		// disable back & forward button when browser startup.
		btnBack.setEnabled(false);
		btnForward.setEnabled(false);

		panel.setLayout(new FlowLayout());
		panel.add(btnBack);
		panel.add(btnForward);
		panel.add(btnHome);
		panel.add(new JLabel("URL: "));
		panel.add(txtURL);
		panel.add(btnReload);
		panel.add(btnBookMark);
		panel.add(menubar);

		frame.setTitle(WEB_BROWSER_TITLE);

		Container content = frame.getContentPane();

		// load home page
		String homePage = Utility.readHomePage();
		if (homePage == null) {
			homePage = DEFAULT_HOME_PAGE;
		}
		txtURL.setText(homePage);
		try {
			editorPane.setPage(homePage);
			// persist startup URL.
			Utility.persistHomePage();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Invalid URL:" + homePage, "Invalid URL", JOptionPane.ERROR_MESSAGE);
		}
		editorPane.setEditable(false);

		// register hyperlink listener
		HyperlinkListener hyperlinkListener = new ActivatedHyperlinkListener(frame, editorPane, txtURL, btnBack,
				btnForward);
		editorPane.addHyperlinkListener(hyperlinkListener);

		// register url listener
		ActionListener urlListner = new URLListener(editorPane, btnBack, btnForward, txtURL, historyMenu);
		txtURL.addActionListener(urlListner);

		// register home page button
		ActionListener homePageListener = new HomeButtonListener(editorPane, txtURL);
		btnHome.addActionListener(homePageListener);

		// register reload button
		ActionListener reloadListener = new ReloadButtonListener(editorPane, txtURL);
		btnReload.addActionListener(reloadListener);

		// register back button
		ActionListener backListener = new BackButtonListener(editorPane, txtURL, btnBack, btnForward);
		btnBack.addActionListener(backListener);

		// register forward button
		ActionListener forwardListener = new ForwardButtonListener(editorPane, txtURL, btnForward, btnBack);
		btnForward.addActionListener(forwardListener);

		// register bookmark button
		ActionListener bookmarkBtnListener = new BookMarkButtonListener(editorPane, txtURL, bookmarkMenu, btnBack,
				historyMenu);
		btnBookMark.addActionListener(bookmarkBtnListener);

		JScrollPane scrollPane = new JScrollPane(editorPane);

		content.add(panel, BorderLayout.NORTH);
		content.add(lblStatus, BorderLayout.SOUTH);
		content.add(scrollPane, BorderLayout.CENTER);

		// open with fullscreen mode.
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}