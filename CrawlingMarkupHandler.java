package assignment;

import java.util.*;
import java.io.File;
import java.net.*;
import org.attoparser.simple.*;

public class CrawlingMarkupHandler extends AbstractSimpleMarkupHandler {

	public HashSet<String> urls = new HashSet<String>();
	ArrayList<URL> list = new ArrayList<URL>();
	HashSet<String> newURLs = new HashSet<String>();
	public int count = 0;
	public int count2 = 0;
	public ArrayList<String> attributesList = new ArrayList<String>();
	int docNum = 0;
	URL current;

	public CrawlingMarkupHandler() {
	}

	public void setCurrent(URL cur) {
		current = cur;
	}

	/**
	 * This method should return a completed Index after we've parsed things.
	 */
	public Index getIndex() {
		WebIndex i = new WebIndex(urls);
		return i;
	}

	/**
	 * This method is to communicate any URLs we find back to the Crawler.
	 */
	public List<URL> newURLs() {
		// list = new ArrayList<URL>();
		for (String s : newURLs) {
			try {
				// System.out.println(s);
				list.add(new URL(s));
			} catch (MalformedURLException e) {
			}
		}
		return list;
	}

	/**
	 * These are some of the methods from AbstractSimpleMarkupHandler. All of
	 * its method implementations are NoOps, so we've added some things to do;
	 * please remove all the extra printing before you turn in your code.
	 *
	 * Note: each of these methods defines a line and col param, but you
	 * probably don't need those values. You can look at the documentation for
	 * the superclass to see all of the handler methods.
	 */

	/**
	 * Called when the parser first starts reading a document.
	 * 
	 * @param startTimeNanos
	 *            the current time (in nanoseconds) when parsing starts
	 * @param line
	 *            the line of the document where parsing starts
	 * @param col
	 *            the column of the document where parsing starts
	 */
	public void handleDocumentStart(long startTimeNanos, int line, int col) {
		// System.out.println("Start of document");
		newURLs = new HashSet<String>();
	}

	/**
	 * Called when the parser finishes reading a document.
	 * 
	 * @param endTimeNanos
	 *            the current time (in nanoseconds) when parsing ends
	 * @param totalTimeNanos
	 *            the difference between current times at the start and end of
	 *            parsing
	 * @param line
	 *            the line of the document where parsing ends
	 * @param col
	 *            the column of the document where the parsing ends
	 * @param m
	 */
	public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) {
		// System.out.println("End of document");
	}

	/**
	 * Called at the start of any tag.
	 * 
	 * @param elementName
	 *            the element name (such as "div")
	 * @param attributes
	 *            the element attributes map, or null if it has no attributes
	 * @param line
	 *            the line in the document where this elements appears
	 * @param col
	 *            the column in the document where this element appears
	 */
	public void handleOpenElement(String elementName, Map<String, String> attributes, int line, int col) {
		if (attributes == null) {
			return;
		}
		try {
			if (elementName.equalsIgnoreCase("a")) {
				count++;
				// System.out.println(attributes.toString());
				for (String entry : attributes.keySet()) {
					if (entry.equalsIgnoreCase("href")) {
						if (!attributesList.contains(attributes.get(entry).toLowerCase())) {
							attributesList.add(attributes.get(entry).toLowerCase());
						} else {
							return;
						}
						try {
							String prefix = current.toString().substring(0, current.toString().lastIndexOf("/"));
							String x = attributes.get(entry).toString();
							String s = prefix.toString() + "/" + x.toString();

							File file = new File(s.substring(6));
							if (file.exists() && !s.contains(".jpg") && !s.contains(".swf") && !s.contains(".gif")) {
								URI newS = new URI(s);
								newS = newS.normalize();
								s = newS.toString();
								urls.add(s);
								newURLs.add(s);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * private String fixURL(String url) { if (url.contains("://")) { return
	 * url; } if (url.charAt(0) == '/') { String cur = current.toString();
	 * String beginning = cur.substring(0, cur.lastIndexOf("/")); beginning =
	 * beginning.substring(0, beginning.lastIndexOf("/")); return beginning +
	 * url; } if (!url.startsWith("www")) { // System.out.println(url); String
	 * cur = current.toString(); String beginning = cur.substring(0,
	 * cur.lastIndexOf("/") + 1); return beginning + url; } return "http://" +
	 * url; }
	 */

	/**
	 * Called at the end of any tag.
	 * 
	 * @param elementName
	 *            the element name (such as "div").
	 * @param line
	 *            the line in the document where this elements appears.
	 * @param col
	 *            the column in the document where this element appears.
	 */
	public void handleCloseElement(String elementName, int line, int col) {

	}

	/**
	 * Called whenever characters are found inside a tag. Note that the parser
	 * is not required to return all characters in the tag in a single chunk.
	 * Whitespace is also returned as characters.
	 * 
	 * @param ch
	 *            buffer containint characters; do not modify this buffer
	 * @param start
	 *            location of 1st character in ch
	 * @param length
	 *            number of characters in ch
	 */
	public void handleText(char ch[], int start, int length, int line, int col) {
		/*
		 * // System.out.print("Characters: \""); for (int i = start; i < start
		 * + length; i++) { // Instead of printing raw whitespace, we're
		 * escaping it switch (ch[i]) { case '\\': // System.out.print("\\\\");
		 * break; case '"': // System.out.print("\\\""); break; case '\n': //
		 * System.out.print("\\n");; case '\r': // System.out.print("\\r");
		 * break; case '\t': // System.out.print("\\t"); break; default: //
		 * System.out.print(ch[i]); break; } } // System.out.print("\"\n");
		 */ }

}
