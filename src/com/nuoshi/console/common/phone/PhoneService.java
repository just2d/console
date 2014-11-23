package com.nuoshi.console.common.phone;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class PhoneService {
	private PhoneRemoteService remote = new PhoneRemoteService();

//	public static void main(String[] args) {
//		PhoneService ps = new PhoneService();
//	}

	/* MD5编码 */
	private String encodeMD5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString(); // 如果只需要16位，可以截取8~24位
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "taofang";
	}

	/**
	 * 为本站用户注册400
	 * 
	 * @param userID
	 * @param money
	 * @param bindTel
	 * @param extcode
	 * @return
	 */
	public String registerUser(long userID, double money, String bindTel,
			String extcode) {
		String strUserID = String.valueOf(userID);

		// 在400系统中注册一个新的企业
		int userId = remote.registerAdOwner(strUserID, " ",
				PhoneConstants.CALL_FEE, 0.0, PhoneConstants.USER_NAME_PREFIX
						+ strUserID, encodeMD5(PhoneConstants.USER_PWD_PREFIX
						+ userID), " ", " ", null, " ", null, null, null, null,
				null, "support@58.com", "100000", 1, 0.0, 0.0);
		if (userId > 0) {
			// 在400系统中给企业注册大号
			if (remote.addCust400(userId, PhoneConstants.PHONE_NUMBER)) {
				// 加入座席组
				String rtn = remote.addWorkGroup_400(userId, PhoneConstants.GROUP_PREFIX + userId, 1, "您好", bindTel,
						0, PhoneConstants.PHONE_NUMBER, extcode);
				if (!"ERROR".equals(rtn)) {
					// 给用户充值
					recharge(userId, money);
					return userId + "," + rtn;
				}
			}
		}

		return null;
	}

	/**
	 * 删除注册用户
	 * 
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(int userId) {
		List<String> lst = remote.getWorkGroupCus(userId, 1);
		lst = lst.size() == 0 ? remote.getWorkGroupCus(userId, 2) : lst;
		for (String item : lst) {
			String groupGuid = item.split(",")[0];
			if (groupGuid != null && remote.delWorkGroup(groupGuid));
			else
				return false;
		}
		return remote.deleteCustomer(userId) && remote.callBack400Num_s(PhoneConstants.PHONE_NUMBER) > 0 ? true : false;
	}

	/**
	 * 修改用户绑定的电话
	 * 
	 * @param userId
	 * @param phone
	 * @return
	 */
	public boolean changeUserPhone(int userId, String phone) {
		List<String> lst = remote.getWorkGroupCus(userId, 1);
		for (String item : lst) {
			String user_id = item.split(",").length > 1 ? item.split(",")[1] : null;
			if (user_id != null && remote.editWorkUser(user_id, "", "", phone, ""));
			else
				return false;
		}
		return true;
	}

	/**
	 * 冻结用户
	 * 
	 * @param userId
	 * @return
	 */
	public boolean freezeUser(int userId) {
		return remote.editCustomerAct(userId, 2);
	}

	/**
	 * 重新激活已冻结的用户
	 * 
	 * @param userId
	 * @return
	 */
	public boolean activeUser(int userId) {
		return remote.editCustomerAct(userId, 1);
	}

	/**
	 * 为用户充值
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	public boolean recharge(int userId, double money) {
		return remote.payAdd(userId, money);
	}

	/**
	 * 获取公司总账的余额
	 * 
	 * @return
	 */
	public double getTotalBalance() {
		return remote.getAgentBalance();
	}

	/**
	 * 获取指定用户的余额
	 * 
	 * @param supplierID
	 * @return
	 */
	public double getUserBalance(int userId) {
		return remote.getBalance(userId);
	}

	/**
	 * 获得低于缺省余额的用户列表
	 * 
	 * @return <List> ID, 余额, 通话费用
	 */
	public List<String> getUserListLowBalance() {
		return remote.getNoFeeCust(PhoneConstants.DEFAULT_USER_FEE);
	}

	/**
	 * 获取所有注册用户的ID列表
	 * 
	 * @return
	 */
	public List<String> getUserList() {
		return remote.getCustomerList();
	}
}
