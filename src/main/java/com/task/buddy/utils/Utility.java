package com.task.buddy.utils;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	public static final Map<Integer, String> priorityMap = Map.of(1, "Highest", 2, "High", 3, "Medium", 4, "Low", 5,
			"Lowest");
}
