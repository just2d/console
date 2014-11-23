package com.nuoshi.console.common.util;

/**
 * Author:
 * CHEN Liang <alinous@gmail.com>
 * Date: 2009-12-29
 * Time: 11:43:09
 */
public class PhotoResizeParam {
    
    int width;
    int height;
    String path;
    boolean mask;//添加水印图

    public boolean isMask() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PhotoResizeParam() {
        
    }

    public PhotoResizeParam(int width, int height) {
        this.width = width;
        this.height = height;
        this.mask = false;
    }

    public PhotoResizeParam(int width, int height, boolean mask) {
        this.width = width;
        this.height = height;
        this.mask = mask;
    }
}
