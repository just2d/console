/*
 * @(#) FileNameGenerator.java    2011-6-22
 * Project  :长沙国安数字电视业务综合运营管理平台(一期)
 * Copyright: 2010 Broadin Inc. All rights reserved.
 */
package com.nuoshi.console.common.util;

import java.util.Random;

import org.springframework.util.StringUtils;

/**
 * TODO class purpose description
 * 
 * @author zhangweidong
 * @version 3.0 Revise History:
 * 
 */
public class FileNameGenerator {

    /**
     * 生成文件名称
     * 
     * @return
     */
    public static String generateName() {
        return String.valueOf(System.currentTimeMillis() + "_" + new Random().nextInt(1000000));
    }

    /**
     * 根据给定的扩展名生成文件名称
     * 
     * @param extName
     *            扩展名称
     * @return
     */
    public static String generateName(String extName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(generateName());
        buffer.append(".");
        buffer.append(extName == null ? "" : StringUtils.trimLeadingCharacter(extName.trim(), '.'));
        return buffer.toString();
    }

    /**
     * 根据给定的扩展名生成文件名称
     * 
     * @param suffix
     *            文件后缀
     * @param extName
     *            文件扩展名
     * @return
     */
    public static String generateName(String suffix, String extName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(generateName());
        buffer.append(suffix);
        if (!extName.startsWith(".")) {
            buffer.append(".");
        }
        buffer.append(extName == null ? "" : extName.trim());
        return buffer.toString();
    }
}
