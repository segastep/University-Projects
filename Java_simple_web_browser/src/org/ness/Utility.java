package org.ness;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class contain utility methods related to persist history & bookmarking
 * functionality.
 * 
 * @author
 *
 */
public class Utility {

	/**
	 * bookmark config file.
	 */
	private static final String BOOKMARK_TXT = "bookmark.txt";
	/**
	 * homepage config file.
	 */
	private static final String CONFIG_TXT = "config.txt";
	/**
	 * bookmark and config location
	 */
	private static final String CONFIG_DIRECTORY = "src";
	private static final List<String> PERSIST_HISTORY = new ArrayList<>();
	private static int currentPosition = 0;

	private Utility() {

	}

	/**
	 * read home page from config file.
	 * 
	 * @return String homepage.
	 */
	public static String readHomePage() {
		String content = null;
		try {
			content = new String(readAllBytes(get(CONFIG_DIRECTORY, CONFIG_TXT)));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "The homepage reading error.", "Homepage reading ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return content;
	}

	/**
	 * reading all bookmarks.
	 * 
	 * @return List of urls.
	 */
	public static List<String> readBookmarks() {
		String content = null;
		try {
			content = new String(readAllBytes(get(CONFIG_DIRECTORY, BOOKMARK_TXT)));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "The bookmarks reading error.", "Bookmarks reading ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return splitURLS(content);
	}

	/**
	 * write new bookmark if already exist give info message.
	 * 
	 * @param bookmarkedURL
	 *            - String url
	 */
	public static boolean writeBookmarks(String bookmarkedURL) {
		String content = null;
		boolean isAdded = false;
		try {
			List<String> existUrls = readBookmarks();
			if (existUrls.contains(bookmarkedURL)) {
				JOptionPane.showMessageDialog(null, "URL already exist:" + bookmarkedURL, "Already exist URL",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				content = new String(readAllBytes(get(CONFIG_DIRECTORY, BOOKMARK_TXT)));
				content = content + "," + bookmarkedURL;
				write(get(CONFIG_DIRECTORY, BOOKMARK_TXT), content.getBytes());
				isAdded = true;
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Bookmark writing/reading error.", "Bookmark ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

		return isAdded;
	}

	/**
	 * persist url for back & forward functionality.
	 * 
	 * @param url
	 */
	public static void persistUrl(String url) {
		if (url != null) {
			PERSIST_HISTORY.add(url);
			currentPosition++;
		}
	}

	/**
	 * persist url home page functionality.
	 * 
	 * @param url
	 */
	public static void persistHomePage() {
		String homePage = readHomePage();
		if (homePage != null) {
			PERSIST_HISTORY.add(homePage);
		}
	}

	/**
	 * get current persist position.
	 * 
	 * @return current persist position.
	 */
	public static int getCurrentHistoryPosition() {
		return currentPosition;
	}

	/**
	 * get persist history size.
	 * 
	 * @return history size.
	 */
	public static int getHistorySize() {
		return PERSIST_HISTORY.size();
	}

	/**
	 * get previous url.
	 * 
	 * @param btnBack
	 *            - back button.
	 * @param btnForward
	 *            - foward button.
	 * @return previous url.
	 */
	public static String getPreviousURL(JButton btnBack, JButton btnForward) {
		currentPosition--;
		if (currentPosition == 0) {
			btnBack.setEnabled(false);
		} else {
			btnBack.setEnabled(true);
		}
		if (PERSIST_HISTORY.size() == currentPosition + 1) {
			btnForward.setEnabled(false);
		} else {
			btnForward.setEnabled(true);
		}
		return PERSIST_HISTORY.get(currentPosition);
	}

	/**
	 * get forward url.
	 * 
	 * @param btnForward
	 *            - back button.
	 * @param btnBack
	 *            - foward button.
	 * @return forward url.
	 */
	public static String getForwardURL(JButton btnForward, JButton btnBack) {
		currentPosition++;
		if (PERSIST_HISTORY.size() == currentPosition + 1) {
			btnForward.setEnabled(false);
		} else {
			btnForward.setEnabled(true);
		}
		if (currentPosition == 0) {
			btnBack.setEnabled(false);
		} else {
			btnBack.setEnabled(true);
		}
		return PERSIST_HISTORY.get(currentPosition);
	}

	/**
	 * split URL from comma separated String.
	 * 
	 * @param content
	 *            - comma separated string that contain bookmarks.
	 * @return List<URLs>
	 */
	private static List<String> splitURLS(String content) {
		String[] urls = content.split(",");
		List<String> bookmarks = new ArrayList<>();
		for (String str : urls) {
			bookmarks.add(str);
		}
		return bookmarks;
	}
	
	/*private static void updateHistory(JMenuItem historyMenuItem, String url){
		JMenuItem historyMenuItem = new JMenuItem(url);
		ActionListener bookmarkListener = null;
		
			bookMarkMenu = ;
			bookmarkMenu.add(bookMarkMenu);

			// register bookmarks menu for each link
			bookmarkListener = new BookmarkListener(editorPane, txtURL, bookMarkMenu, btnBack);
			bookMarkMenu.addActionListener(bookmarkListener);
		}
	}*/
}
