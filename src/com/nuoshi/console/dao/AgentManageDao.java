package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.agent.AgentManage;
import com.nuoshi.console.domain.agent.AgentRate;
import com.nuoshi.console.domain.agent.CityDist;
import com.nuoshi.console.domain.agent.HouseInfo;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.persistence.read.admin.agent.RejectReasonReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.AgentManageReadMapper;
import com.nuoshi.console.persistence.write.admin.agent.RejectReasonWriteMapper;
import com.nuoshi.console.persistence.write.taofang.agent.AgentManageWriteMapper;

/**
 * @author wanghongmeng
 * 
 */
@Repository
public class AgentManageDao {
	@Resource
	private AgentManageReadMapper agentManageReadMapper;
	@Resource
	private AgentManageWriteMapper agentManageWriteMapper;
	@Resource
	private RejectReasonReadMapper rejectReasonReadMapper;
	@Resource
	private RejectReasonWriteMapper rejectReasonWriteMapper;

	/**
	 * @param conditionList
	 *            搜索条件集合
	 * @return 符合搜索条件的经纪人集合
	 */
	public List<AgentManage> getAllAgentByPage(List<String> conditionList) {
		List<AgentManage> agentList = agentManageReadMapper.getAllAgentByPage(conditionList);
		for (AgentManage agent : agentList) {
			agent.setCityname(this.getLocalName(agent.getCityid()));
			agent.setDistname(this.getLocalName(agent.getDistid()));
			agent.setBlockname(this.getLocalName(agent.getBlockid()));
			agent.setCitydir(this.getDirName(agent.getCityid()));
			agent.setIllegal(this.getIllegalCount(agent.getId()));
			if ("".equals(agent.getBrand()) || null == agent.getBrand()) {
				agent.setBrand(SmcConst.AGENT_BRAND_ADDR_NULL);
			}
			if ("".equals(agent.getAddress()) || null == agent.getAddress()) {
				agent.setAddress(SmcConst.AGENT_BRAND_ADDR_NULL);
			}
			String status = agent.getStatus();
			int verify = agent.getVerifyStatus();
			if (verify==1 && "111".equals(status)) {//已认证 Globals.STATUS_OK.equalsIgnoreCase(status)
				agent.setVerifyResult(SmcConst.AGENT_VERIFY_NEW);
			} 
			if(verify==0 && status != null && status.contains("0")) {//未认证 status != null && status.contains("0") && 
				agent.setVerifyResult(SmcConst.AGENT_NOT_VERIFY_NEW);
			}
			if(status != null && status.contains("3") && !status.contains("4") && verify==1) {//已认证待审核
				agent.setVerifyResult(SmcConst.AGENT_VERIFY_AND_READ);
			}
			if(status != null && ("221".equals(status) ||  status.contains("3") && !status.contains("4") && verify==0 )) {//未认证待审核
				agent.setVerifyResult(SmcConst.AGENT_NOT_VERIFY_AND_READ);
			} 
			if(status != null && status.contains("4") && verify==1) {//已认证拒绝
				agent.setVerifyResult(SmcConst.AGENT_VERIFY_AND_REJECT);
			} 
			if(status != null && status.contains("4") && verify==0) {//未认证拒绝
				agent.setVerifyResult(SmcConst.AGENT_NOT_VERIFY_AND_REJECT);
			}
			
		}
		return agentList;
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人违规次数
	 */
	public int getIllegalCount(int id) {
		return agentManageReadMapper.getIllegalCount(id);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @param conditionList
	 *            查询条件
	 * @return 经纪人所有房源集合
	 */
	public List<HouseInfo> getAllHouseByPage(int id, List<String> conditionList) {
		List<HouseInfo> houseList = agentManageReadMapper.getAllHouseByPage(id, conditionList);
		return houseList;
	}

	/**
	 * @param id
	 *            地标id
	 * @return 地标中文名称
	 */
	public String getLocalName(int id) {
		return agentManageReadMapper.getLocalName(id);
	}

	/**
	 * @param id
	 *            地标id
	 * @return 地标拼音
	 */
	public String getDirName(int id) {
		return agentManageReadMapper.getDirName(id);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人具体信息
	 */
	public AgentManage getAgentInfo(int id) {
		AgentManage agent = agentManageReadMapper.getAgentInfo(id);
		if ("".equals(agent.getBrand()) || null == agent.getBrand()) {
			agent.setBrand(SmcConst.AGENT_BRAND_ADDR_NULL);
		}
		if ("".equals(agent.getAddress()) || null == agent.getAddress()) {
			agent.setAddress(SmcConst.AGENT_BRAND_ADDR_NULL);
		}
		return agent;
	}



	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteAgent(int id) {
		agentManageWriteMapper.deleteAgentRent(id);
		agentManageWriteMapper.deleteAgentResale(id);
		agentManageWriteMapper.deleteUser(id);
		//agentManageWriteMapper.deleteRateCount(id);
		//agentManageWriteMapper.deleteRate(id);
		agentManageWriteMapper.deleteUserStatus(id);
		//agentManageWriteMapper.deleteAgentStatus(id);
		agentManageWriteMapper.deleteAgentMaster(id);
	}
	
	/**
	 * @param id
	 *            经纪人id
	 */
	public void resetAgent(int id) {
		agentManageWriteMapper.resetAgentMaster(id);
	}

	/**
	 * @param pid
	 *            地标父亲id
	 * @return 地标信息集合
	 */
	public List<CityDist> getCityDist(int pid) {
		return agentManageReadMapper.getCityDist(pid);
	}

	/**
	 * @param agent
	 *            经纪人对象
	 */
	public void updateAgent(AgentManage agent) {
		agentManageWriteMapper.updateAgent(agent);
		agentManageWriteMapper.updateAgentMaster(agent);
	}

	/**
	 * @param blockid
	 *            商圈id
	 * @return 该商圈内的公司名称集合
	 */
	public List<String> getBrandByBlockId(int blockid) {
		return agentManageReadMapper.getBrandByBlockId(blockid);
	}

	/**
	 * @param brand
	 *            公司名称
	 * @return 该公司旗下的门店名称集合
	 */
	public List<String> getAddressByBrand(String brand) {
		return agentManageReadMapper.getAddressByBrand(brand);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 该经纪人审核的公司名称
	 */
	public String getAgentVerifyBrand(int id) {
		return agentManageReadMapper.getAgentMasterBrand(id);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 该经纪人审核的门店名称
	 */
	public String getAgentVerifyAddress(int id) {
		return agentManageReadMapper.getAgentMasterAddress(id);
	}

	/**
	 * @return 获取broker表id为1的公司名称
	 */
	public String getAgentBrokerBrand() {
		return agentManageReadMapper.getAgentBrokerBrand();
	}

	/**
	 * @return 获取broker表id为1的门店名称
	 */
	public String getAgentBrokerAddress() {
		return agentManageReadMapper.getAgentBrokerAddress();
	}

	/**
	 * @param type
	 *            类型标号，type=10的是短信息
	 * @return 短信信息集合
	 */
	public List<RejectReason> getAllMsg(int type) {
		return rejectReasonReadMapper.getAllMsg(type);
	}

	/**
	 * @param rejectReason
	 *            短信信息对象
	 * @return 增加短信记录的数据库id
	 */
	public int addMsg(RejectReason rejectReason) {
		return rejectReasonWriteMapper.addMsg(rejectReason);
	}

	/**
	 * @param id
	 *            短信信息id
	 */
	public void delMsg(int id) {
		rejectReasonWriteMapper.delMsg(id);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人的400id
	 */
	public int getUser400Id(int id) {
		return agentManageReadMapper.getUser400Id(id);
	}

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人绑定的电话
	 */
	public String getUserCallNumber(int id) {
		return agentManageReadMapper.getUserCallNumber(id);
	}
	
	public int disbound400Phone(int agentId) {
		return agentManageWriteMapper.disbound400Phone(agentId);
	}

}