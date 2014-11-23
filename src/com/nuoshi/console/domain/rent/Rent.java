package com.nuoshi.console.domain.rent;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuoshi.console.domain.base.House;

public class Rent extends House implements Serializable {

	private static final long serialVersionUID = -5369379803580585706L;
	
	// 支付方式
	private byte paytype;

	// 押金
	private byte deposit;

	// 是否合租 flatting = 1 整租 ， flatting = 2 合租 3床位
	private byte flatting;

	// 装备
	private int equipment;

	private int rentType;

	public byte getPaytype() {
		return paytype;
	}

	public void setPaytype(byte paytype) {
		this.paytype = paytype;
	}

	public byte getDeposit() {
		return deposit;
	}

	public void setDeposit(byte deposit) {
		this.deposit = deposit;
	}

	public byte getFlatting() {
		return flatting;
	}

	public void setFlatting(byte flatting) {
		this.flatting = flatting;
	}

	public int getEquipment() {
		return equipment;
	}

	public void setEquipment(int equipment) {
		this.equipment = equipment;
	}

	public int getRentType() {
		return rentType;
	}

	public void setRentType(int rentType) {
		this.rentType = rentType;
	}
	 /**
	 * 房源所填写的项数
	 * @return
	 */
	public int getOptionNum() {
		Gson gson = new Gson();
		int option = 0;
			if(!StringUtils.isBlank(this.getRecordCode())){
				option++;
			}
			if(this.getFacility()!=null&&this.getFacility()>0){
				option++;
			}
			if(this.getEquipment()>0){
				option++;
			}
			if(this.getCompletion()!=null&&this.getCompletion()>0){
				option++;
			}
			// 标签词
			if (this.getLabelWord() != null && this.getLabelWord().trim().length() > 0) {
				Type type = new TypeToken<String[]>() {}.getType();
				String[] labelWords = gson.fromJson(this.getLabelWord(), type);
				if(labelWords!=null){
					for(int i =0;i<labelWords.length;i++){
						if(!StringUtils.isBlank(labelWords[i])){
							option++;
							break;
						}
					}
				}
			}
		
		return option;
	}
}
