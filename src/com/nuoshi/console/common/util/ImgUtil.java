package com.nuoshi.console.common.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.PhotoPath;
import com.nuoshi.console.common.PhotoSize;
import com.nuoshi.console.view.BasePhoto;


class Dim {
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

	public Dim() {
		this.width = 0;
		this.height = 0;
	}

	public Dim(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Rectangle getCropRect(int maxWidth, int maxHeight) {
		double aspo = (maxWidth + 0.0) / maxHeight;
		double imgAspo = (width + 0.0) / height;

		int rheight = 0;
		int rwidth = 0;
		int x = 0;
		int y = 0;
		if (imgAspo > aspo) {
			// if image aspect ratio is wider
			rheight = height;
			rwidth = (int) Math.round(height * aspo);

			x = (width - rwidth) / 2;
			y = 0;

		} else {
			rwidth = width;
			rheight = (int) Math.round(width / aspo);

			x = 0;
			y = (height - rheight) / 2;
		}

		return new Rectangle(x, y, rwidth, rheight);

	}

	public Dim regulate(int maxWidth, int maxHeight, boolean noZoomOut) {
		if (noZoomOut && width <= maxWidth && height <= maxHeight) {
			return new Dim(width, height);
		} else {
			return regulate(maxWidth, maxHeight);
		}
	}

	public Dim regulate(int maxWidth, int maxHeight) {
		int nw = width;
		int nh = height;
		if (width > 0 && height > 0) {
			if ((height + 0.0) / width > (maxHeight + 0.0) / maxWidth) {
				nw = width * maxHeight / height;
				nh = maxHeight;
			} else {
				nw = maxWidth;
				nh = height * maxWidth / width;
			}
		}
		return new Dim(nw, nh);
	}

	int width;
	int height;
}

public class ImgUtil {

	private static Logger log = Logger.getLogger("ImgUtil");

	public static boolean regulate(String srcPathName, String destPathName) throws Exception {
		int width = getWidth(srcPathName);
		int height = getHeight(srcPathName);
		if ((height + 0.0) / width > 1.5) {
			MagickImage image = null;
			MagickImage scaled = null;
			try {
				int newHeight = (int) Math.round(width * 1.5);
				Rectangle rect = new Rectangle(0, (height - newHeight) / 2,
						width, newHeight);
				ImageInfo info = null;
				info = new ImageInfo();
				image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
				scaled = image.cropImage(rect);
				scaled.setFileName(destPathName);
				scaled.writeImage(info);
				return true;
			} finally {
				if (image != null) {
					image.destroyImages();
				}
				if (scaled != null) {
					scaled.destroyImages();
				}
			}
		} else {
			return false;
		}
	}

	public static final int ImageResizeFlag_None = 0;
	public static final int ImageResizeFlag_NoZoomOut = 1 << 10;

