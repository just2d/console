package com.nuoshi.console.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Author: CHEN Liang <alinous@gmail.com>
 * Date: 2009-9-3
 * Time: 11:35:04
 */
public class Utilities {

    public static int timeFlag = 1;

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static java.sql.Date getCurrentDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static String join(byte[] ids, String seperator) {
        int cur = 1;
        int cnt = ids.length;
        StringBuffer sb = new StringBuffer();
        for (long id : ids) {
            sb.append(id);
            if (cur < cnt) {
                sb.append(seperator);
            }
            ++cur;
        }
        return sb.toString();
    }

    public static String join(long[] ids, String seperator) {
        int cur = 1;
        int cnt = ids.length;
        StringBuffer sb = new StringBuffer();
        for (long id : ids) {
            sb.append(id);
            if (cur < cnt) {
                sb.append(seperator);
            }
            ++cur;
        }
        return sb.toString();
    }

    public static String join(ArrayList<String> l, String seperator) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = l.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(seperator);
            }
        }
        return sb.toString();
    }


    public static Calendar getDate(Calendar when) {
        Calendar cl = (Calendar) when.clone();

        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH);
        int date = cl.get(Calendar.DATE);

        cl.clear();
        cl.set(year, month, date);
        return cl;
    }

    public static Calendar getDate(Date when) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(when);
        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH);
        int date = cl.get(Calendar.DATE);

        cl.clear();
        cl.set(year, month, date);
        return cl;
    }

    public static Calendar getToday() {
        return getDate(new Date());
    }
    public static Calendar getTomorrow() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DATE, 1);

        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH);
        int date = cl.get(Calendar.DATE);
        cl.clear();
        cl.set(year, month, date);
        return cl;
    }

    public static Calendar getThisWeekFirstDay() {
        Calendar cl = Calendar.getInstance();

        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH);
        int date = cl.get(Calendar.DATE);
        cl.clear();
        cl.set(year, month, date);
        cl.add(Calendar.DAY_OF_WEEK, -cl.get(Calendar.DAY_OF_WEEK) + 1);
        return cl;

    }

    public static Calendar getThisMonthFirstDay() {
        Calendar cl = getThisWeekFirstDay();
        cl.add(Calendar.DAY_OF_MONTH, -cl.get(Calendar.DAY_OF_MONTH) + 1);
        return cl;
    }

    public static Calendar getSixMonthBefore() {
        Calendar cl = getThisMonthFirstDay();
        cl.add(Calendar.MONTH, -5);
        return cl;
    }

    public static Calendar getOneMonthBefore() {
        Calendar cl = Calendar.getInstance();
        return getNMonthesBefore(cl, 1);
    }


    public static Calendar getOneMonthLater() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MONTH, 1);
        return cl;
    }

    public static Calendar getNMonthesBefore(Calendar now, int n) {
        now.add(Calendar.MONTH, -n);
        return now;
    }

    public static Calendar getNMonthesLater(Calendar now, int n) {
        now.add(Calendar.MONTH, n);
        return now;
    }

    public static Calendar getOneDayBefore() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, -1);
        return cl;
    }

    public static Calendar getOneWeekBefore() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.WEEK_OF_YEAR, -1);
        return cl;
    }

    public static Calendar getOneDayAfter() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, 1);
        return cl;
    }

    public static Calendar getOneMinBefore() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MINUTE, -1);
        return cl;
    }


    public static int setOneFlag(int flags, int mask, boolean type) {
        if (type) {
            flags = flags | mask;
        } else {
            flags = flags & (~mask);
        }
        return flags;
    }

    public static ClassLoader getTCL() throws IllegalAccessException,
            InvocationTargetException {

        // Are we running on a JDK 1.2 or later system?
        Method method = null;
        try {
            method = Thread.class.getMethod("getContextClassLoader");
        } catch (NoSuchMethodException e) {
            // We are running on JDK 1.1
            return null;
        }

        return (ClassLoader) method.invoke(Thread.currentThread());
    }

    public static int parseIdFromString(String prefix, String key) {
        if (prefix == null || key == null) {
            return 0;
        }
        String sid = key.replace(prefix, "");

        if (sid == null) {
            return 0;
        }

        try {
            return Integer.parseInt(sid);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static String getRealIp( HttpServletRequest request) {
        String realIp = request.getHeader("X-Real-IP");
        if (realIp == null || realIp.length() == 0) {
            realIp = request.getRemoteAddr();
        }

        return realIp;
    }

    /**
     * 取得一个 1-n 之间的随机值
     *
     * @return
     */
    public static byte getRandomNumber(int n) {
        Random ran = new Random();
        return (byte) ran.nextInt(n);
    }

    public static void transfer(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[102400];
        int len;
        while ((len = input.read(buffer)) >= 0) {
            output.write(buffer, 0, len);
        }
    }

    public static byte[] readStream(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        transfer(input, output);
        return output.toByteArray();
    }

    private static final DateFormat DefaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat ShortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDefaultDate(String s) {
        synchronized (DefaultDateFormat) {
            try {
                return DefaultDateFormat.parse(s.trim());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String formatDefaultDate(Date d) {
        synchronized (DefaultDateFormat) {
            return DefaultDateFormat.format(d);
        }
    }

    public static Date parseShortDate(String s) {
        synchronized (ShortDateFormat) {
            try {
                return ShortDateFormat.parse(s.trim());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String formatShortDate(Date d) {
        synchronized (ShortDateFormat) {
            return ShortDateFormat.format(d);
        }
    }

    public static boolean isIdentityCardValid(String number) {
        if (number != null && (number.length() == 15 || number.length() == 18)) {
            if (number.matches("^\\d*.?$")) {
                if (number.length() == 18) {
                    char last = number.charAt(17);
                    char c = getIDCardCheckDigit(number);
                    return last == c || (last == 'x' && c == 'X');
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private static final int[] IDDWeight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    private static final char[] IDCheckDigit = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    protected static char getIDCardCheckDigit(String eighteenCardID) {
        assert (eighteenCardID.length() == 18);
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            char k = eighteenCardID.charAt(i);
            int a = (k - '0');
            sum += IDDWeight[i] * a;
        }
        return IDCheckDigit[sum % 11];
    }

    public static Cookie findCookie(HttpServletRequest request,String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if(name.equalsIgnoreCase(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static <T> List<T> randomSelect(List<T> list,int top) {
        if(list==null) {
            return list;
        }
        int size = list.size();
        if(size <=top) {
            return list;
        } else {
            List<T> result=new ArrayList<T>(list);
            Random random=new Random(System.currentTimeMillis());
            while((size=result.size())>top) {
                result.remove(random.nextInt(size));
            }
            return result;
        }
    }

    public static String replaceHtmlSimple(String content) {
        if(content!=null) {
            content=content.replaceAll("<.*?>","");
        }
        return content;
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object object,Class<T> klass) {
        if(object==null) {
            return null;
        }
        if(Integer.TYPE.equals(klass) || Integer.class.equals(klass)) {
            Number number=null;
            if(object instanceof Number) {
                number=(Number)object;
            } else {
                String s=object.toString().trim();
                if(s.length()>0) {
                    number=Double.valueOf(s);
                }
            }
            if(number!=null) {
                return (T)Integer.valueOf(number.intValue());
            }
        }
        return null;
    }

    private static final DateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
    public static Date trimDate(Date date) {
        try {
            synchronized(format1) {
                return format1.parse(format1.format(date));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把一个list转换为map
     *
     * @param list
     * @param prop
     * @param <U>
     * @param <V>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <U,V> Map<U,V> list2Map(List<V> list,String prop) {
        Map<U,V> map=new HashMap<U,V>();
        try {
            for (V v : list) {
                map.put((U) PropertyUtils.getProperty(v,prop),v);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
    
    public static boolean isStringNotNull(String str){
    	if(str != null){
    		if(!"".equals(str)){
    			if(!"null".equalsIgnoreCase(str)){
        			return true;
    			}
    		}
    	}
    	return false;
    }

    public static String splitMobile(String mobile)
    {
    	String tmp1 = fillString(mobile, 3, 1, ' ');
    	return fillString(tmp1, 8, 1, ' ');
    }
    
    public static String fillString(String src,int index,int count,char fillChar)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append(src.substring(0, index));
    	for(int i=1;i<=count;i++)
    	{
    		sb.append(fillChar);
    	}
    	if(index!=src.length())
    	{
    		sb.append(src.substring(index));
    	}
    	
    	return sb.toString();
    }
    
    public static int compareFloat(Float a1 ,Float a2)
    {
    	a1 = a1==null?0:a1;
    	a2 = a2==null?0:a2;
    	return a1.compareTo(a2);
    }
    
    public static boolean isNotEmptyString(String str){
    	return str!=null&&(!"".equals(str));
    }
    
    public static String getTimestampTillNow(long dateTimeStamp) {
		if (dateTimeStamp > 0) {
			long t = (System.currentTimeMillis() - dateTimeStamp * 1000) / 1000; // 换算为秒
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
				return String.valueOf(days) + "天前";
			}
		}
		return "未知";
	}
}