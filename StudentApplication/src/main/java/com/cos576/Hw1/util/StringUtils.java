package com.cos576.Hw1.util;

public class StringUtils {
	
	public static String cleanup(String s) {
		if (s == null) {
			return null;
		} else {
			String trimmed = s.trim();
			return ("".equals(trimmed) ? null : trimmed);
		}
	}

}
