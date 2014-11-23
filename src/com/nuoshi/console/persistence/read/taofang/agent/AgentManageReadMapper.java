package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentManage;
import com.nuoshi.console.domain.agent.AgentRate;
import com.nuoshi.console.domain.agent.CityDist;
import com.nuoshi.console.domain.agent.HouseInfo;

/**
 * @author wanghongmeng
 * 
 */
public interface AgentManageReadMapper {
	/**
	 * @param conditionList
	 *            搜索条件集合
	 * @return 符合搜索条件的经纪人集合
	 */
	public List<AgentManage> getAllAgentByPage(@Param("conditionList") List<String> conditionList);

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人违规次数
	 */
	public int getIllegalCount(int id);

	/**
	 * @param id
	 *            经纪人id
	 * @param conditionList
	 *            查询条件
	 * @return 经纪人所有房源集合
	 */
	public List<HouseInfo> getAllHouseByPage(@Param("id") int id,
			@Param("conditionList") List<String> conditionList);

	/**
	 * @param id
	 *            地标id
	 * @return 地标中文名称
	 */
	public String getLocalName(int id);

	/**
	 * @param id
	 *            地标id
	 * @return 地标拼音简称
	 */
	public String getDirName(int id);

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人具体信息
	 */
	public AgentManage getAgentInfo(int id);

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人评价集合
	 */
	//public List<AgentRate> getAllRateByPage(int id);

	/**
	 * @param pid
	 *            地标父亲id
	 * @return 地标信息集合
	 */
	public List<CityDist> getCityDist(int pid);

	/**
	 * @param blockid
	 *            商圈id
	 * @return 该商圈内所有公司名称集合
	 */
	public List<String> getBrandByBlockId(int blockid);

	/**
	 * @param brand
	 *            公司名称
	 * @return 该公司旗下的门店名称集合
	 */
	public List<String> getAddressByBrand(String brand);

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人在agent_verify表中的公司
	 */
	public String getAgentMasterBrand(int id);

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人在agnet_verify表中的门店
	 */
	public String getAgentMasterAddress(int id);

	/**
	 * @return broker表中id=1的公司
	 */
	public String getAgentBrokerBrand();

	/**
	 * @return broker表中id=1的门店
	 */
	public String getAgentBrokerAddress();

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人的400id
	 */
	public int getUser400Id(int id);

	/**
	 * @param id
	 *            经纪人id
	 * @return 经纪人绑定的电话
	 */
	public String getUserCallNumber(int id);

	/**
	 * @param mobile
	 *            经纪人电话
	 * @return 经纪人的id
	 */
	public Integer getAgentIdByMobile(String mobile);

	/**
	 * @param mobile
	 *            用户电话
	 * @return 该用户的角色(经纪人、普通用户、未注册用户)
	 */
	public Integer getUserRole(String mobile);

}
