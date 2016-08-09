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

        parseQueryToNGrams(query, 3);


        //Check the count for the query
        /*
        System.out.println("Getting counts...");
        Map<String, Integer> map = index.getCountsFaster("jav");
        for (Entry<String, Integer> entry: map.entrySet()) {
        	System.out.println(entry);
        }
        */

    }

    //Merge intersection of url sets
    public Set<String> intersect(List<Set<String>> urlList){
        Set<String> intersection = new HashSet<String>();
        Set<String> s1 = urlList.get(0);
        intersection = s1;
        int numSet = urlList.size();

        for(int i =0; i<numSet; i++){
            intersection.retainAll(urlList.get(i));
        }

        return intersection;
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

    /* A function that gets the counts for a term that should be in the all the urls?
     *
     */
    public static void getCountsForUrls(Set<String> urls, String term) {

    }

}
