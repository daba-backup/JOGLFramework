package com.daxie.tool;

/**
 * Offers methods to handle strings.
 * @author Daba
 *
 */
public class StringFunctions {
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,int value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		ret+=value;
		ret+=";";
		
		return ret;
	}
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,float value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		ret+=value;
		ret+=";";
		
		return ret;
	}
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,boolean value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		
		if(value==false)ret+="false";
		else ret+=true;
		
		ret+=";";
		
		return ret;
	}
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,String value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		ret+="\"";
		ret+=value;
		ret+="\"";
		ret+=";";
		
		return ret;
	}
	
	/**
	 * Returns the first non-space character.
	 * @param str A string
	 * @return First non-space character
	 */
	public static char GetFirstNonSpaceCharacter(String str) {
		char ret=0;
		
		for(int i=0;i<str.length();i++) {
			if(str.charAt(i)!=' ') {
				ret=str.charAt(i);
				break;
			}
		}
		
		return ret;
	}
	
	/**
	 * Returns a string enclosed with two characters.
	 * @param str A string
	 * @param first_character First character
	 * @param second_character Second character
	 * @return A string between the first character and the second character
	 */
	public static String GetFirstStringInBetween(String str,char first_character,char second_character) {
		int first_pos=str.indexOf(first_character);
		int second_pos=str.indexOf(second_character, first_pos+1);
		
		if(first_pos<0||second_pos<0)return str;
		
		return str.substring(first_pos+1, second_pos);
	}
}
