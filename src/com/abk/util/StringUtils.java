package com.abk.util;

public class StringUtils {
	public static String trimStart(String str) {
		String leftRemoved = str.replaceAll("^\\s+", "");
		return leftRemoved;
	}
	public static String trimStartAndEnd(String str){
		return StringUtils.trimEnd(StringUtils.trimStart(str));
	}
	public static String trimEnd(String str) {
		String rightRemoved = str.replaceAll("\\s+$", "");
		return rightRemoved;
	}
	public static String removeLastCharacter(String str) {
		if(str!=null){
			return str.substring(0,str.length()-1);
		}
		return null;
	} 
	public static String replaceTurkishCharacters(String str){	
		char[] trarray = {'ı','ğ','ü','ç','ş','ö','İ','Ğ','Ü','Ç','Ş','Ö'};
		char[] enarray = {'i','g','u','c','s','o','I','G','U','C','S','O'};
		StringBuilder bl = new StringBuilder();
		for(int i =0;i<str.length();i++){
			int j = 0;
			while(j<trarray.length && str.charAt(i)!=trarray[j]){
				j++;
			}
			if(j==trarray.length){
				bl.append(str.charAt(i));
			}else{
				bl.append(enarray[j]);
			}
		}
		return bl.toString();
		
	}
	public static String lowerCase(String str1){
		char[] upper = {'Ü','İ','I','Ö','Ğ','Ç','Ş'};
		char[] lower = {'ü','i','ı','ö','ğ','ç','ş'};
		StringBuilder bl = new StringBuilder();
		String str = str1.replaceAll("I", "ı");
		for(int i =0;i<str.length();i++){
			char cr =str.charAt(i);
			if(cr>=65 && cr<=90){
				cr += 32;
			}else{
				int index = 0;
				while(index<upper.length && upper[index]!=cr){
					index++;
				}
				if(index != upper.length){
					cr = lower[index];
				}
			}
			bl.append(cr);
		}
		return bl.toString();
	}
	public static String removeLastCharacterIfExistsAndTrim(String str,char cr){
		String str2 = str;
		if(str!=null && str.charAt(str.length()-1)==cr){
			str2 = str.substring(0,str.length()-1);
		}
		return StringUtils.trimEnd(str2);
	}
	public static String removeNonAlphaCharacters(String str){
		return str.replaceAll("[^A-Za-z0-9 ]", "");
	}
	public static String removeLastCharacterIfExists(String str,char cr){
		if(str!=null && str.charAt(str.length()-1)==cr){
			return str.substring(0,str.length()-1);
		}
		return str;
	}
	public static boolean checkIfLastCharacterExists(String str,char cr){
		if(str!=null && str.charAt(str.length()-1)==cr)
			return true;
		return false;
	}
	public static String removeAllWhiteSpacesExceptOne(String str){
		boolean foundBefore = false;
		StringBuilder sb = new StringBuilder();
		for(int i =0;i<str.length();i++){
			if(str.charAt(i)==' '){
				if(!foundBefore){
					sb.append(' ');
					foundBefore = true;	
				}
			}else{
				sb.append(str.charAt(i));
				foundBefore = false;
			}
		}
		return sb.toString();
	}
}
