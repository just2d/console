package com.nuoshi.console.common.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

public class PhoneRemoteService {
	private PhoneRemoteConnector connector = new PhoneRemoteConnector();

	/**
	 * 添加新企业
	 * 
	 * @param supplierName
	 * @param address
	 * @param callfee
	 * @param agentfree
	 * @param userName
	 * @param pwd
	 * @param contactMan
	 * @param phone
	 * @param mobilePhone
	 * @param artiPersion
	 * @param licence
	 * @param taxNumber
	 * @param contactIDCard
	 * @param homePage
	 * @param desc
	 * @param contactMail
	 * @param postcode
	 * @param sex
	 * @param adfree
	 * @param minfree
	 * @return 0:不成功
	 */
	public int registerAdOwner(String supplierName, String address,
			Double callfee, Double agentfee, String userName, String pwd,
			String contactMan, String phone, String mobilePhone,
			String artiPerson, String licence, String taxNumber,
			String contactIDCard, String homePage, String desc,
			String contactMail, String postcode, int sex, Double adfee,
			Double minfee) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierName", supplierName);
		paramName.put("address", address);

		if (callfee == null) {
			paramName.put("callfee", String.valueOf(PhoneConstants.CALL_FEE));
		} else {
			paramName.put("callfee", String.valueOf(callfee));
		}

		if (agentfee == null) {
			paramName.put("agentfee", "0");
		} else {
			paramName.put("agentfee", String.valueOf(agentfee));
		}

		paramName.put("userName", userName);
		paramName.put("pwd", pwd);
		paramName.put("contactMan", contactMan);
		paramName.put("phone", phone);
		paramName.put("mobilePhone", mobilePhone);
		paramName.put("artiPerson", artiPerson);
		paramName.put("licence", licence);
		paramName.put("taxNumber", taxNumber);
		paramName.put("contactIDCard", contactIDCard);
		paramName.put("homePage", homePage);
		paramName.put("desc", desc);
		paramName.put("contactMail", contactMail);
		paramName.put("postcode", postcode);
		paramName.put("sex", String.valueOf(sex));

		if (adfee == null) {
			paramName.put("adfee", "0");
		} else {
			paramName.put("adfee", String.valueOf(adfee));
		}

