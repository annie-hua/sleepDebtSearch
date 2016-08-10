package com.sleepdeptsearch;

import java.io.IOException;
import java.util.Set;
import java.util.ArrayList;


import javax.servlet.http.*;





@SuppressWarnings("serial")
public class Searched extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		Set<String> urls = Search.parseQueryToNGrams(req.getParameter("search_option"), 3);
		resp.getWriter().println("<body><img class='displayed' src='/resources/logo.jpg' width='1000' height='300'><link rel='stylesheet' type='text/css' href='/resources/search_result.css'></body> \n<ol>");
		for(String a: urls){
				String[] title_url = a.split("/");
				String title = title_url[title_url.length-1];
				resp.getWriter().println("<li><a href='" + a + "'>" + title + "</a></li>");
		}
		resp.getWriter().println("</ol>");
	}
}