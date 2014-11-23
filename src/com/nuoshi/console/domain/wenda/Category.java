package com.nuoshi.console.domain.wenda;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @description
 * @author shanyuqiang
 * @createDate Feb 29, 2012 2:06:48 PM
 * @email shanyq@taofang.com
 * @version 1.0
 */
public class Category {
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 分类名字
	 */
	private String name;
	/**
	 * 拼音
	 */
	private String pinyin;
	/**
	 * 父ID
	 */
	private Integer pid;
	/**
	 * 层级
	 */
	private Integer layer;
	/**
	 * 显示顺序
	 */
	private Integer displayOrder;
	/**
	 * 问题个数
	 */
	private Integer questions;
	/**
	 * 搜索关键字
	 */
	private String keyword;
	/**
	 * 分类状态
	 */
	private Integer status;
	/**
	 * 关闭时间
	 */
	private Date closeTime;
	/**
	 * 主题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String describle;
	/**
	 * 页面关键词
	 */
	private String pageword;
	/**
	 *  定位
	 */
	private Integer location;
	// ------显示字段
	/**
	 * 关闭时间
	 */
	private String closeTimeStr;
	/**
	 * 上级分类
	 */
	private String parentName;
	// ------查询字段
	/**
	 * 一级查询参数(一级分类ID)
	 */
	private Integer firstid;
	/**
	 * 二级查询参数(二级分类ID)
	 */
	private Integer secoundid;
	
	private String oldName;
	/**
	 * 分类url。2012-11-1添加
	 */
	private String cateUrl;
	/**
	 * 问题url的分类id部分/pId/id/。2012-11-1添加
	 * 2013-1-4 修改  改为短链接
	 */
	private String questionUrlHead;
	
	public String getQuestionUrlHead() {
		if(StringUtils.isNotBlank(questionUrlHead)){
			return questionUrlHead;
		}else if(pid!=null&&pid>0){
			StringBuffer str=new StringBuffer();
			if(id!=null&&id>0){
				str.append(this.getId());
			}else{
				str.append(this.getPid());
			}
			return str.toString();
		}else{
			return questionUrlHead;
		}
	}

	public void setQuestionUrlHead(String questionUrlHead) {
		this.questionUrlHead = questionUrlHead;
	}

	public String getCateUrl() {
		if(StringUtils.isNotBlank(cateUrl)){
			return cateUrl;
		}else if(pid!=null&&pid>0){
			StringBuffer str=new StringBuffer();
			
			if(id!=null&&id>0){
				str.append("http://www.taofang.com/wen/"+this.getId()+"/");
			}else{
				str.append("http://www.taofang.com/wen/"+this.getPid()+"/");
			}
			return str.toString();
		}else{
			return cateUrl;
		}
	}

	public void setCateUrl(String cateUrl) {
		this.cateUrl = cateUrl;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getTitle() {
		return title;
	}
	
	/*----- 构造器 ------*/
	public Category() {
	}

	public Category(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getQuestions() {
		return questions;
	}

	public void setQuestions(Integer questions) {
		this.questions = questions;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getCloseTimeStr() {
		return closeTimeStr;
	}

	public void setCloseTimeStr(String closeTimeStr) {
		this.closeTimeStr = closeTimeStr;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getFirstid() {
		return firstid;
	}

	public void setFirstid(Integer firstid) {
		this.firstid = firstid;
	}

	public Integer getSecoundid() {
		return secoundid;
	}

	public void setSecoundid(Integer secoundid) {
		this.secoundid = secoundid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public String getPageword() {
		return pageword;
	}

	public void setPageword(String pageword) {
		this.pageword = pageword;
	}
}
