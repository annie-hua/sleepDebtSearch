package com.sleepdeptsearch;

import java.io.IOException;
import java.util.Set;
import java.util.ArrayList;


import javax.servlet.http.*;





@SuppressWarnings("serial")
public class Searched extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		ArrayList<Set<String>> urls = Search.parseQueryToNGrams(req.getParameter("search_option"), 3);
		resp.getWriter().println("<body background-image: url('Globe.jpg')><ol>");
		for(Set<String> set: urls){
			for (String a: set){
				resp.getWriter().println("<li><a href='" + a + "'>" + a + "</a></li>");
			}
		}
		resp.getWriter().println("</ol></body>");
	}

}