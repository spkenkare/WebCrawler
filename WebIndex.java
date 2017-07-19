package assignment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class WebIndex extends Index {
    private static final long serialVersionUID = 1L;
    private ArrayList<URL> list = new ArrayList<URL>();
    public WebIndex(HashSet<String> urls) {
  	  for(String s: urls) {
  		  try {
  			list.add(new URL(s));
  		} catch (MalformedURLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	  }
    }
    
    public ArrayList<URL> getList() {
    	return list;
    }
    
    public String toString() {
    	return list.toString();
    }
    
}
