package com.nuoshi.console.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChangePINYIN {
	public static  String getEstateName(String src) {
		if(src==null||src.trim().length()==0){
			return "";
		}
		src=src.toLowerCase();
		char[] t1 = null;
		t1 = src.toCharArray();
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		int num=0;
		for (int i = 0; i < t0; i++) {
			// 判断是否为汉字字符
		    if(num>=4){
		    	break;
		    }
			
			if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
				t4 += java.lang.Character.toString(t1[i]);
				num++;
			} 
			//只保留字母和数字(过滤特殊字符)
			if (java.lang.Character.toString(t1[i]).matches("[0-9a-zA-Z]+")) {
				t4 += java.lang.Character.toString(t1[i]);
			}
		}
		if(t4!=null){
			t4=t4.trim();
		}
		return t4;
	}
	public static String getPingYin(String src) {
		if(src==null||src.trim().length()==0){
			return "";
		}
		char[] t1 = null;
		src=src.toLowerCase();
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					if(t2!=null && t2.length >0){
						t4 += t2[0];
					}
						
				} else
					t4 += java.lang.Character.toString(t1[i]);
			}
		//	t4 = subPinYin(t4);
			return t4;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	
	//去除末尾的数字
	public  static String subPinYin(String pinYin){
		if(pinYin==null||pinYin.trim().length()==0){
			return "";
		}
		char[] t1 = null;
		int  j = 0;
		t1 = pinYin.toCharArray();
		for(int i = t1.length-1;i>0;i--){
			if(java.lang.Character.toString(t1[i]).matches("[0-9]+")){
				j++;
			}else{
				break;
			}
		}
		pinYin = pinYin.substring(0,pinYin.length()-j);
		//System.out.println(pinYin);
		return pinYin;
	}
	
	public static void main(String args[]){
		String pinYin = "rongfenga";
		String s = subPinYin(pinYin);
		
	}
}