	/**
	 * 标准的 裁剪，一张原图缩放成大，中，小三张图
	 * 
	 * @param comment
	 * @param srcPath
	 * @param lparam
	 * @param mparam
	 * @param sparam
	 * @return
	 */
	public static boolean resizeAll(String comment, String srcPath,
			PhotoResizeParam lparam, PhotoResizeParam mparam,
			PhotoResizeParam sparam, Rectangle cropRect, int flags) {
		assert (comment != null);
		assert (srcPath != null);

		boolean result = false;

		MagickImage oriImage = null;
		MagickImage image = null;
		try {
			ImageInfo info = new ImageInfo(srcPath + "[0]");
			oriImage = new MagickImage(info);
			Dimension imageDim = oriImage.getDimension();
			int width = imageDim.width;
			int height = imageDim.height;

			assert (width > 0);
			assert (height > 0);

			if (cropRect != null && cropRect.x >= 0 && cropRect.y >= 0
					&& cropRect.width > 0 && cropRect.height > 0
					&& (cropRect.x + cropRect.width) <= width
					&& (cropRect.y + cropRect.height) <= height) {
				image = oriImage.cropImage(cropRect);

				imageDim = image.getDimension();
				width = imageDim.width;
				height = imageDim.height;

				assert (width > 0);
				assert (height > 0);
			} else {
				image = oriImage;
				oriImage = null;
			}
			if(StringUtils.isNotBlank(comment)){
				setComment(image, comment);
			}
			MagickImage mscaled = null;
			MagickImage lscaled = null;
			MagickImage sscaled = null;
			MagickImage scropped = null;
			try {
				ImageInfo ainfo = new ImageInfo();

				Dim dim = new Dim(width, height);

				Dim ldim;
				boolean noZoomOut = (flags & ImageResizeFlag_NoZoomOut) > 0;
				if (noZoomOut) {
					ldim = dim.regulate(lparam.getWidth(), lparam.getHeight(),
							true);
				} else {
					ldim = dim.regulate(lparam.getWidth(), lparam.getHeight());
				}
				lscaled = image.scaleImage(ldim.getWidth(), ldim.getHeight());

				Dim mdim;
				if (noZoomOut) {
					mdim = dim.regulate(mparam.getWidth(), mparam.getHeight(),
							true);
				} else {
					mdim = dim.regulate(mparam.getWidth(), mparam.getHeight());
				}
				mscaled = image.scaleImage(mdim.getWidth(), mdim.getHeight());


				// 自动裁剪
				Rectangle rcrop = dim.getCropRect(lparam.getWidth(),
						lparam.getHeight());
				// oh shit，jimagek有个问题：就是 不能连续被corp两次！！！！
				if (oriImage == null) {
					scropped = image.cropImage(rcrop);
				} else {
					rcrop.x += cropRect.x;
					rcrop.y += cropRect.y;
					scropped = oriImage.cropImage(rcrop);
				}
				Dim adim = new Dim(rcrop.width, rcrop.height);
				Dim sdim = adim.regulate(sparam.getWidth(), sparam.getHeight());
				sscaled = scropped
						.scaleImage(sdim.getWidth(), sdim.getHeight());
				if (lparam.isMask()) {
					maskImage(Globals.maskPath , lscaled, 0,
							0);
				}
				lscaled.setFileName(lparam.getPath());
				lscaled.writeImage(ainfo);

				mscaled.setFileName(mparam.getPath());

				if (mparam.isMask()) {
					maskImage(Globals.maskPath, mscaled, 0,
							0);
				}

				mscaled.writeImage(ainfo);

				sscaled.setFileName(sparam.getPath());
				sscaled.writeImage(ainfo);

				result = true;

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (mscaled != null) {
					mscaled.destroyImages();
					mscaled = null;
				}

				if (sscaled != null) {
					sscaled.destroyImages();
					sscaled = null;
				}

				if (lscaled != null) {
					lscaled.destroyImages();
					lscaled = null;
				}

				if (scropped != null) {
					scropped.destroyImages();
					scropped = null;

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (image != null) {
				image.destroyImages();
				image = null;
			}
			if (oriImage != null) {
				oriImage.destroyImages();
				oriImage = null;
			}
		}

		return result;
	}

	

	/**
	 * resize, 无论原尺寸大小，都会按比例缩放
	 * 
	 * @param srcPathName
	 * @param destPathName
	 * @param maxWidth
	 * @param maxHeight
	 * @throws MagickException
	 */
	public static void resize(String srcPathName, String destPathName,
			int maxWidth, int maxHeight) throws Exception {
		int width = 0;
		int height = 0;
		boolean change = true;
		width = getWidth(srcPathName);
		height = getHeight(srcPathName);
		if (width > 0 && height > 0) {
			if (height / width > maxHeight / maxWidth) {
				width = width * maxHeight / height;
				height = maxHeight;
			} else {
				height = height * maxWidth / width;
				width = maxWidth;
			}
		}

		MagickImage image = null;
		MagickImage scaled = null;
		try {
			ImageInfo info = null;
			info = new ImageInfo();
			image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
			if (change) {
				scaled = image.scaleImage(width, height);
			} else {
				scaled = image;
			}
			scaled.setFileName(destPathName);
			scaled.writeImage(info);
		} finally {
			if (image != null) {
				image.destroyImages();
			}
			if (scaled != null) {
				scaled.destroyImages();
			}
		}
	}
	
	

	/**
	 * 缩放图片，如果原图尺寸小于 maxwidth, maxheight，则不作处理，isMask决定是否加水印
	 * 
	 * @param srcPathName
	 * @param destPathName
	 * @param maxWidth
	 * @param maxHeight
	 * @param isMask
	 * @return
	 */
	public static boolean resizePhoto(String srcPathName, String destPathName, int maxWidth, int maxHeight, boolean isMask) {
		File src = new File(srcPathName);
		File file = new File(destPathName);
		if(!src.getParentFile().exists()){
			src.getParentFile().mkdirs();
		}
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		int width = 0;
		int height = 0;
		boolean change = true;
		try {
			width = getWidth(srcPathName);
			height = getHeight(srcPathName);
		} catch (Exception e) {
			return false;
		}

		if (maxWidth > width && maxHeight > height) {
			change = false;
		} else {
			if (width > 0 && height > 0) {
				if (height / width > maxHeight / maxWidth) {
					width = width * maxHeight / height;
					height = maxHeight;
				} else {
					height = height * maxWidth / width;
					width = maxWidth;
				}
			}
		}

		MagickImage image = null;
		MagickImage scaled = null;
		try {

			/**
			 * do resize, and finally clean up
			 */
			try {
				ImageInfo info = null;
				info = new ImageInfo();
				image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
				if (change) {
					scaled = image.scaleImage(width, height);
				} else {
					scaled = image;
				}
				scaled.setFileName(destPathName);

				if (isMask) {
					maskImage(Globals.maskPath + Globals.mLogoPath, scaled, 0, 0);
				}

				scaled.writeImage(info);
			} finally {
				if (image != null) {
					image.destroyImages();
				}
				if (scaled != null) {
					scaled.destroyImages();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 重载函数，缩放图片，默认不加水印
	 * 
	 * @param srcPathName
	 * @param destPathName
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static boolean resizePhoto(String srcPathName, String destPathName,
			int maxWidth, int maxHeight) {
		boolean isMask = false;
		return resizePhoto(srcPathName, destPathName, maxWidth, maxHeight,
				isMask);
	}

	public static void setComment(MagickImage img, String comment)
			throws Exception {
		ImageInfo info = new ImageInfo();
		assert (info != null && img != null);
		img.profileImage("*", null);
		img.setImageAttribute("comment", comment);
	}

	public static void setComment(String srcPathName, String comment)
			throws Exception {
		ImageInfo info = null;
		info = new ImageInfo();
		MagickImage image = null;
		try {
			image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
			image.profileImage("*", null);
			image.setImageAttribute("comment", comment);
			image.writeImage(info);
		} finally {
			if (image != null) {
				image.destroyImages();
			}
		}
	}

	public static boolean checkComment(String srcPathName, String comment) throws Exception {
		MagickImage image = null;
		try {
			image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
			String cmt = "";
			cmt = image.getImageAttribute("comment");
			if (cmt != null) {
				if (cmt.startsWith("lx") && !comment.equals(cmt)) {
					return false;
				}
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			if (image != null) {
				image.destroyImages();
			}
		}
	}

	public static void maskImage(String logopath, MagickImage target,
			int location, int scale) throws Exception {

		MagickImage logo = null;
		try {

			logo = new MagickImage(new ImageInfo(logopath + "[0]"));

			log.info("logo path = " + logopath);
			Dimension targetDim = target.getDimension();

			int width = (int) targetDim.getWidth();
			int height = (int) targetDim.getHeight();

			Dimension logoDim = logo.getDimension();

			int logowidth = (int) logoDim.getWidth();
			int logoheight = (int) logoDim.getHeight();

			if (width > height) {
				if (width < 2 * logowidth || height < 2 * logoheight) {
					return;
				}
			} else {
				if (width < (logowidth + 10) || height < 2 * logoheight) {
					return;
				}
			}

			int x = width - logowidth - 10;
			int y = height - logoheight - 10;

			target.compositeImage(CompositeOperator.AtopCompositeOp, logo, x, y);
			return;
		} finally {
			if (logo != null) {
				logo.destroyImages();
			}
		}
	}

	public static void mask(String logoPath, String srcPathName,
			String destPathName, int location, int scale)
			throws Exception {
		int width = getWidth(srcPathName);
		int height = getHeight(srcPathName);
		int x = 0, y = 0;
		int w, h;
		w = scale * 70 / 100;
		h = scale * 65 / 100;
		boolean lc = true;
		if (width < height) {
			switch (location) {
			case 0:
				lc = false;
				break;
			case 1:
				x = width / 4 - w;
				y = height / 8 - h / 2;
				break;
			case 2:
				x = width / 2 - w;
				y = height / 8 - h / 2;
				break;
			case 3:
				x = width * 3 / 4 - w;
				y = height / 8 - h / 2;
				break;
			case 4:
				x = width / 4 - w;
				y = height / 2 - h / 2;
				break;
			case 5:
				x = width / 2 - w;
				y = height / 2 - h / 2;
				break;
			case 6:
				x = width * 3 / 4 - w;
				y = height / 2 - h / 2;
				break;
			case 7:
				x = width / 4 - w;
				y = height * 7 / 8 - h / 2;
				break;
			case 8:
				x = width / 2 - w;
				y = height * 7 / 8 - h / 2;
				break;
			case 9:
				x = width * 3 / 4 - w;
				y = height * 7 / 8 - h / 2;
				break;
			}
		} else {
			switch (location) {
			case 0:
				lc = false;
				break;
			case 1:
				x = width / 7 - w;
				y = height / 6 - h / 2;
				break;
			case 2:
				x = width / 2 - w;
				y = height / 6 - h / 2;
				break;
			case 3:
				x = width * 6 / 7 - w;
				y = height / 6 - h / 2;
				break;
			case 4:
				x = width / 7 - w;
				y = height / 2 - h / 2;
				break;
			case 5:
				x = width / 2 - w;
				y = height / 2 - h / 2;
				break;
			case 6:
				x = width * 6 / 7 - w;
				y = height / 2 - h / 2;
				break;
			case 7:
				x = width / 7 - w;
				y = height * 5 / 6 - h / 2;
				break;
			case 8:
				x = width / 2 - w;
				y = height * 5 / 6 - h / 2;
				break;
			case 9:
				x = width * 6 / 7 - w;
				y = height * 5 / 6 - h / 2;
				break;
			}
		}
		if (x < 10) {
			x = 10;
		}
		if (x + w * 2 + 10 > width) {
			x = width - w * 2 - 10;
		}
		if (y < 10) {
			y = 10;
		}
		if (y + h + 10 > height) {
			y = height - h - 10;
		}
		if (lc) {
			ImageInfo info = new ImageInfo();
			MagickImage image = null;
			MagickImage mask = null;
			MagickImage dest = null;
			try {
				image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
				mask = new MagickImage(new ImageInfo(logoPath));
				image.setFileName(destPathName);
				image.writeImage(info);
				dest = new MagickImage(new ImageInfo(destPathName));
				dest.compositeImage(CompositeOperator.AtopCompositeOp, mask, x,
						y);
				dest.setFileName(destPathName);
				dest.writeImage(info);
			} finally {
				if (image != null) {
					image.destroyImages();
				}
				if (mask != null) {
					mask.destroyImages();
				}
				if (dest != null) {
					dest.destroyImages();
				}
			}
		}
	}

	public static int getWidth(String src) throws Exception {
		MagickImage magImage = null;
		try {
			ImageInfo info = new ImageInfo(src + "[0]");
			magImage = new MagickImage(info);
			Dimension imageDim = magImage.getDimension();
			return imageDim.width;
		} finally {
			if (magImage != null) {
				magImage.destroyImages();
			}
		}
	}

	public static int getHeight(String src) throws Exception {
		MagickImage magImage = null;
		try {
			ImageInfo info = new ImageInfo(src + "[0]");
			magImage = new MagickImage(info);
			Dimension imageDim = magImage.getDimension();
			return imageDim.height;
		} finally {
			if (magImage != null) {
				magImage.destroyImages();
			}
		}
	}

	public static boolean shrinkWidth(String srcPathName, String destPathName,
			int maxWidth, int maxHeight) throws Exception {
		int width = getWidth(srcPathName);
		int height = getHeight(srcPathName);
		if (width <= maxWidth) {
			return false;
		}
		int y = height * maxWidth / width;
		MagickImage image = null;
		MagickImage scaled = null;
		try {
			ImageInfo info = new ImageInfo();
			image = new MagickImage(new ImageInfo(srcPathName + "[0]"));
			scaled = image.scaleImage(maxWidth, y);
			scaled.setFileName(destPathName);
			scaled.writeImage(info);
			return true;
		} finally {
			if (image != null) {
				image.destroyImages();
			}
			if (scaled != null) {
				scaled.destroyImages();
			}
		}
	}

	public static void cp(File src, File dest) throws IOException {
		InputStream instream = new FileInputStream(src);
		OutputStream outstream = new FileOutputStream(dest);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = instream.read(buffer, 0, 8192)) != -1) {
			outstream.write(buffer, 0, bytesRead);
		}
		outstream.close();
		instream.close();
	}
	
    public static String getMD5(File file){          
        String name="";  
        try {             
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];  
            int len = 0;                      
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");           
            while ((len = inputStream.read(bytes)) > 0) {  
                messagedigest.update(bytes, 0, len);  
            }  
            name = MD5Util.bufferToHex(messagedigest.digest());   
            inputStream.close();              
        } catch (MalformedURLException e) {  
            log.warn(e);  
        } catch (IOException e) {  
            log.warn(e);  
        } catch (NoSuchAlgorithmException e) {  
            log.warn(e);  
        }  
        return name;  
    } 
    /**
     * 
     * @param agentId 经纪人id
     * @param comment
     * @param file 上传的文件
     * @param cropRect  图片的裁剪范围
     * @param photo 
     * @param hasMask 是否加水印图
     * @return
     */
    public static BasePhoto processUpload(String comment,String savePathPrefix, File file,Rectangle cropRect,BasePhoto photo, boolean hasMask) {

		PhotoPath lphotoPath = new PhotoPath(savePathPrefix,PhotoSize.LARGE);
		PhotoPath mphotoPath = new PhotoPath(savePathPrefix,PhotoSize.MIDDLE);
		PhotoPath sphotoPath = new PhotoPath(savePathPrefix,PhotoSize.SMALL);

		if (lphotoPath.getSavePath() == null || mphotoPath.getSavePath() == null || sphotoPath.getSavePath() == null) {
			return null;
		}

		if (!lphotoPath.createTempFile() || !mphotoPath.createTempFile() || !sphotoPath.createTempFile()) {
			return null;
		}

		boolean resizeResult = false;

		PhotoResizeParam sparam = new PhotoResizeParam(BasePhoto.S_MAX_WIDTH, BasePhoto.S_MAX_HEIGHT);
		sparam.setPath(sphotoPath.getTmpFile().getPath());

		PhotoResizeParam mparam = new PhotoResizeParam(BasePhoto.M_MAX_WIDTH, BasePhoto.M_MAX_HEIGHT, hasMask);
		mparam.setPath(mphotoPath.getTmpFile().getPath());
		PhotoResizeParam lparam = new PhotoResizeParam(BasePhoto.L_MAX_WIDTH, BasePhoto.L_MAX_HEIGHT, hasMask);

		 
		lparam.setPath(lphotoPath.getTmpFile().getPath());
		// 生成水印图
		resizeResult = ImgUtil.resizeAll(comment, file.getPath(), lparam, mparam, sparam, cropRect, ImgUtil.ImageResizeFlag_None);
		if (!resizeResult) {
			lphotoPath.cleanup();
			mphotoPath.cleanup();
			sphotoPath.cleanup();
			return photo;
		}

		if (!lphotoPath.moveToDest() || !mphotoPath.moveToDest() || !sphotoPath.moveToDest()) {
			return photo;
		}

		photo.setlPhoto(lphotoPath.getDestRelatedPath());
		photo.setmPhoto(mphotoPath.getDestRelatedPath());
		photo.setsPhoto(sphotoPath.getDestRelatedPath());
		return photo;
	}
    
	/***
	 * 裁剪图片，先缩放再裁剪，
	 * 
	 * @param srcPathName
	 * @param destPathName
	 * @param maxWidth
	 * @param maxHeight
	 * @param isMask
	 * @return
	 */
	public static boolean cutPhoto(String srcPathName, String destPathName, int maxWidth, int maxHeight, boolean isMask) {
		try {
			
			File src = new File(srcPathName);
			File file = new File(destPathName);
			if(!src.getParentFile().exists()){
				src.getParentFile().mkdirs();
			}
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			int srcwidth = 0;
			int srcheight = 0;
			double tRate = (double) maxWidth / maxHeight;
			MagickImage srcImage = new MagickImage(new ImageInfo(srcPathName + "[0]"));
			try {
				srcwidth = getWidth(srcPathName);
				srcheight = getHeight(srcPathName);
			} catch (Exception e) {
				return false;
			}
			double srcRate = (double) srcwidth / srcheight;

			if (srcwidth >= maxWidth || srcheight >= maxHeight) {
				if (srcRate >= tRate) {
					srcwidth = (int) (maxHeight * srcwidth / srcheight);
					srcheight = maxHeight;
				} else {
					srcheight = (int) (maxWidth * srcheight / srcwidth);
					srcwidth = maxWidth;
				}
			}
			MagickImage scaled = srcImage.scaleImage(srcwidth, srcheight);
			int tWidth = 0;
			int tHeight = 0;
			if (srcRate >= tRate) {
				tWidth = (int) (srcheight * tRate);
				tHeight = srcheight;
			} else {
				tWidth = srcwidth;
				tHeight = (int) (srcwidth / tRate);
			}

			int x = (srcwidth - tWidth) / 2;
			int y = (srcheight - tHeight) / 2;

			Rectangle rect = new Rectangle();
			rect.setBounds(x, y, tWidth, tHeight);
			MagickImage cropped = scaled.cropImage(rect);
			scaled = cropped.scaleImage(tWidth, tHeight);// 小图片文件的大小.
			scaled.setFileName(destPathName);
			scaled.writeImage(new ImageInfo());
			srcImage.destroyImages();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
   
}