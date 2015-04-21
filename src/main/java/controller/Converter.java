package controller;

import java.util.HashMap;

/**
 * Class that contains static methods for string manipulation operations.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Converter
{
	// code map for special characters
	public static final HashMap<String, String> CODE_MAP = constructMap();
	
	// array of special character
	public static final String[] CHAR_ARRAY = constructArray();
	
	/**
	 * Replaces all occurences of each special character in the input
	 * with its corresponding character code in the code map. Returns the
	 * resulting string.
	 *  
	 * @param input		input to be converted
	 * @return			converted String
	 */
	public static String convert(String input)
	{
		for(int i=0; i < CHAR_ARRAY.length; i++)
			input = input.replace(CHAR_ARRAY[i], CODE_MAP.get(CHAR_ARRAY[i]));
		
		return input;
	}

	/**
	 * Replaces all occurences of character codes in the input with its
	 * corresponding character in the code map. Returns the resulting
	 * string.
	 * 
	 * @param input		input to be reverted
	 * @return			reverted String
	 */
	public static String revert(String input)
	{
		for(int i = CHAR_ARRAY.length - 1; i >= 0; i--)
			input = input.replace(CODE_MAP.get(CHAR_ARRAY[i]), CHAR_ARRAY[i]);
		
		return input;
	}
	
	/**
	 * Constructs the array of special characters.
	 * 
	 * @return	array containing special characters
	 */
	private static String[] constructArray()
	{
		String[] charArray = new String[5];
		
		charArray[0] = "%";
		charArray[1] = " ";
		charArray[2] = "=";
		charArray[3] = "&";
		charArray[4] = "?";
		
		return charArray;
	}
	
	/**
	 * Constructs the code map for the special characters.
	 * 
	 * @return	map of codes for special characters
	 */
	private static HashMap<String, String> constructMap()
	{
		HashMap<String, String> charMap = new HashMap<String, String>();
		
		charMap.put("%", "%25");
		charMap.put(" ", "%20");
		charMap.put("=", "%3D");
		charMap.put("&", "%26");
		charMap.put("?", "%3F");
		
		return charMap;
	}
}
