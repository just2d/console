package com.nuoshi.console.common.phone;

import org.apache.axis2.addressing.EndpointReference;

import com.nuoshi.console.common.Resources;

public class PhoneConstants {

	public static EndpointReference TARGET_EPR = new EndpointReference(Resources.getString("taofang.400.url"));
	public static String LOGIN_USER = "58taofangwang";
	public static String LOGIN_PWD = "58taofangwang";

	public static String PHONE_NUMBER = "4008885920";
	public static String GROUP_PREFIX = "TF400";
	public static String USER_NAME_PREFIX = "TFU_";
	public static String USER_PWD_PREFIX = "TFP_";

	public static double CALL_FEE = 0.21; // 每分钟扣费钱数
	public static double DEFAULT_USER_FEE = 10.0; // 每个用户缺省的金额
	public static double MAX_ALLOW_OVERDUE = -10.0;
}