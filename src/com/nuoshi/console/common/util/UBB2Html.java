package com.nuoshi.console.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nuoshi.console.common.Globals;

/**
 * Created by IntelliJ IDEA.
 * User: ERIC SUN
 * Date: 2010-4-21
 * Time: 10:51:03
 * To change this template use File | Settings | File Templates.
 */
public class UBB2Html //类定义
{
@SuppressWarnings("unused")
private String source; //待转化的HTML代码字符串
private static String ubbTags[] = {"\\[b\\]","\\[/b\\]","\\[p\\]","\\[/p\\]","\\[br\\]","\\[color=([^\\]]*)\\]","\\[/color\\]","\\[ft=([^\\]]*)\\]","\\[/ft\\]","\\[url=([^\\]]*)\\]","\\[/url\\]","\\[img\\]","\\[/img\\]","\\[i\\]","\\[/i\\]","\\[u\\]","\\[/u\\]","\\[flash\\]","\\[/flash\\]","\\[br\\s/\\]"}; //UBB标记数组
private static String htmlTags[] = {"<b>","</b>","<p>","</p>","<br>","<span style=\"color:$1\">","</span>","<span style=\"font-size:$1\">","</span>","<a target=\"_blank\" href=\"$1\">","</a>","<img style=\"border:none;\" src=\"","\" />","<em>","</em>","<u>","</u>","<embed src=\"","\" pluginspage=\" http://www.macromedia.com/go/getflashplayer \" type=\"application/x-shockwave-flash\" wmode=\"transparent\" quality=\"high\" width=\"500\" height=\"413\" />",""}; //HTML标记数组

private static String houseFilter[] = {"\\[url=([^\\]]*)\\]","\\[/url\\]","\\[img\\][^\\[]*\\[/img\\]","\\[i\\]","\\[/i\\]","\\[u\\]","\\[/u\\]","\\[flash\\]","\\[/flash\\]","Normal.*?\\}"};
private static String forumFilter[] = {};
public enum Filter{HOUSE_EXT, FORUM};//过滤器类型
//初始化,分别为UBB标记数组和HTML标记数组赋值


//private static String originString[] = {"(http://taofang\\.com/\\[\\w|\\-|\\?|=|&\\]*)"};
//private static String originString[] = {"(http://broker\\.com[-/\\w]*\\.html)"};
private static String originString[] = {"(" + Globals.mainContext + "[-/\\w]*\\.html)"};
private static String newString[] = {"<a target=\"_blank\" href=\"$1\">$1</a>"};

public enum SpecialReplace {
    HOUSE_EXT_INNERURL(0);//房源编辑器，内部链接直接跳转
    private final int value;

    SpecialReplace(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }       
};

public UBB2Html()
{
//byte byte0 = 74;
//source = new String();
//ubbTags = new String[7];
//htmlTags = new String[7];
//ubbTags[0] = "[b]";
//htmlTags[0] = "<b>";                                                                                  
//ubbTags[1] = "[/b]";
//htmlTags[1] = "</b>";
//ubbTags[2] = "[p]";
//htmlTags[2] = "<p>";
//ubbTags[3] = "[/p]";
//htmlTags[3] = "</p>";
//ubbTags[4] = "[br]";
//htmlTags[4] = "<br>";
//ubbTags[5] = "[img]";
//htmlTags[5] = "<img src=\"";
//ubbTags[6] = "[/img]";
//ubbTags[6] = "/>";
}

    private static String replace(String s, String s1, String s2) {
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
//            switch(c){
//            case 91: // '['
//            stringbuffer.append("\\[");
//            break;
//
//            case 93: // ']'
//             stringbuffer.append("\\]");
//             break;
//
//            default:
//             stringbuffer.append(c);
//             break;
//            }
            stringbuffer.append(c);
        }

        Pattern pattern = Pattern.compile(stringbuffer.toString());
        Matcher matcher = pattern.matcher(s);
        StringBuffer stringbuffer1 = new StringBuffer();
        for(boolean flag = matcher.find(); flag; flag = matcher.find())
        matcher.appendReplacement(stringbuffer1, s2);
        
        return matcher.appendTail(stringbuffer1).toString();
    }

    public static String replaceNormalUBBCode(String s)
    {
        String s1 = new String(s);
        for(int i = 0; i < ubbTags.length; i++)
        s1 = replace(s1, ubbTags[i], htmlTags[i]);
        return s1;
//        return s;
    }

    public static String replaceSpecialCode(String s, SpecialReplace[] sr){
        String s1 = new String(s);
        for(SpecialReplace r:sr){
             s1 = replace(s1, originString[r.getValue()], newString[r.getValue()]);
        }
        return s1;
    }

    public static String filtUBBCode(String s, Filter f){
        String s1 = new String(s);
        List<String> f1 = new ArrayList<String>();
        switch(f){
            case HOUSE_EXT :
                for(int i = 0; i < houseFilter.length; i++){
                    f1.add(houseFilter[i]);
                }
                break;
            case FORUM :
                for(int i = 0; i < forumFilter.length; i++){
                    f1.add(forumFilter[i]);
                }
                break;
        }
        for(int i = 0; i < f1.size(); i++)
        s1 = replace(s1, f1.get(i), "");
        return s1;
    }

}
