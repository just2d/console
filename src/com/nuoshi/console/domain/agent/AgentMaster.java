package com.nuoshi.console.domain.agent;

import java.sql.Timestamp;
import java.util.Date;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.phone.PhoneConstants;
import com.nuoshi.console.domain.user.TFUser;

/**
 * Created by: liang Date: 2009-11-9 Time: 15:47:45
 */
public class AgentMaster extends TFUser {
	
	private static final long serialVersionUID = 8730581845073424701L;
	public static int PAY_STATUS_DEFAULT = 0 ;//付费状态默认
	public static int PAY_STATUS_PAY = 2 ;//付费用户
	public static int PAY_STATUS_OLD_PAY = 1 ;//曾经是付费用户
	public static int VERIFY_STATUS_PASS = 1 ;//(曾经或现在)认证通过
	public static int VERIFY_STATUS_DEFULT = 0 ;//未认证用户
	private int agentId;
	private String idcardNumber;
	private String status;
	private String cityCode;
	private int companyId;
	private String company;
	private int storeId;
	private String store;
	
	private String idCardImg;
	private String headImg;
	private String nameCardImg;
	
	private String msgIdcard;
	private String msgHead;
	private String msgNamecard;
	private int adminId;
	private String v400Number;
	private int v400UserId;
	private int regStep;
	private int credit;
	private Timestamp lastUpdateDate;
	private String headPhotoUrl;
	private int mobileCheckStatus;// 手机验证状态：2 通过或不需要验证，1 审核验证中
	private Date submitMobileDate;// 申请人工验证注册码的时间
	private String banFlag; //经纪人关闭状态Y ：关闭 ；其它：开启
	private int payStatus; // 付费状态：0:免费用户（默认）；1:免费用户（曾经是付费用户）；2:付费用户
	private int verifyStatus ; //0:未认证用户；1:认证用户(曾经或现在是认证通过的)（即可发房源用户）
	
	private int tipType ; //给予付费提示（0 默认； 1  付费提示）
	
	private String salerId;
	private double balance;
	private Timestamp packageExpiredDate;
	private Timestamp packageActiveDate;
	
	private Timestamp verifyPassDate;
	
	public Timestamp getPackageExpiredDate() {
		return packageExpiredDate;
	}

	public void setPackageExpiredDate(Timestamp packageExpiredDate) {
		this.packageExpiredDate = packageExpiredDate;
	}

	public Timestamp getPackageActiveDate() {
		return packageActiveDate;
	}

	public void setPackageActiveDate(Timestamp packageActiveDate) {
		this.packageActiveDate = packageActiveDate;
	}

	/*
	 * 要加的  房源数量或标签数量  用完后要归0
	 */
	private int addHouseNum;
	private int addLabelNum;
	
	public int getAddHouseNum() {
		return addHouseNum;
	}

	public void setAddHouseNum(int addHouseNum) {
		this.addHouseNum = addHouseNum;
	}

	public int getAddLabelNum() {
		return addLabelNum;
	}

	public void setAddLabelNum(int addLabelNum) {
		this.addLabelNum = addLabelNum;
	}

	/**
	 * status拆分出idcardNumber,headNumber,namecardNumber
	 * 
	 * @return
	 */
	private int idcardStatusNumber;
	private int headStatusNumber;
	private int namecardStatusNumber;
	private int packageId;
	private int houseNum;
	private int labelNum;
	private int availableHouseNum;
	private int availableLabelNum;
	private boolean hasDelPhoto = false;
	
