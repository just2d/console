package com.nuoshi.console.domain.base;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.NetClient;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.Escape;
import com.nuoshi.console.common.util.StringFormat;
import com.nuoshi.console.domain.agent.Broker;
import com.nuoshi.console.service.LocaleService;

/**
 * Created by IntegerelliJ IDEA. User: pekky Date: 2009-11-24 Time: 15:42:37 To
 * change this template use File | Settings | File Templates.
 */
public class House {
	
	public static final int HOUSE_UPDATE_FLAG_TRUE = 0;
	public static final int HOUSE_UPDATE_FLAG_FALSE = 1;
	
	public static final int RESALE_TYPE = 1;
	public static final int RENT_TYPE = 2;
	//审核状态
	/**
	 * 未审核的房源状态
	 */
	public static final Integer AUDIT_STATUS_UNVERIFIED = 0;//未审核
	/**
	 * 房源基本信息和图片审核都通过的房源状态
	 */
	public static final Integer AUDIT_STATUS_OK = 1;//审核通过
	/**
	 * 打回的房源状态
	 */
	public static final Integer AUDIT_STATUS_BACK = 2;//退回
	/**
	 * 房源基本信息审核通过，待图片审核的房源状态
	 */
	public static final Integer AUDIT_STATUS_BASE_OK = 3;
	
	
	
	//房屋状态
	public static final Integer HOUSE_STATUS_DELETE_FOREVER = -1;//
	public static final Integer HOUSE_STATUS_UNSHELVED = 0;//待发布 
	public static final Integer HOUSE_STATUS_SHELVED = 1;//已发布
	public static final Integer HOUSE_STATUS_ILLEGAL = 2;// 违规房源
	public static final Integer HOUSE_STATUS_OUTDATE = 3;//过期房源
	public static final Integer HOUSE_STATUS_DELETED = 4;//已删除
	public static final Integer HOUSE_STATUS_DRAFT = 5;          //草稿
	public static final int HOUSE_STATUS_NOAUDIT_AGENT = 6;// 未审核经纪人的房源
	public static final String HOUSE_ACTION_LABEL_VALUE = "Y";
	public static float HOUSE_BASE_SCORE = 3.53f; //房屋质量总分
	
	public static final String HOUSE_VIP_FLAG_YES = "Y";
	public static final String HOUSE_VIP_FLAG_NO = "N";
	
	/**
	 * 视频标签
	 */
	public static String HOUSE_LABEL_VCR ="V";
	/**
	 * 新推标签
	 */
	public static String HOUSE_LABEL_XT ="N";
	/**
	 *随时看房标签
	 */
	public static String HOUSE_LABEL_SSKF ="K";
	

	private String houseLabelName;
	
	private int updateBase;
	// 价格
	private Integer price;

	// 总楼层数
	private Integer totalfloor;

	// 户型
	private String flattype = "";

	// 第几层
	private Integer floor;

	// 房屋类型
	private Byte type;

	// 发布时间
	private Timestamp pubdate;

	// 编辑时间
	private Timestamp editdate;
	
	private Timestamp enterdate;
	//基本分数
	private Double baseScore;
	
	//额外分数（手动调整排序时用）
	private Double extraScore;
	//搜索分数
	private Double searchScore; 
	

	// 封面照片id
	private Integer photoid;

	// 封面照片 小尺寸
	private String phototiny = "";

	// 封面照片 中尺寸
	private String photobrowse = "";
	// 标签词
	private String[] labelWords ;

	// 详细信息
	private String extinfo;

	// 朝向
	private Byte faceto;

	// 装修
	private Byte decoration;

	// 房屋年份
	private Short completion;
	
	 // 基本设施
	private Integer facility;


	// 锁定时间
	private Timestamp lockts;

	// 最后更新时间
	private Timestamp rts;

	// 该房源的室内图数量
	private Integer photoCount;
	
	private Integer layoutCount;
	
	private Integer innerCount;
	
	private Integer communityCount;
	
	//房源内部编号
	//private String userInnerCode;
	

	private String estatefacility;
	
	private String estatetraffic;
	
	//设置为加急状态的时间
	private Timestamp urgenttime;
	
	private Integer id;

