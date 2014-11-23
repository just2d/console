package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.StrUtils;
import com.nuoshi.console.dao.CategoryManageDao;
import com.nuoshi.console.domain.wenda.Category;

/**
 * @description
 * @author shanyuqiang
 * @createDate Feb 29, 2012 1:50:43 PM
 * @email shanyq@taofang.com
 * @version 1.0
 */
@Service
public class CategoryManageService extends BaseService {
	@Resource
	private CategoryManageDao categoryManageDao;

	/**
	 * 根据对象进行分级查询
	 * @param category
	 * @return list
	 * @author shanyq
	 */
	public List<Category> queryCategoryList(Category category) {
		return categoryManageDao.getCategoryList(category);
	}

	/**
	 * 根据主键查询分级对象
	 * @param id 主键
	 * @return category
	 * @athor shanyq
	 */
	public Category getCategoryById(Integer id) {
		return categoryManageDao.getCategoryById(id);
	}

	/**
	 * 根据参数查询分级列表,并且对查出的对象进行显示处理
	 * @param category
	 * @return category
	 * @athor shanyq
	 */
	public List<Category> getCategoryList(Category category) {
		List<Category> categoryList = categoryManageDao.getCategoryList(category);
		this.dealCategoryList(categoryList);
		return categoryList;
	}

	/**
	 * 对分级list页面显示进行参数处理
	 * @param categorylist
	 * @return void
	 * @author shanyq
	 */
	private void dealCategoryList(List<Category> categoryList) {
		if(CollectionUtils.isEmpty(categoryList)){
			return;
		}
		Map<Integer,String> ParentCategoryName=this.getCategoryNmae(categoryList);
		for (Category category : categoryList) {
			if (category != null) {
				// 如果为一级分级
				if (category.getCloseTime() != null) {
					category.setCloseTimeStr(StrUtils.date2Str(category.getCloseTime()));
				}
				if (category.getPid().equals(0)) {
					category.setParentName("无");
				} else if (category.getPid() != null && category.getPid().intValue() != 0&&ParentCategoryName!=null) {
					String catMame=ParentCategoryName.get(category.getPid().intValue()) ;
					if (StringUtils.isNotBlank(catMame)) {
						category.setParentName(ParentCategoryName.get(category.getPid().intValue()));
					}
				}
			}
		}
	}
	
	/**
	 * 获取父类别id和名字的对应map
	 * @param categoryList
	 * @return
	 * @author wangjh
	 * Apr 23, 2012 3:51:09 PM
	 */
	public Map<Integer,String> getCategoryNmae(List<Category> categoryList){
		if(CollectionUtils.isEmpty(categoryList)){
			return null;
		}
		Set<Integer> categoryIdSet=new HashSet<Integer>();
		for (Category category : categoryList) {
			Integer pids=category.getPid();
			if(pids!=null&& pids.intValue()>0){
				categoryIdSet.add(pids);
			}
		}
		if(CollectionUtils.isEmpty(categoryIdSet)){
			return null;
		}
		List<Category> categoryTmp = categoryManageDao.getCategoryByIds(new ArrayList<Integer>(categoryIdSet));
		if(CollectionUtils.isEmpty(categoryTmp)){
			return null;
		}
		Map<Integer,String> map=new HashMap<Integer, String>();
		for (Category category : categoryTmp) {
			map.put(category.getId(), category.getName());
		}
		return map;
		
	}

	/**
	 * 根据form提交的表单数据进行参数设置
	 * @param category
	 * @return category
	 * @author shanyq
	 */
	public Category dealCategory(Category category) {
		if (category == null)
			return new Category();
		// 一级ID
		Integer firstId = category.getFirstid();
		// 二级ID
		Integer secoundId = category.getSecoundid();
		// 如果一级ID不为null并且一级ID不为0二级ID为null,则只根据一级ID查
		if (!firstId.equals(0) && firstId != null && secoundId.equals(0)) {
			category.setId(firstId);
		}
		// 如果一级ID不为null二级ID也不为null并且二级ID不为0,则按照二级ID查询
		if (firstId != null && secoundId != null && !secoundId.equals(0)) {
			category.setId(secoundId);
		}
		if (firstId.equals(0)) {
			category.setPid(firstId);
		}
		return category;
	}

	/**
	 *  根据对象ID是否存在判断保存或者更新操作
	 *  @param category
	 *  @return void
	 *  @athor shanyq
	 */
	public void saveOrUpdateCategory(Category category) {
		if (category == null)
			return;
		//如果ID为空,则是添加操作
		if (category.getId() == null) {
			//正常状态
			category.setStatus(0);
			//如果上级不为空,则层级+1
			if (!category.getPid().equals(0)) {
				category.setLayer(1);
			} else {
				category.setLayer(0);
			}
			category.setDisplayOrder(0);
			category.setQuestions(0);
			try {
				categoryManageDao.saveCategory(category);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//如果ID不为NULL,则是更新操作
		} else {
			try {
				categoryManageDao.updateCateogry(category);
				if (!category.getOldName().equals(category.getName())) {
					//更新问题表中的cate冗余字段
					categoryManageDao.updateQuestions(category);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		this.callFrontend();
	}

	/**
	 * 更新分级的状态(开启/关闭)
	 * @params id 主键, status 状态(0:正常,1:关闭)
	 * @return void
	 * @author shanyq
	 */
	public void updateStatus(Integer id, Integer pid, Integer status) {
		if (id != null) {
			Category category = new Category();
			category.setId(id);
			category.setStatus(status);
			//如果更新为关闭状态,则关闭时间设置为当前时间
			if (status.equals(1)) {
				category.setCloseTime(new Date());
			} else {
				//反之则为null
				category.setCloseTime(null);
			}
			try {
				categoryManageDao.updateCateogry(category);
				//如果该分类为一级分类,则将该分类下所有的分类全部关闭或者开启
				if (pid.intValue() == 0) {
					categoryManageDao.updateStatusByPid(id,status);
				}
				this.callFrontend();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新问答系统分类缓存
	 * @author wangjh
	 * Mar 19, 2012 5:09:13 PM
	 */
	public void callFrontend() {
		StringBuffer wenDaUrl = new StringBuffer();
		wenDaUrl.append(Resources.getString("sys.url.wenda") + "/updateCategory");
		System.out.println("更新分类缓存url：" + Resources.getString("sys.url.wenda") + "/updateCategory");
		//修改更新问答中的分类
		try {
			callService(wenDaUrl.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新问答系统分类缓存出错！  更新分类缓存url：" + wenDaUrl);
		}
	}

	/**
	 *  更新定位
	 */
	public void updateLocation(String ids, String locations) {
		if (StrUtils.notEmpty(ids) && StrUtils.notEmpty(locations)) {
			String[] idsArr = ids.split(",");
			String[] locationsArr = locations.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				try {
					this.categoryManageDao.updateLocationById(idsArr[i], locationsArr[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
