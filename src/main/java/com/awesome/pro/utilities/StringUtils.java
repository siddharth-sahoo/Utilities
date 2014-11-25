package com.awesome.pro.utilities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * String operation utilities.
 * @author siddharth.s
 */
public final class StringUtils {

	/**
	 * @param text String to split.
	 * @param delimeter Character to split by.
	 * @return Resulting array after splitting.
	 */
	public static String[] split(final String text, final char delimeter) {
		int count = 1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == delimeter) {
				count++;
			}
		}

		String[] array = new String[count];

		int a = -1;
		int b = 0;

		for (int i = 0; i < count; i++) {
			while (b < text.length() && text.charAt(b) != delimeter) {
				b++;
			}

			array[i] = text.substring(a+1, b);
			a = b;
			b++;
		}
		return array;
	}

	/**
	 * @param text Text to be split.
	 * @param delimiter Delimiting string.
	 * @return Split array.
	 */
	public static List<String> split(final String text, final String delimiter) {
		if (text == null) {
			return null;
		}
		
		if (text.length() == 0) {
			return null;
		}
		
		if (!text.contains(delimiter)) {
			return new LinkedList<>(Arrays.asList(text));
		}
		
		List<String> split = new LinkedList<>(Arrays.asList(
				text.split("[" + delimiter + "]")));
		for (int i = 0; i < split.size(); i ++) {
			if (split.get(i).equals("") || split.get(i).equals(" ")) {
				split.remove(i);
				i --;
			}
		}
		return split;
	}
	
	/**
	 * @param line Text to take substring of.
	 * @param separator Reference string after which the substring
	 * will be taken.
	 * @return Text after the first occurrence of separator.
	 * Null is returned if the separator is not found in the text.
	 */
	public static String substringAfterFirst(final String line, final String separator) {
		if (line == null) {
			return null;
		}
		
		if (line.length() == 0) {
			return null;
		}
		
		if (!line.contains(separator)) {
			return null;
		}
		
		int index = line.indexOf(separator);
		if (index == -1) {
			return null;
		}
		return line.substring(index + separator.length(), line.length());
	}

	/**
	 * @param line Text to take substring from.
	 * @param separator Reference string before which the substring
	 * will be taken.
	 * @return Text before the first occurrence of separator.
	 * Null is returned if the separator is not found the text.
	 */
	public static String substringBeforeFirst(final String line, final String separator) {
		if (line == null) {
			return null;
		}
		
		if (line.length() == 0) {
			return null;
		}
		
		if (!line.contains(separator)) {
			return null;
		}
		
		int index = line.indexOf(separator);
		if (index == -1) {
			return null;
		}
		return line.substring(0, index);
	}
	
	/**
	 * @param line Text to take substring from.
	 * @param separator Reference string before which the substring
	 * will be taken.
	 * @return Text before the last occurrence of separator.
	 * Null is returned if the separator is not found the text.
	 */
	public static String substringBeforeLast(final String line, final String separator) {
		if (line == null) {
			return null;
		}
		
		if (line.length() == 0) {
			return null;
		}
		
		if (!line.contains(separator)) {
			return null;
		}
		
		int index = line.lastIndexOf(separator);
		if (index == -1) {
			return null;
		}
		return line.substring(0, index);
	}
	
	/**
	 * @param line Text to take substring from.
	 * @param separator Reference string after which the substring
	 * will be taken.
	 * @return Text after the last occurrence of separator.
	 * Null is returned if the separator is not found the text.
	 */
	public static String substringAfterLast(final String line, final String separator) {
		if (line == null)
			return null;
		
		if (line.length() == 0)
			return null;
		
		if (!line.contains(separator))
			return null;
		
		int index = line.lastIndexOf(separator);
		if(index == -1)
			return null;
		return line.substring(index + separator.length(), line.length());
	}

}
