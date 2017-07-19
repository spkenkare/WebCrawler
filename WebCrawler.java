package assignment;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;

import org.attoparser.simple.*;
import org.attoparser.config.ParseConfiguration;

public class WebCrawler {

	/**
	 * The WebCrawler's main method starts crawling a set of pages. You can
	 * change this method as you see fit, as long as it takes URLs as inputs and
	 * saves an Index at "index.db".
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// Basic usage information
		if (args.length == 0) {
			System.out.println("No URLs specified.");
			System.exit(1);
		}
		// We'll throw all of the args into a list
		List<URL> remaining = new LinkedList<>();
		for (String url : args) {
			try {
				remaining.add(new File(url).toURI().toURL());
			} catch (MalformedURLException e) {
				// Throw this one out
				e.printStackTrace();
			}
		}

		// Create a parser from the attoparser library
		ISimpleMarkupParser parser = new SimpleMarkupParser(ParseConfiguration.htmlConfiguration());

		// We're using the handler we've defined

		HashSet<String> usedURLs = new HashSet<String>();
		for (int i = 0; i < remaining.size(); i++) {
			usedURLs.add(remaining.get(i).toString());
		}
		URL current = null;
		HashSet<String> list = new HashSet<String>();
		CrawlingMarkupHandler handler = new CrawlingMarkupHandler();
		try {
			Date date1 = new Date();
			long time1 = date1.getTime();
			Timestamp ts1 = new Timestamp(time1);
			while (!remaining.isEmpty()) {
				current = remaining.get(0);
				handler.setCurrent(current);
				try {
					parser.parse(new InputStreamReader(remaining.remove(0).openStream()), handler);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ArrayList<URL> newURLs = (ArrayList<URL>) handler.newURLs();
				for (int i = 0; i < newURLs.size(); i++) {
					list.add(newURLs.get(i).toString());
					if (!usedURLs.contains(newURLs.get(i).toString())) {
						usedURLs.add(newURLs.get(i).toString());
						remaining.add(newURLs.get(i));
					}
				}
			}
			Date date2 = new Date();
			long time2 = date2.getTime();
			Timestamp ts2 = new Timestamp(time2);
			System.out.println(ts1 + " " + ts2);
			// WebIndex wi = (WebIndex) handler.getIndex();
			// for (int i = 0; i < wi.getList().size(); i++) {
			// System.out.println(wi.getList().get(i));
			// }
			// System.out.println(wi.getList().size());
			// System.out.println(handler.count);
			// System.out.println(handler.count2);
			System.out.println(handler.urls.size());
			
			// handler.getIndex().save("index.db");
		} catch (Exception e) {
			e.printStackTrace();
			// Bad exception handling
		}
	}
}
