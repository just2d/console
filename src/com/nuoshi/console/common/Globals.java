package com.nuoshi.console.common;

import java.util.HashMap;
import java.util.Map;

public class Globals {
	
	/** 房源管理权限  ,对应数据库的房源管理角色*/
	public static final int PERMISSIOONS_HOUSE_MANAGE = 1; 
	
	public static final int pwdMinLen = 6;
	public static final int pwdMaxLen = 20;
	public static final String defaultHousePhotoTiny = "/s/house_s.png";
	public static final String defaultHousePhotoBrowse = "/m/house_m.png";
	public static final long oneDayInMilliSeconds = 86400000;
	public static final String mainContext = Resources.getString("sys.url.main");
	public static final String maskPath = Resources.getString("sys.path.mask");// "/opt/pof/head";
	public static final String tmp = Resources.getString("sys.path.tmp");// "/opt/pof/head";
	public static final String ESTATE_TMP = Resources.getString("sys.path.estate.photo.tmp");// "/opt/pof/head";
	
	public static final int SESSION_DESTORY_EMPTY_TASK_NUM = 1000;
	/**
	 * 经纪人身份认证状态数值： 0:初始 1:通过 2:完成 3:修改 4:拒绝'
	 */
	public static final String STATUS_NULL = "000";
	public static final String STATUS_OK = "111";
	public static final int STATUS_INI_VALUE = 0;
	public static final int STATUS_OK_VALUE = 1;
	public static final int STATUS_COMPLETE_VALUE = 2;
	public static final int STATUS_EDIT_VALUE = 3;
	public static final int STATUS_REJECT_VALUE = 4;
	
	public static final String HOUSE_URGENT = "Y";
	public static final String HOUSE_FRESH = "Y";
	
	
	/**
	 * 专题部分用到的常量
	 */
	public static final int TAG_LISTPAGE_DEFAULT = 0;
	public static final int TAG_LISTPAGE_ZUIXINFANGYUAN = 1;
	public static final int TAG_LISTPAGE_JISHOUFANGYUAN = 2;
	
	public static final int TAG_VIEWTYPE_PIC = 0;
	public static final int TAG_VIEWTYPE_WORD = 1;
	
	public static final int TAG_TYPE_RESALE = 0;
	public static final int TAG_TYPE_RENT = 1;
	public static final int TAG_TYPE_ESTATE = 2;
	
	public static final int TOPIC_DEPLOY_STATUS = 1;
	public static final int TOPIC_UNDEPLOY_STATUS = 0;
	
	public static final int TOPIC_CHECK_OK = 0;
	public static final int TOPIC_CHECK_REJECT = 1;
	public static final int TOPIC_CHECK_UNCHECK = 2;
	
	public static final int PLATE_TYPE_TOPIC = 0;
	public static final int PLATE_TYPE_TAG = 1;
	
	public static final int PLATE_STATUS_OPEN = 0;
	public static final int PLATE_STATUS_CLOSED = 1;
	
	public static final String TOPIC_ROLE = "TU";
	
	public static final int TOPIC_ALL_TODAY = 1;
	public static final int TOPIC_ALL_YESTERDAY = 2;
	public static final int TOPIC_ALL_MY_TODAY = 3;
	public static final int TOPIC_ALL_MY_YESTERDAY = 4;
	public static final int TOPIC_ALL_MY = 5;
	
	public static final String TAG_CODE_START = "{[taofangTag:_";
	public static final String TAG_CODE_END = "_]}";
	public static final String TOPIC_KEYWORD = "{[keyword]}";
	public static final String TOPIC_DESCRIPTION = "{[description]}";
	public static final String TOPIC_TITLE = "{[title]}";
	public static final String TAG_PREVIEW_HTML = "<html><head><title></title></head><body>{[tag]}</body><html>";
	
	public static final int DEFAULT_PAGESIZE = 10;
	
	public static final String SEARCH_RESALE_URL = Resources.getString("search_engine_addr_resale") + "/searchResale/select/";
	public static final String SEARCH_RENT_URL = Resources.getString("search_engine_addr_rent") + "/searchRent/select/";
	public static final String SEARCH_ESTETE_URL = Resources.getString("search_engine_addr_estate") + "/communitySearch/select/";
	
	/** 图片审核状态待审核 */
	public static final int PHOTO_STATUS_UNAUDITED = 0;
	/** 图片审核状态通过 */
	public static final int PHOTO_STATUS_PASS = 1;
	/** 图片审核状态拒绝 */
	public static final int PHOTO_STATUS_REJECT = 2;
	
