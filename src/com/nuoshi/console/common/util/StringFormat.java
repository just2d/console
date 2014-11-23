package com.nuoshi.console.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import com.nuoshi.console.common.Globals;

/**
 * This class contains a number of static methods that can be used to validate
 * the format of Strings, typically received as input from a user, and to format
 * values as Strings that can be used in HTML output without causing
 * interpretation problems.
 * 
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class StringFormat {
	// Static format objects
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();
	private static DecimalFormat numberFormat = new DecimalFormat();

	/**
	 * Returns true if the specified date string represents a valid date in the
	 * specified format, using the default Locale.
	 * 
	 * @param dateString
	 *            a String representing a date/time.
	 * @param dateFormatPattern
	 *            a String specifying the format to be used when parsing the
	 *            dateString. The pattern is expressed with the pattern letters
	 *            defined for the java.text.SimpleDateFormat class.
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidDate(String dateString,
			String dateFormatPattern) {
		Date validDate = null;
		synchronized (dateFormat) {
			try {
				dateFormat.applyPattern(dateFormatPattern);
				dateFormat.setLenient(false);
				validDate = dateFormat.parse(dateString);
			} catch (ParseException e) {
				// Ignore and return null
			}
		}
		return validDate != null;
	}

	/**
	 * Returns true if the specified number string represents a valid integer in
	 * the specified range, using the default Locale.
	 * 
	 * @param numberString
	 *            a String representing an integer
	 * @param min
	 *            the minimal value in the valid range
	 * @param min
	 *            the maximal value in the valid range
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidInteger(String numberString, int min, int max) {
		Integer validInteger = null;
		try {
			Number aNumber = numberFormat.parse(numberString);
			int anInt = aNumber.intValue();
			if (anInt >= min && anInt <= max) {
				validInteger = new Integer(anInt);
			}
		} catch (ParseException e) {
			// Ignore and return null
		}
		return validInteger != null;
	}

	public static boolean isValidInteger(String numberString) {
		try {
			Number aNumber = numberFormat.parse(numberString);
			return true;
		} catch (ParseException e) {
			// Ignore and return false
		}
		return false;
	}

	/**
	 * Returns true if the specified number string represents a valid cellphone
	 * number, using the default Locale.
	 * 
	 * @param cellPhone
	 *            a String representing an cellphone number
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidCellPhone(String cellPhone) {
		// Integer validInteger = null;
		// try {
		// Number aNumber = numberFormat.parse(cellPhone);
		// int anInt = aNumber.intValue();
		// //加入更多的判断以确定是否是一个合法的移动电话号码.
		// validInteger = new Integer(anInt);
		// }
		// catch (ParseException e) {
		// // Ignore and return null
		// }
		// return validInteger != null;
		if (cellPhone == null) {
			return false;
		}

		Pattern p1 = Pattern.compile("^\\d{11}$");
		Matcher m1 = p1.matcher(cellPhone);
		if (!m1.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isValidTransferPhone(String transferPhone) {
		if (transferPhone == null) {
			return false;
		}

		Pattern p1 = Pattern.compile("^\\d{4}$");
		Matcher m1 = p1.matcher(transferPhone);
		if (!m1.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if spcified string represents a valid identity code.
	 * 
	 * @param id
	 * @return
	 */

	public static boolean isValidIdentity(String id) {
		// String regex =
		// "^([\\d]{6}(19|20)[\\d]{2}((0[1-9])|(10|11|12))([012][\\d]|(30|31))[\\d]{3}[xX\\d])"
		// +
		// "|([\\d]{6}[\\d]{2}((0[1-9])|(10|11|12))([012][\\d]|(30|31))[\\d]{3})$";
		String regex = "^([\\d]{6}(19|20)[\\d]{2}((0[1-9])|(10|11|12))([012][\\d]|(30|31))[\\d]{3}[xX\\d])$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(id);
		if (!m.matches()) {
			return false;
		} else {
			return Utilities.isIdentityCardValid(id);
		}
	}

	/**
	 * Returns true if specified nick represents a valid usr nick
	 * 
	 * @param nick
	 * @return
	 */
	public static boolean isValidName(String nick) {

		// Pattern p1 = Pattern.compile("^[\\w\\u4e00-\\u9fa5]{2,12}");
		// Matcher m1 = p1.matcher(nick);
		// Pattern p2 = Pattern.compile("^\\d[\\w\\u4e00-\\u9fa5]{1,11}");
		// Matcher m2 = p2.matcher(nick);
		Pattern p1 = Pattern.compile("^[\\u4e00-\\u9fa5]{2,10}");
		Matcher m1 = p1.matcher(nick);
		// Pattern p2 = Pattern.compile("^\\d[\\w\\u4e00-\\u9fa5]{1,11}");
		// Matcher m2 = p2.matcher(nick);
		// can't leading with number
		if (!m1.matches()) {
			return false;
		}

		// only character number chinese underscore is valid
		// if(m2.matches()) {
		// return false;
		// }
		return true;
	}

	/**
	 * Returns true if the param string contains fix phone number.
	 * 
	 * @param s
	 * @return
	 */
	public static boolean containsFixPhone(String s) {
		Pattern p = Pattern.compile("^[2-9][0-9]{6,7}$");
		Matcher m = p.matcher(s);
		return m.find();
	}

	private static final String[] badwords = { "最佳", "极品", "唯一", "最高级", "国家级" };

	/**
	 * Returns bad words list which contains in the string.
	 * 
	 * @param in
	 * @return
	 */
	public static ArrayList<String> filterBadwords(String in) {
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < badwords.length; i++) {
			if (in.contains(badwords[i])) {
				words.add(badwords[i]);
			}
		}
		return words;
	}

	/**
	 * replace badwords with the replacement
	 * 
	 * @param in
	 * @return
	 */
	public static String replaceBadwords(String in) {
		String[] replacement = { "*", "**", "***" };
		String restr = in;
		for (int i = 0; i < badwords.length; i++) {
			if (restr.contains(badwords[i])) {
				restr = replaceInString(restr, badwords[i],
						replacement[badwords[i].length() - 1]);
			}
		}
		return restr;
	}

	/**
	 * Returns true if param string contains mobile number.
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean containsMobile(String cellphone) {
		Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[3,5,8][0-9]{9}");
		Matcher m1 = p1.matcher(cellphone);
		return m1.find();

	}

	/**
	 * Returns true if param string contains invalid words
	 * 
	 * @param
	 */

	/**
	 * Returns true if
	 * 
	 * @param nick
	 * @return
	 */
	public static boolean isValidNick(String nick) {
		Pattern p1 = Pattern.compile("^[a-zA-Z][\\w]{4,14}");
		Matcher m1 = p1.matcher(nick);

		return m1.matches();

	}

	/**
	 * Returns true if the specified url represents a valid url
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url) {
		int c;
		if (url == null || url.length() < 1 || url.length() > 30) {
			return false;
		}
		if (url.charAt(0) == 45) { // 减号
			return false;
		}
		if ("img".equalsIgnoreCase(url)) {
			return false;
		}
		if ("www".equalsIgnoreCase(url)) {
			return false;
		}
		if (url.startsWith("www.")) {
			return false;
		}
		for (int i = 0; i < url.length(); i++) {
			c = url.charAt(i);
			if (!(c >= 65 && c <= 90) && !(c >= 97 && c <= 122) && c != 45
					&& !(c >= 48 && c <= 57)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate the password.
	 * 
	 * @param passwd
	 * @return
	 */
	public static boolean isValidPassword(String passwd) {
		return !(passwd == null || passwd.length() < Globals.pwdMinLen || passwd
				.length() > Globals.pwdMaxLen);
	}

	/**
	 * Returns true if the specified number string represents a valid phone
	 * number, using the default Locale.
	 * 
	 * @param cellPhone
	 *            a String representing an phone number
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidOfficePhone(String cellPhone) {
		Integer validInteger = null;
		try {
			Number aNumber = numberFormat.parse(cellPhone);
			int anInt = aNumber.intValue();
			// 加入更多的判断以确定是否是一个合法的座机号码.
			validInteger = new Integer(anInt);
		} catch (ParseException e) {
			// Ignore and return null
		}
		return validInteger != null;
	}

	/**
	 * Returns true if the string is in the format of a valid SMTP mail address:
	 * only one at-sign, except as the first or last character, no white-space
	 * and at least one dot after the at-sign, except as the first or last
	 * character.
	 * <p/>
	 * Note! This rule is not always correct (e.g. on an intranet it may be okay
	 * with just a name) and it does not guarantee a valid Internet email
	 * address but it takes care of the most obvious SMTP mail address format
	 * errors.
	 * 
	 * @param mailAddr
	 *            a String representing an email address
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidEmailAddr(String mailAddr) {
		return StringFormat.isValidEmailAddr(mailAddr, true);
	}

	/**
	 * Returns true if the string is in the format of a valid SMTP mail address:
	 * only one at-sign, except as the first or last character, no white-space
	 * and at least one dot after the at-sign, except as the first or last
	 * character.
	 * <p/>
	 * Note! This rule is not always correct (e.g. on an intranet it may be okay
	 * with just a name) and it does not guarantee a valid Internet email
	 * address but it takes care of the most obvious SMTP mail address format
	 * errors.
	 * 
	 * @param mailAddr
	 * @param strict
	 * @return
	 */
	public static boolean isValidEmailAddr(String mailAddr, boolean strict) {
		if (mailAddr == null) {
			return false;
		}

		if (!strict) {
			if (mailAddr.length() < 1) {
				return true;
			}
		} else {
			if (mailAddr.length() < 1) {
				return false;
			}
		}

		if (mailAddr.length() > 50) {
			return false;
		}

		boolean isValid = true;
		mailAddr = mailAddr.trim();

		// Check at-sign and white-space usage
		int atSign = mailAddr.indexOf('@');
		if (atSign == -1 || atSign == 0 || atSign == mailAddr.length() - 1
				|| mailAddr.indexOf('@', atSign + 1) != -1
				|| mailAddr.indexOf(' ') != -1 || mailAddr.indexOf('\t') != -1
				|| mailAddr.indexOf('\n') != -1 || mailAddr.indexOf('\r') != -1) {
			isValid = false;
		}
		// Check dot usage
		if (isValid) {
			mailAddr = mailAddr.substring(atSign + 1);
			int dot = mailAddr.indexOf('.');
			if (dot == -1 || dot == 0 || dot == mailAddr.length() - 1) {
				isValid = false;
			}
		}
		return isValid;
	}

	/**
	 * Returns true if the specified string matches a string in the set of
	 * provided valid strings, ignoring case if specified.
	 * 
	 * @param value
	 *            the String validate
	 * @param validStrings
	 *            an array of valid Strings
	 * @param ignoreCase
	 *            if true, case is ignored when comparing the value to the set
	 *            of validStrings
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidString(String value, String[] validStrings,
			boolean ignoreCase) {
		boolean isValid = false;
		for (int i = 0; validStrings != null && i < validStrings.length; i++) {
			if (ignoreCase) {
				if (validStrings[i].equalsIgnoreCase(value)) {
					isValid = true;
					break;
				}
			} else {
				if (validStrings[i].equals(value)) {
					isValid = true;
					break;
				}
			}
		}
		return isValid;
	}

	/**
	 * Returns true if the strings in the specified array all match a string in
	 * the set of provided valid strings, ignoring case if specified.
	 * 
	 * @param values
	 *            the String[] validate
	 * @param validStrings
	 *            an array of valid Strings
	 * @param ignoreCase
	 *            if true, case is ignored when comparing the value to the set
	 *            of validStrings
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidString(String[] values, String[] validStrings,
			boolean ignoreCase) {

		if (values == null) {
			return false;
		}
		boolean isValid = true;
		for (int i = 0; values != null && i < values.length; i++) {
			if (!isValidString(values[i], validStrings, ignoreCase)) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

	/**
	 * Returns the specified string converted to a format suitable for HTML. All
	 * signle-quote, double-quote, greater-than, less-than and ampersand
	 * characters are replaces with their corresponding HTML Character Entity
	 * code.
	 * 
	 * @param in
	 *            the String to convert
	 * @return the converted String
	 */
	public static String toHTMLString(String in) {
		return toHTMLString(in, false);
	}

	public static String toHTMLString(String in, boolean decodeFirst) {
		if (in == null) {
			return "";
		}
		if (decodeFirst) {
			// 先解码
			in = StringEscapeUtils.unescapeHtml(in);
		}
		// " 替为了 &quot;
		// ' 用 replaceAll 替为了 &#039;
		// return StringEscapeUtils.escapeHtml(in).replaceAll("'","&#039;");
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#039;");
			} else if (c == '\"') {
				out.append("&#034;");
			} else if (c == '<') {
				out.append("&lt;");
			} else if (c == '>') {
				out.append("&gt;");
			} else if (c == '&') {
				out.append("&amp;");
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public static String htmlDecode(String in) {
		if (in == null) {
			return null;
		}
		return StringEscapeUtils.unescapeHtml(in);
	}

	/**
	 * Converts a String to a Date, using the specified pattern. (see
	 * java.text.SimpleDateFormat for pattern description) and the default
	 * Locale.
	 * 
	 * @param dateString
	 *            the String to convert
	 * @param dateFormatPattern
	 *            the pattern
	 * @return the corresponding Date
	 * @throws ParseException
	 *             , if the String doesn't match the pattern
	 */
	public static Date toDate(String dateString, String dateFormatPattern)
			throws ParseException {
		Date date = null;
		if (dateFormatPattern == null) {
			dateFormatPattern = "yyyy-MM-dd";
		}
		synchronized (dateFormat) {
			dateFormat.applyPattern(dateFormatPattern);
			dateFormat.setLenient(false);
			date = dateFormat.parse(dateString);
		}
		return date;
	}

	/**
	 * Converts a String to a Number, using the specified pattern. (see
	 * java.text.NumberFormat for pattern description) and the default Locale.
	 * 
	 * @param numString
	 *            the String to convert
	 * @param numFormatPattern
	 *            the pattern
	 * @return the corresponding Number
	 * @throws ParseException
	 *             , if the String doesn't match the pattern
	 */
	public static Number toNumber(String numString, String numFormatPattern)
			throws ParseException {
		Number number = null;
		if (numFormatPattern == null) {
			numFormatPattern = "######.##";
		}
		synchronized (numberFormat) {
			numberFormat.applyPattern(numFormatPattern);
			number = numberFormat.parse(numString);
		}
		return number;
	}

	/**
	 * Replaces one string with another throughout a source string.
	 * 
	 * @param in
	 *            the source String
	 * @param from
	 *            the sub String to replace
	 * @param to
	 *            the sub String to replace with
	 * @return a new String with all occurences of from replaced by to
	 */
	public static String replaceInString(String in, String from, String to) {
		if (in == null || from == null || to == null) {
			return in;
		}

		StringBuffer newValue = new StringBuffer();
		char[] inChars = in.toCharArray();
		int inLen = inChars.length;
		char[] fromChars = from.toCharArray();
		int fromLen = fromChars.length;

		for (int i = 0; i < inLen; i++) {
			if (inChars[i] == fromChars[0] && (i + fromLen) <= inLen) {
				boolean isEqual = true;
				for (int j = 1; j < fromLen; j++) {
					if (inChars[i + j] != fromChars[j]) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					newValue.append(to);
					i += fromLen - 1;
				} else {
					newValue.append(inChars[i]);
				}
			} else {
				newValue.append(inChars[i]);
			}
		}
		return newValue.toString();
	}

	public static boolean isRobot(String agent) {
		String allow_agent[] = { "Baiduspider", "Scooter", "ia_archiver",
				"Googlebot", "FAST-WebCrawler", "MSNBOT", "Slurp", "spider" };
		if (null == agent || agent.length() == 0) {
			return false;
		}
		for (int i = 0; i < allow_agent.length; i++) {
			if (agent.indexOf(allow_agent[i]) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a page-relative or context-relative path URI as a
	 * context-relative URI.
	 * 
	 * @param relURI
	 *            the page or context-relative URI
	 * @param currURI
	 *            the context-relative URI for the current request
	 * @throws IllegalArgumentException
	 *             if the relURI is invalid
	 */
	public static String toContextRelativeURI(String relURI, String currURI)
			throws IllegalArgumentException {

		if (relURI.startsWith("/")) {
			// Must already be context-relative
			return relURI;
		}

		String origRelURI = relURI;
		if (relURI.startsWith("./")) {
			// Remove current dir characters
			relURI = relURI.substring(2);
		}

		String currDir = currURI.substring(0, currURI.lastIndexOf("/") + 1);
		StringTokenizer currLevels = new StringTokenizer(currDir, "/");

		// Remove and count all parent dir characters
		int removeLevels = 0;
		while (relURI.startsWith("../")) {
			if (relURI.length() < 4) {
				throw new IllegalArgumentException("Invalid relative URI: "
						+ origRelURI);
			}
			relURI = relURI.substring(3);
			removeLevels++;
		}

		if (removeLevels > currLevels.countTokens()) {
			throw new IllegalArgumentException("Invalid relative URI: "
					+ origRelURI + " points outside the context");
		}
		int keepLevels = currLevels.countTokens() - removeLevels;
		StringBuffer newURI = new StringBuffer("/");
		for (int j = 0; j < keepLevels; j++) {
			newURI.append(currLevels.nextToken()).append("/");
		}
		return newURI.append(relURI).toString();
	}

	public static boolean isNotBlank(String str) {
		return (str != null && str.length() > 0);
	}

	public static String exception2String(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		t.printStackTrace(writer);
		writer.flush();
		return sw.toString();
	}
	
	public static boolean isValidateInnerCode(String innerCode){
		boolean result = true;
		if(innerCode == null || "".equals(innerCode)) {
			return true;
		}
		if(innerCode.length() > 10) {
			result = false;
		}
		Pattern p1 = Pattern.compile("^\\w+$");
		Matcher m1 = p1.matcher(innerCode);
		if (!m1.matches()) {
			result = false;
		}
		
		return result;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line;
		String pattern = "yyyy-MM-dd HH:mm:ss";
		@SuppressWarnings("unused")
		DateFormat format = new SimpleDateFormat(pattern);
		while ((line = reader.readLine()).trim().length() > 0) {
			line = line.trim();
			if (line.length() == pattern.length()) {
				//System.out.println(getDateTillNowString(format.parse(line)));
			}
		}
	}
	public static String getDateTillNowString(Date date) {
		if (date == null) {
			return "未知";
		}
		long t = (System.currentTimeMillis() - date.getTime()) / 1000; // 换算为秒
		// 小于五分钟
		if (t < 5 * 60) {
			return "刚刚";
		}
		// 五分钟至一小时
		else if (t < 3600) {
			return String.valueOf(t / 60) + "分钟前";
		}
		// 24小时
		else if (t < 86400) {
			return String.valueOf(t / 3600) + "小时前";
		}
		// 24-48小时
		else if (t < 172800) {
			return "昨天";
		}
		// 48小时以上
		else {
			int days = (int) t / 86400;
			if (days > 3) {
				days = 3;
			}
			// return String.valueOf(t/86400)+"天前";
			return String.valueOf(days) + "天前";
		}
	}

}
