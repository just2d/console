package com.nuoshi.console.web.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.taofang.biz.domain.house.HousePhoto;
import com.taofang.biz.local.HousePhotoUrlUtil;
import com.taofang.biz.util.BizConfig;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.FileHandleUtil;
import com.nuoshi.console.common.util.GenerateUUID;
import com.nuoshi.console.common.util.ImgUtil;
import com.nuoshi.console.common.util.StrUtils;
import com.nuoshi.console.domain.estate.EstateData;
import com.nuoshi.console.domain.estate.EstateInfo;
import com.nuoshi.console.domain.estate.EstateMd5;
import com.nuoshi.console.domain.topic.Estate;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.CommPhotoService;
import com.nuoshi.console.service.EstateMd5Service;
import com.nuoshi.console.service.EstateService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.PhotoService;
import com.nuoshi.console.view.BasePhoto;
import com.nuoshi.console.view.EstatePhoto;
import com.nuoshi.console.view.EstatePhotoCondition;
import com.nuoshi.console.view.EstatePhotoDetail;

@Controller
@RequestMapping("/estate/commPhoto")
public class CommPhotoController extends BaseController {


	@Resource
	CommPhotoService commPhotoService;
	@Resource
	EstateService estateService;

	@Resource
	LocaleService localeService;
	@Resource
	PhotoService  photoService;
	@Resource
	private EstateMd5Service estateMd5Service;