	// 卧室数量
	private Byte beds;
	// 卫生间数量
	private Byte baths;
	// 客厅数量
	private Byte livings;

	// 发布者id
	private Integer authorid;

	// 发布者名字
	private String author;
	
	private String authorPhone;
	
	// 发布者的中介公司信息
	private Broker broker;

	// 所在城市
	private Integer cityid = new Integer(1);

	// 所在城区
	private Integer distid;

	// 所在板块
	private Integer blockid;

	// 地址
	private String addr;

	// 房源标题
	private String title;

	// 房屋面积
	private Integer area;

	// 小区id
	private Integer estateid;
	
	// 备案编号
	private String recordCode;
	// 标签词
	private String labelWord;

	// 小区名字
	private String estatename;

	
	private Integer audit_status;
	
	private Integer house_status;
	
	// 标签
	private Set<String> labels = new TreeSet<String>();
	// 标签
	private String houseLabel;
	 
	
	private Integer isTodayFresh;
	
	//违规原因
	private String invalidreason;
	
	//昨日点击量
	private int yesClickCount;
	
	private int photoStatus;
	
	private Timestamp vcrSubmitTime;
	
	private int complainNum;
	
	private Integer vcrCheckStatus;
	
	private List<RejectPhoto> rejectPhoto;
	
	/**
	 * 评价人数
	 */
	private int evaluationCount;
	
	/**
	 * 评价平均分
	 */
	private float evaluationPoint;
	
	private int realFlag;
	
	private int evaluationVisible;
	
	private int sourceId;
	
	private int picMode;
	
	private int falseHouseReport;
	
	private Timestamp appealExpiredTime;
	
	
	public int getUpdateBase() {
		return updateBase;
	}

	public void setUpdateBase(int updateBase) {
		this.updateBase = updateBase;
	}

	public boolean isTreated() {
		return audit_status != AUDIT_STATUS_UNVERIFIED;
	}

	public boolean isApprovaled() {
		return audit_status == AUDIT_STATUS_OK;
	}


	public boolean isOutofdate() {
		return house_status == HOUSE_STATUS_OUTDATE;
	}

	public boolean isOnshelf() {
		return house_status == HOUSE_STATUS_SHELVED;
	}
	
	public Integer getFacility() {
		return facility;
	}

	public void setFacility(Integer facility) {
		this.facility = facility;
	}

	public void setEnterdate(Timestamp enterdate) {
		this.enterdate = enterdate;
	}

	public boolean isDeleted() {
		return house_status == HOUSE_STATUS_DELETED;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}


	public String[] getLabelWords() {
		return labelWords;
	}

	public void setLabelWords(String[] labelWords) {
		this.labelWords = labelWords;
	}

	public String getFlattype() {
		return flattype;
	}

	public void setFlattype(String flattype) {
		this.flattype = flattype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = StringFormat.toHTMLString(Escape.trim(title), true);
	}

