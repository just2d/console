package com.nuoshi.console.domain.resale;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuoshi.console.domain.base.House;

public class Resale extends House implements Serializable {

	private static final long serialVersionUID = 4835643545811052433L;

	public byte getPright() {
		return pright;
	}

	public void setPright(byte pright) {
		this.pright = pright;
	}

	public byte getAppointment() {
		return appointment;
	}

	public void setAppointment(byte appointment) {
		this.appointment = appointment;
	}

	public byte getRented() {
		return rented;
	}

	public void setRented(byte rented) {
		this.rented = rented;
	}

	// 产权类型
	private byte pright;

	// 是否需要预约
	private byte appointment;

	// 租约
	private byte rented;
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
			if(this.getRented()>0){
				option++;
			}
			if(this.getDecoration()!=null&&this.getDecoration()>0){
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
