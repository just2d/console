package com.nuoshi.console.domain.base;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.nuoshi.console.domain.agent.Broker;

/**
 * Created by IntegerelliJ IDEA. User: pekky Date: 2009-11-24 Time: 15:42:37 To
 * change this template use File | Settings | File Templates.
 */
public class AgentHouse {

	public static final Integer AUDIT_STATUS_UNVERIFIED = 0;
	public static final Integer AUDIT_STATUS_OK = 1;
	public static final Integer AUDIT_STATUS_BACK = 2;

	public static final Integer HOUSE_STATUS_DELETE_FOREVER = -1;
	public static final Integer HOUSE_STATUS_UNSHELVED = 0;
	public static final Integer HOUSE_STATUS_SHELVED = 1;
	public static final Integer HOUSE_STATUS_ILLEGAL = 2;
	public static final Integer HOUSE_STATUS_OUTDATE = 3;
	public static final Integer HOUSE_STATUS_DELETED = 4;
	public static final Integer HOUSE_STATUS_TEMP = 5;
	public static final Integer HOUSE_STATUS_ONLINE_UNVERIFIED = 6; // 未审核经纪人发布的在线房源
	
	
	public static final int RESALE_TYPE = 1;
	public static final int RENT_TYPE = 2;

	/**
	 *随时看房标签
	 */
	public static final String HOUSE_LABEL_READY_VISIT = "K";
	/**
	 * 视频标签
	 */
	public static final String HOUSE_LABEL_VIDEO = "V";
	/**
	 * 新推标签
	 */
	public static final String HOUSE_LABEL_RECOMMEND = "N";
	
	private boolean isAddEstatePhoto;
	private boolean isAddInnerPhoto;
	private boolean isAddLayoutPhoto;
	
	// 提取图片的时的状态信息
	private String imageExtractionStatusMessage = null;

	private String leftDays;

	// 价格
	private Float price;
	// 标签
	private Set<String> labels = new TreeSet<String>();
	// 标签
	private String houseLabel;

	// 总楼层数
	private Integer totalfloor;

	// 户型
	private String flattype = "";
	
	private String rankFlag; //竞价推广状态

	// 第几层
	private Integer floor;
	// 发布房源时发布人的ip地址
	private String sourceIp;
	
	private String vipPicFlag ;//列表大图展示
	private String pushFlag ;//主推房源

	// 房屋类型
	private Byte type;

	// 发布时间
	private Timestamp pubdate;
	// 发布时间
	private Timestamp vcrSubmitTime;

	// 编辑时间
	private Timestamp editdate;

	// 封面照片id
	private Integer photoid;

	// 封面照片 小尺寸
	private String phototiny = "";

	// 封面照片 中尺寸
	private String photobrowse = "";

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
	// 基本分数
	private Double baseScore;

	// 锁定时间
	private Timestamp lockts;

	// 最后更新时间
	private Timestamp rts;

	private Timestamp enterdate;

	// 该房源的室内图数量
	private Integer photoCount;

	private Integer layoutCount;

	private Integer innerCount;

	private Integer communityCount;

	// 房源内部编号
	private String userinnercode;

	// 备案编号
	private String recordCode;
	// 标签词
	private String labelWord;

	// 违规原因
	private String invalidreason;

	// 设置为加急状态的时间
	private Timestamp urgenttime;

	private Integer id;
	private Integer sourceid;

	// 卧室数量
	private Byte beds;
	// 卫生间数量
	private Byte baths;
	// 客厅数量
	private Byte livings;

	// 发布者id
	private Integer authorid;

	// 发布者名字
	private String authorName;
	// 发布者电话
	private String authorPhone;
	// 发布者的中介公司信息
	private Broker broker;
	// 发布者的中介公司信息
	private String brokerName;
	// 发布者的中介公司门店地址
	private String storeName;

	// 所在城市
	private Integer cityid;

	// 所在城区
	private Integer distid;

	// 所在板块
	private Integer blockid;

	// 地址
	private String addr;

	// 房源标题
	private String title;

	// 房屋面积
	private Float area;

