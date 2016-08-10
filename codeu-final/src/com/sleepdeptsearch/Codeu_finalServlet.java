package com.sleepdeptsearch;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Codeu_finalServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<html>"
				+ "<head>"
				+ "<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>"
				+ "<script src='https://code.jquery.com/ui/1.11.4/jquery-ui.js'></script>"
				+ "<link href='http://fonts.googleapis.com/css?family=Rock+Salt' rel='stylesheet' type='text/css'>"
				+ "<link rel='stylesheet' type='text/css' href='/resources/search_page.css'>"
				+ "<script src='https://code.jquery.com/ui/1.11.4/jquery-ui.js'></script>"
				+ "<title>Search-R-Us</title>"
				+ "<body>"
				+ "<center>"
				+ "<form id = 'enter_search' action='/searched'>"
				+ "<input type='text' name='search_option' cols='50' placeholder='Search...'>"
				+ "</input>"
				+ "</form>"
				+ "</center>"
				+ "</body>"
				+ "<html>");
	}
}