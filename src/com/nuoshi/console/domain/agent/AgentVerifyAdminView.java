package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.sql.Timestamp;

import com.nuoshi.console.common.phone.PhoneConstants;
/**
 * Created by IntelliJ IDEA.
 * User: pekky
 * Date: 2010-2-5
 * Time: 13:58:53
 */
public class AgentVerifyAdminView implements Serializable {
	private static final long serialVersionUID = 1188806474605933126L;
	private String idcardmsg = "";
    private String namecardmsg = "";
    private String headmsg = "";
    private int flags;
    private int admin;
    private Timestamp pubdate;
    private Timestamp editdate;
    private String idnum = "";
    private int agentid;
    private String head;
    private int idcard;
    private int namecard;
    private String brand = "";
    private String addr = "";
    private int brokerid;
    private String name;
    private String callnumber;
	private int user400Id;
	private String mobile;
	private int cityid;
	private int distid;
	private int blockid;
	private String cityName;
	private String distName;
	private String blockName;
	private String serviceLocation;
	
	/**
	 * 身份认证：身份证和名片图片
	 */
	private IdentityPhoto idCardPhoto;
	private IdentityPhoto nameCardPhoto;
	
	
    public String getIdcardmsg() {
        return idcardmsg;
    }

    public void setIdcardmsg(String idcardmsg) {
        this.idcardmsg = idcardmsg;
    }

    public String getNamecardmsg() {
        return namecardmsg;
    }

    public void setNamecardmsg(String namecardmsg) {
        this.namecardmsg = namecardmsg;
    }

    public String getHeadmsg() {
        return headmsg;
    }

    public void setHeadmsg(String headmsg) {
        this.headmsg = headmsg;
    }


    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public Timestamp getPubdate() {
        return pubdate;
    }

    public void setPubdate(Timestamp pubdate) {
        this.pubdate = pubdate;
    }

    public Timestamp getEditdate() {
        return editdate;
    }

    public void setEditdate(Timestamp editdate) {
        this.editdate = editdate;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getIdcard() {
        return idcard;
    }

    public void setIdcard(int idcard) {
        this.idcard = idcard;
    }

    public int getNamecard() {
        return namecard;
    }

    public void setNamecard(int namecard) {
        this.namecard = namecard;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(int brokerid) {
        this.brokerid = brokerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallnumber() {
		return callnumber;
	}
    
    public String getPhone400() {
		return callnumber == null ? "未绑定" : PhoneConstants.PHONE_NUMBER + "-"
				+ callnumber;
	}

	public void setCallnumber(String callnumber) {
		this.callnumber = callnumber;
	}

	public int getUser400Id() {
		return user400Id;
	}

	public void setUser400Id(int user400Id) {
		this.user400Id = user400Id;
	}
	
	public IdentityPhoto getIdCardPhoto() {
		return idCardPhoto;
	}

	public void setIdCardPhoto(IdentityPhoto idCardPhoto) {
		this.idCardPhoto = idCardPhoto;
	}

	public IdentityPhoto getNameCardPhoto() {
		return nameCardPhoto;
	}

	public void setNameCardPhoto(IdentityPhoto nameCardPhoto) {
		this.nameCardPhoto = nameCardPhoto;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getDistid() {
		return distid;
	}

	public void setDistid(int distid) {
		this.distid = distid;
	}

	public int getBlockid() {
		return blockid;
	}

	public void setBlockid(int blockid) {
		this.blockid = blockid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getServiceLocation() {
		return "".equals(serviceLocation) ? "未填写" : serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public boolean isIdcardOk() {
        return ((getFlags() & AgentVerify.flagIdcardOk) == AgentVerify.flagIdcardOk);
    }

    public boolean isHeadOk() {
        return ((getFlags() & AgentVerify.flagHeadOk) == AgentVerify.flagHeadOk);
    }

    public boolean isNamecardOk() {
        return ((getFlags() & AgentVerify.flagNamecardOk) == AgentVerify.flagNamecardOk);
    }

    public boolean isIdcardEdited() {
        return ((getFlags() & AgentVerify.flagIdcardEdited) == AgentVerify.flagIdcardEdited);
    }

    public boolean isHeadEdited() {
        return ((getFlags() & AgentVerify.flagHeadEdited) == AgentVerify.flagHeadEdited);
    }

    public boolean isNamecardEdited() {
        return ((getFlags() & AgentVerify.flagNamecardEdited) == AgentVerify.flagNamecardEdited);
    }
    
    public boolean isIdcardTreated() {
		return ((getFlags() & AgentVerify.flagIdcardTreated) == AgentVerify.flagIdcardTreated);
	}

	public boolean isHeadTreated() {
		return ((getFlags() & AgentVerify.flagHeadTreated) == AgentVerify.flagHeadTreated);
	}

	public boolean isNamecardTreated() {
		return ((getFlags() & AgentVerify.flagNamecardTreated) == AgentVerify.flagNamecardTreated);
	}
}
    