	// 小区id
	private Integer estateid;

	// 小区名字
	private String estatename;

	private String estatefacility;

	private String estatetraffic;

	private Integer auditStatus;

	private Integer houseStatus;

	private Integer isTodayFresh;

	private int photoStatus;

	// 房源删除之前的状态
	private int oldStatus;

	private List<RejectPhoto> rejectPhoto;
	
	public boolean isAddEstatePhoto() {
		return isAddEstatePhoto;
	}

	public void setAddEstatePhoto(boolean isAddEstatePhoto) {
		this.isAddEstatePhoto = isAddEstatePhoto;
	}

	public boolean isAddInnerPhoto() {
		return isAddInnerPhoto;
	}

	public void setAddInnerPhoto(boolean isAddInnerPhoto) {
		this.isAddInnerPhoto = isAddInnerPhoto;
	}

	public boolean isAddLayoutPhoto() {
		return isAddLayoutPhoto;
	}

	public void setAddLayoutPhoto(boolean isAddLayoutPhoto) {
		this.isAddLayoutPhoto = isAddLayoutPhoto;
	}

	/**
	 * 房源视频URL
	 */
	private String vcrUrl;
	
	/**
	 * 房源视频审核状态
	 */
	private int vcrCheckStatus;
	/**
	 * 房源视频审核状态
	 */
	private Integer complainNum;
	/**
	 * 房源视频提取状态
	 */
	private int vcrAuditSign;

	public boolean isTreated() {
		return auditStatus != AUDIT_STATUS_UNVERIFIED;
	}

	public boolean isApprovaled() {
		return auditStatus == AUDIT_STATUS_OK;
	}

	public String getAuthorPhone() {
		return authorPhone;
	}

	public Integer getSourceid() {
		return sourceid;
	}

