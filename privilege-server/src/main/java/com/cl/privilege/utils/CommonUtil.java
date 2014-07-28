package com.cl.privilege.utils;


public class CommonUtil {
	/**
	 * 按照数字的方式比较两个字符串
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int compareTo(String str1,String str2)
	{
		int dis = Math.abs(str1.length()-str2.length());
		if(dis!=0)
		{
			String temp = "";
			for(int i=0;i<dis;i++)
			{
				temp += "0";
			}
			if(str1.length()>str2.length())
			{
				str2 = temp + str2;
			} else {
				str1 = temp + str1;
			}
		}
		
		return str1.compareTo(str2);
	}
	
	public static boolean isNumber(String str)
	{
		return str.matches("^\\d+(\\,\\d+)*$");
	}
}