	public void setPubdate(Timestamp pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEstateid() {
		return estateid;
	}

	public void setEstateid(Integer estateid) {
		this.estateid = estateid;
	}

	public String getFloorLabel() {
		return floorLabel(floor, totalfloor);
	}

	public static String floorLabel(Integer floor, Integer total) {
		if (floor > 0) {
			return floor + "/" + total + "层";
		} else {
			return "地下室/" + total + "层";
		}
	}

	
	
	public static String floorLabel(Integer floor) {
		if (floor > 0) {
			return floor + "层";
		} else {
			return "地下室";
		}
	}

	
	
	

	
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getAuthorid() {
		return authorid;
	}

	public void setAuthorid(Integer authorid) {
		this.authorid = authorid;
	}

	public Integer getTotalfloor() {
		return totalfloor;
	}

	public void setTotalfloor(Integer totalfloor) {
		this.totalfloor = totalfloor;
	}

	public String getEstatename() {
		return estatename;
	}

	public void decodeBeforeSave() {
		//this.estatename = StringFormat.htmlDecode(this.estatename);
		//this.extinfo = StringFormat.htmlDecode(this.extinfo);
		//this.title = StringFormat.htmlDecode(this.title);
	}

	public void setEstatename(String estatename) {
		this.estatename = StringFormat.toHTMLString(Escape.trim(estatename),
				true);
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getDistid() {
		return distid;
	}

	public void setDistid(Integer distid) {
		this.distid = distid;
	}

	public Integer getBlockid() {
		return blockid;
	}

	public void setBlockid(Integer blockid) {
		this.blockid = blockid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public void setEditdate(Timestamp editdate) {
		this.editdate = editdate;
	}

	public Byte getBeds() {
		return beds;
	}

	public void setBeds(Byte beds) {
		this.beds = beds;
	}

	public Byte getBaths() {
		return baths;
	}

	public void setBaths(Byte baths) {
		this.baths = baths;
	}

	public Byte getLivings() {
		return livings;
	}

	public void setLivings(Byte livings) {
		this.livings = livings;
	}

	public String getPhototiny() {
		if (phototiny != null && !phototiny.isEmpty()) {
			return phototiny;
		} else {
			return Resources.getString("defaultHousePhotoTiny");
		}

	}

	public void setPhototiny(String phototiny) {
		this.phototiny = phototiny;
	}

	public String getPhotobrowse() {
		if (photobrowse != null && !photobrowse.isEmpty()) {
			return photobrowse;
		} else {
			return Resources.getString("defaultHousePhotoBrowse");
		}
	}

	public void setPhotobrowse(String photobrowse) {
		this.photobrowse = photobrowse;
	}

	public Integer getPhotoid() {
		return photoid;
	}

	public void setPhotoid(Integer photoid) {
		this.photoid = photoid;
	}

	public String getExtinfo() {
		return extinfo;
	}

	public void setExtinfo(String extinfo) {
		this.extinfo = extinfo;
	}

	public Byte getFaceto() {
		return faceto;
	}

	public void setFaceto(Byte faceto) {
		this.faceto = faceto;
	}

	public Byte getDecoration() {
		return decoration;
	}

	public void setDecoration(Byte decoration) {
		this.decoration = decoration;
	}

	public Short getCompletion() {
		return completion;
	}

	public void setCompletion(Short completion) {
		this.completion = completion;
	}


	public Timestamp getLockts() {
		return lockts;
	}

	public void setLockts(Timestamp lockts) {
		this.lockts = lockts;
	}

	public void setRts(Timestamp rts) {
		this.rts = rts;
	}




	public Timestamp getUrgenttime() {
		return urgenttime;
	}

	public void setUrgenttime(Timestamp urgenttime) {
		this.urgenttime = urgenttime;
	}



	public Integer getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Integer photoCount) {
		this.photoCount = photoCount;
	}

	public Integer getLayoutCount() {
		return layoutCount;
	}

	public void setLayoutCount(Integer layoutCount) {
		this.layoutCount = layoutCount;
	}

	public Integer getInnerCount() {
		return innerCount;
	}

	public void setInnerCount(Integer innerCount) {
		this.innerCount = innerCount;
	}

	public Integer getCommunityCount() {
		return communityCount;
	}

	public void setCommunityCount(Integer communityCount) {
		this.communityCount = communityCount;
	}
	
	public Integer getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(Integer audit_status) {
		this.audit_status = audit_status;
	}

	public Integer getHouse_status() {
		return house_status;
	}

	public void setHouse_status(Integer house_status) {
		this.house_status = house_status;
	}

	public String getEnterdate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(enterdate);
	}
	
	public String getEnterdateMin() {
		return new SimpleDateFormat("HH:mm").format(enterdate);
	}
	
	public String getEditdate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(editdate);
	}
	public String getEditdateMin() {
		return new SimpleDateFormat("HH:mm").format(editdate);
	}
	
	public String getLeftDays(Date time, Integer maxDay, String name) {
		long passed = System.currentTimeMillis() - time.getTime();

		long left = maxDay * Globals.oneDayInMilliSeconds - passed;
		if (left < 0) {
			return name + "已到期";
		} else {
			left /= Globals.oneDayInMilliSeconds;
			left += 1;
			return left + "天";
		}
	}

	public Timestamp getRtsTime() {
		return rts;
	}
	
	public String getRts() {
		return new SimpleDateFormat("yyyy-MM-dd").format(rts);
	}
	public String getRtsMin() {
		return new SimpleDateFormat("HH:mm").format(rts);
	}
	
