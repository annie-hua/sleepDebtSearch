package com.flatironschool.javacs;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashSet;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.Scanner;
import redis.clients.jedis.Jedis;

public class Search {

    public static JedisIndex index;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Search: ");
        String query = input.nextLine();
        query = query.trim();

        //Set up Jedis
        Jedis jedis = JedisMaker.make();
		Search.index = new JedisIndex(jedis);


        List<Set<String>> urlsContainingQuery = parseQueryToNGrams(query, 3); //query as trigrams
        //System.out.println("ArrayList of sets: " + urlsContainingQuery);

        Set<String> intersection = intersect(urlsContainingQuery);

        //Remove false positives
        Set<String> removals = new HashSet<String>();
        for (String url : intersection) {
            if (!verifyUrl(url, query)) {
                removals.add(url);
            }
        }
        intersection.removeAll(removals);

        System.out.println(intersection);

        //System.out.println(index.getCount("https://en.wikipedia.org/wiki/Computer_architecture", "eas"));

        //Check the count for the query
        /*
        System.out.println("Getting counts...");
        Map<String, Integer> map = index.getCountsFaster("jav");
        for (Entry<String, Integer> entry: map.entrySet()) {
        	System.out.println(entry);
        }
        */

    }


    public static ArrayList<Set<String>> parseQueryToNGrams(String query, int n) {
        ArrayList<Set<String>> setsOfUrls = new ArrayList<Set<String>>();

        if (query.length() <= n) {
            //If the text is less than or equal to a trigram
            Set<String> currentSet = Search.index.getURLs(query);
            setsOfUrls.add(currentSet);

        } else {
            //Iterate through the string in substrings of length n until the end of the string (may include spaces)
            for(int i = 0; i <= query.length() - n; i++) {
                String currentSubstring = query.substring(i, i + n);
                Set<String> currentSet = Search.index.getURLs(currentSubstring);
                setsOfUrls.add(currentSet);
            }
        }

        return setsOfUrls;
    }

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

    /* A function that gets the counts for a term that should be in the all the urls?
     *
     */
    public static void getCountsForUrls(Set<String> urls, String term) {

    }



}
