package com.nuoshi.console.common.util;

public class SmcConst {
	/**
	 * 提示信息
	 */
	public static final String MESSAGE = "message";
	public static final String RESULT = "result";
	// 用户信息，存放在session 中
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";

	// 身份认证图片的前缀
	public static final String HEAD_PHOTO_URL = "http://head.taofang.com";
	public static final String ID_PHOTO_URL = "http://adminpic.taofang.com";

	// 中介套餐类型
	public static final String AGENT_APPLY_TYPE_ONE = "免费版";
	public static final String AGENT_APPLY_TYPE_TWO = "入门版";
	public static final String AGENT_APPLY_TYPE_THREE = "标准版";
	public static final String AGENT_APPLY_TYPE_FOUR = "精英版";
	public static final String AGENT_APPLY_TYPE_FIVE = "专家版";
	public static final String AGENT_APPLY_TYPE_SIX = "至尊版";

	// 中介收费类型
	public static final int AGENT_APPLY_TYPE1 = 0;
	public static final int AGENT_APPLY_TYPE2 = 1;
	public static final String AGENT_APPLY_FREE = "免费用户";
	public static final String AGENT_APPLY_MONEY = "付费用户";

	// 照片标志
	public static final int PHOTO_FLAGS_TEMP = -1;

	// 中介验证状态
	public static final int AGENT_VERIFIED = 0;
	public static final int AGENT_VERIFY_READY = 1;
	public static final int AGENT_NOT_VERIFY = 2;
	public static final int AGENT_REJECTED = 3;
	
	public static final int AGENT_VERIFY_NEW = 0;//已认证
	public static final int AGENT_NOT_VERIFY_NEW = 1;//未认证
	public static final int AGENT_VERIFY_AND_READ = 2;//已认证-待审核
	public static final int AGENT_NOT_VERIFY_AND_READ = 3;//未认证-待审核
	public static final int AGENT_VERIFY_AND_REJECT = 4;//已认证-拒绝
	public static final int AGENT_NOT_VERIFY_AND_REJECT = 5;//未认证-拒绝

	// 用户搜索条件
	public static final String SEARCH_TYPE_ID = "id";
	public static final String SEARCH_TYPE_NAME = "name";
	public static final String SEARCH_TYPE_BRAND = "brand";
	public static final String SEARCH_TYPE_ADDRESS = "address";
	public static final String SEARCH_TYPE_MOBILE = "mobile";
	public static final String SEARCH_TYPE_ICCARD = "iccard";

	public static final String SEARCH_VERIFIED = "0";
	public static final String SEARCH_VERIFY_READY = "1";
	public static final String SEARCH_VERIFY_NOT_READY = "2";

	public static final String SEARCH_ACCOUNT_FREE = "0";
	public static final String SEARCH_ACCOUNT_MONEY = "1";

	public static final String SEARCH_TXT_NULL = "请输入搜索内容";
	public static final String SEARCH_SELECT_NULL = "请选择";

	public static final String SEARCH_STARTDATE_NULL = "起始日期";
	public static final String SEARCH_ENDDATE_NULL = "终止日期";
	
	

	// 身份认证拒绝理由类型
	public static final int REJECT_IDCARD = 0;
	public static final int REJECT_HEAD = 1;
	public static final int REJECT_NAMECARD = 2;

	// 发送中介短信类型
	public static final int AGENT_MSG = 10;

	// 公司门店为空
	public static final String AGENT_BRAND_ADDR_NULL = "未验证";

	// 房源状态
	public static final int HOUSE_STATUS_ALL = 0;// 全部
	public static final int HOUSE_STATUS_ONSHELVE = 1;// 在线
	public static final int HOUSE_STATUS_UNSHELVE = 2;// 待发布
	public static final int HOUSE_STATUS_OUTOFDATE = 3;// 过期
	public static final int HOUSE_STATUS_ILLEGAL = 4;// 违规
	public static final int HOUSE_STATUS_ONSHELVE_UNCHECK = 5;// 在线未审核
	public static final int HOUSE_STATUS_ONSHELVE_CHECKED = 6;// 在线审核通过
	public static final int HOUSE_STATUS_DELETED = 7;// 删除
	public static final int HOUSE_STATUS_DRAFTBOX = 8;// 草稿箱
	public static final int HOUSE_STATUS_NOAUDIT_AGENT = 9;// 未审核经纪人房源

	// 用户角色
	public static final int USER_ROLE_NOT_REGISTER = 0;// 全部
	public static final int USER_ROLE_NORMAL = 1;// 在线
	public static final int USER_ROLE_AGENT = 2;// 待发布

	// 信息质量
	public static final int CONTROL_SEARCH_SELECT_NULL = -1;
	public static final String CONTROL_SEARCH_COMMENTS_TXT_NULL = "请输入关键字";
	public static final String CONTROL_SEARCH_REASON_TXT_NULL = "请输入原因";
	
	//用户黑白名单
	/**
	 * 经纪人
	 */
	public static final int USER_BLACKLIST_USER_TYPE_AGENT=2;
	/**
	 * 普通用户
	 */
	public static final int USER_BLACKLIST_USER_TYPE_NORMAL=1;
	/**
	 * 未注册
	 */
	public static final int USER_BLACKLIST_USER_TYPE_UNREGISTERED=0;
	
	
	/**
	 * 经纪人
	 */
	public static final int USER_BLACKLIST_LIST_TYPE_AGENT=101;
	/**
	 * 问答
	 */
	public static final int USER_BLACKLIST_LIST_TYPE_WENDA=102;
	
	/**
	 * 有效
	 */
	public static final int USER_BLACKLIST_VALID=1;
	/**
	 * 无效
	 */
	public static final int USER_BLACKLIST_INVALID=0;
	/**
	 * 问题答案进入黑名单状态（0 不是  1 是）
	 */
	public static final int BLACK_TYPE_BLACK=1;
	/**
	 * 问题答案进入黑名单状态（0 不是  1 是）
	 */
	public static final int BLACK_TYPE_UNBLACK=0;
	
	
	
}