		if (minfee == null) {
			paramName.put("minfee", "0");
		} else {
			paramName.put("minfee", String.valueOf(minfee));
		}
		String result = connector.callAPI("RegisterAdOwner", paramName);
		return result != null && result.matches("^\\d+$") ? Integer
				.parseInt(result) : 0;
	}

	/***
	 * 添加企业400号码
	 * 
	 * @param custid
	 * @param num400
	 * @return
	 */
	public boolean addCust400(int custid, String num400) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("custid", String.valueOf(custid));
		paramName.put("num400", num400);

		return connector.callAPI("AddCust400", paramName) != null ? true
				: false;
	}

	/**
	 * 添加企业白名单
	 * 
	 * @param supplierID
	 * @param tel
	 * @return
	 */
	public boolean addWhite(int supplierID, String tel) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("tel", tel);

		return connector.callAPI("AddWhite", paramName) != null ? true : false;
	}

	/**
	 * 添加新座席组_手工400号码
	 * 
	 * @param supplierID
	 * @param name
	 * @param ACDType
	 * @param welcomeInfo
	 * @param bindTel
	 * @param SoundType
	 * @param bigcode
	 * @param extcode
	 * @return
	 */
	public String addWorkGroup_400(int supplierID, String name, int ACDType,
			String welcomeInfo, String bindTel, int SoundType, String bigcode,
			String extcode) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("name", name);
		paramName.put("ACDType", String.valueOf(ACDType));
		paramName.put("welcomeInfo", welcomeInfo);
		paramName.put("bindTel", bindTel);
		paramName.put("SoundType", String.valueOf(SoundType));
		paramName.put("bigcode", bigcode);
		paramName.put("extcode", extcode);

		String result = connector.callAPI("AddWorkGroup_400", paramName);
		return result != null ? result : "ERROR";
	}

	/**
	 * 添加座席
	 * 
	 * @param loginid
	 * @param name
	 * @param extension
	 * @param password
	 * @param custid
	 * @return
	 */
	public int addWorkUser(String loginid, String name, String extension,
			String password, int custid) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("loginid", loginid);
		paramName.put("name", name);
		paramName.put("extension", extension);
		paramName.put("password", password);
		paramName.put("custid", String.valueOf(custid));

		String result = connector.callAPI("AddWorkUser", paramName);
		return result != null && result.matches("^\\d+$") ? Integer
				.parseInt(result) : 0;
	}

	/**
	 * 为企业充值
	 * 
	 * @param paidID
	 * @param money
	 * @return
	 */
	public boolean payAdd(int paidID, double money) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("paidID", String.valueOf(paidID));
		paramName.put("money", String.valueOf(money));

		return connector.callAPI("PayAdd", paramName) != null ? true : false;
	}

	/**
	 * 获取代理商现有400号码列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAgent400() {
		List<String> list = new ArrayList<String>();
		String result = connector.callAPI("GetAgent400_xml", null);

		if (result != null) {
			Element root = connector.parseXml(result, false);
			List<Element> ds = root.getChildren("ds");
			for (Iterator<Element> iterator = ds.iterator(); iterator.hasNext();) {
				Element brand = (org.jdom.Element) iterator.next();
				// Element AgentID = brand.getChild("AgentID");
				Element BigCode = brand.getChild("BigCode");
				list.add(BigCode.getValue());
			}
		}

		return list;
	}

	/**
	 * 查询企业余额
	 * 
	 * @param supplierID
	 * @return
	 */
	public Double getBalance(int supplierID) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));

		String result = connector.callAPI("GetBalance", paramName);
		return result != null && result.matches("^\\d+\\.{0,1}\\d+$") ? Double
				.parseDouble(result) : null;
	}

	/**
	 * 获取代理商余额
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public Double getAgentBalance() {
		String result = connector.callAPI("GetAgentBalance", null);
		return result != null && result.matches("^\\d+\\.{0,1}\\d+$") ? Double
				.parseDouble(result) : null;
	}

	/**
	 * 冻结座席组
	 * 
	 * @param supplierID
	 * @param GroupGuid
	 * @return
	 */
	public boolean lockGroup(int supplierID, String GroupGuid) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("GroupGuid", GroupGuid);

		return connector.callAPI("LockGroup", paramName) != null ? true : false;
	}

	/**
	 * 激活座席组
	 * 
	 * @param supplierID
	 * @param groupGuid
	 * @return
	 */
	public boolean acticeGroup(int supplierID, String groupGuid) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("GroupGuid", groupGuid);

		return connector.callAPI("ActiceGroup", paramName) != null ? true
				: false;
	}

	/**
	 * 删除座席组
	 * 
	 * @param GroupGuid
	 * @return
	 */
	public boolean delWorkGroup(String GroupGuid) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("GroupGuid", GroupGuid);

		return connector.callAPI("DelWorkGroup", paramName) != null ? true
				: false;
	}

	/**
	 * 修改座席组的400小号
	 * 
	 * @param supplierID
	 * @param guid
	 * @param extcode
	 * @return
	 */
	public boolean editWorkGroupExt400(int supplierID, String guid,
			String extcode) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("guid", guid);
		paramName.put("extcode", extcode);

		return connector.callAPI("EditWorkGroupExt400", paramName) != null ? true
				: false;
	}

	/**
	 * 修改呼转电话
	 * 
	 * @param supplierID
	 * @param guid
	 * @param phonenum
	 * @return
	 */
	public boolean editReCall(int supplierID, String guid, String phonenum) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("guid", guid);
		paramName.put("phonenum", phonenum);

		return connector.callAPI("EditReCall", paramName) != null ? true
				: false;
	}

	/**
	 * 删除企业
	 * 
	 * @param supplierID
	 * @return
	 */
	public boolean deleteCustomer(int custId) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("custid", String.valueOf(custId));

		return connector.callAPI("DeleteCustomer", paramName) != null ? true
				: false;
	}

	/**
	 * 单独大号下的小号码回收
	 * 
	 * @return
	 */
	public int callBack400Num_s(String bigcode) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("bigcode", String.valueOf(bigcode));

		String result = connector.callAPI("CallBack400Num_s", paramName);
		return result != null && result.matches("^\\d+$") ? Integer
				.parseInt(result) : 0;
	}

	/**
	 * 获得代理商下面所有企业ID列表
	 * 
	 * @return
	 */
	public List<String> getCustomerList() {
		List<String> result = new ArrayList<String>();
		String rtn = connector.callAPI("GetCustomerList_xml", null);
		if (result != null) {
			Element root = connector.parseXml(rtn, false);
			@SuppressWarnings("unchecked")
			List<Element> ds = root.getChildren("ds");
			for (Iterator<Element> iterator = ds.iterator(); iterator.hasNext();) {
				Element brand = (org.jdom.Element) iterator.next();
				Element id = brand.getChild("ID");
				result.add(id.getValue());
			}
		}
		return result;
	}

	/**
	 * 获取指定数量可用的400号码
	 * 
	 * @param supplierID
	 * @param maxReturn
	 * @return
	 */
	public String get400Codes(int supplierID, int maxReturn) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("supplierID", String.valueOf(supplierID));
		paramName.put("maxReturn", String.valueOf(maxReturn));

		String result = connector.callAPI("Get400Codes", paramName);
		return result != null ? result : "ERROR";
	}

	/**
	 * 修改座席
	 * 
	 * @param userid
	 * @param name
	 * @param extension
	 * @param password
	 * @param status
	 * @return
	 */
	public boolean editWorkUser(String userid, String name, String extension,
			String password, String status) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("userid", userid);
		paramName.put("name", name);
		paramName.put("extension", extension);
		paramName.put("password", password);
		paramName.put("status", status);

		return connector.callAPI("EditWorkUser", paramName) != null ? true
				: false;
	}

	/**
	 * 获取某企业下座席组列表
	 * 
	 * @param custid
	 * @param status
	 * @return
	 */
	public List<String> getWorkGroupCus(int custid, int status) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("custid", String.valueOf(custid));
		paramName.put("status", String.valueOf(status));

		List<String> result = new ArrayList<String>();
		String rtn = connector.callAPI("GetWorkGroupCus_xml", paramName);
		if (result != null) {
			Element root = connector.parseXml(rtn, false);
			@SuppressWarnings("unchecked")
			List<Element> ds = root.getChildren("ds");
			for (Iterator<Element> iterator = ds.iterator(); iterator.hasNext();) {
				Element brand = (org.jdom.Element) iterator.next();
				Element guid = brand.getChild("GroupGuid");
				Element user_list = brand.getChild("UserList");
				result.add(guid.getValue() + "," + user_list.getValue());
			}
		}
		return result;
	}

	/**
	 * 获得所有企业账户余额列表
	 * 
	 * @return ID, 余额, 通话费用
	 */
	public List<String> getFeeCust() {
		List<String> result = new ArrayList<String>();
		String rtn = connector.callAPI("GetFeeCust_xml", null);
		if (result != null) {
			Element root = connector.parseXml(rtn, false);
			@SuppressWarnings("unchecked")
			List<Element> ds = root.getChildren("ds");
			for (Iterator<Element> iterator = ds.iterator(); iterator.hasNext();) {
				Element brand = (org.jdom.Element) iterator.next();
				Element id = brand.getChild("ID");
				Element money = brand.getChild("Money");
				Element callFee = brand.getChild("CallFee");
				result.add(id.getValue() + "," + money.getValue() + ","
						+ callFee.getValue());
			}
		}
		return result;
	}

	/**
	 * 获得所有余额小于指定数字的企业列表
	 * 
	 * @param money
	 * @return ID, 余额, 通话费用
	 */
	public List<String> getNoFeeCust(double money) {
		List<String> result = new ArrayList<String>();
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("money", String.valueOf(money));

		String rtn = connector.callAPI("GetNoFeeCust_xml", null);
		if (result != null) {
			Element root = connector.parseXml(rtn, false);
			@SuppressWarnings("unchecked")
			List<Element> ds = root.getChildren("ds");
			for (Iterator<Element> iterator = ds.iterator(); iterator.hasNext();) {
				Element brand = (org.jdom.Element) iterator.next();
				Element id = brand.getChild("ID");
				Element m = brand.getChild("Money");
				Element callFee = brand.getChild("CallFee");
				result.add(id.getValue() + "," + m.getValue() + ","
						+ callFee.getValue());
			}
		}
		return result;
	}

	/**
	 * 激活或冻结企业
	 * 
	 * @param custid
	 * @param status
	 *            1 -激活企业 2 - 冻结企业
	 * @return
	 */
	public boolean editCustomerAct(int custid, int status) {
		Map<String, String> paramName = new HashMap<String, String>();
		paramName.put("custid", String.valueOf(custid));
		paramName.put("status", String.valueOf(status));

		return connector.callAPI("EditCustomerAct", paramName) != null ? true
				: false;
	}
}