	/**
	 * 经纪人对房源的操作日志
	 */
	public static final int ACTION_SHELVE = 0;
	public static final int ACTION_UNSHELVE = 1;
	public static final int ACTION_URGENT = 2;
	public static final int ACTION_FRESH = 3;
	public static final int ACTION_DELETE = 4;
	public static final int ACTION_UNSHELVE_LABEL = 5;
	public static final int ACTION_DELETE_SHELVE = 6;
	public static final int ACTION_DELETE_LABEL = 7;
	public static final int ACTION_REJECT = 8;
	public static final int ACTION_REJECT_LABEL = 9;
	
	public static final int RESALE_TYPE = 1;
	public static final int RENT_TYPE = 2;
	
	/*------------房源审核相关  wangjh-------------------*/
	/**
	 * 审核房源每次提取任务的数量
	 */
	public static final int AUDIT_HOUSE_COUNT=10;
	/**
	 * 清空用户领取任务的数量
	 */
	public static final int EMPTY_TASK_COUNT=40;
	/**
	 * auditStep审核步骤--基本信息审核
	 */
	public static final int AUDIT_BASE_INFO=101;
	/**
	 * auditStep审核步骤--户型图审核
	 */
	public static final int AUDIT_HOUSEHOLD_PHOTO=102;
	/**
	 * auditStep审核步骤--小区图审核
	 */
	public static final int AUDIT_ESTATE_PHOTO=103;
	/**
	 * auditStep审核步骤--室内图审核
	 */
	public static final int AUDIT_INDOOR_PHOTOO=104;
	
	
	/*-----新房源审核结果相关------*/
	/**
	 * 审核结果使用的常量--通过
	 */
	public static final int AUDIT_RESULT_PASS=-1;
	/**
	 * 审核结果使用的常量--打回
	 */
	public static final int AUDIT_RESULT_REJECT=-2;
	/**
	 * 审核结果使用的常量--未通过前被复审打回
	 */
	public static final int AUDIT_RESULT_NOPASS_REJECT=-3;
	/**
	 * 任务列表中审核结果使用的常量--未分配审核人员
	 */
	public static final int AUDIT_RESULT_UNDISTRIBUTED=0;
	
	
	/**
	 * 经纪人类型
	 */
	public static final int AGENT_TYPE=3;
	/**
	 * 房源类型-出租房
	 */
	public static final int HOUSE_TYPE_RENT=1;
	/**
	 * 房源类型-二手房
	 */
	public static final int HOUSE_TYPE_RESALE=2;
	
	/**
	 * 城市北京id
	 */
	public static final int BEIJINGID = 1;
	
	public static final long MILI_SECOND_DAY = 86400000;
	
	public static final int AUDIT_HISTORY_INDOOR = 2;
	public static final int AUDIT_HISTORY_LAYOUT = 1;
	public static final int AUDIT_HISTORY_COMMUNITY = 3;
	public static final int AUDIT_HISTORY_BASEINFO = 4;
	public static final int AUDIT_HISTORY_AGENT_IDCARD_IMG = 5;
	public static final int AUDIT_HISTORY_AGENT_HEAD_IMG = 6;
	
	/**
	 * 套餐的状态值 
	 */
	public static final int PACKAGE_ACTIVE_STATUS_NO_USE = 0;//未使用
	public static final int PACKAGE_ACTIVE_STATUS_USE = 1; //使用中
	public static final int PACKAGE_ACTIVE_STATUS_EXPIRE = 4; //过期
	public static final int PACKAGE_FREE_ID = 7; //免费套餐
	
	/*------------经纪人审核相关  wangjh-------------------*/
	/**
	 * 审核经纪人每次提取任务的数量
	 */
	public static final int AUDIT_AGENT_COUNT=5;
	
	public static final String mLogoPath = "/m/logo_m.png";
	/*--------------------打回理由 -----------*/
	/**
	 * 身份证理由
	 */
	public static final int ID_CARD_REASON=0;
	/**
	 * 头像理由
	 */
	public static final int HEAD_PORTRAIT_REASON=1;
	/**
	 * 名片理由
	 */
	public static final int CALLING_CARD_REASON=2;
	/**
	 * 二手房审核违规原因
	 */
	public static final int RESALE_ILLEGAL_REASON=3;
	/**
	 * 二手房审核删除理由
	 */
	public static final int RESALE_DELETE_REASON=4;
	/**
	 * 租房审核违规原因
	 */
	public static final int RENT_ILLEGAL_REASON=5;
	/**
	 * 租房审核删除理由
	 */
	public static final int RENT_DELETE_REASON=6;
	/**
	 * 图片审核删除理由
	 */
	public static final int PHOTO_DELETE_REASON=7;
	
	/*****************经济人操作历史记录的类型*********************/
	/**
	 * 记录开关经济人在线发布房源的操作
	 */
	public static final int HISTORY_SWITCH_AGENT=1;
	
