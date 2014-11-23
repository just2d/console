package com.nuoshi.console.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.taofang.seek.core.SearchClient;
import com.taofang.seek.core.SearchException;
import com.nuoshi.console.common.Helper;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.dao.RentDao;
import com.nuoshi.console.dao.ResaleDao;
import com.nuoshi.console.domain.estate.EstateChangeMessage;
import com.nuoshi.console.domain.estate.EstateData;
import com.nuoshi.console.domain.estate.EstateInfo;
import com.nuoshi.console.domain.estate.EstateSearchParam;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.domain.topic.Estate;
import com.nuoshi.console.jms.EstateJms;
import com.nuoshi.console.persistence.read.taofang.estate.EstateReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateWriteMapper;
import com.nuoshi.console.view.EstateListView;
import com.nuoshi.console.view.EstatePhoto;
import com.nuoshi.console.view.EstatePhotoCondition;

@Service
public class EstateService extends BaseService {

	Log log = LogFactory.getLog(EstateService.class);
	
	private String searchUrl = Resources.getString("search_engine_addr_estate");

	private SearchClient searchClient = new SearchClient(searchUrl);
	@Resource
	private EstateReadMapper estateReadMapper;

	@Resource
	private EstateWriteMapper estateWriteMapper;
	@Resource
	private ResaleDao resaleDao;
	@Resource
	private RentDao rentDao;

	private static final int DEFAULT_PAGE_SIZE = 20;

	@SuppressWarnings("unchecked")
	public List<Estate> initBasicInfo(int startIdx, int pageSize) {
		List<Estate> basicInfoList = null;
		basicInfoList = estateReadMapper.getBasicInfo(startIdx, pageSize);
		if (basicInfoList != null && basicInfoList.size() > 0) {
			for (Estate estate : basicInfoList) {
				estate = setEstate(estate);
				log.error(estate.getEstateId() + "---" + estate.getWyType() + "---" + estate.getGreenRate() + "----"
						+ estate.getAreaRate());
				estateWriteMapper.updateById(estate);
				EstateJms.send(estate.getId(), EstateChangeMessage.ChangeStatus.Modify);
			}
		}
		return basicInfoList != null && basicInfoList.size() > 0 ? basicInfoList : Collections.EMPTY_LIST;
	}
	
	public Estate getEstateByName(String name){
		return estateReadMapper.getEstateByName(name);
	}

