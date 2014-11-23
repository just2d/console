package com.nuoshi.console.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileHandleUtil {
    /**
     * 删除存在的文件
     * 
     * @param fileDir
     *            fileName文件所在的文件夹
     * @param fileName
     *            文件名
     */
    public static void deleteFile(String fileDir, String fileName) {
        if (fileName != null) {
            File file = new File(fileFullPath(fileDir, File.separator, fileName));
            if (file.exists()) {// 如果文件存在则删除
                file.delete();
            }
        }
    }
    
    /**
     * 从源目录移动到指定的目录
     * @param fromFilePath(源目录)
     * @param destFilePath(目标目录)
     * @param isDeleteFile(是否删除源目录)
     * @return
     */
    public static boolean moveToDest(String fromFilePath,String destFilePath,boolean isDeleteFile) {
        if (destFilePath == null ||fromFilePath=="") {
            return false;
        }
        File  fromFile=new File(fromFilePath);
        if(!fromFile.exists()){
        	return false;
        }
        File  destfile=new File(destFilePath);
    	if(!destfile.getParentFile().exists()){
    		destfile.getParentFile().mkdirs();
		}

            try {
                InputStream instream = new FileInputStream(fromFile);
                OutputStream outstream = new FileOutputStream(destfile);
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
            if(isDeleteFile){
        		fromFile.deleteOnExit();
        	}
        	fromFile = null;
        return true;
    }  

    /**
     * 检查文件类型是否合法合法返回true否则返回false
     * 
     * @param file
     * @param typeStrs
     *            类型列表,如果多个类型以;分割
     * @return
     */
    public static boolean checkFileType(MultipartFile file, String typeStrs) {
        try {
            String[] types = null;
            if (typeStrs != null) {
                types = StringUtils.split(typeStrs, ";");
                String type = FileTypeUtil.getFileByFile(file.getInputStream());
                if (type == null) {
                    return false;
                }
                for (String str : types) {
                    if (str != null && type.equalsIgnoreCase(str)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            LogFactory.getLog(FileHandleUtil.class).error(e);
        }
        return false;
    }

    /**
     * 检查文件大小是否合法,如果合法返回true否则返回false
     * 
     * @param file
     * @param maxSize
     * @return
     */
    public static boolean checkSize(MultipartFile file, long maxSize) {
        if (file.getSize() / 1024 > maxSize) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据文件目录和文件名, 返回文件全路径
     * 
     * @param path
     *            : 文件目录
     * @param separator
     *            : 文件分隔符
     * @param fileName
     *            : 文件名
     * @return
     */
    public static String fileFullPath(String path, String separator, String fileName) {

        String fileFullPath = null;
        if (path.endsWith(separator)) {
            fileFullPath = path + fileName;
        } else {
            fileFullPath = path + separator + fileName;
        }

        return fileFullPath;
    }

    /**
     * 删除目录, 无论目录下有多少子目录都会递归删除
     * 
     * @param delDir
     * @return
     */
    public static boolean deleteDir(String delDir) {
        if (delDir == null)
            return false;

        File file = new File(delDir);
        if (!file.isDirectory()) {
            return file.delete();
        }

        String[] filelist = file.list();
        for (int i = 0; i < filelist.length; i++) {
            File delfile = new File(fileFullPath(delDir, File.separator, filelist[i]));
            if (!delfile.isDirectory()) {
                delfile.delete();
            } else if (delfile.isDirectory()) {
                deleteDir(fileFullPath(delDir, File.separator, filelist[i]));
            }
        }
        return file.delete();
    }


    /**
     * 获取文件扩展名称
     * 
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
        String ext = "";
        int pos = fileName.lastIndexOf('.');
        if (pos != -1) {
            ext = fileName.substring(pos).trim();
        }
        return ext;
    }

    /**
     * 文件下载 方法, 输入文件全名, 即向客户端打开文件输出流
     * 
     * @param fileFullPath
     *            文件全路径
     * @param alias
     *            文件别名
     * @param response
     * @throws Exception
     */
    public static void download(String fileFullPath, String alias, HttpServletResponse response) throws Exception {
        File file = new File(fileFullPath);
        if (alias == null || "".equals(alias)) {
            alias = file.getName();
        }
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + alias);
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        int i = 0;
        byte b[] = new byte[1024];
        while ((i = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, i);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 设置下载文件中文件的名称
     * 
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
         * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
         * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }

    /**
     * 设置下载文件中文件的名称
     * 
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFileName(HttpServletRequest request, String fileName) {
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE")) {
                return URLEncoder.encode(fileName, "UTF8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                return "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            } else {
                return fileName;
            }
        } catch (Exception e) {
            return fileName;
        }

    }
}