	/*****************问答审核************************/
	/**
	 * 审核问答每次提取任务的数量
	 */
	public static final int AUDIT_WEN_DA_COUNT=10;
	/**
	 * 问答审核类型---问题
	 */
	public static final int WEN_DA_TYPE_QUESTION=1;
	/**
	 * 问答审核类型---答案
	 */
	public static final int WEN_DA_TYPE_ANSWER=2;
	/**
	 * 问题审核状态：待审核
	 */
	public static int QUESTION_STATUS_WAIT_VERIFY = 0;
	/**
	 * 问题审核状态：已通过审核
	 */
	public static int QUESTION_STATUS_PASS = 1;
	/**
	 * 问题审核状态：删除
	 */
	public static int QUESTION_STATUS_DELETE = 2;
	/**
	 * 问题解决的状态:新建
     */
    public static int QUESTION_SOLVING_STATUS_CREATE=0;
	/**
	 * 问题解决的状态:待回答
     */
    public static int QUESTION_SOLVING_STATUS_TO_SOLVE=1;
    /**
     * 问题解决的状态:已解决
     */
    public static int QUESTION_SOLVING_STATUS_RESOLVED=2;
    /**
     * 问题解决的状态:关闭
     */
    public static int QUESTION_SOLVING_STATUS_CLOSE=3;
    /**
     * 回答待审核状态
     */
    public static int ANSWER_STATUS_WAIT_VERIFY = 0;
    /**
     * 回答已审核状态
     */
    public static int ANSWER_STATUS_PASS = 1;
    /**
     * 回答已删除状态
     */
    public static int ANSWER_STATUS_DELETE = 2;
	/**
	 * 问题所有的审核状态
	 */
	public static Map<Integer,String> QUESTION_STATIC_MAP=new HashMap<Integer, String>();
	static{
		QUESTION_STATIC_MAP.put(QUESTION_STATUS_WAIT_VERIFY, "待审核");
		QUESTION_STATIC_MAP.put(QUESTION_STATUS_PASS, "审核通过");
		QUESTION_STATIC_MAP.put(QUESTION_STATUS_DELETE, "已删除");
	}
	/**
	 * 问题所有的处理状态
	 */
	public static Map<Integer,String> QUESTION_SOLVING_MAP=new HashMap<Integer, String>();
	static{
		QUESTION_SOLVING_MAP.put(QUESTION_SOLVING_STATUS_CREATE, "新建问题");
		QUESTION_SOLVING_MAP.put(QUESTION_SOLVING_STATUS_TO_SOLVE, "待回答");
		QUESTION_SOLVING_MAP.put(QUESTION_SOLVING_STATUS_RESOLVED, "已解决");
		QUESTION_SOLVING_MAP.put(QUESTION_SOLVING_STATUS_CLOSE, "问题关闭");
	}
	/**
	 * 回答所有的审核状态
	 */
	public static Map<Integer,String> ANSWER_STATIC_MAP=new HashMap<Integer, String>();
	static{
		ANSWER_STATIC_MAP.put(ANSWER_STATUS_WAIT_VERIFY, "待审核");
		ANSWER_STATIC_MAP.put(ANSWER_STATUS_PASS, "审核通过");
		ANSWER_STATIC_MAP.put(ANSWER_STATUS_DELETE, "已删除");
	}
    
    
    /*户型是否有方向*/
    /**
     * 没有户型方向
     */
    public  static int NO_DIRECTION=0;
    /**
     * 有户型方向
     */
    public  static int HAVE_DIRECTION=1;
    
    public static Map<Integer, String> facetoTFMap = new HashMap<Integer, String>();
    public static Map<Integer, String> faceto58Map = new HashMap<Integer, String>();
    static{
	    facetoTFMap.put(0, "不限");
		facetoTFMap.put(1, "南");
		facetoTFMap.put(2, "东");
		facetoTFMap.put(3, "西");
		facetoTFMap.put(4, "北");
		facetoTFMap.put(5, "东南");
		facetoTFMap.put(6, "西南");
		facetoTFMap.put(7, "东北");
		facetoTFMap.put(8, "西北");
		facetoTFMap.put(9, "南北");
		facetoTFMap.put(10, "东西");
		//58定义
		faceto58Map.put(0, "不限");
		faceto58Map.put(1, "东");
		faceto58Map.put(2, "西");
		faceto58Map.put(3, "南");
		faceto58Map.put(4, "北");
		faceto58Map.put(5, "东西");
		faceto58Map.put(6, "南北");
		faceto58Map.put(7, "东南");
		faceto58Map.put(8, "东北");
		faceto58Map.put(9, "西南");
		faceto58Map.put(10, "西北");
    }
    
    /*论坛*/
    /**
     * 正常帖子状态.
     */
    public static int FROUM_STATUS_NORMAL = 0;
    /**
     * 黑名单帖子状态.
     */
    public static int FROUM_STATUS_BLACK_USER = 3;
    
}