	/**
	 * 根据查询条件获得小区列表
	 * 
	 * @param condition
	 * @return
	 * @throws SearchException
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	public List<EstateData> getEstateByCondition(EstateListView condition) throws SearchException,
			JsonProcessingException, IOException {
		// condition = this.setCondition(condition);
		List<EstateData> estateList = estateReadMapper.queryByCondition(condition.getCityId(), condition.getDistId(),
				condition.getBlockId(), StringUtils.isNotBlank(condition.getAddress()) ? "%" + condition.getAddress()
						+ "%" : null,
				StringUtils.isNotBlank(condition.getEstateName()) ? "%" + condition.getEstateName() + "%" : null,
				condition.getAuthStatus(), condition.getIsClicked(), condition.getStart(), condition.getLimit());
		return estateList != null && estateList.size() > 0 ? estateList : Collections.EMPTY_LIST;
	}

	public int countByCondition(EstateListView condition) {
		int total = estateReadMapper.countByCondition(condition.getCityId(), condition.getDistId(),
				condition.getBlockId(), StringUtils.isNotBlank(condition.getAddress()) ? "%" + condition.getAddress()
						+ "%" : null,
				StringUtils.isNotBlank(condition.getEstateName()) ? "%" + condition.getEstateName() + "%" : null,
				condition.getAuthStatus(), condition.getIsClicked());
		return total;
	}

	public int validateByCondition(EstateListView condition) {
		int total = estateReadMapper.countByCondition(condition.getCityId(), condition.getDistId(), condition
				.getBlockId(), StringUtils.isNotBlank(condition.getAddress()) ? "%" + condition.getAddress() + "%"
				: null, StringUtils.isNotBlank(condition.getEstateName()) ? condition.getEstateName() : null, condition
				.getAuthStatus(), condition.getIsClicked());
		return total;
	}

	public void updateDelStatus(EstateInfo estateInfo) {
		estateWriteMapper.updateDelStatus(estateInfo.getEstateId(), estateInfo.getDelStatus());
		EstateJms.send(estateInfo.getEstateId(), EstateChangeMessage.ChangeStatus.Modify);

	}

	public int updateAuthStatus(String authStatus, int estateId, String origStatus) {
		EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		return estateWriteMapper.updateAuthStatus(estateId, authStatus, origStatus);
		

	}
	public int updateAuthWStatus(String authStatus, int estateId, String origStatus,Integer createUserId) {
		if("3".equalsIgnoreCase(authStatus)){
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Delete);
		}else{
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}
			
		
		if(createUserId == null || createUserId==0){
			return estateWriteMapper.updateAuthWStatus(estateId, authStatus, origStatus,createUserId);
		}else{
			return estateWriteMapper.updateAuthStatus(estateId, authStatus, origStatus);
		}
		

	}

	public void updateAuthStatusAndNum(Map paramMap) {
		estateWriteMapper.updateAuthStatusAndNum(paramMap);
	}

	public void update(EstateInfo estateInfo) {
		if (estateInfo != null) {
			//			estateWriteMapper.updateById(estateInfo.getEstateName(), estateInfo.getAddress(), estateInfo.getEstateId(), estateInfo
			//					.getBasicInfo(), estateInfo.getCityId(), estateInfo.getDistId(), estateInfo.getBlockId(), estateInfo.getLon(),
			//					estateInfo.getLat(), estateInfo.getWyType(), estateInfo.getGreenRate(), estateInfo.getAreaRate(), estateInfo
			//							.getAuthStatus(), estateInfo.getDesc(), estateInfo.getAlias(), estateInfo.getNamepy(),estateInfo.getPinYin());
			Estate estate = estateReadMapper.getEstateInfoById(estateInfo.getEstateId());
			if(estate != null && (!estate.getDistId().equals(estateInfo.getDistId()) || !estate.getBlockId().equals(estateInfo.getBlockId()))){
				resaleDao.updateLocaleByEstate(estateInfo);
				rentDao.updateLocaleByEstate(estateInfo);
			}
			
			if(estate != null && !estateInfo.getEstateName().equals(estate.getEstateName())){
				resaleDao.updateEstateNameByEstateId(estateInfo);
				rentDao.updateEstateNameByEstateId(estateInfo);
			}
			
			estateWriteMapper.updateById(estateInfo);
			EstateJms.send(estateInfo.getEstateId(), EstateChangeMessage.ChangeStatus.Modify);
		}

	}

	/**
	 * 根据主键获得小区基本信息
	 * 
	 * @param estateId
	 * @return
	 */
	public Estate getEstateInfoById(int estateId) {
		return estateReadMapper.getEstateInfoById(estateId);
	}

	/**
	 * 根据小区id获得小区信息
	 * 
	 * @param estateId
	 * @return
	 */
	public EstateData getEstateDataById(int estateId) {
		return estateReadMapper.getEstateDataById(estateId);
	}

