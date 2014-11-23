package com.nuoshi.console.domain.auditHistory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 审核历史一页用一条记录
 */
public class NewSubAuditHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String photo_ids;
	private String audit_ids;
	private int audit_step;
	private String auditStepString;// 对应audit_step
	private Date audit_time;
	private String dealer_name;
	private int dealer_id;
	private String audit_results;
	private int photoCount;
	private int house_type;// 本批房源类型
	private int re_result;// 复审结果
	
	// 主表字段
	private String authorName;
	private int authorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoto_ids() {
		return photo_ids;
	}

	public void setPhoto_ids(String photo_ids) {
		this.photo_ids = photo_ids;
	}

	public String getAudit_ids() {
		return audit_ids;
	}

	public void setAudit_ids(String audit_ids) {
		this.audit_ids = audit_ids;
	}

	public int getAudit_step() {
		return audit_step;
	}

	public void setAudit_step(int audit_step) {
		this.audit_step = audit_step;
	}

	public Date getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}

	public int getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(int dealer_id) {
		this.dealer_id = dealer_id;
	}

	public String getAudit_results() {
		return audit_results;
	}

	public void setAudit_results(String audit_results) {
		this.audit_results = audit_results;
	}

	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuditStepString() {
		String auditStepString = "";
		switch (this.audit_step) {
		case 1:
			auditStepString = "户型图";
			break;
		case 2:
			auditStepString = "室内图";
			break;
		case 3:
			auditStepString = "小区图";
			break;

		default:
			break;
		}
		return auditStepString;
	}

	public void setAuditStepString(String auditStepString) {
		this.auditStepString = auditStepString;
	}

	public int getHouse_type() {
		return house_type;
	}

	public void setHouse_type(int house_type) {
		this.house_type = house_type;
	}

	public int getRe_result() {
		return re_result;
	}

	public void setRe_result(int re_result) {
		this.re_result = re_result;
	}

}