	public void setSourceid(Integer sourceid) {
		this.sourceid = sourceid;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getRankFlag() {
		return rankFlag;
	}

	public void setRankFlag(String rankFlag) {
		this.rankFlag = rankFlag;
	}

	public void setAuthorPhone(String authorPhone) {
		this.authorPhone = authorPhone;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public boolean isOutofdate() {
		return houseStatus == HOUSE_STATUS_OUTDATE;
	}

	public boolean isOnshelf() {
		return (houseStatus == HOUSE_STATUS_SHELVED || houseStatus == HOUSE_STATUS_ONLINE_UNVERIFIED);
	}

	public boolean isDeleted() {
		return houseStatus == HOUSE_STATUS_DELETED;
	}

	public boolean isRecommend() {
		return this.labels.contains(AgentHouse.HOUSE_LABEL_RECOMMEND);
	}
	
	public boolean isVcr(){
		return this.labels.contains(AgentHouse.HOUSE_LABEL_VIDEO);
	}
	
	public boolean isSskf(){
		return this.labels.contains(AgentHouse.HOUSE_LABEL_READY_VISIT);
	}

	public Byte getType() {
		return type;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public String getImageExtractionStatusMessage() {
		return imageExtractionStatusMessage;
	}

	public void setImageExtractionStatusMessage(String imageExtractionStatusMessage) {
		this.imageExtractionStatusMessage = imageExtractionStatusMessage;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public void setType(Byte type) {
		this.type = type;
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
		this.title = title;
	}

	public Timestamp getPubdate() {
		return pubdate;
	}

	public void setPubdate(Timestamp pubdate) {
		initLeftDays();
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Integer getFacility() {
		if (facility == null) {
			return 0;
		}
		return facility;
	}

	public void setFacility(Integer facility) {
		this.facility = facility;
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

	public void setEstatename(String estatename) {
		this.estatename = estatename;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public Timestamp getEditdate() {
		return editdate;
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
		return phototiny;
	}

	public void setPhototiny(String phototiny) {
		this.phototiny = phototiny;
	}

	public String getPhotobrowse() {
		return photobrowse;
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
		if (decoration == null) {
			return 0;
		}
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

	public Timestamp getRts() {
		return rts;
	}

	public void setRts(Timestamp rts) {
		this.rts = rts;
	}

	public String getUserinnercode() {
		return userinnercode;
	}

	public void setUserinnercode(String userinnercode) {
		this.userinnercode = userinnercode;
	}

	public Timestamp getUrgenttime() {
		return urgenttime;
	}

	public void setUrgenttime(Timestamp urgenttime) {
		this.urgenttime = urgenttime;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getInvalidreason() {
		return invalidreason;
	}

	public void setInvalidreason(String invalidreason) {
		this.invalidreason = invalidreason;
	}

	public Integer getIsTodayFresh() {
		return isTodayFresh;
	}

	public void setIsTodayFresh(Integer isTodayFresh) {
		this.isTodayFresh = isTodayFresh;
	}

	public Timestamp getEnterdate() {
		return enterdate;
	}

	public void setEnterdate(Timestamp enterdate) {
		this.enterdate = enterdate;
	}

	public String getLabelWord() {
		return labelWord;
	}

	public void setLabelWord(String labelWord) {
		this.labelWord = labelWord;
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

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public void setRejectPhoto(List<RejectPhoto> rejectPhoto) {
		this.rejectPhoto = rejectPhoto;
	}

	// 得到标签数
	public int getUsedLabelNum() {
		
		return this.labels.size();
	}

	public int getUsedHouseNum() {
		if (AgentHouse.HOUSE_STATUS_SHELVED == this.houseStatus || AgentHouse.HOUSE_STATUS_ONLINE_UNVERIFIED == this.houseStatus) {
			return 1;
		}
		return 0;
	}

	public int getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(int oldStatus) {
		this.oldStatus = oldStatus;
	}

	public static Byte getFacetoMap(int sourceid, int value) {
		if (sourceid == 1) {//
			if (value == 1) {
				return new Byte("2");
			}
			if (value == 2) {
				return new Byte("3");
			}
			if (value == 3) {
				return new Byte("1");
			}
			if (value == 5) {
				return new Byte("10");
			}
			if (value == 6) {
				return new Byte("9");
			}
			if (value == 7) {
				return new Byte("5");
			}
			if (value == 8) {
				return new Byte("7");
			}
			if (value == 9) {
				return new Byte("6");
			}
			if (value == 10) {
				return new Byte("8");
			}
		}
		return new Byte(value + "");
	}

	public Set<String> getLabels() {
		return labels;
	}

	public void setLabels(Set<String> labels) {
		this.labels = labels;
	}

	
	public String getVcrUrl() {
		return vcrUrl;
	}

	public void setVcrUrl(String vcrUrl) {
		this.vcrUrl = vcrUrl;
	}

	public int getVcrCheckStatus() {
		return vcrCheckStatus;
	}

	public void setVcrCheckStatus(int vcrCheckStatus) {
		this.vcrCheckStatus = vcrCheckStatus;
	}

	public int getVcrAuditSign() {
		return vcrAuditSign;
	}

	public void setVcrAuditSign(int vcrAuditSign) {
		this.vcrAuditSign = vcrAuditSign;
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

	public Integer getComplainNum() {
		return complainNum;
	}

	public void setComplainNum(Integer complainNum) {
		this.complainNum = complainNum;
	}

	public Timestamp getVcrSubmitTime() {
		return vcrSubmitTime;
	}

	public void setVcrSubmitTime(Timestamp vcrSubmitTime) {
		this.vcrSubmitTime = vcrSubmitTime;
	}

	public static Byte getDecorationMap(int sourceid, int value) {
		if (sourceid == 1) {//
			if (value == 3) {
				return new Byte("2");
			}
			if (value == 4) {
				return new Byte("3");
			}
			if (value == 5) {
				return new Byte("4");
			}
		}
		return new Byte(value + "");
	}

	public void initLeftDays() {

	}

	public String getVipPicFlag() {
		return vipPicFlag;
	}

	public void setVipPicFlag(String vipPicFlag) {
		this.vipPicFlag = vipPicFlag;
	}

	public String getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}

	public String getLeftDays() {
		return leftDays;
	}

	public void setLeftDays(String leftDays) {
		this.leftDays = leftDays;
	}
}

