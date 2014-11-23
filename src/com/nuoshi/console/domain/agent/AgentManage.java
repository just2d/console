package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nuoshi.console.common.phone.PhoneConstants;

/**
 * @author wanghongmeng
 * 
 */
public class AgentManage implements Serializable {
	private static final long serialVersionUID = -8592725400332070062L;
	private int id;
	private int cityid;
	private int distid;
	private int blockid;
	private int points;
	private int illegal;
	private int verifyResult;
	private int type;
	private String status;
	private int brokerid;
	private int verifyFlags;
	private String name;
	private String mobile;
	private String cityname;
	private String distname;
	private String blockname;
	private String brand;
	private String address;
	private String typename;
	private String citydir;
	private String phone400;
	private int boundDate;
	private Date cts;
	private Date expires;
	private int payStatus;
	private int verifyStatus;
	/**
	 * 经纪人拥有房源的平均得分
	 */
	private Double aveScore;
	/**
	 * 经济人发布在线房源是否开启(Y:关闭；N:开启)
	 */
	private String banFlag;
	
	private Integer packageId;

	private int agentHouseCount;
	
	private int labelNum;
	
	private int availableLabelNum;
	
	public int getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(int labelNum) {
		this.labelNum = labelNum;
	}

	public int getAvailableLabelNum() {
		return availableLabelNum;
	}

	public void setAvailableLabelNum(int availableLabelNum) {
		this.availableLabelNum = availableLabelNum;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getBanFlag() {
		return banFlag;
	}

	public void setBanFlag(String banFlag) {
		this.banFlag = banFlag;
	}

	public Double getAveScore() {
		return aveScore;
	}

	public void setAveScore(Double aveScore) {
		this.aveScore = aveScore;
	}

	/**
	 * @return 城市拼音简写
	 */
	public String getCitydir() {
		return citydir;
	}

	/**
	 * @param citydir
	 *            城市拼音简写
	 */
	public void setCitydir(String citydir) {
		this.citydir = citydir;
	}

	/**
	 * @return 经纪人id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            经纪人id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 经纪人名字
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            经纪人名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 经纪人电话
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            经纪人电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 城市中文名称
	 */
	public String getCityname() {
		return cityname;
	}

	/**
	 * @param cityname
	 *            城市中文名称
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	/**
	 * @return 区域中文名称
	 */
	public String getDistname() {
		return distname;
	}

	/**
	 * @param distname
	 *            区域中文名称
	 */
	public void setDistname(String distname) {
		this.distname = distname;
	}

	/**
	 * @return 商圈中文名称
	 */
	public String getBlockname() {
		return blockname;
	}

	/**
	 * @param blockname
	 *            商圈中文名称
	 */
	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}

	/**
	 * @return 公司名称
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            公司名称
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return 门店名称
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            门店名称
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return 按照yyyy-mm-dd的格式返回经纪人注册时间
	 */
	public String getCts() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != cts) {
			return sdf.format(cts);
		} else {
			return "";
		}
	}

	/**
	 * @param cts
	 *            经纪人注册时间
	 */
	public void setCts(Date cts) {
		this.cts = cts;
	}

	/**
	 * @return 经纪人信用值
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            经纪人信用值
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return 经纪人违规次数
	 */
	public int getIllegal() {
		return illegal;
	}

	/**
	 * @param illegal
	 *            经纪人违规次数
	 */
	public void setIllegal(int illegal) {
		this.illegal = illegal;
	}

	/**
	 * @return 按照yyyy-mm-dd的格式返回经纪人套餐过期日期
	 */
	public String getExpires() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != expires) {
			return sdf.format(expires);
		} else {
			return "";
		}
	}

	/**
	 * @param expires
	 *            经纪人套餐过期日期
	 */
	public void setExpires(Date expires) {
		this.expires = expires;
	}

	/**
	 * @return 经纪人套餐类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            经纪人套餐类型
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 经济人验证状态
	 */
	public int getVerifyResult() {
		return verifyResult;
	}

	/**
	 * @param verifyStatus
	 *            经纪人验证状态
	 */
	public void setVerifyResult(int verifyResult) {
		this.verifyResult = verifyResult;
	}

	/**
	 * @return 套餐类型中文
	 */
	public String getTypename() {
		return typename;
	}

	/**
	 * @param typename
	 *            套餐类型中文
	 */
	public void setTypename(String typename) {
		this.typename = typename;
	}

	/**
	 * @return 城市id
	 */
	public int getCityid() {
		return cityid;
	}

	/**
	 * @param cityid
	 *            城市id
	 */
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	/**
	 * @return 区域id
	 */
	public int getDistid() {
		return distid;
	}

	/**
	 * @param distid
	 *            区域id
	 */
	public void setDistid(int distid) {
		this.distid = distid;
	}

	/**
	 * @return 商圈id
	 */
	public int getBlockid() {
		return blockid;
	}

	/**
	 * @param blockid
	 *            商圈id
	 */
	public void setBlockid(int blockid) {
		this.blockid = blockid;
	}

	/**
	 * @return 经纪人400绑定电话
	 */
	public String getPhone400() {
		return phone400;
	}

	/**
	 * @param phone400
	 *            经纪人400绑定电话
	 */
	public void setPhone400(String phone400) {
		this.phone400 = phone400;
	}

	/**
	 * @return brokerid，关联broker表
	 */
	public int getBrokerid() {
		return brokerid;
	}

	/**
	 * @param brokerid
	 *            brokerid，关联broker表
	 */
	public void setBrokerid(int brokerid) {
		this.brokerid = brokerid;
	}

	/**
	 * @return agent_verify表中flags
	 */
	public int getVerifyFlags() {
		return verifyFlags;
	}

	/**
	 * @param verifyFlags
	 *            agent_verify表中flags
	 */
	public void setVerifyFlags(int verifyFlags) {
		this.verifyFlags = verifyFlags;
	}

	public int getBoundDate() {
		return boundDate;
	}

	public void setBoundDate(int boundDate) {
		this.boundDate = boundDate;
	}
	
	public String getBoundDateStr(){
		StringBuilder sb = new StringBuilder();
		if(phone400 == null) {
			sb.append("未绑定");
		} else {
			sb.append(PhoneConstants.PHONE_NUMBER + "-" + phone400);
		}
		if(boundDate != 0) {
			sb.append("(").append(boundDate).append(")");
		}
		return  sb.toString();
	}

	public int getAgentHouseCount() {
		return agentHouseCount;
	}

	public void setAgentHouseCount(int agentHouseCount) {
		this.agentHouseCount = agentHouseCount;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	//是否是(曾经或现在)认证通过的免费用户
	public boolean isVerifyFreeUser(){
		return ((this.getPayStatus()==1||this.getPayStatus()==0)&&this.getVerifyStatus()==1);
		
	}
	//是否是付费用户
	public boolean isPayUser(){
		return (this.getPayStatus()==2);
		
	}
}
