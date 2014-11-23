package com.nuoshi.console.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taofang.biz.local.AgentPhotoUrlUtil;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.phone.PhoneService;
import com.nuoshi.console.common.util.FileHandleUtil;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.dao.AgentManageDao;
import com.nuoshi.console.dao.AgentMasterDao;
import com.nuoshi.console.domain.agent.AgentDeleteLog;
import com.nuoshi.console.domain.agent.AgentManage;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.AgentSearch;
import com.nuoshi.console.domain.agent.CityDist;
import com.nuoshi.console.domain.agent.HouseInfo;
import com.nuoshi.console.domain.agent.RejectReason;

/**
 * @author wanghongmeng
 * 
 */
@Service
public class AgentManageService extends BaseService {
	@Resource
	private AgentManageDao agentManageDao;
	@Resource
	private AgentMasterDao agentMasterDao;

	/**
	 * @param conditionList
	 *            搜索条件集合
	 * @return 符合搜索条件的经纪人集合
	 */
	public List<AgentManage> getAllAgentByPage(List<String> conditionList) {
		return agentManageDao.getAllAgentByPage(conditionList);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @param type
	 *            查询类型
	 * @return 经纪人所有房源集合
	 */
	public List<HouseInfo> getAllHouseByPage(int id, int type) {
		List<String> conditionList = new ArrayList<String>();
		switch (type) {
		case SmcConst.HOUSE_STATUS_ALL:
			conditionList = null;
		break;
		case SmcConst.HOUSE_STATUS_ONSHELVE:
			conditionList.add("house_status = 1 ");
			break;
		case SmcConst.HOUSE_STATUS_UNSHELVE:
			conditionList.add("house_status = 0 ");
			break;
		case SmcConst.HOUSE_STATUS_OUTOFDATE:
			conditionList.add("house_status = 3 ");
			break;
		case SmcConst.HOUSE_STATUS_ILLEGAL:
			conditionList.add("house_status = 2 ");
			break;
		case SmcConst.HOUSE_STATUS_DELETED:
			conditionList.add("house_status = 4 ");
			break;
		case SmcConst.HOUSE_STATUS_DRAFTBOX:
			conditionList.add("house_status = 5 ");
			break;
		case SmcConst.HOUSE_STATUS_ONSHELVE_UNCHECK:
			conditionList.add("house_status = 1 ");
			conditionList.add("audit_status = 0 ");
			break;
		case SmcConst.HOUSE_STATUS_ONSHELVE_CHECKED:
			conditionList.add("house_status = 1 ");
			conditionList.add("audit_status = 1 ");
			break;
		case SmcConst.HOUSE_STATUS_NOAUDIT_AGENT:
			conditionList.add(" house_status = 6 ");
			break;
		default:
			break;
	}
		return agentManageDao.getAllHouseByPage(id, conditionList);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人具体信息
	 */
	public AgentManage getAgentInfo(int id) {
		return agentManageDao.getAgentInfo(id);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人信用集合
	 */
	/*public List<AgentRate> getAllRateByPage(int id) {
		return agentManageDao.getAllRateByPage(id);
	}*/

	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteAgentBind400(int id) {
		PhoneService phoneService = new PhoneService();
		Integer user400id = agentManageDao.getUser400Id(id);
		phoneService.deleteUser(user400id);
	}

	/**
	 * @param id
	 *            经纪人id
	 */
	public void resetPhoneNumber(int id) {
		String callNumber = agentManageDao.getUserCallNumber(id);
		agentMasterDao.resetPhoneStatus(callNumber);
	}

	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteAgent(int id, int operatorId, String operatorName) {
		AgentMaster agent = agentMasterDao.selectAgentMasterById(id);
		agentManageDao.deleteAgent(id);
		AgentDeleteLog agentDeleteLog = new AgentDeleteLog();
		agentDeleteLog.setAgentId(id);
		agentDeleteLog.setName(agent.getName());
		agentDeleteLog.setMobile(agent.getMobile());
		agentDeleteLog.setInfo("城市：" + agent.getCityId() + ",城区：" + agent.getDistId() + "，商圈："+ agent.getBlockId() +","+ 
				agent.getCompanyId() + ":" + agent.getCompany() + ";" + agent.getStoreId() + ":" + agent.getStore());
		agentDeleteLog.setEntryId(operatorId);
		agentDeleteLog.setEntryName(operatorName);
		
		agentMasterDao.addAgentDeleteLog(agentDeleteLog);
	}
	
	/**
	 * @param id
	 *            经纪人id
	 */
	public void resetAgent(int id) {
		agentManageDao.resetAgent(id);
	}
	

	/**
	 * @param pid
	 *            地标父亲id
	 * @return 地标信息集合
	 */
	public List<CityDist> getCityDist(int pid) {
		return agentManageDao.getCityDist(pid);
	}

	/**
	 * @param agent
	 *            经纪人对象
	 */
	public void updateAgent(AgentManage agent) {
		//取DB中的agent，若城市不同，则移动相应图片
		AgentManage agentDB = agentManageDao.getAgentInfo(agent.getId());
		agentManageDao.updateAgent(agent);
		
		if(agent.getCityid() != agentDB.getCityid()){
			//审核中头像
			 FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getPendingAgentHeadPortraitRelativeUrlLarge(agentDB.getCityid(), agentDB.getId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getPendingAgentHeadPortraitRelativeUrlLarge(agent.getCityid(), agent.getId())), true);
			 FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getPendingAgentHeadPortraitRelativeUrlSmall(agentDB.getCityid(), agentDB.getId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getPendingAgentHeadPortraitRelativeUrlSmall(agent.getCityid(), agent.getId())), true);
			 //审核通过的头像
			 FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentHeadPortraitRelativeUrlLarge(agentDB.getCityid(), agentDB.getId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentHeadPortraitRelativeUrlLarge(agent.getCityid(), agent.getId())), true);
			 FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentHeadPortraitRelativeUrlSmall(agentDB.getCityid(), agentDB.getId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentHeadPortraitRelativeUrlSmall(agent.getCityid(), agent.getId())), true);
			 
			 //名片
			 FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentBusinessCardPhotoRelativeUrl(agentDB.getCityid(), agentDB.getId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentBusinessCardPhotoRelativeUrl(agent.getCityid(), agent.getId())), true);
			//身份证
			 FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentIDCardPhotoRelativeUrl(agentDB.getCityid(), agentDB.getId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentIDCardPhotoRelativeUrl(agent.getCityid(), agent.getId())), true);
			 
		}
	}

	/**
	 * @param blockid
	 *            商圈id
	 * @return 该商圈内的公司名称集合
	 */
	public List<String> getBrandByBlockId(int blockid) {
		return agentManageDao.getBrandByBlockId(blockid);
	}

	/**
	 * @param brand
	 *            公司名称
	 * @return 该公司旗下的门店名称集合
	 */
	public List<String> getAddressByBrand(String brand) {
		return agentManageDao.getAddressByBrand(brand);
	}

	/**
	 * @param agentSearch
	 *            搜索条件对象
	 * @return 搜索条件list集合
	 */
	public List<String> convertSearchCondition(AgentSearch agentSearch) {
		List<String> conditionList = new ArrayList<String>();
		if (agentSearch.getSearchtxt() != null
				&& !SmcConst.SEARCH_TXT_NULL.equals(agentSearch.getSearchtxt())
				&& !agentSearch.getSearchtxt().trim().equals("")) {
			if (agentSearch.getType() != null
					&& !SmcConst.SEARCH_SELECT_NULL.equals((agentSearch.getType()))) {
				if (SmcConst.SEARCH_TYPE_ID.equals(agentSearch.getType())) {
					conditionList.add("u.id like '%"
							+ agentSearch.getSearchtxt().trim() + "%' ");
				} else if (SmcConst.SEARCH_TYPE_NAME.equals(agentSearch.getType())) {
					conditionList.add("u.name like '%"
							+ agentSearch.getSearchtxt().trim() + "%' ");
				} else if (SmcConst.SEARCH_TYPE_BRAND.equals(agentSearch.getType())) {
					conditionList.add("am.company like '%"
							+ agentSearch.getSearchtxt().trim() + "%' ");
				} else if (SmcConst.SEARCH_TYPE_ADDRESS.equals(agentSearch
						.getType())) {
					conditionList.add("am.store like '%"
							+ agentSearch.getSearchtxt().trim() + "%' ");
				} else if (SmcConst.SEARCH_TYPE_MOBILE.equals(agentSearch
						.getType())) {
					conditionList.add("u.mobile like '%"
							+ agentSearch.getSearchtxt().trim() + "%' ");
				} else if (SmcConst.SEARCH_TYPE_ICCARD.equals(agentSearch
						.getType())) {
					conditionList.add("am.idcard_num like '%"
							+ agentSearch.getSearchtxt().trim() + "%' ");
				}
			}
		}
		String verify = agentSearch.getVerifyResult();
		if (verify != null && !SmcConst.SEARCH_SELECT_NULL.equals(verify)) {
			if ((SmcConst.AGENT_VERIFY_NEW+"").equals(verify)) {//已审核
				conditionList.add(" am.verify_status = 1 and am.status = '111' ");
			} else if ((SmcConst.AGENT_NOT_VERIFY_NEW+"").equals(verify)) {//未审核
				conditionList.add(" am.verify_status = 0 and am.status like '%0%'");//in('000','001','020','021','200','201','220')
				//conditionList.add(" am.mobile_check_status = 2  ");
			} else if ((SmcConst.AGENT_VERIFY_AND_READ+"").equals(verify)) {//已认证待审核
				conditionList.add(" am.verify_status = 1 and am.status in('131','311','331') "); // 
				conditionList.add(" am.mobile_check_status = 2  ");//手机审核通过为2，
			} else if ((SmcConst.AGENT_NOT_VERIFY_AND_READ+"").equals(verify)) {//未认证待审核
				conditionList.add(" am.verify_status = 0 and am.status in('221','131','311','331')   "); //  am.status in('231','321','331','121','131','211','311')
				conditionList.add(" am.mobile_check_status = 2  ");
			} else if ((SmcConst.AGENT_VERIFY_AND_REJECT+"").equals(verify)) {//已认证拒绝
				conditionList.add(" am.verify_status = 1 and am.status like '%4%'");
				conditionList.add(" am.mobile_check_status = 2  ");
			} else if ((SmcConst.AGENT_NOT_VERIFY_AND_REJECT+"").equals(verify)) {//未认证拒绝
				conditionList.add(" am.verify_status = 0 and am.status like '%4%'");
				conditionList.add(" am.mobile_check_status = 2  ");
			}
			
			
//			if (SmcConst.SEARCH_VERIFIED.equals(verify)) {
//				conditionList.add("am.status = '111'");
//			} else if (SmcConst.SEARCH_VERIFY_READY.equals(verify)) {
//				conditionList.add("  am.status in('221','231','321','331','121','131','211','311') ");
//				conditionList.add(" am.mobile_check_status=2  ");
//			} else if (SmcConst.SEARCH_VERIFY_NOT_READY.equals(verify)) {
//				//5-18修改，和DBA确认过该语句
//				conditionList.add("(am.STATUS not in('111','221','231','321','331','121','131','211','311') " +
//						"or (am.status ='222' and am.mobile_check_status!=2) " +
//						"or (am.STATUS ='221' and am.mobile_check_status!=2))");
//			}
		}
		// if (agentSearch.getAccountType() != null
		// && !SmcConst.SEARCH_SELECT_NULL.equals(agentSearch
		// .getAccountType())) {
		// if (SmcConst.SEARCH_ACCOUNT_FREE.equals(agentSearch
		// .getAccountType())) {
		// conditionList.add("agent_account.type <> 0 ");
		// } else {
		// conditionList.add("agent_account.type = 0 ");
		// }
		// }

		if (agentSearch.getStartDate() != null
				&& !SmcConst.SEARCH_STARTDATE_NULL.equals(agentSearch
						.getStartDate())) {
			if (agentSearch.getEndDate() != null
					&& !SmcConst.SEARCH_ENDDATE_NULL.equals(agentSearch
							.getEndDate())) {
				conditionList.add("DATE_FORMAT(u.create_time,'%Y-%m-%d') >= '"
						+ agentSearch.getStartDate()
						+ "' AND DATE_FORMAT(u.create_time,'%Y-%m-%d') <= '"
						+ agentSearch.getEndDate() + "' ");
			} else {
				conditionList.add("DATE_FORMAT(u.create_time,'%Y-%m-%d') >= '"
						+ agentSearch.getStartDate() + "' ");
			}
		} else {
			if (agentSearch.getEndDate() != null
					&& !SmcConst.SEARCH_ENDDATE_NULL.equals(agentSearch
							.getEndDate())) {
				conditionList.add("DATE_FORMAT(u.create_time,'%Y-%m-%d') <= '"
						+ agentSearch.getEndDate() + "' ");
			}
		}
		String flag="";
		flag=agentSearch.getBanFlag();
		if (flag != null
				&& !SmcConst.SEARCH_SELECT_NULL.equals(flag)) {
			if("Y".equals(flag)){
				conditionList.add("am.ban_flag = '" + flag + "' ");
			}
			if("N".equals(flag)){
				conditionList.add("( am.ban_flag = 'N' or  am.ban_flag is null )");
			}
		}
		Integer payStatus=0;
		payStatus=agentSearch.getPayStatus();
		if (payStatus != null&&payStatus>0) {
			if(payStatus==2){//付费用户
				conditionList.add("am.pay_status = " + 2 + " ");
			}else if(payStatus == 1){//免费用户
				conditionList.add("am.pay_status = " + 0 + " ");
			} else if(payStatus == 3) {
				conditionList.add("am.pay_status = " + 1 + " ");
			}
		}
		
		if (agentSearch.getCity() != null
				&& !SmcConst.SEARCH_SELECT_NULL.equals(agentSearch.getCity())) {
			conditionList.add("am.city_id = " + agentSearch.getCity() + " ");
		}
		if (agentSearch.getDist() != null
				&& !SmcConst.SEARCH_SELECT_NULL.equals(agentSearch.getDist())) {
			conditionList.add("am.dist_id = " + agentSearch.getDist() + " ");
		}
		if (agentSearch.getBlock() != null
				&& !SmcConst.SEARCH_SELECT_NULL.equals(agentSearch.getBlock())) {
			conditionList.add("am.block_id = " + agentSearch.getBlock() + " ");
		}
		if (agentSearch.getBrand() != null
				&& !SmcConst.SEARCH_SELECT_NULL.equals(agentSearch.getBrand())) {
			conditionList.add("am.company like '%"
					+ agentSearch.getBrand() + "%' ");
		}
		if (agentSearch.getAddress() != null
				&& !SmcConst.SEARCH_SELECT_NULL
						.equals(agentSearch.getAddress())) {
			conditionList.add("am.store like '%"
					+ agentSearch.getAddress() + "%' ");
		}
		return conditionList;
	}

	/**
	 * @param type
	 *            类型标号，type=10的是短信息
	 * @return 短信信息集合
	 */
	public List<RejectReason> getAllMsg(int type) {
		return agentManageDao.getAllMsg(type);
	}

	/**
	 * @param rejectReason
	 *            短信信息对象
	 * @return 增加短信记录的数据库id
	 */
	public int addMsg(RejectReason rejectReason) {
		return agentManageDao.addMsg(rejectReason);
	}

	/**
	 * @param id
	 *            短信信息id
	 */
	public void delMsg(int id) {
		agentManageDao.delMsg(id);
	}
	
	public int disbound400Phone(int agentId) {
		return agentManageDao.disbound400Phone(agentId);
	}
	
	public String[] checkSensitiveWord(String content) {
//		System.out.println("校验敏感词是否存在: 校验内容"+content);
		String result = "";
		String hostUrl;
		String urlStr;
		URL url;
		HttpURLConnection httpUrl = null;
		BufferedReader in;
		String line;
		try {
			hostUrl = Resources.getString("taofang.sensitive.url");
			content = URLEncoder.encode(content, "utf-8");
			urlStr = hostUrl;
			//urlStr = hostUrl + "?content='"	+ content + "'";
			url = new URL(urlStr);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.setDoOutput(true);// 打开写入属性 
			httpUrl.setDoInput(true);// 打开读取属性 
			httpUrl.setRequestMethod("POST");
			//httpUrl.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			httpUrl.connect(); 
			
			DataOutputStream out = new DataOutputStream(httpUrl.getOutputStream());
			String contents = "content='" + content+"'";
//			System.out.println("校验敏感词是否存在: 校验内容编码 "+contents);
			out.write(contents.getBytes());
			out.flush();
		    out.close();
			
			in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line;
			}
//			System.out.println("校验敏感词是否存在: 校验返回结果 "+result);
			in.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			return null;
		}finally{
			if(httpUrl != null) httpUrl.disconnect();
		}
		int start = result.indexOf("][");
		result = result.substring(start + 2, result.length() - 1);
		if(result.length() > 1) {
			return result.split(",");
		}
	    return null;
	   
	}
	
	public static void main(String[] args) {
		String str = "[六合彩, 妓女, 一夜情, 夜情][二奶, 一夜]";
		System.out.println(str.indexOf("]["));
		String temp = "<p>a";
		System.out.println(temp.contains("<p>"));
	}
}
