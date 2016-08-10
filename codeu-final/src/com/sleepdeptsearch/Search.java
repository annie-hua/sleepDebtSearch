package com.sleepdeptsearch;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;



import java.util.ArrayList;



public class Search {

//    public static JedisIndex index = new JedisIndex(JedisMaker.make());
//
//    public static void main(String[] args) throws IOException {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Search: ");
//        String query = input.nextLine();
//        query = query.trim();
//
//        //Set up Jedis
//        Jedis jedis = JedisMaker.make();
//		Search.index = new JedisIndex(jedis);
//
//       // parseQueryToNGrams("java", 3);
//
//
//        //Check the count for the query
//        /*
//        System.out.println("Getting counts...");
//        Map<String, Integer> map = index.getCountsFaster("jav");
//        for (Entry<String, Integer> entry: map.entrySet()) {
//        	System.out.println(entry);
//        }
//        */
//
//    }

    //Merge intersection of url sets
    public static Set<String> intersect(List<Set<String>> urlList){
        Set<String> intersection = new HashSet<String>();
        Set<String> s1 = urlList.get(0);
        intersection = s1;
        int numSet = urlList.size();

        for(int i =0; i<numSet; i++){
            intersection.retainAll(urlList.get(i));
        }

        return intersection;
    }

    public static Set<String> parseQueryToNGrams(String query, int n) throws IOException {
    	
    	JedisIndex index = new JedisIndex(JedisMaker.make());
    	
        ArrayList<Set<String>> setsOfUrls = new ArrayList<Set<String>>();

        if (query.length() <= n) {
            //If the text is less than or equal to a trigram
            Set<String> currentSet = index.getURLs(query);
            setsOfUrls.add(currentSet);

        } else {
            //Iterate through the string in substrings of length n until the end of the string (may include spaces)
            for(int i = 0; i <= query.length() - n; i++) {
                String currentSubstring = query.substring(i, i + n);
                Set<String> currentSet = index.getURLs(currentSubstring);
                setsOfUrls.add(currentSet);
            }
        }

        Set<String> intersection = intersect(setsOfUrls);

        //Remove false positives
        Set<String> removals = new HashSet<String>();
        for (String url : intersection) {
            if (!verifyUrl(url, query)) {
                removals.add(url);
            }
        }
        intersection.removeAll(removals);
        
        return intersection;

    }

    /* A function that verifies the query exists in the return set of urls
    *
    */
   public static Boolean verifyUrl(String url, String term) throws IOException {
       Elements paragraphs = WikiCrawler.wf.fetchWikipedia(url);
       return verifyElements(paragraphs, term);
   }

   /**
	 * Takes a collection of Elements and counts their words.
	 *
	 * @param paragraphs
	 */
	public static Boolean verifyElements(Elements paragraphs, String term) {

		for (Node node: paragraphs) {
           // NOTE: we could use select to find the TextNodes, but since
   		// we already have a tree iterator, let's use it.
   		for (Node innerNode: new WikiNodeIterable(node)) {
   			if (innerNode instanceof TextNode) {
                   String text = ((TextNode) innerNode).text();
   				String[] array = text.replaceAll("\\pP", " ").toLowerCase().trim().split("\\s+");
                   for (String nodeText: array) {
                       if (nodeText.equals(term)) {
                           return true;
                       }
                   }

   			}
   		}
		}

       return false;
	}

}