	public void deleteByEstateId(int estateId) {
		estateWriteMapper.delelteByEstateId(estateId);
		EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Delete);
	}

	public void addEstate(EstateInfo estaete) {
		estateWriteMapper.insertEstate(estaete);
		EstateJms.send(estaete.getId(), EstateChangeMessage.ChangeStatus.Add);

	}

	public void updateBacupAndLayoutNum(int estateId, int layoutcnt,int layoutphotocnt,int photocnt,int commphotocnt ) {
		EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		estateWriteMapper.updateBacupAndLayoutNum(estateId, layoutcnt, layoutcnt,photocnt,commphotocnt);

	}

	public void setDefaultPhoto(EstateInfo estateInfo) {
		estateWriteMapper.setDefaultPhoto(estateInfo.getEstateId(), estateInfo.getPhotoId());
		EstateJms.send(estateInfo.getEstateId(), EstateChangeMessage.ChangeStatus.Modify);
	}
	
	public  void udpateRturl(String url,int estateid){
		estateWriteMapper.updateRturl(url, estateid);
		EstateJms.send(estateid, EstateChangeMessage.ChangeStatus.Modify);
	}

	private Estate setEstate(Estate estate) {
		String basicInfo = estate.getBasicInfo();
		if (StringUtils.isNotEmpty(basicInfo) && StringUtils.isNotBlank(basicInfo) && basicInfo.contains(";")) {
			String infoArray[] = basicInfo.split(";");
			if (infoArray != null && infoArray.length > 0) {
				// 只查到容积率和绿化率
				for (int i = 0; i < infoArray.length; i++) {
					String info = infoArray[i];
					// 获得物业类型
					if (info != null) {
						if (i == 0) {
							// String info = basicInfo.substring(0,
							// basicInfo.indexOf(";"));
							String newWyType = "";
							if (info.startsWith("物业类型:")) {
								if (info.contains("住宅") || info.contains("公寓")) {
									estate.setWyType("1");
									newWyType = Helper.getConfig("xiao_wyType_zhuzhai");
								} else if (info.contains("商铺")) {
									newWyType = Helper.getConfig("xiao_wyType_shuangpu");
									estate.setWyType("2");
								} else if (info.contains("写字楼")) {
									newWyType = Helper.getConfig("xiao_wyType_xiezilou");
									estate.setWyType("4");
								} else if (info.contains("别墅")) {
									estate.setWyType("3");
									newWyType = Helper.getConfig("xiao_wyType_bieshu");
								} else {
									estate.setWyType("5");
									newWyType = Helper.getConfig("xiao_wyType_qita");
								}
							} else if (info.startsWith("商铺类型")) {
								estate.setWyType("2");
								newWyType = Helper.getConfig("xiao_wyType_shuangpu");
							} else if (info.startsWith("写字楼类型")) {
								estate.setWyType("4");
								newWyType = Helper.getConfig("xiao_wyType_xiezilou");
							} else if (info.startsWith("别墅类型")) {
								estate.setWyType("4");
								newWyType = Helper.getConfig("xiao_wyType_xiezilou");
							} else {
								estate.setWyType("5");
								newWyType = Helper.getConfig("xiao_wyType_qita");
							}
						} else if (info.startsWith("容积率")) {
							String areaRate[] = info.split(":");
							if (areaRate != null && areaRate.length >= 2 && !"暂无资料".equals(areaRate[1])
									&& areaRate[1].length() <= 5) {
								estate.setAreaRate(areaRate[1]);
							}
						} else if (info.startsWith("绿化率")) {
							String greenRate[] = info.split(":");
							if (greenRate != null && greenRate.length >= 2 && !"暂无资料".equals(greenRate[1])
									&& greenRate[1].length() <= 5) {
								estate.setGreenRate(greenRate[1]);
							}
						}
					}

				}
			}

			// 获得容积率

			// basicInfo = newWyType.concat(basicInfo.replaceFirst(info,
			// ""));
		} else if ("".equals(basicInfo) || basicInfo == null) {
			estate.setWyType("5");
		}
		// 将小区审核状态全部置为未通过审核.
		estate.setAuthStatus("0");
	
		return estate;
	}

	public int countBasicInfoNum() {
		return estateReadMapper.countBasicInfoNum();
	}

	/**
	 * 拼装查询条件.
	 * 
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String bufferQueryCondition(EstateListView estate) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder("qt=search");
		sb.append("&start=" + estate.getStart());
		if (estate.getLimit() == 0) {
			estate.setLimit(DEFAULT_PAGE_SIZE);
		}
		/* fq:查询缓存 */
		// 城市
		if (estate.getCityId() != 0 && estate.getCityId() != -1) {
			sb.append("&fq=cityId:" + estate.getCityId());
		}
		if (estate.getDistId() != 0 && estate.getDistId() != -1) {
			sb.append("&fq=distId:" + estate.getDistId());
		}
		if (estate.getBlockId() != 0 && estate.getBlockId() != -1) {
			sb.append("&fq=blockId:" + estate.getBlockId());
		}
		if (StringUtils.isNotBlank(estate.getAddress())) {
			sb.append("&fq=address:" + estate.getAddress());
		}
		if (StringUtils.isNotBlank(estate.getEstateName())) {
			sb.append("&fq=estateName:" + estate.getEstateName());
		}
		sb.append("&rows=" + estate.getLimit());
		sb.append("&wt=json");
		return sb.toString();
	}

	/**
	 * public static void main(String args[]) { String s ="物业类型:住宅,普通住宅;楼盘地址:嘉定区绿苑路177弄;容积率:2.30;绿化率:40%;建筑年代:1995-01-01;楼层状况:多层。;物业费:暂无资料;车位信息:暂无资料;物业公司:暂无资料;开发商:暂无资料;占地面积:;建筑面积:;教育配套:幼儿园：黄渡中心幼儿园 中小学：黄渡中学、黄渡小学 大学：同济大学嘉定校区;商业配套:综合商场：上海燕俊萍贸易商行、上海 医院：黄渡镇卫生院 邮局：黄渡邮政支局 银行：农行黄渡支行、中行黄渡支 其他：金师傅馄饨黄渡店 小区内部配套：棋牌室 羽毛球场;交通配套:青黄专线、翔安专线、翔黄专线、安虹线、安亭5路;"
	 * ; String preStr = s.substring(0, s.indexOf(";")); String lastStr =
	 * s.substring(s.indexOf(";"));
	 * System.out.println("wy".concat(s.replaceFirst("物业类型:住宅,普通住宅;", "")));
	 * System.out.println(lastStr); System.out.println(preStr); }
	 **/

	private EstateListView setCondition(EstateListView condition) {
		condition.setAddress("%" + condition.getAddress());
		condition.setEstateName("%" + condition.getEstateName());
		return condition;
	}

	/**
	 * 获得户型图列表.
	 * 
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EstatePhoto> getPhotoListByPage(EstatePhotoCondition condition) {
		List<EstatePhoto> list = estateReadMapper.getPhotoList(setConditionName(condition));
		return list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
	}

	/**
	 * 统计户型图列表.
	 * 
	 * @param condition
	 * @return
	 */
	public int countPhotoList(EstatePhotoCondition condition) {
		int total = estateReadMapper.countPhotoList(setConditionName(condition));
		return total;
	}

	private EstatePhotoCondition setConditionName(EstatePhotoCondition condition) {
		EstatePhotoCondition tmpCondition = new EstatePhotoCondition();
		if (condition != null) {
			if ("".equals(condition.getEstateName())) {
				condition.setEstateName(null);

			}
			BeanUtils.copyProperties(condition, tmpCondition);
			if (StringUtils.isNotBlank(condition.getEstateName())) {
				tmpCondition.setEstateName("%" + condition.getEstateName() + "%");
			}

		}
		return tmpCondition;
	}

	/**
	 * 更新小区二手房,租房数量
	 * @param paramMap
	 */
	public void updateResaleAndRentCount(Map paramMap) {
		estateWriteMapper.updateResaleAndRentCount(paramMap);
	}

	public String getPinYin(String pinyin) {
		int num = 1;
		String callURL = Helper.getConfig("serviceUrl") + "/estate/getEstateByPinyin";
		String result = callService(callURL, pinyin);
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		int hasExist = 0;
		String new_Pinyin = "";
		if (jsonResult != null) {
			hasExist = Integer.parseInt(StringUtils.isNotBlank(jsonResult.getData()) ? jsonResult.getData() : "0");
			if (jsonResult.isResult() && hasExist > 0) {
				while (true) {
					new_Pinyin = pinyin + num;
					result = callService(callURL, new_Pinyin);
					jsonResult = gson.fromJson(result, JsonResult.class);
					hasExist = Integer.parseInt(jsonResult.getData());
					if (jsonResult.isResult() && hasExist > 0) {
						num++;
					} else {
						break;
					}
				}
			} else {
				new_Pinyin = pinyin;
			}
		}

		return new_Pinyin;
	}

	public Double getHouseAvgPriceByType(Integer estateId, Integer houseType, String month) {
		return estateReadMapper.getHouseAvgPriceByType(estateId, houseType, month);
	}

	public EstateData getEstateDataByIdAndAuthStatus(Integer estateId, String authStatus) {
		// TODO Auto-generated method stub
		return estateReadMapper.getEstateDataByIdAndAuthStatus(estateId,authStatus);
	}
	
	/**
	 * 在发布房源时，下拉显示小区时用
	 * @param searchCondition
	 * @return
	 * @throws SearchException
	 */
	public String doSearchForAdd(EstateSearchParam searchCondition) throws SearchException {
		String rs = "";
		try {
			String expression = translateForAdd(searchCondition);
			rs = searchClient.queryAsString(expression);
		} catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
		}

		return rs;
	}
	
	/**
	 * 将domain对象翻译成solar接收的查询串
	 * 在发布房源时，下拉显示小区时用
	 * @param searchCondition
	 * @throws UnsupportedEncodingException
	 */
	private String translateForAdd(EstateSearchParam searchCondition) throws UnsupportedEncodingException {
		StringBuffer exp = new StringBuffer();
		
		exp.append("wt=json");
		if(searchCondition.getKeyword()!=null){
			exp.append("&q=" + URLEncoder.encode(searchCondition.getKeyword().toLowerCase(), "utf-8"));
		}
	
		if(searchCondition.getCityId()>0){
			exp.append("&fq=cityId:" + searchCondition.getCityId());
		}
			
		exp.append("&start=" + searchCondition.getStart());
		exp.append("&rows=" + searchCondition.getLimit());
		return exp.toString();
	}

}
