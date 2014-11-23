package com.nuoshi.console.domain.feedback;

import java.util.Date;

public class FeedBack {
	private Integer id;

	private String author;

	private String mobile;

	private String email;

	private Date cts;

	private Date uts;

	private String content;

	private String reply;

	private Integer adminid;

	private Integer flags;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author == null ? null : author.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

	public Date getUts() {
		return uts;
	}

	public void setUts(Date uts) {
		this.uts = uts;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply == null ? null : reply.trim();
	}

	public Integer getAdminid() {
		return adminid;
	}

	public void setAdminid(Integer adminid) {
		this.adminid = adminid;
	}

	public Integer getFlags() {
		return flags;
	}

	public void setFlags(Integer flags) {
		this.flags = flags;
	}
}