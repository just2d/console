package com.nuoshi.console.domain.adv;

import java.util.Date;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.nuoshi.console.service.LocaleService;

public class AdvHouse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8580428983217193209L;
	private int id;
	   public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int cityId;
	   private int distId;
	   public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getDistId() {
		return distId;
	}
	public void setDistId(int distId) {
		this.distId = distId;
	}
	public int getBlockId() {
		return blockId;
	}
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	private int blockId;
	   private String f1;
	   private String f2;
	   private String f3;
	   private String f4;
	   private String f5;
	   private String f6;
	   private int location;
	   public int getLocation() {
		return location;
	   }
	   public String getCityCode(){
		  return  LocaleService.getCode(this.getCityId());
	   }
	   public String getLocationName() {
		    String str="";
		    if(this.getCityId()>0){
		    	 str+=LocaleService.getName(this.getCityId());
		    }
		    if(this.getDistId()>0){
		    	 str+="/"+LocaleService.getName(this.getDistId());
		    }
		    if(this.getBlockId()>0){
		    	 str+="/"+LocaleService.getName(this.getBlockId());
		    }
			return str;
		   }
	public void setLocation(int location) {
		this.location = location;
	}
	private int houseType;
	   private Date  sdate;
	   private Date  edate;
	   private String sdateh;
	   public String getSdateh() {
		return sdateh;
	}
	public void setSdateh(String sdateh) {
		this.sdateh = sdateh;
	}
	public String getEdateh() {
		return edateh;
	}
	public void setEdateh(String edateh) {
		this.edateh = edateh;
	}
	public String getSdated() {
		return sdated;
	}
	public void setSdated(String sdated) {
		this.sdated = sdated;
	}
	public String getEdated() {
		return edated;
	}
	public void setEdated(String edated) {
		this.edated = edated;
	}
	private String edateh;
	   private String sdated;
	   private String edated;
	   private int  websitePos;
	   public int getWebsitePos() {
		return websitePos;
	}
	public void setWebsitePos(int websitePos) {
		this.websitePos = websitePos;
	}
	public Date getSdate() {
		     if(this.sdate!=null){
		    	 return this.sdate;
		     }
		     return getStringToDate(this.getSdated(),this.getSdateh());
	}
	public String  getShowDate(){
		 StringBuffer str=new StringBuffer();
	     if(this.sdate!=null){
	    	 str.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.sdate)+"至");
	    	 if(this.edate!=null){
	    		  str.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.edate));
	    	 }
	     }
	     return str.toString();
     }
	public String  getShowSDate(){
		 if(this.sdate!=null){
	    	 return  new SimpleDateFormat("yyyy-MM-dd").format(this.sdate);
		 }
		 return "";
    }
	public String  getShowSDateH(){
		 if(this.sdate!=null){
	    	 return  new SimpleDateFormat("HH").format(this.sdate);
		 }
		 return "";
   }
	public String  getShowEDateH(){
		 if(this.sdate!=null){
	    	 return  new SimpleDateFormat("HH").format(this.edate);
		 }
		 return "";
  }
	public String  getShowEDate(){
		 if(this.sdate!=null){
	    	 return  new SimpleDateFormat("yyyy-MM-dd").format(this.edate);
		 }
		 return "";
    }
		public void setSdate(Date sdate) {
			this.sdate = sdate;
		}
		public Date getEdate() {
			if(this.edate!=null){
				try {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(edate.toString());
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
		     }
			 return getStringToDate(this.getEdated(),this.getEdateh());
		}
		public void setEdate(Date edate) {
			this.edate = edate;
		}
		
		public String getF1() {
			return f1;
		}
		public void setF1(String f1) {
			this.f1 = f1;
		}
		public String getF2() {
			return f2;
		}
		public void setF2(String f2) {
			this.f2 = f2;
		}
		public String getF3() {
			return f3;
		}
		public void setF3(String f3) {
			this.f3 = f3;
		}
		public String getF4() {
			return f4;
		}
		public void setF4(String f4) {
			this.f4 = f4;
		}
		public String getF5() {
			return f5;
		}
		public void setF5(String f5) {
			this.f5 = f5;
		}
		public String getF6() {
			return f6;
		}
		public void setF6(String f6) {
			this.f6 = f6;
		}
		public int getHouseType() {
			return houseType;
		}
		public void setHouseType(int houseType) {
			this.houseType = houseType;
		}
		public int getWebsite() {
			return website;
		}
		public void setWebsite(int website) {
			this.website = website;
		}
		private int website;
		public Date  getStringToDate(String dated,String dateh){
			if(dated==null||dated.length()==0){
				 return null;
			}
       	    DateFormat  df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date d=null;
		    try {
				d=df.parse(dated+" "+dateh+":00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return  d;
       }
       }
        