	private static final Integer PAGE_SIZE = 20;
	private static final Integer LIMIT = 1;
	private static final String COMM_PHOTO_CATEGORY = "3";
	private static final Integer MAX_LAYOUT_PHOTO_NUM = 60;
	private static final String FILE_TYPES = "gif;jpeg;jpg";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}

	// 比较精选
	@RequestMapping("/compSel")
	public String compare(@RequestParam(value = "estateId", required = false) Integer estateId,
			@RequestParam(value = "estateName", required = false) String estateName,
			@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "distId", required = false) Integer distId,
			@RequestParam(value = "blockId", required = false) Integer blockId,
			@RequestParam(value = "backupCount", required = false) Integer backupCount,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam(value = "hasOrNot", required = false) String hasOrNot,
			@RequestParam(value = "idOrder", required = false) String idOrder,
			@RequestParam(value = "index", required = false) Integer index, Model model, HttpServletRequest request) {
		// 获得小区户型图
		EstatePhotoCondition condition = new EstatePhotoCondition();
		setDefaultCity( cityId, model);
		String cityName = getNameById(condition.getCityId());
		String distName = getNameById(distId);
		if (distName != null) {
			model.addAttribute("blockList", localeService
					.getDistLocalByCityId(distId));
		}
		if(hasOrNot == null){
			hasOrNot = "";
		}
		if(idOrder == null){
			idOrder = "";
		}
		condition.setIdOrder(idOrder);
		condition.setPhotoType("1");
		condition.setEstateId(estateId);
		condition.setCityId(cityId);
		condition.setEstateName(estateName);
		condition.setCityId(cityId);
		condition.setBlockId(blockId);
		condition.setDistId(distId);
		condition.setHasOrNot(hasOrNot);
		
		List<EstatePhotoDetail> photoList = commPhotoService.getCommPhotoList(condition);
		int photoNum = commPhotoService.countCommNum(estateId);
		model.addAttribute("photoNum", photoNum);
		
		// 获得备选库户型图
		List<EstatePhotoDetail> backupPhotoList = commPhotoService.getBackupCommPhoto(condition);
	    backupCount = commPhotoService.countBackupCommPhoto(condition);
		model.addAttribute("hasNext", true);
		if (photoList != null && photoList.size() > 0) {
			model.addAttribute("layoutList", photoList);
		}
		if (backupPhotoList != null && backupPhotoList.size() > 0) {
			model.addAttribute("backupPhotoList", backupPhotoList);
		}

		// 获得下一个小区的id.
		condition.setStart((pageNo - 1) * PAGE_SIZE + index);
		condition.setLimit(LIMIT);
		List<EstatePhoto> nextLayout = estateService.getPhotoListByPage(condition);
		String url = appendUrl(backupCount, condition, pageNo, index, flag,null);
		String nextPageUrl = "";
		Integer nextPageNo = 1;
		Integer nextIndex = 1;
		if (nextLayout != null && nextLayout.size() > 0) {
			EstatePhoto nextPhoto = nextLayout.get(0);
			if (nextPhoto != null) {
				// 如果到了某一页的最后一页,则跳到下一页.
				if (index == 20) {
					nextPageNo = pageNo + 1;
					nextIndex = 1;
				} else {
					// 如不是最后一页,则索引值加1.
					nextPageNo = pageNo;
					nextIndex = index + 1;
				}
				
				nextPageUrl = "&backupCount=" + nextPhoto.getBackupLayoutCount() + "&estateId="
						+ nextPhoto.getEstateId() + "&pageNo=" + nextPageNo + "&index=" + nextIndex+ "&flag=" + flag+"&hasOrNot=" + hasOrNot+"&estateName="+estateName+"&idOrder="+idOrder;
				if(cityId != null){
					nextPageUrl +="&cityId="+cityId;
				}if(distId != null){
					nextPageUrl +="&distId="+distId;
				}if(blockId != null){
					nextPageUrl +="&blockId="+blockId;
				}
			}
		} else {
			model.addAttribute("hasNext", false);
		}
		// 获得url;
		model.addAttribute("paramMap", url);
		model.addAttribute("nextpageUrl", nextPageUrl);
		Integer id = getEstateId(condition);
		Estate estate = null;
		if(estateId != null){
			 estate = estateService.getEstateInfoById(id);
		}
		EstateInfo estateInfo = new EstateInfo();
		if(estate != null){
			//exception :===Source must not be null
			BeanUtils.copyProperties(estate, estateInfo);
		}
		
		estateInfo.setExtInfo(estateInfo);
		String errorStr = request.getParameter("errorStr");
		if(errorStr != null){
			model.addAttribute("errorStr", errorStr);
		}
		model.addAttribute("estateInfo", estateInfo);
		model.addAttribute("condition", condition);
		model.addAttribute("backupCount", backupCount);
		model.addAttribute("cityName", cityName);
		model.addAttribute("flag", flag);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("index", index);

		return "tiles:commPhoto.compare";
	}

	/**
	 * 移动到精选库
	 * 
	 * @return
	 */
	@RequestMapping("/moveToUsing")
	public String moveToUsing(
			@RequestParam(value = "toUsingIds", required = false) String toUsingIds,
			EstatePhotoCondition condition, 
			@RequestParam(value = "unUsingIds", required = false) String unUsingIds,
			@RequestParam(value = "backupCount", required = false) Integer backupCount,
			@RequestParam(value = "flag", required = false, defaultValue = "1") String flag,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "index", required = false) Integer index, 
			Model model) {
		List<Integer> idList = null;
		List<Integer> unUsingIdList = null;
		if (toUsingIds != null && toUsingIds.length() > 0) {
			idList = StrUtils.str2IntList(toUsingIds);
			//图片去重,md5处理.
			//获得所有的图片md5信息.(根据photoid)
			backupCount -= idList.size();
		}

		String errorStr = commPhotoService.batchAddEstatePhoto(idList, unUsingIdList, condition.getEstateId(),0);
		model.addAttribute("flag", flag);
		String url = "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,errorStr);
		return url;
	}

	/**
	 * 从备选库中删除
	 * 
	 * @return
	 */
	@RequestMapping("/delBackup")
	public String moveToUsing(@RequestParam(value = "unUsingIds", required = false) String unUsingIds,
			EstatePhotoCondition condition, @RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "backupCount", required = false) Integer backupCount,
			@RequestParam(value = "flag", required = false, defaultValue = "1") String flag,
			@RequestParam(value = "index", required = false) Integer index, Model model) {
		// 删除没有被选中的备选库数据.
		List<Integer> unUsingIdList = StrUtils.str2IntList(unUsingIds);
		if (unUsingIds != null && unUsingIds.length() > 0) {
			commPhotoService.delFromBackupPhoto(unUsingIdList, condition.getEstateId());
		}
		model.addAttribute("flag", flag);
		String url = "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,null);
		return url;
	}

	/**
	 * 设置为默认图片
	 * 
	 * @param id
	 * @param estateId
	 * @return
	 */
	@RequestMapping("/setDefaultLayout")
	public void moveToBackup(EstateInfo estateInfo, HttpServletResponse response) {
		estateService.setDefaultPhoto(estateInfo);
		String respStr = "{success:true}";
		sentResponseInfo(response, new Gson().toJson(respStr).toString());
	}

	/**
	 * 移动到备选库
	 * 
	 * @param unUsingIds
	 * @param estateId
	 * @param category
	 * @return
	 */
	@RequestMapping("/mvToBackup")
	public String moveToBackup(@RequestParam(value = "unUsingIds", required = false) String unUsingIds,
			EstatePhotoCondition condition, @RequestParam(value = "backupCount", required = false) Integer backupCount,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "index", required = false) Integer index,
			@RequestParam(value = "flag", required = false, defaultValue = "1") String flag,
			@RequestParam(value = "category", required = false) String category, Model model) {
		List<Integer> unUsingIdList = null;
		if (unUsingIds != null && unUsingIds.length() > 0) {
			unUsingIdList = StrUtils.str2IntList(unUsingIds);
			backupCount -= unUsingIdList.size();
			commPhotoService.batchDelLayoutPhoto(condition.getEstateId(), unUsingIdList, category);
		}
		model.addAttribute("flag", flag);
		String url = "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,null);
		return url;
	}

	/**
	 * 上传户型图.
	 * 
	 * @param file
	 *@param estateId
	 * @return
	 */
	@RequestMapping("/uploadAction")
	public String uploadAction(@RequestParam(value = "commPhoto", required = false) MultipartFile mFile,
			@RequestParam(value = "estateId", required = false) Integer estateId,
			@RequestParam(value = "backupCount", required = false) Integer backupCount,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "index", required = false) Integer index,
			@RequestParam(value = "flag", required = false, defaultValue = "1") String flag,
			HttpServletResponse response, Model model) {
		try {
			model.addAttribute("flag", flag);
			if (mFile != null && !mFile.isEmpty()) {
				if (!FileHandleUtil.checkFileType(mFile, FILE_TYPES)) {
					// respStr = "{success:false;msg:只允许上传gif,jpg,jpeg格式的图片}";
					model.addAttribute("msg", "只允许上传gif,jpg,jpeg格式的图片");
					EstatePhotoCondition condition = new EstatePhotoCondition();
					condition.setEstateId(estateId);
					return "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,null);
					// sentResponseInfo(response, new
					// Gson().toJson(respStr).toString());
				} else {
					// 图片类型匹配成功
					// 图片去重
					String fileName = mFile.getOriginalFilename();
					String contentType = mFile.getContentType();
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					String path = Globals.tmp + "/xq/"; // 获取本地临时存储路径
					File file = new File(path + GenerateUUID.getUUID() + "." + ext); // 新建一个文件
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					try {
						mFile.transferTo(file);
					} catch (Exception e) {
						log.error(e);
					}
					// 获得小区户型图数量,看是否已满。
					int existNum = commPhotoService.countCommNum(estateId);
					if (existNum < MAX_LAYOUT_PHOTO_NUM) {
						String photoMd5String = ImgUtil.getMD5(file);
						EstateMd5 md5Estate = estateMd5Service.queryMd5Exist(photoMd5String, null,estateId);
						
						if (md5Estate != null) {
							// %2C就是逗号.
							model.addAttribute("msg", "图片已存在,请选择其他图片上传");
							EstatePhotoCondition condition = new EstatePhotoCondition();
							condition.setEstateId(estateId);
							return "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,null);
						} else {
							String savePathPrefix = BizConfig.getProperty("path.photo.estate");
							HousePhoto housePhoto = new HousePhoto();
							EstateData estate = estateService.getEstateDataById(estateId);
							housePhoto.setCityId(estate.getCityId());
							housePhoto.setEstateId(estateId);
							housePhoto.setCategory(200);
							housePhoto.setDisplayOrder(1);
							int photoId = BizServiceHelper.getEstatePhotoService().addEstatePhoto(housePhoto);
							housePhoto.setId(photoId);
							
							ImgUtil.resizePhoto(file.getAbsolutePath(), savePathPrefix+HousePhotoUrlUtil.getEstatePhotoPathLarge(housePhoto), BasePhoto.L_MAX_WIDTH, BasePhoto.L_MAX_HEIGHT, true);
					        ImgUtil.resizePhoto(file.getAbsolutePath(), savePathPrefix+HousePhotoUrlUtil.getEstatePhotoPathMedium(housePhoto), BasePhoto.M_MAX_WIDTH, BasePhoto.M_MAX_HEIGHT, false);
					        ImgUtil.cutPhoto(file.getAbsolutePath(), savePathPrefix+HousePhotoUrlUtil.getEstatePhotoPathSmall(housePhoto), BasePhoto.S_MAX_WIDTH, BasePhoto.S_MAX_HEIGHT, false);
			
							// 更新小区户型图数量.
							estateService.updateBacupAndLayoutNum(estateId,0,0, 0, 1);// 小区户型图数量加1
							// 更新md5值.
							estateMd5Service.saveEstateMd5Info(COMM_PHOTO_CATEGORY, estateId, photoMd5String,
									housePhoto.getId());
							model.addAttribute("msg", "上传成功");
							EstatePhotoCondition condition = new EstatePhotoCondition();
							condition.setEstateId(estateId);
							return "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,null);
						}

					} else {
						model.addAttribute("msg", "图片已满");
						EstatePhotoCondition condition = new EstatePhotoCondition();
						condition.setEstateId(estateId);
						return "tiles:redirect:compSel?" + appendUrl(backupCount, condition, pageNo, index, flag,null);
					}
				}
			} else {
				// respStr = "{success:false;msg:请选择图片上传}";
				// sentResponseInfo(response, new
				// Gson().toJson(respStr).toString());
				model.addAttribute("msg", "请选择图片上传");
				return "tiles:redirect:compSel";
			}
		} catch (Exception e) {
			System.out.print("====上传过程中出现异常....");
			log.error(e);
			return null;
		}
	}

	private String appendUrl(Integer backupCount, EstatePhotoCondition condition, Integer pageNo, Integer index,
			String flag,String errorStr) {
		StringBuilder sb = new StringBuilder();
		sb.append("backupCount=").append(backupCount != null ? backupCount : "").append("&estateId=")
				.append(condition.getEstateId() != null ? condition.getEstateId() : "").append("&cityId=")
				.append(condition.getCityId() != null ? condition.getCityId() : "").append("&distId=")
				.append(condition.getDistId() != null ? condition.getDistId() : "").append("&blockId=")
				.append(condition.getBlockId() != null ? condition.getBlockId() : "").append("&pageNo=")
				.append(pageNo != null ? pageNo : "").append("&index=").append(index != null ? index : "")
				.append("&estateName=").append(condition.getEstateName()==null?"":condition.getEstateName());
		if(errorStr != null){
			sb.append("&errorStr=").append(errorStr);
		}
		return sb.toString();
	}

	private Integer getEstateId(EstatePhotoCondition condition) {
		if (condition != null) {
			return condition.getEstateId();
		}
		return null;
	}
	
	private void setDefaultCity(Integer cityId,
			Model model) {
		if (cityId != null) {
			List<Locale> distList = localeService.getDistLocalByCityId(cityId);
			model.addAttribute("distList", distList);
		}
	}
	private String getNameById(Integer id) {

		if (id != null && id != 0 && id != -1) {
			return localeService.getName(id.intValue());
		}
		return null;
	}

}
