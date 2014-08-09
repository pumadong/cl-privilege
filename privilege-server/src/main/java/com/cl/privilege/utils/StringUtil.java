package com.cl.privilege.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
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

  public static String getValue(String str)
  {
    if (str == null) return "";
    if (str.trim().length() <= 0) return "";
    str = "H" + str;
    str = str.trim();
    str = str.substring(1);
    return str;
  }

  public static boolean isStrEmpty(String str)
  {
    return ((str == null) || (str.equals("")));
  }

  public static boolean isStrTrimEmpty(String str)
  {
    return ((str == null) || (str.trim().equals("")));
  }

  public static boolean chkTextLen(String text, int len)
  {
    return ((text != null) && (text.length() <= len));
  }

  public static boolean chkTextTrimLen(String text, int len)
  {
    return ((text != null) && (text.trim().length() <= len));
  }

  public static boolean isStrEn(String text)
  {
    for (int i = 0; i < text.length(); ++i) {
      if (text.charAt(i) > '') {
        return false;
      }
    }
    return true;
  }

  public static boolean isCharNum(char ch)
  {
    return ((ch > '/') && (ch < ':'));
  }

  public static boolean isStrNum(String str)
  {
    if (isStrEmpty(str)) {
      return true;
    }
    boolean notNum = false;
    for (int i = 0; i < str.length(); ++i) {
      if (!(isCharNum(str.charAt(i)))) {
        notNum = true;
      }
    }
    return (!(notNum));
  }

  public static boolean isNum(String strSrc)
    throws Exception
  {
    for (int i = 0; i < strSrc.length(); ++i) {
      if (!(isCharNum(strSrc.charAt(i))))
        return false;
    }
    return true;
  }

  public static boolean isCharLetter(char ch)
  {
    return (((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z')));
  }

  public static boolean isStrLetter(String str)
  {
    if (isStrEmpty(str))
      return true;
    boolean notLetter = false;
    for (int i = 0; i < str.length(); ++i) {
      if (!(isCharLetter(str.charAt(i)))) {
        notLetter = true;
      }
    }
    return (!(notLetter));
  }

  public String nullToSpace(String Content)
  {
    if (Content == null) {
      Content = "";
    }
    return Content;
  }

  public static char StrToChar(String src)
  {
    src = src.trim();
    char result = src.charAt(0);
    return result;
  }

  public static String encodeSQL(String sql)
  {
    StringBuffer tempBuff = new StringBuffer();
    for (int i = 0; i < sql.length(); ++i) {
      tempBuff.append(Integer.toHexString(sql.charAt(i)));
    }
    return tempBuff.toString();
  }

  public static String decodeSQL(String encoded)
  {
    StringBuffer tempBuff = new StringBuffer();
    for (int i = 0; i < encoded.length(); i += 2) {
      tempBuff.append((char)Integer.parseInt(encoded.substring(i, i + 2), 16));
    }

    return tempBuff.toString();
  }

  public static String getAbsolutePath(String path1, String context1)
  {
    int i1 = path1.indexOf(context1);
    if (i1 < 0) return path1;

    return path1.substring(path1.indexOf(context1) + context1.length());
  }

  public static String getSubString(String str1, int sindex, int eindex)
  {
    if (str1 == null) return "";
    if (str1.trim().length() <= 0) return "";
    if (str1.length() > sindex)
    {
      if (eindex >= 0)
        return str1.substring(sindex, eindex);
      if (eindex < 0)
        return str1.substring(sindex);
    }
    return "";
  }

  public static String[] getValues(String[] strs, int size1)
  {
    String[] strs1 = new String[size1];
    for (int i = 0; i < size1; ++i)
    {
      strs1[i] = "";
    }
    if (strs == null) return strs1;

    if (strs.length < size1)
    {
      for (int i = 0; i < strs.length; ++i)
      {
        strs1[i] = strs[i];
      }
      return strs1;
    }

    return strs;
  }

  public static String replaceStrAll(String strSource, String strFrom, String strTo)
  {
    String strDest = "";
    int intFromLen = strFrom.length();

    int intPos;
	while ((intPos = strSource.indexOf(strFrom)) != -1)
    {
      strDest = strDest + strSource.substring(0, intPos);
      strDest = strDest + strTo;
      strSource = strSource.substring(intPos + intFromLen);
    }
    strDest = strDest + strSource;
    return strDest;
  }

  public static String replaceStr(String strTarget, String strNew)
  {
    int iIndex = -1;
    while (true)
    {
      iIndex = strTarget.indexOf(10);

      if (iIndex < 0) {
        break;
      }

      String strTemp = null;
      strTemp = strTarget.substring(0, iIndex);

      strTarget = strTemp + strNew + strTarget.substring(iIndex + 1);
    }

    return strTarget;
  }

  public static boolean includestr(String str1, String[] strarray)
  {
    if ((strarray == null) || (strarray.length <= 0)) return false;
    for (int i = 0; i < strarray.length; ++i)
    {
      if (strarray[i] == null)
      {
        if (str1 == null) return true;

      }
      else if (strarray[i].trim().equals(str1))
      {
        return true;
      }
    }
    return false;
  }

  public static String[] getAreaValues(String fvalue)
  {
    String tmpstr = fvalue;
    int i = 0;
    if (tmpstr == null) return null;
    if (tmpstr.trim().equals("")) return null;
    while (tmpstr.indexOf("\n") >= 0) {
      ++i;
      tmpstr = tmpstr.substring(tmpstr.indexOf("\n") + 1);
    }
    if (tmpstr.trim().equals("")) --i;
    String[] fvalues = new String[i + 1];
    tmpstr = fvalue;
    i = 0;
    while (tmpstr.indexOf("\n") >= 0)
    {
      fvalues[i] = tmpstr.substring(0, tmpstr.indexOf("\n"));
      if (fvalues[i].indexOf("\r") >= 0) fvalues[i] = fvalues[i].substring(0, fvalues[i].indexOf("\r"));
      ++i;
      tmpstr = tmpstr.substring(tmpstr.indexOf("\n") + 1);
    }
    if (!(tmpstr.trim().equals("")))
      fvalues[i] = tmpstr;
    return fvalues;
  }

  public static String getrealAreaValues(String fvalue)
  {
    String tmpstr = fvalue;
    String returnstr = "";
    if (tmpstr == null) return null;
    if (tmpstr.trim().equals("")) return "";
    while (tmpstr.indexOf("|") > 0)
    {
      returnstr = returnstr + tmpstr.substring(0, tmpstr.indexOf("|")) + "\n";
      tmpstr = tmpstr.substring(tmpstr.indexOf("|") + 1);
    }
    return returnstr;
  }

  public static int CountChar(String strInput, char chr)
  {
    int iCount = 0;
    char chrTmp = ' ';

    if (strInput.trim().length() == 0) {
      return 0;
    }
    for (int i = 0; i < strInput.length(); ++i) {
      chrTmp = strInput.charAt(i);
      if (chrTmp == chr) {
        ++iCount;
      }
    }
    return iCount;
  }

  public static String StrArrayToStr(String[] strs)
  {
    StringBuffer returnstr = new StringBuffer("");
    if (strs == null) return "";
    for (int i = 0; i < strs.length; ++i) {
      returnstr.append(strs[i]);
    }
    return returnstr.toString();
  }

  public static void printStrs(String[] strs)
  {
    for (int i = 0; i < strs.length; ++i)
      System.out.println(strs[i]);
  }

  public static void printDualStr(String[][] dualStr)
  {
    for (int i = 0; i < dualStr.length; ++i) {
      for (int j = 0; j < dualStr[i].length; ++j) {
        System.out.print(dualStr[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static String[][] rowToColumn(String[][] dualStr)
  {
    String[][] returnDualStr = (String[][])null;
    if (dualStr != null) {
      returnDualStr = new String[dualStr[0].length][dualStr.length];
      for (int i = 0; i < dualStr.length; ++i)
        for (int j = 0; j < dualStr[0].length; ++j)
          returnDualStr[j][i] = dualStr[i][j];
    }
    return returnDualStr;
  }

  public static String latinString(String inStr)
  {
    String res = inStr;
    if (null == res) return null;
    res = replaceStrAll(res, "\"", "\\\"");
    res = replaceStrAll(res, "'", "\\'");
    return res;
  }

  /**
   * 替换空格
   * @param strTarget
   * @param strNew
   * @return
   */
  public static String replaceWhiteSpace(String strTarget, String strNew)
  {
	  if(strTarget==null)
	  {
		  return "";
	  }
	  int iIndex = -1;
	  while (true) {
		  char cRep = ' ';
		  iIndex = strTarget.indexOf(cRep);
		  if (iIndex < 0) {
			  break;
		  }
		  String strTemp = null;
		  strTemp = strTarget.substring(0, iIndex);

		  strTarget = strTemp + strNew + strTarget.substring(iIndex + 1);
	  }
	  return strTarget;
  }

  public static String double2str(double amount, int length)
  {
    String strAmt = Double.toString(amount);

    int pos = strAmt.indexOf(46);

    if ((pos != -1) && (strAmt.length() > length + pos + 1)) {
      strAmt = strAmt.substring(0, pos + length + 1);
    }
    return strAmt;
  }

  public static String[] doSplit(String str, char chr)
  {
    int iCount = 0;
    char chrTmp = ' ';

    for (int i = 0; i < str.length(); ++i) {
      chrTmp = str.charAt(i);
      if (chrTmp == chr) {
        ++iCount;
      }
    }
    String[] strArray = new String[iCount];
    for (int i = 0; i < iCount; ++i) {
      int iPos = str.indexOf(chr);
      if (iPos == 0) {
        strArray[i] = "";
      }
      else {
        strArray[i] = str.substring(0, iPos);
      }
      str = str.substring(iPos + 1);
    }
    return strArray;
  }

 public static String strIntercept(String src, int len)
  {
    if (src == null) {
      return "";
    }
    if (src.length() > len) {
      src = String.valueOf(String.valueOf(src.substring(0, len))).concat("...");
    }

    return src;
  }

  public static String strtochn(String str_in)
  {
    try
    {
      String temp_p = str_in;
      if (temp_p == null) {
        temp_p = "";
      }
      String temp = "";
      if (!(temp_p.equals(""))) {
        byte[] byte1 = temp_p.getBytes("ISO8859_1");
        temp = new String(byte1);
      }
      return temp;
    }
    catch (Exception e) {
    }
    return "null";
  }

  public static String ISO2GBK(String strvalue)
  {
    try
    {
      if (strvalue == null) {
        return null;
      }
      strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
      return strvalue;
    }
    catch (Exception e) {
    }
    return null;
  }

  public String GBK2ISO(String strvalue)
    throws Exception
  {
    try
    {
      if (strvalue == null) {
        return null;
      }
      strvalue = new String(strvalue.getBytes("GBK"), "ISO8859_1");
      return strvalue;
    }
    catch (Exception e) {
    }
    return null;
  }

  public static String cnCodeTrans(String str)
  {
    String s = "";
    try {
      s = new String(str.getBytes("GB2312"), "8859_1");
    }
    catch (UnsupportedEncodingException a) {
      System.out.print("chinese thansform exception");
    }
    return s;
  }

  public static String chiEnUTF8(String str)
  {
    String s = "";
    try {
      s = new String(URLEncoder.encode(str, "UTF-8"));
    }
    catch (UnsupportedEncodingException a) {
      System.out.print("chinese thansform exception");
    }
    return s;
  }

  public static String chiDeUTF8(String str)
  {
    String s = "";
    try {
      s = new String(URLDecoder.decode(str, "UTF-8"));

      byte[] bytes = s.getBytes("ISO-8859-1");
      s = new String(bytes, "UTF-8");
    }
    catch (UnsupportedEncodingException a) {
      System.out.print("chinese thansform exception");
    }
    return s;
  }

  public static boolean judgeMatch(String strSource, String strRule)
  {
    int i = 0;

    if ((null == strSource) || (strSource.length() == 0)) {
      return false;
    }
    if ((null == strRule) || (strRule.length() == 0)) {
      return false;
    }
    if (strSource.length() > strRule.length()) {
      return false;
    }
    for (i = 0; i < strRule.length(); ++i)
    {
      if (strSource.length() < i + 1) {
        break;
      }
      if ((strRule.charAt(i) != '*') && (strSource.charAt(i) != strRule.charAt(i)))
      {
        return false;
      }
    }

    for (; i < strRule.length(); ++i) {
      if (strRule.charAt(i) != '*')
        return false;
    }
    return true;
  }

  public static String getFullChnStr(String strSource)
  {
    if ((null == strSource) || (strSource.length() == 0)) {
      return "";
    }
    if (strSource.length() == 1)
    {
      if (strSource.charAt(0) > '')
      {
        return "";
      }
      return strSource;
    }
    if ((strSource.charAt(strSource.length() - 2) <= '') && (strSource.charAt(strSource.length() - 1) > '')) {
      return strSource.substring(0, strSource.length() - 1);
    }

    boolean prechn = false;
    String returnstr = "";
    for (int i = 0; i < strSource.length(); ++i)
    {
      if (strSource.charAt(i) <= '')
      {
        if (prechn)
        {
          returnstr = returnstr.substring(0, returnstr.length() - 1);
        }

        returnstr = returnstr + strSource.charAt(i);
        prechn = false;
      }
      else {
        returnstr = returnstr + strSource.charAt(i);
        if (prechn)
        {
          prechn = false;
        }
        else prechn = true;
      }
    }

    if (prechn)
    {
      returnstr = returnstr.substring(0, returnstr.length() - 1);
    }
    return returnstr;
  }

  public static String filerQuery(String qryStr)
  {
    int comma = 0;
    int len = 0;
    String Str = "";

    comma = qryStr.indexOf("'");
    len = qryStr.length();

    if (qryStr == "") {
      Str = "";
    }
    else if (comma > 0)
      Str = qryStr.substring(0, comma) + "''" + qryStr.substring(comma + 1, len);
    else if (comma == 0)
      Str = "''";
    else if (comma < 0) {
      Str = qryStr;
    }

    return Str;
  }

  public static String appendStr(String str)
  {
    int strlength = 0;
    int adddot = 0;
    int tmpstrlength = 0;
    int callength = 0;
    int plusdot = 0;
    String firststr = "";
    String endstr = "";
    String returnStr = "";
    String tmpfirststr = "";
    String tmpendstr = "";
    boolean isNegative = false;

    if (str.startsWith("-")) {
      isNegative = true;
    }
    tmpstrlength = str.indexOf(".");
    if (isNegative)
      firststr = str.substring(1, tmpstrlength);
    else {
      firststr = str.substring(0, tmpstrlength);
    }

    endstr = str.substring(tmpstrlength);
    strlength = firststr.length();
    adddot = strlength / 3;
    plusdot = strlength % 3;
    if (plusdot == 0)
    {
      adddot -= 1;
    }
    for (int i = 0; i < adddot; ++i)
    {
      callength = firststr.length();
      tmpfirststr = firststr.substring(0, callength - (4 * i + 3));
      tmpendstr = firststr.substring(callength - (4 * i + 3));
      firststr = tmpfirststr + "," + tmpendstr;
    }

    if (isNegative)
      returnStr = "-" + firststr + endstr;
    else {
      returnStr = firststr + endstr;
    }
    return returnStr;
  }

  public static boolean CompareString(String str1, String str2)
  {
    if (str1.equals(str2))
    {
      return true;
    }
    if ((str1.endsWith("\r\n")) && (str1.length() > 2))
    {
      str1 = str1.substring(0, str1.length() - 2);
    }
    if ((str2.endsWith("\r\n")) && (str2.length() > 2))
    {
      str2 = str2.substring(0, str2.length() - 2);
    }

    return (str1.equals(str2));
  }

  public static String SepFormatStr(String SourceStr, int maxlength, String separator)
  {
    if (SourceStr == null) return null;
    SourceStr = SourceStr.replaceAll("\r", "");
    SourceStr = SourceStr.replaceAll("\n", "");
    SourceStr = SourceStr.replaceAll(separator, "");

    boolean cnflag = false;

    boolean end = false;

    StringBuffer resultsb = new StringBuffer();

    StringBuffer tempsb = new StringBuffer();

    String tempstr = "";
    for (int i = 0; i < SourceStr.length(); ++i)
    {
      char Cindex = SourceStr.charAt(i);

      if (Cindex > '')
      {
        if (cnflag)
        {
          if (tempsb.length() >= maxlength - 1)
          {
            if (resultsb.length() > 0)
            {
              resultsb.append(separator);
            }
            resultsb.append(tempsb);
            tempsb.delete(0, tempsb.length());
          }

          tempstr = SourceStr.substring(i - 1, i + 1);

          tempsb.append(tempstr);
          cnflag = false;
        } else {
          cnflag = true;
        }
      }
      else {
        if (cnflag)
        {
          cnflag = false;
        }

        tempsb.append(Cindex);
      }

      if (i == SourceStr.length() - 1) end = true;

      if ((tempsb.length() <= 0) || ((tempsb.length() < maxlength) && (!(end)))) {
        continue;
      }
      if (resultsb.length() > 0)
      {
        resultsb.append(separator);
      }
      resultsb.append(tempsb);
      tempsb.delete(0, tempsb.length());
      end = false;
    }

    return resultsb.toString();
  }

  public static String exchangeName(String strTmp)
  {
    String ori = strTmp.trim();
    if (ori != null)
    {
      if ((ori.length() == 4) || (ori.length() == 6))
      {
        if (ori.length() == 4)
        {
          ori = ori.substring(2, 4) + ori.substring(0, 2);
        }
        else
        {
          ori = ori.substring(2, 6) + ori.substring(0, 2);
        }

      }
      else
      {
        return ori;
      }
    }
    return ori;
  }

  public static String isExistAcc(String strIn)
  {
    strIn = strIn.replaceAll("\r\n", "\n");
    strIn = strIn.replaceAll("\n", "");

    String strAccRet = "";
    int iCount = 0;
    if ((strIn != null) && (strIn.length() > 0))
    {
      StringBuffer buff = new StringBuffer(strIn);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if ((ch >= '0') && (ch <= '9'))
        {
          ++iCount;
          strAccRet = strAccRet + String.valueOf(ch);
        }
        else
        {
          if (iCount > 15)
            break;
          iCount = 0;
          strAccRet = "";
        }
      }
    }

    if (iCount > 15) {
      return strAccRet;
    }
    return "";
  }

  public static boolean judgeAllNumberic(String strIn)
  {
    strIn = strIn.replaceAll("\r\n", "\n");
    strIn = strIn.replaceAll("\n", "");
    strIn = strIn.replaceAll(" ", "");

    if ((strIn != null) && (strIn.length() > 0))
    {
      StringBuffer buff = new StringBuffer(strIn);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if ((ch < '0') || (ch > '9'))
        {
          return false;
        }
      }
    }
    else {
      return false; }
    return true;
  }

  public static boolean judgeAllChn(String strIn)
  {
    strIn = strIn.replaceAll("\r\n", "\n");
    strIn = strIn.replaceAll("\n", "");
    strIn = strIn.replaceAll(" ", "");

    if ((strIn != null) && (strIn.length() > 0))
    {
      StringBuffer buff = new StringBuffer(strIn);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if (ch <= 0) continue; if (ch < '~')
        {
          return false; }
      }
      return true;
    }

    return false;
  }

  public static boolean judgeChnCode(String strIn)
  {
    strIn = strIn.replaceAll("\r\n", "\n");
    strIn = strIn.replaceAll("\n", "");
    strIn = strIn.replaceAll(" ", "");

    if ((strIn != null) && (strIn.length() > 0))
    {
      StringBuffer buff = new StringBuffer(strIn);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if (((ch >= '0') && (ch <= '9')) || (ch <= 0)) continue; if (ch < '~')
        {
          return false;
        }
      }
    }
    else {
      return false; }
    return true;
  }

  public static boolean judgeExistChn(String strIn)
  {
    if (judgeChnCode(strIn))
    {
      return true;
    }

    strIn = strIn.replaceAll("\r\n", "\n");
    strIn = strIn.replaceAll("\n", "");
    strIn = strIn.replaceAll(" ", "");

    if ((strIn != null) && (strIn.length() > 0))
    {
      StringBuffer buff = new StringBuffer(strIn);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if ((ch <= 0) || (ch >= '~'))
        {
          return true;
        }

      }

      return false;
    }

    return false;
  }

  public static boolean judgeExistNumber(String strIn)
  {
    if (judgeChnCode(strIn))
    {
      return true;
    }

    strIn = strIn.replaceAll("\r\n", "\n");
    strIn = strIn.replaceAll("\n", "");
    strIn = strIn.replaceAll(" ", "");

    if ((strIn != null) && (strIn.length() > 0))
    {
      StringBuffer buff = new StringBuffer(strIn);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if ((ch >= '0') && (ch <= '9'))
        {
          return true;
        }

      }

      return false;
    }

    return false;
  }

  public static boolean chkString(String existStr, String value)
  {
    Pattern pattern = Pattern.compile(existStr);
    Matcher matcher = pattern.matcher(value);
    boolean didMatch = matcher.matches();
    return didMatch;
  }

  public static boolean isExist_Str(String regexp, String mathStr)
  {
    boolean result = false;
    Pattern pattern = Pattern.compile(regexp);
    Matcher matcher = pattern.matcher(mathStr);
    result = matcher.find();
    return result;
  }

  public static String getSeqNumber(String numStr)
  {
    numStr = numStr.replaceAll("\r\n", "\n");
    numStr = numStr.replaceAll("\n", "");

    String seqNumRet = "";
    int iCount = 0;
    if ((numStr != null) && (numStr.length() > 0))
    {
      StringBuffer buff = new StringBuffer(numStr);
      int len = buff.length();
      for (int i = 0; i < len; ++i)
      {
        char ch = buff.charAt(i);
        if ((ch >= '0') && (ch <= '9'))
        {
          ++iCount;
          seqNumRet = seqNumRet + String.valueOf(ch);
        }
        else
        {
          if (iCount > 10) { if (ch < '0') break; if (ch > '9') break;
          }
          iCount = 0;
          seqNumRet = "";
        }
      }

    }

    return seqNumRet;
  }

  public static double round(double v, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException("参数scale必须是大于0的整数");
    }

    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, 4).doubleValue();
  }

  public static String rfillCharater(String str, char ch, int len)
  {
    StringBuffer fillStr = new StringBuffer(str);
    int fillLen = len - fillStr.length();

    for (int i = 0; i < fillLen; ++i) {
      fillStr.append(ch);
    }

    return fillStr.toString();
  }

  public static String lfillCharater(String str, char ch, int len)
  {
    StringBuffer fillStr = new StringBuffer();
    int fillLen = len - str.length();

    for (int i = 0; i < fillLen; ++i) {
      fillStr.append(ch);
    }
    fillStr.append(str);

    return fillStr.toString();
  }

  public static boolean propRule(String regexPattern, String value)
  {
    Pattern pattern = Pattern.compile(regexPattern);
    Matcher matcher = pattern.matcher(value);
    boolean didMatch = matcher.matches();
    return didMatch;
  }

  public static boolean isExistStr(String str, String[] strarray)
  {
    if ((strarray == null) || (strarray.length <= 0)) return false;
    for (int i = 0; i < strarray.length; ++i)
    {
      if (strarray[i] == null)
      {
        if (str == null) return true;

      }
      else if (str.indexOf(strarray[i].trim()) != -1)
      {
        return true;
      }
    }
    return false;
  }

  public static String codeToLines(String str)
  {
    String strTemp = "";
    if (str == null) {
      return strTemp;
    }
    while (str.length() > 19) {
      strTemp = strTemp + "\n" + str.substring(0, 19);
      if (' ' == str.charAt(19)) {
        str = str.substring(20);
      }
      str = str.substring(19);
    }

    strTemp = strTemp + "\n" + str;
    strTemp = strTemp.substring(1);
    return strTemp;
  }

  public static String nameToLines(String str)
  {
    String strTemp = "";
    if (str == null) {
      return strTemp;
    }
    while (str.length() > 8) {
      strTemp = strTemp + "<br>" + str.substring(0, 8);
      str = str.substring(8);
    }
    strTemp = strTemp + "<br>" + str;
    strTemp = strTemp.substring(4);
    return strTemp;
  }

  public static String lowerToUppser(String str) {
    str = str.trim();
    String strTemp = "";

    if ((str == null) || (str.trim().length() == 0)) {
      return strTemp;
    }
    for (int i = 0; i < str.length(); ++i) {
      char a = str.charAt(i);
      if ((a >= 'a') && (a <= 'z')) {
        a = (char)(a - ' ');
      }
      strTemp = strTemp + a;
    }
    return strTemp;
  }

  public static String getDomain(String str)
  {
    String strTemp = "";
    if (str == null) {
      return strTemp;
    }
    int i = str.indexOf("http://");
    if (i >= 0)
    {
      strTemp = str.substring(i + 7);
    }
    i = str.indexOf("www.");
    if (i >= 0)
    {
      strTemp = strTemp.substring(i + 4);
    }
    i = str.indexOf(47);
    if (i >= 0)
    {
      strTemp = strTemp.substring(0, i);
    }
    return strTemp;
  }

  public static boolean isExist(String str)
  {
    return ((str != null) && (str.length() > 0));
  }

  public static boolean isNumber(String str)
  {
    return str.matches("^\\d+(\\,\\d+)*$");
  }

  public static boolean isValidDate(String dateStr)
  {
    boolean isValid = false;
    if ((dateStr == null) || (dateStr.length() <= 0)) {
      return false;
    }
    String pattern = "yyyy-MM-dd";
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      String date = sdf.format(sdf.parse(dateStr));
      if (date.equalsIgnoreCase(dateStr))
        isValid = true;
    }
    catch (Exception e) {
      isValid = false;
    }
    return isValid; }

  public static String joinStr(String[] array, String joinChar) {
    if (array == null)
      return "";
    if (joinChar == null) joinChar = ",";
    String str = "";
    for (int i = 0; i < array.length; ++i) {
      str = str + array[i];
      if (i >= array.length - 1) continue; str = str + joinChar;
    }
    return str;
  }
  
  /**
   * 对日期进行格式化
   * @param d
   * @param f
   * @return
   */
  public static String formatDate(Date d,String f)
  {
	  SimpleDateFormat sdf = new SimpleDateFormat(f);
	  return sdf.format(d);
  }
  
  /**
   * md5加密
   * @param password
   * @return
   */
  public static String makeMD5(String password) {   
	  MessageDigest md;
	  try {   
		  // 生成一个MD5加密计算摘要   
		  md = MessageDigest.getInstance("MD5");   
		  // 计算md5函数   
		  md.update(password.getBytes());
		  // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符   
		  // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
		  String pwd = new BigInteger(1, md.digest()).toString(16);   
		  return pwd;   
	  } catch (Exception e) {   
		  e.printStackTrace();   
	  }
	  return password;   
  }
}