	/**
	 * 经纪人图片
	 */
	private IdentityPhoto headPhoto;
	private IdentityPhoto idPhoto;
	private IdentityPhoto namePhoto;
	private String serviceLocation;
	
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		this.setIdcardStatusNumber(Integer.parseInt(status.substring(0, 1)));
		this.setHeadStatusNumber(Integer.parseInt(status.substring(1, 2)));
		this.setNamecardStatusNumber(Integer.parseInt(status.substring(2)));
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}


	public String getMsgIdcard() {
		return msgIdcard;
	}

	public void setMsgIdcard(String msgIdcard) {
		this.msgIdcard = msgIdcard;
	}

	public String getMsgHead() {
		return msgHead;
	}

	public void setMsgHead(String msgHead) {
		this.msgHead = msgHead;
	}

	public String getMsgNamecard() {
		return msgNamecard;
	}

	public void setMsgNamecard(String msgNamecard) {
		this.msgNamecard = msgNamecard;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getV400Number() {
		return v400Number;
	}

	public void setV400Number(String v400Number) {
		this.v400Number = v400Number;
	}

	public int getV400UserId() {
		return v400UserId;
	}

	public void setV400UserId(int v400UserId) {
		this.v400UserId = v400UserId;
	}

	public int getRegStep() {
		return regStep;
	}

	public void setRegStep(int regStep) {
		this.regStep = regStep;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getIdcardStatusNumber() {
		return idcardStatusNumber;
	}

	public void setIdcardStatusNumber(int idcardStatusNumber) {
		this.idcardStatusNumber = idcardStatusNumber;
	}

	public int getHeadStatusNumber() {
		return headStatusNumber;
	}

	public void setHeadStatusNumber(int headStatusNumber) {
		this.headStatusNumber = headStatusNumber;
	}

	public int getNamecardStatusNumber() {
		return namecardStatusNumber;
	}

	public void setNamecardStatusNumber(int namecardStatusNumber) {
		this.namecardStatusNumber = namecardStatusNumber;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	public boolean isAgentVerified() {
		return (Globals.STATUS_OK.equals(this.getStatus())||"110".equals(this.getStatus()));
	}

	public boolean isIdcardReady() {
		return getIdcardStatusNumber() == Globals.STATUS_COMPLETE_VALUE;
	}

	public boolean isHeadReady() {
		return getHeadStatusNumber() == Globals.STATUS_COMPLETE_VALUE;
	}

	public boolean isNamecardReady() {
		return getNamecardStatusNumber() == Globals.STATUS_COMPLETE_VALUE;
	}

	public boolean isIdcardOk() {
		return getIdcardStatusNumber() == Globals.STATUS_OK_VALUE;
	}

	public boolean isHeadOk() {
		return getHeadStatusNumber() == Globals.STATUS_OK_VALUE;
	}

	public boolean isNamecardOk() {
		return getNamecardStatusNumber() == Globals.STATUS_OK_VALUE;
	}

	public boolean isIdcardEdited() {
		return getIdcardStatusNumber() == Globals.STATUS_EDIT_VALUE;
	}

	public boolean isHeadEdited() {
		return getHeadStatusNumber() == Globals.STATUS_EDIT_VALUE;
	}

	public boolean isNamecardEdited() {
		return getNamecardStatusNumber() == Globals.STATUS_EDIT_VALUE;
	}

	public boolean isIdcardRejected() {
		return getIdcardStatusNumber() == Globals.STATUS_REJECT_VALUE;
	}

	public boolean isHeadRejected() {
		return getHeadStatusNumber() == Globals.STATUS_REJECT_VALUE;
	}

	public boolean isNamecardRejected() {
		return getNamecardStatusNumber() == Globals.STATUS_REJECT_VALUE;
	}
	
	public boolean isIdcardInit() {
		return getIdcardStatusNumber() == Globals.STATUS_INI_VALUE;
	}

	public boolean isHeadInit() {
		return getIdcardStatusNumber() == Globals.STATUS_INI_VALUE;
	}

	public boolean isNamecardInit() {
		return getNamecardStatusNumber() == Globals.STATUS_INI_VALUE;
	}

	// 当为True的时候，就可以在后台审核
	public boolean isReady() {
		// 有可能存在只修改其中某几项的情况，就是，某些项是ready, 某些项是 ok, 但不是全ok
		return (isHeadReady() || isHeadOk())
				&& (isIdcardReady() || isIdcardOk())
				&& (isNamecardReady() || isNamecardOk() && !isApprovaled());
	}
	
	public boolean isRejected() {
		return this.isHeadRejected() || this.isIdcardRejected() || this.isNamecardRejected();
	}

	public boolean isApprovaled() {
		return isHeadOk() && isIdcardOk() && isNamecardOk();
	}

	public void setIdcardReady() {
		setOneFlag(Globals.STATUS_COMPLETE_VALUE, 0);
	}

	public void setNamecardReady() {
		setOneFlag(Globals.STATUS_COMPLETE_VALUE, 2);
	}

	public void setHeadReady() {
		setOneFlag(Globals.STATUS_COMPLETE_VALUE, 1);
	}

	public void setIdcardOk() {
		setOneFlag(Globals.STATUS_OK_VALUE, 0);
	}

	public void setNamecardOk() {
		setOneFlag(Globals.STATUS_OK_VALUE, 2);
	}

	public void setHeadOk() {
		setOneFlag(Globals.STATUS_OK_VALUE, 1);
	}

	public void setIdcardEdited() {
		setOneFlag(Globals.STATUS_EDIT_VALUE, 0);
	}

	public void setNamecardEdited() {
		setOneFlag(Globals.STATUS_EDIT_VALUE, 2);
	}

	public void setHeadEdited() {
		setOneFlag(Globals.STATUS_EDIT_VALUE, 1);
	}

	public void setIdcardRejected() {
		setOneFlag(Globals.STATUS_REJECT_VALUE, 0);
	}

	public void setHeadRejected() {
		setOneFlag(Globals.STATUS_REJECT_VALUE, 1);
	}

	public void setNamecardRejected() {
		setOneFlag(Globals.STATUS_REJECT_VALUE, 2);
	}

	private void setOneFlag(int flags, int position) {
		if (position == 0) {
			setIdcardStatusNumber(flags);
		} else if (position == 1) {
			setHeadStatusNumber(flags);
		} else if (position == 2) {
			setNamecardStatusNumber(flags);
		}
		this.status = getComposeStatus();
	}

	private String getComposeStatus() {
		return "" + getIdcardStatusNumber() + getHeadStatusNumber()
				+ getNamecardStatusNumber();
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public int getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(int labelNum) {
		this.labelNum = labelNum;
	}

	public int getAvailableHouseNum() {
		return availableHouseNum;
	}

	public void setAvailableHouseNum(int availableHouseNum) {
		this.availableHouseNum = availableHouseNum;
	}

	public int getAvailableLabelNum() {
		return availableLabelNum;
	}

	public void setAvailableLabelNum(int availableLabelNum) {
		this.availableLabelNum = availableLabelNum;
	}

	public boolean isHasDelPhoto() {
		return hasDelPhoto;
	}

	public void setHasDelPhoto(boolean hasDelPhoto) {
		this.hasDelPhoto = hasDelPhoto;
	}

	public IdentityPhoto getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(IdentityPhoto headPhoto) {
		this.headPhoto = headPhoto;
	}

	public IdentityPhoto getNamePhoto() {
		return namePhoto;
	}

	public void setNamePhoto(IdentityPhoto namePhoto) {
		this.namePhoto = namePhoto;
	}

	public IdentityPhoto getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(IdentityPhoto idPhoto) {
		this.idPhoto = idPhoto;
	}

	public String getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	
	public String getAgentCallNumber(){
		if(this.v400Number == null || "".equalsIgnoreCase(this.v400Number)) {
			return "未选择";
		}
		return PhoneConstants.PHONE_NUMBER + this.v400Number;
	}

	public String getHeadPhotoUrl() {
		return headPhotoUrl;
	}

	public void setHeadPhotoUrl(String headPhotoUrl) {
		this.headPhotoUrl = headPhotoUrl;
	}
	
	public int getTruePackageId(){
		int totalHouseNum = getTrueHouseNum();
		int truePackageId = 0;
		switch(totalHouseNum) {
			case 6:
				truePackageId = 1;
				break;
			case 10:
				truePackageId = 2;
				break;
			case 14:
				truePackageId = 3;
				break;
			case 18:
				truePackageId = 4;
				break;
			case 22:
				truePackageId = 5;
				break;
			case 26:
				truePackageId = 6;
				break;
			case 30:
				truePackageId = 7;
				break;
			default:
				truePackageId = packageId;
		}
		return truePackageId;
		
	}
	public int getTrueHouseNum(){
		int result = 6;
		if(isIdcardOk()) {
			result += 12;
		}
		
		if(isHeadOk()) {
			result += 8;
		}
		
		if(isNamecardOk()) {
			result += 4;
		}
		if(result < houseNum) {
			result = houseNum;
		}
		return result;
	}
	
	public int getTrueLabelNum(){
		return getTrueHouseNum() / 2;
	}

	public String getIdCardImg() {
		return idCardImg;
	}

	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getNameCardImg() {
		return nameCardImg;
	}

	public void setNameCardImg(String nameCardImg) {
		this.nameCardImg = nameCardImg;
	}

	public int getMobileCheckStatus() {
		return mobileCheckStatus;
	}

	public void setMobileCheckStatus(int mobileCheckStatus) {
		this.mobileCheckStatus = mobileCheckStatus;
	}

	public Date getSubmitMobileDate() {
		return submitMobileDate;
	}

	public void setSubmitMobileDate(Date submitMobileDate) {
		this.submitMobileDate = submitMobileDate;
	}

	public String getBanFlag() {
		return banFlag;
	}

	public void setBanFlag(String banFlag) {
		this.banFlag = banFlag;
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

	public int getTipType() {
		return tipType;
	}

	public void setTipType(int tipType) {
		this.tipType = tipType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	public Timestamp getVerifyPassDate() {
		return verifyPassDate;
	}

	public void setVerifyPassDate(Timestamp verifyPassDate) {
		this.verifyPassDate = verifyPassDate;
	}

}