	public String getPubdate() {
		if(pubdate==null) return null;
		return new SimpleDateFormat("yyyy-MM-dd").format(pubdate);
	}

	public String getPubdateMin() {
		return new SimpleDateFormat("HH:mm").format(pubdate);
	}

	public Integer getIsTodayFresh() {
		return isTodayFresh;
	}

	public void setIsTodayFresh(Integer isTodayFresh) {
		this.isTodayFresh = isTodayFresh;
	}

	public String getInvalidreason() {
		return invalidreason;
	}

	public void setInvalidreason(String invalidreason) {
		this.invalidreason = invalidreason;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getLabelWord() {
		return labelWord;
	}

	public String getEstatefacility() {
		return estatefacility;
	}

	public void setEstatefacility(String estatefacility) {
		this.estatefacility = estatefacility;
	}

	public String getEstatetraffic() {
		return estatetraffic;
	}

	public void setEstatetraffic(String estatetraffic) {
		this.estatetraffic = estatetraffic;
	}

	public void setLabelWord(String labelWord) {
		this.labelWord = labelWord;
	}

	public int getYesClickCount() {
		return yesClickCount;
	}

	public void setYesClickCount(int yesClickCount) {
		this.yesClickCount = yesClickCount;
	}
	public boolean isRecommend() {
		return this.labels.contains(House.HOUSE_LABEL_XT);
	}
	
	public boolean isVcr(){
		return this.labels.contains(House.HOUSE_LABEL_VCR);
	}
	
	public boolean isSskf(){
		return this.labels.contains(House.HOUSE_LABEL_SSKF);
	}

	/**
	 * 房源所填写的项数
	 * @return
	 */
	public int getOptionNum() {
		// TODO Auto-generated method stub
		return 5;
	}

	public Double getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(Double baseScore) {
		this.baseScore = baseScore;
	}

	public int getPhotoStatus() {
		return photoStatus;
	}

	public void setPhotoStatus(int photoStatus) {
		this.photoStatus = photoStatus;
	}

	public List<RejectPhoto> getRejectPhoto() {
		return rejectPhoto;
	}

	public void setRejectPhoto(List<RejectPhoto> rejectPhoto) {
		this.rejectPhoto = rejectPhoto;
	}
	
	public Timestamp getVcrSubmitTime() {
		return vcrSubmitTime;
	}

	public void setVcrSubmitTime(Timestamp vcrSubmitTime) {
		this.vcrSubmitTime = vcrSubmitTime;
	}

	public int getComplainNum() {
		return complainNum;
	}

	public void setComplainNum(int complainNum) {
		this.complainNum = complainNum;
	}

	public Set<String> getLabels() {
		return labels;
	}

	public void setLabels(Set<String> labels) {
		this.labels = labels;
	}
	
	
	public String getHouseLabelName(){
		String name = "";
		if (!CollectionUtils.isEmpty(labels)) {
		Iterator<String> i = labels.iterator();
		
		while(i.hasNext()){
			name =name+","+Resources.getString("house.label."+i.next()) ;
		}
		}
		if(name.length()>1){
			name = name.substring(1,name.length());
		}
		return name;
	}
	
	public void setHouseLabelName(String houseLabelName) {
		this.houseLabelName = houseLabelName;
	}
	
	/**
	 * 获取标签，只是数据库插入时使用，
	 * 对象操作请操作labels
	 * @return
	 */
	public String getHouseLabel() {
		if (!CollectionUtils.isEmpty(labels)) {
			Iterator<String> i = labels.iterator();
			if (!i.hasNext()){
				return null;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(',');
			for (;;) {
				String label = i.next();
				sb.append(label);
				if (!i.hasNext()) {
					sb.append(',');
					break;
				}
				sb.append(",");
			}
			this.houseLabel = sb.toString();
			return this.houseLabel;
		} else {
			return null;
		}
	}

	/**
	 * 添加标签
	 * @param labelName
	 */
	public boolean setLabel(String labelName){
		
		return this.getLabels().add(labelName);
		
	}
	public boolean removeLable(String labelName){
		return this.getLabels().remove(labelName);
	}
	
	public int getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(int evaluationCount) {
		this.evaluationCount = evaluationCount;
	}

	public float getEvaluationPoint() {
		return evaluationPoint;
	}

	public void setEvaluationPoint(float evaluationPoint) {
		this.evaluationPoint = evaluationPoint;
	}

	public int getRealFlag() {
		return realFlag;
	}

	public void setRealFlag(int realFlag) {
		this.realFlag = realFlag;
	}

	public int getEvaluationVisible() {
		return evaluationVisible;
	}

	public void setEvaluationVisible(int evaluationVisible) {
		this.evaluationVisible = evaluationVisible;
	}
	
	public String getAuthorPhone() {
		return authorPhone;
	}

	public void setAuthorPhone(String authorPhone) {
		this.authorPhone = authorPhone;
	}

	/**
	 * 是否含有指定标签
	 * @param lableName 指定的标签名
	 * @return
	 */
	public boolean hasLabel(String lableName){
		return this.getLabels().contains(lableName);
	}
	public void setHouseLabel(String houseLabel) {
		if (houseLabel != null) {
			String[] labelList = houseLabel.split(",");
			labels = new TreeSet<String>();
			for (String label : labelList) {
				if (StringUtils.isNotBlank(label)) {
					labels.add(label);
				}
			}
		}

		this.houseLabel = houseLabel;
	}
	
	
	public String getHouseStatusLabel(){
		switch(getHouse_status()) {
		case 0:
			return "待发布房源";
		case 1:
			return "在线房源";
		case 2:
			return "违规房源";
		case 3:
			return "过期房源";
		case 4:
			return "删除房源";
		case 5:
			return "草稿箱";
		case 6:
			return "未审核经纪人房源";
			default:
				return "";
		}
	}
	
	public String getCityName() {
		if(cityid > 0) {
			return LocaleService.getName(cityid);
		}
		return "";
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getPicMode() {
		return picMode;
	}

	public void setPicMode(int picMode) {
		this.picMode = picMode;
	}

	public Double getExtraScore() {
		return extraScore;
	}

	public void setExtraScore(Double extraScore) {
		this.extraScore = extraScore;
	}

	public Double getSearchScore() {
		return searchScore;
	}

	public void setSearchScore(Double searchScore) {
		this.searchScore = searchScore;
	}
	public Double getSearchScore(int houseType){
		String url = null;
		if(houseType ==1 ){
			 url = Resources.getString("search_engine_addr_resale")+"/searchResale/select/?fq=id:"+this.getId()+"&fl=id&qt=search&debugQuery=true&wt=json";
		}else{
			 url = Resources.getString("search_engine_addr_rent")+"/searchRent/select/?fq=id:"+this.getId()+"&fl=id&qt=search&debugQuery=true&wt=json";
		}
		
		try{
			String result = NetClient.getHttpResponse(url) ;
			int uidIndex = result.indexOf("\"uid\":");
			int idIndex = result.indexOf(",\"id\":"+this.getId());
			if(uidIndex>=0&&idIndex>=0&&idIndex>uidIndex){
				String uid = result.substring(result.indexOf("\"uid\":")+6,result.indexOf(",\"id\":"+this.getId()));
				String score = result.substring(result.indexOf("\""+uid+"\":\"")+("\""+uid+"\":\"").length(), result.indexOf("= (MATCH)"));
				score = score.replaceAll("[^(0-9|.)]", "");
				return new Double(score);
			}else{
				return 0.0;
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	public static void main(String[] args) {
		House house = new House();
		house.setId(26992909);
		System.out.println(house.getSearchScore(1));
	}

	public Integer getVcrCheckStatus() {
		return vcrCheckStatus;
	}

	public void setVcrCheckStatus(Integer vcrCheckStatus) {
		this.vcrCheckStatus = vcrCheckStatus;
	}

	public int getFalseHouseReport() {
		return falseHouseReport;
	}

	public void setFalseHouseReport(int falseHouseReport) {
		this.falseHouseReport = falseHouseReport;
	}

	public Timestamp getAppealExpiredTime() {
		return appealExpiredTime;
	}

	public void setAppealExpiredTime(Timestamp appealExpiredTime) {
		this.appealExpiredTime = appealExpiredTime;
	}
}
