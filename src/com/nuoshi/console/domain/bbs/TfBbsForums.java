package com.nuoshi.console.domain.bbs;

import java.util.Date;

import com.nuoshi.console.common.BbsGlobals;
import com.nuoshi.console.service.ForumService;
import com.nuoshi.console.service.LocaleService;

public class TfBbsForums {

	private Integer id;

	private String idAndForumType;
	private String visibleRoleName;
	private int displayOrder;

	private Integer cityId;

	private Byte forumType;

	private Byte visibleRole;

	private String name;

	private Byte status = 1;

	private Integer threads;

	private Integer posts;

	private Integer todayPosts;

	private String lastPost;

	private Date addTime;

	private String author;

	private Date lastUpdateTime;

	private String forumNo;

	private Integer parentId;

	private Integer authorId;

	private String keyword;

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public void setVisibleRoleName(String visibleRoleName) {
		this.visibleRoleName = visibleRoleName;
	}

	public String getIdAndForumType() {
		return idAndForumType;

	}

	public String getCityName() {
		return LocaleService.getName(this.getCityId());
	}

	public String getVisibleRoleName() {
		return BbsGlobals.getVisibleRoleName(this.visibleRole);
	}

	public void setIdAndForumType(String idAndForumType) {
		this.idAndForumType = idAndForumType;
	}

	public String getTurnoffName() {
		return BbsGlobals.getStatusName(this.status);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private String descript;

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	private String title;

	private Date turnofftime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Byte getForumType() {
		return forumType;
	}

	public void setForumType(Byte forumType) {
		this.forumType = forumType;
	}

	public Byte getVisibleRole() {
		return visibleRole;
	}

	public void setVisibleRole(Byte visibleRole) {
		this.visibleRole = visibleRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

	public Integer getPosts() {
		return posts;
	}

	public void setPosts(Integer posts) {
		this.posts = posts;
	}

	public Integer getTodayPosts() {
		return todayPosts;
	}

	public void setTodayPosts(Integer todayPosts) {
		this.todayPosts = todayPosts;
	}

	public String getLastPost() {
		return lastPost;
	}

	public void setLastPost(String lastPost) {
		this.lastPost = lastPost;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getForumNo() {
		return forumNo;
	}

	public void setForumNo(String forumNo) {
		this.forumNo = forumNo;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParent() {
		String p = null;
		String pp = null;
		if (this.getParentId() > 0) {
			TfBbsForums pTmp = ForumService.getForum(this.getParentId());
			if (pTmp != null) {
				p = pTmp.getName();
				System.out.println(pTmp.getParentId());
				if (pTmp.getParentId()!=null&&pTmp.getParentId().intValue() > 0) {
					TfBbsForums ppTmp = ForumService.getForum(pTmp
							.getParentId());
					if (ppTmp != null) {
						pp = ppTmp.getName();
					}

				}
			}
		}

		String parentStr = "";
		if (pp != null) {
			parentStr += pp + "-";
		}
		if (p != null) {
			parentStr += p + "-";
		}
		if (parentStr.length() > 0) {
			parentStr = parentStr.substring(0, parentStr.length() - 1);
		}
		return parentStr;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTurnofftime() {
		return turnofftime;
	}

	public void setTurnofftime(Date turnofftime) {
		this.turnofftime = turnofftime;
	}

}