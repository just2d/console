package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.sql.Timestamp;

public class Brand  implements Serializable{

	private static final long serialVersionUID = 5521883670094643644L;
	private Integer id;
    private String name;
    private String logo;
    private String brokercnt;
    private Timestamp cts;
    /**
     * 公司类型：1.大客户，0.普通'
     */
    private String type;
    /**
     * 城市id
     */
	private Integer city;
	/**
	 * crm后台 标志
	 */
	private String flag;    

	

    public Integer getCity() {
		return city;
	}


	public void setCity(Integer city) {
		this.city = city;
	}




	public String getType() {
		return type;
	}

	

	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Integer getId() {
        return id;
    }

   
    public void setId(Integer id) {
        this.id = id;
    }

 
    public String getName() {
        return name;
    }

   
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

   
    public String getLogo() {
        return logo;
    }

  
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

   
    public String getBrokercnt() {
        return brokercnt;
    }

   
    public void setBrokercnt(String brokercnt) {
        this.brokercnt = brokercnt == null ? null : brokercnt.trim();
    }


	public Timestamp getCts() {
		return cts;
	}


	public void setCts(Timestamp cts) {
		this.cts = cts;
	}

   
}
