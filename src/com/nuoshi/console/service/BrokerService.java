package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.BrokerDao;
import com.nuoshi.console.domain.agent.Brand;
import com.nuoshi.console.domain.agent.Broker;

@Service
public class BrokerService {
	@Resource
	BrokerDao brokerDao;
	@Resource
	BrandService brandService;
	/**
	 * 门店数量加一
	 */
	private final Integer ADD_ONE=1;
	/**
	 * 门店数量减一
	 */
	private final Integer CUT_ONE=2;
	
	public int addBroker(Broker broker) {
		brokerDao.addBroker(broker);
		try{
			this.saveBrokerCount(broker, this.ADD_ONE);
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
		return broker.getId();
	}

	public void deleteBroker(String id) {
		//删除门店时更新公司下门店数量
		if(StringUtils.isBlank(id)){
			throw new RuntimeException("id类型错误!");
		}
		try{
			Broker broker=brokerDao.searchBrokerById(id);
			brokerDao.deleteBroker(id);
			if(broker!=null){
			this.saveBrokerCount(broker, this.CUT_ONE);
			}
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
	}

	public void updateBroker(Broker broker) {
		brokerDao.updateBroker(broker);
	}

	public Broker searchBrokerById(String id) {
		return brokerDao.searchBrokerById(id);
	}
	
	private Brand brokerCount(Brand brand,Integer operateType){
		String brokerNum=brand.getBrokercnt();
		Integer brokerInt = 0;
		//转换string类型的数量
		if (StringUtils.isBlank(brokerNum)) {
			brokerInt=0;
		}else{
			try{
				brokerInt = Integer.valueOf(brokerNum);
			}catch(RuntimeException e){
				throw new RuntimeException("公司中的门店数量不是有效的数字!");
			}
		}
		
		//操作数量
		if(operateType==this.ADD_ONE){
			
			if(brokerInt<0){
				brokerInt=0;
			}
			if(brokerInt>=0){
				++brokerInt;
			}
			
		}
		if(operateType==this.CUT_ONE){
			if(brokerInt<=0){
				brokerInt=0;
			}
			if(brokerInt>0){
				--brokerInt;
			}
		}
		brand.setBrokercnt(""+brokerInt);
		return brand;
		
	}
	//保存公司门店数量
	private void saveBrokerCount(Broker broker ,Integer operatorType){
		try {
			Integer brandId = broker.getBrandid();
			Brand brand;
			if (brandId != null) {
				brand = brandService.getBrandById(brandId);
			} else {
				throw new RuntimeException("门店不属于任何公司!");
			}
			if (brand != null) {
				brand = this.brokerCount(brand, operatorType);
				brandService.updateBrand(brand);
			} else {
				throw new RuntimeException("公司ID对应的公司不存在!");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("更新公司门店数量出错!");
		}
	}
	
	/**
	 * 通过公司id删除门店
	 * @param id
	 */
	public void deleteBrokerByBrandId(String id) {
		
		brokerDao.deleteBrokerByBrandId(id);
	}

}
