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
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Search: ");
        String query = input.nextLine();

        //Set up Jedis
        Jedis jedis = JedisMaker.make();
		JedisIndex index = new JedisIndex(jedis);

        //Check the count for the query
        Map<String, Integer> map = index.getCountsFaster(query);
        for (Entry<String, Integer> entry: map.entrySet()) {
        	System.out.println(entry);
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
}
