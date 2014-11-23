package com.nuoshi.console.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import com.nuoshi.console.domain.photo.Photo;

/**
 * Author:
 * CHEN Liang <alinous@gmail.com>
 * Date: 2010-1-8
 * Time: 16:19:09
 */
public class PhotoPath {
    private Photo photo;
    private File savePath;
    private File tmpFile;
    private File destFile;

    public static final String L_DIR_PREFIX = "/l";//大图所在目录
    public static final String M_DIR_PREFIX = "/m";//中图所在目录
    public static final String S_DIR_PREFIX = "/s";//小图所在目录

    public static final String L_FILE_PREFIX = "lp_";//大图文件前缀
    public static final String M_FILE_PREFIX = "mp_";//中图文件前缀
    public static final String S_FILE_PREFIX = "sp_";//小图文件前缀

    private String dirPrefix;
    private String filePrefix;
    private String fileExt = ".jpg";
    private String destFilePath;
    private String savePathPrefix;//上传文件目录前缀

    public PhotoPath(String savePathPrefix,PhotoSize size){
    	 this.photo = null;
    	 this.savePathPrefix = savePathPrefix;
         init (size);
    }
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public File getSavePath() {
        return savePath;
    }

    public void setSavePath(File savePath) {
        this.savePath = savePath;
    }

    public File getTmpFile() {
        return tmpFile;
    }

    public void setTmpFile(File tmpFile) {
        this.tmpFile = tmpFile;
    }

    public File getDestFile() {
        return destFile;
    }

    public void setDestFile(File destFile) {
        this.destFile = destFile;
    }

    public String getDirPrefix() {
        return dirPrefix;
    }

    public void setDirPrefix(String dirPrefix) {
        this.dirPrefix = dirPrefix;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }


    /**
     * constructor
     * @param size
     */
    public void init(PhotoSize size) {
        this.destFilePath = null;
         switch(size) {
           case LARGE:
               this.dirPrefix = PhotoPath.L_DIR_PREFIX;
               this.filePrefix = PhotoPath.L_FILE_PREFIX;
               if (this.photo != null) {
                   this.destFilePath = this.photo.getL();
               }
               break;
           case MIDDLE:
               this.dirPrefix = PhotoPath.M_DIR_PREFIX;
               this.filePrefix = PhotoPath.M_FILE_PREFIX;
               if (this.photo != null) {
                   this.destFilePath = this.photo.getM();
               }
               break;
           case SMALL:
               this.dirPrefix = PhotoPath.S_DIR_PREFIX;
               this.filePrefix = PhotoPath.S_FILE_PREFIX;
               if (this.photo != null) {
                   this.destFilePath = this.photo.getS();
               }
               break;
        }
        this.makeDirs();
    }


    public PhotoPath(String savePathPrefix,PhotoSize size, Photo photo) {
    	this.savePathPrefix = savePathPrefix;
        this.photo = photo;
        init(size);
    }


    public boolean createTempFile() {
        if (this.tmpFile != null) {
            return true;
        }

        try {
            this.tmpFile = File.createTempFile(this.filePrefix, this.fileExt);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean createDestFile() {
        if (this.destFile != null) {
            return true;
        }
        try {
            this.destFile = File.createTempFile(this.filePrefix, this.fileExt, this.savePath);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 返回某天的目录 例如: 20100401
     * @param cl
     * @return
     */
    public static String getDiv1(Calendar cl) {
		String ys, ms;
		ys = cl.get(Calendar.YEAR) + "";
		int month = cl.get(Calendar.MONTH) + 1;
		if (month < 10) {
			ms = "0" + month;
		} else {
			ms = month + "";
		}
		int day = cl.get(Calendar.DATE);
		if (day < 10) {
			return ys + ms + "0" + day;
		} else {
			return ys + ms + "" + day;
		}
	}

    /**
     * 返回当前时刻的目录 每时分钟生成一个新的目录，例如: 1900, 1910, 1920，代表19点0～9， 19点10~19分
     * @param cl
     * @return
     */
	protected static String getDiv2(Calendar cl) {
		String div2;
		if (cl.get(Calendar.HOUR_OF_DAY) < 10) {
			div2 = "0" + cl.get(Calendar.HOUR_OF_DAY) + "";
		} else {
			div2 = cl.get(Calendar.HOUR_OF_DAY) + "";
		}
		int min = cl.get(Calendar.MINUTE);
		switch (min / 10) {
		case 0:
			div2 = div2 + "00";
			break;
		case 1:
			div2 = div2 + "10";
			break;
		case 2:
			div2 = div2 + "20";
			break;
		case 3:
			div2 = div2 + "30";
			break;
		case 4:
			div2 = div2 + "40";
			break;
		case 5:
			div2 = div2 + "50";
			break;
		}
		return div2;
	}

    public String getDestRelatedPath() {
        return (destFile != null ) ? this.getRelatedPath() + "/" + this.destFile.getName():"";
    }
    public static String getPathHash() {
        Calendar cl = Calendar.getInstance();
        return "/" + getDiv1(cl) + "/" + getDiv2(cl);
    }

    protected String getRelatedPath() {
        return this.dirPrefix + getPathHash();
    }

    protected String getSavePathPrefix() {
        return this.savePathPrefix;
    }


    // get a absolute file store path in file system
    protected void makeDirs() {
        String destDir = getSavePathPrefix() +  this.dirPrefix + getPathHash();
        this.savePath = new File(destDir);
        this.savePath.mkdirs();
    }

    // return the absolute path of a image file in file system.
   public String getAbsoluteFilePath(String filepath) {
        return getSavePathPrefix() + filepath;
    }

    public String getAbsoluteFilePath() {
        return getSavePathPrefix() + this.destFilePath;
    }


//从临时目录移动到指定的目录
    public boolean moveToDest() {
        if (this.tmpFile == null) {
            return false;
        }

        if (this.destFile == null && !createDestFile()) {
            return false;
        }

        try {
            try {
                InputStream instream = new FileInputStream(this.tmpFile);
                OutputStream outstream = new FileOutputStream(this.destFile);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = instream.read(buffer, 0, 8192)) != -1) {
                    outstream.write(buffer, 0, bytesRead);
                }
                outstream.close();
                instream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        finally {
            this.tmpFile.delete();
            this.tmpFile = null;
        }
        return true;
    }


    public void cleanup() {
        if (this.tmpFile != null) {
            this.tmpFile.delete();
            this.tmpFile = null;
        }
    }


    public void deleteDestFile() {
        if (this.destFile != null) {
            this.destFile.delete();
        }
    }
}
