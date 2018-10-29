package kr.or.ddit.util;

public class StringUtil {
	public static String stringUtil(String contentDisposition) {
		String[] fileNameList  = contentDisposition.split(";");
		String condition = "filename=\"";
		String profile = "";
		for(String str : fileNameList){
			if(str.contains(condition)){
				//샘 방식
				int beginIndex = str.lastIndexOf(condition)+condition.length();
				int endIndex = str.lastIndexOf("\"");
				profile = str.substring(beginIndex, endIndex);
				//내 방식
//				profile = str.substring(condition.length()+1, str.length()-1);
			}
		}
		return profile;
	}

	public static String getCookie(String cookieString, String name) {

		String[] cookieList = cookieString.split("; ");
		String resultStr = null;
		
		for(String str : cookieList){
			String[] strList = str.split("=");
//			if(strList[0].equals(name)){
//				resultStr = strList[1];
//				break;
//			}
			if(str.startsWith(name + "=")){
				resultStr = strList[1];
				break;
			}
		}
		
		return resultStr;
	}
	
	public static String[] tempUtil(String temp) {
		String[] tempList = temp.split(";");
		for(String t : tempList){
			System.out.println("t : " + t);
		}
		return tempList; 
	}
}
