package assignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class WebQueryEngine {

	private static ArrayList<URL> links = new ArrayList<URL>();

	/**
	 * Returns a WebQueryEngine that uses the given Index to constructe answers
	 * to queries.
	 *
	 * @param index
	 *            The WebIndex this WebQueryEngine should use
	 * @return A WebQueryEngine ready to be queried
	 */
	public static WebQueryEngine fromIndex(WebIndex index) {
		WebQueryEngine wqe = new WebQueryEngine();
		wqe.links=index.getList();
		System.out.println(index.getList().size());
		System.out.println(links.toString());
		return wqe;
	}

	/**
	 * Returns a Collection of URLs (as Strings) of web pages satisfying the
	 * query expression.
	 *
	 * @param query
	 *            a query expression
	 * @return a Collection of web pages satisfying the query
	 */
	public Collection<Page> query(String query) {
		ArrayList<Page> list = new ArrayList<Page>();
		try {
			
			for (int i = 0; i < links.size(); i++) {
				BufferedReader in = new BufferedReader(new InputStreamReader(links.get(i).openStream()));
				String body = "";
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					body+=inputLine;
				}
				in.close();
				if(body.contains(query)) {
					list.add(new Page(links.get(i)));
				}
			}
		}
		catch (Exception e) {
			
		}
		
		return list;
	}
}
