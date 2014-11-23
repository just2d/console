package com.nuoshi.console.common.util;
import java.util.regex.Pattern;

public class Escape {
	public static String banned = ".*(5q|baodoo|dipian|5jia1|yeejee|faceben|校内网).*";
	public static String filter = ".*faceben.*";

    //页面输出的时候换行转换
    public static String parse2Pages(String str){
		if (null == str) {
			return null;
		}
        return str.replaceAll("\n","<br>");
    }


    public static String parse2DB(String str){
        if(null == str){
            return null;
        }
        return str.replaceAll("<br />","\n");
    }



	/**
	 * 上载后再编辑就会有问题，显示不出来
	 * @param string
	 * @return
	 */
	public static String stringToHTMLString(String string) {
		if (string == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(string.length());
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = string.charAt(i);
			switch (c) {
			case '<': sb.append("&lt;"); break;
			case '>': sb.append("&gt;"); break;
			case '&': sb.append("&amp;"); break;
			case '#': sb.append("&#35;"); break;
//			case '"': sb.append("&quot;"); break;
			// be carefull with this one (non-breaking whitee space)
//			case ' ': sb.append("&nbsp;");break;
			case '(': sb.append("&#40;"); break;
			case ')': sb.append("&#41;"); break;
			case '"': sb.append("&#34;"); break;
			case '\'': sb.append("&#39;"); break;            
			default:  sb.append(c); break;
			}
		}
		
		return sb.toString();
    }
 
	public static String stringToHtml(String str)  
	{  
	    if(str==null) return "";  
	    if(str.equals("")) return "";  
	    str = str.replaceAll("&amp;", "&");  
	    str = str.replaceAll("&lt;", "<");  
	    str = str.replaceAll("&gt;", ">");  
	    str = str.replaceAll("&gt;", ">");  
	    str = str.replaceAll("&quot;", "\"");  
	    str = str.replaceAll("&nbsp;", " ");  
	    return str;  
	}  
	
	/**
	 * can not be used till now
	 * @param string
	 * @return
	 */
	public static String escapeText(String string) {
		if (string == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(string.length());
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = string.charAt(i);
			switch (c) {
			case '<': 
				int pos0 = i;
				if (string.startsWith("<a href=\"", pos0)) {
					int pos1 = string.indexOf("\">", pos0);
					if (pos1 < pos0) {
						sb.append("&lt;");
						break;
					}
					int pos2 = string.indexOf("</a>", pos1 + 2);
					if (pos2 < pos1) {
						sb.append("&lt;");
						break;
					}
					String url = string.substring(pos0+9, pos1);
					url = purgeWhiteSpace(url);
					/** embed can not insert into other to attack, it is only used in the way of <embed>
					 *  and both javascript/vbscript can lead to attack */
//					url = Pattern.compile("javascript|embed", Pattern.CASE_INSENSITIVE).matcher(url).replaceAll("");
					url = Pattern.compile("script", Pattern.CASE_INSENSITIVE).matcher(url).replaceAll("");
					String disp = string.substring(pos1 + 2, pos2);
					/** how about escape it? */
//					url = escapeText(url);
					url = stringToHTMLString(url);
					disp = escapeText(disp);
					sb.append("<a href=\""+url+"\">"+disp+"</a>");
					i = pos2 + 3;
				} else if (string.startsWith("<img src=\"", pos0)) {
					int pos1 = string.indexOf("\" />", pos0);
					if (pos1 < pos0) {
						sb.append("&lt;");
						break;
					}
					String url = string.substring(pos0+10, pos1);
					url = purgeWhiteSpace(url);
//					url = Pattern.compile("javascript|embed", Pattern.CASE_INSENSITIVE).matcher(url).replaceAll("");
					/** embed can not insert into other to attack, it is only used in the way of <embed> 
					 *  and both javascript/vbscript can lead to attack */
					url = Pattern.compile("script", Pattern.CASE_INSENSITIVE).matcher(url).replaceAll("");
					/** how about escape it? */
//					url = escapeText(url);
					url = stringToHTMLString(url);
					sb.append("<img src=\""+url+"\" />");
					i = pos1 + 3; 
				} else {
					sb.append("&lt;");
				}
				break;
			case '>': sb.append("&gt;"); break;
			case '&': sb.append("&amp;"); break;
			case '#': sb.append("&#35;"); break;
			case '(': sb.append("&#40;"); break;
			case ')': sb.append("&#41;"); break;
			case '"': sb.append("&#34;"); break;
			case '\'': sb.append("&#39;"); break;
			default:  sb.append(c); break;
			}
		}
		return sb.toString();
    }
	
	static final String[] script = {"onabort", "onblur", "onchange", "onclick", "ondblclick", "onerror", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onload", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onreset", "onresize", "onselect", "onsubmit", "onunload", "script", "frame", "applet", "object", "form", "&", "meta", "location"};
	
	public static String checkHtml(final String str) {
		if (str == null) {
			return null;
		}
		
		String st = str.toLowerCase();
		for (int i = 0; i < script.length; i++) {
			if (st.indexOf(script[i]) > -1) {
				return script[i]; 
			}
		}
		return null;
	}
	
	public static String trim(final String str) {
		if (str == null) {
			return null;
		}
		
		String st = str.trim();
		while (st.length() > 0 && st.charAt(0) == '　') {
			st = st.substring(1);
		}
		while (st.length() > 0 && st.charAt(st.length() - 1) == '　') {
			st = st.substring(0, st.length() - 1);
		}
		return st;
	}
	
	public static String purgeWhiteSpace(String st) {
		if (st == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(st.length());
		int len = st.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = st.charAt(i);
			if (!Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}