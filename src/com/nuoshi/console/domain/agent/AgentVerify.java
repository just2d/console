package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA. User: pekky Date: 2010-1-8 Time: 17:24:33
 */
public class AgentVerify implements Serializable {
	private static final long serialVersionUID = 4075599439997927375L;
	public static final int flagNull = 0; // 0 bit
	public static final int flagHeadOk = 1; // 1 bit
	public static final int flagIdcardOk = 2; // 2bit
	public static final int flagNamecardOk = 4; // 3bit

	public static final int flagHeadEdited = 8; // 4bit
	public static final int flagIdcardEdited = 16; // 5bit
	public static final int flagNamecardEdited = 32; // 6bit

	public static final int flagHeadTreated = 64; // 7bit
	public static final int flagIdcardTreated = 128; // 8bit
	public static final int flagNamecardTreated = 256; // 9bit

	public static final int flagHeadReady = 1024; // 10bit
	public static final int flagIdcardReady = 2048; // 11bit
	public static final int flagNamecardReady = 4096; // 12bit
	public static final int statusAgentVerified = 8; // 4 bit
	public static final int flagPhoneOk = 1;
	public static final int flagPhoneNone = 0;
	public static final int flag4Admin = 32768;

	private Timestamp futs;
	private int headid = 0;
	private String fulladdr = "";
	private String fullbrand = "";
	private String brand = "";
	private String addr = "";
	private int brokerid = 0;
	private String name = "";
	private String idcardmsg = "";
	private String namecardmsg = "";
	private String headmsg = "";
	private int flags = 0;
	private Timestamp pubdate;
	private Timestamp editdate;
	private String idnum = "";
	private String head = "";
	private int id = 0;
	private int admin = 0;
	private int agentid = 0;
	private int idcard = 0;
	private int namecard = 0;
	private String callnumber;
	private int user400Id;

	// 注册引导步骤
	private int registerstep;

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

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public String getCallnumber() {
		return callnumber;
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

	protected int setOneFlag(int mask, boolean type) {
		int flags = getFlags();
		if (type) {
			flags = flags | mask;
		} else {
			flags = flags & (~mask);
		}
		setFlags(flags);
		return flags;
	}

	public boolean isIdcardReady() {
		return ((getFlags() & flagIdcardReady) == flagIdcardReady);
	}

	public boolean isHeadReady() {
		return ((getFlags() & flagHeadReady) == flagHeadReady);
	}

	public boolean isNamecardReady() {
		return ((getFlags() & flagNamecardReady) == flagNamecardReady);
	}

	public boolean isIdcardOk() {
		return ((getFlags() & flagIdcardOk) == flagIdcardOk);
	}

	public boolean isHeadOk() {
		return ((getFlags() & flagHeadOk) == flagHeadOk);
	}

	public boolean isNamecardOk() {
		return ((getFlags() & flagNamecardOk) == flagNamecardOk);
	}

	public boolean isIdcardTreated() {
		return ((getFlags() & flagIdcardTreated) == flagIdcardTreated);
	}

	public boolean isHeadTreated() {
		return ((getFlags() & flagHeadTreated) == flagHeadTreated);
	}

	public boolean isNamecardTreated() {
		return ((getFlags() & flagNamecardTreated) == flagNamecardTreated);
	}

	public boolean isIdcardEdited() {
		return ((getFlags() & flagIdcardEdited) == flagIdcardEdited);
	}

	public boolean isHeadEdited() {
		return ((getFlags() & flagHeadEdited) == flagHeadEdited);
	}

	public boolean isNamecardEdited() {
		return ((getFlags() & flagNamecardEdited) == flagNamecardEdited);
	}

	public boolean is4Admin() {
		return ((getFlags() & flag4Admin) == flag4Admin);
	}

	public boolean isReady() {
		// 有可能存在只修改其中某几项的情况，就是，某些项是ready, 某些项是 ok, 但不是全ok
		return (isHeadReady() || isHeadOk())
				&& (isIdcardReady() || isIdcardOk())
				&& (isNamecardReady() || isNamecardOk() && !isApprovaled());
	}

	public boolean isApprovaled() {
		return isHeadOk() && isIdcardOk() && isNamecardOk();
	}

	public int setIdcardReady(boolean type) {
		return setOneFlag(flagIdcardReady, type);
	}

	public int setNamecardReady(boolean type) {
		return setOneFlag(flagNamecardReady, type);
	}

	public int setHeadReady(boolean type) {
		return setOneFlag(flagHeadReady, type);
	}

	public int setIdcardOk(boolean type) {
		return setOneFlag(flagIdcardOk, type);
	}

	public int setNamecardOk(boolean type) {
		return setOneFlag(flagNamecardOk, type);
	}

	public int setHeadOk(boolean type) {
		return setOneFlag(flagHeadOk, type);
	}

	public int setIdcardTreated(boolean type) {
		return setOneFlag(flagIdcardTreated, type);
	}

	public int setNamecardTreated(boolean type) {
		return setOneFlag(flagNamecardTreated, type);
	}

	public int setHeadTreated(boolean type) {
		return setOneFlag(flagHeadTreated, type);
	}

	public int setIdcardEdited(boolean type) {
		return setOneFlag(flagIdcardEdited, type);
	}

	public int setNamecardEdited(boolean type) {
		return setOneFlag(flagNamecardEdited, type);
	}

	public int setHeadEdited(boolean type) {
		return setOneFlag(flagHeadEdited, type);
	}

	public int set4Admin(boolean type) {
		return setOneFlag(flag4Admin, type);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getBrokerid() {
		return brokerid;
	}

	public void setBrokerid(int brokerid) {
		this.brokerid = brokerid;
	}

	public String getFullbrand() {
		return fullbrand;
	}

	public void setFullbrand(String fullbrand) {
		this.fullbrand = fullbrand;
	}

	public String getFulladdr() {
		return fulladdr;
	}

	public void setFulladdr(String fulladdr) {
		this.fulladdr = fulladdr;
	}

	public int getHeadid() {
		return headid;
	}

	public void setHeadid(int headid) {
		this.headid = headid;
	}

	public Timestamp getFuts() {
		return futs;
	}

	public void setFuts(Timestamp futs) {
		this.futs = futs;
	}

	public int getRegisterstep() {
		return registerstep;
	}

	public void setRegisterstep(int registerstep) {
		this.registerstep = registerstep;
	}

	/**
	 * 重置状态
	 */
	public void modifyFlags() {
		this.setHeadReady(false);
		this.setIdcardReady(false);
		this.setNamecardReady(false);
		this.setHeadTreated(true);
		this.setIdcardTreated(true);
		this.setNamecardTreated(true);
		this.setHeadEdited(false);
		this.setIdcardEdited(false);
		this.setNamecardEdited(false);
	}

	public static boolean isAgentVerified(int flags) {
		return ((flags & statusAgentVerified) == statusAgentVerified);
	}

	public static boolean isVerifyReady(int flags) {
		return ((flags & flag4Admin) == flag4Admin);
	}
}
