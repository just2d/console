package com.nuoshi.console.domain.adv;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdvHouseSearch {
	   private int id;
	   private int  isChecked=0;
	public int getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int searchCity=-2;
	   private int searchDist=-2;
	   public int getSearchCity() {
		return searchCity;
	}
	public void setSearchCity(int searchCity) {
		this.searchCity = searchCity;
	}
	public int getSearchDist() {
		return searchDist;
	}
	public void setSearchDist(int searchDist) {
		this.searchDist = searchDist;
	}
	public int getSearchBlock() {
		return searchBlock;
	}
	public void setSearchBlock(int searchBlock) {
		this.searchBlock = searchBlock;
	}
	public String getSearchHouseId() {
		return searchHouseId;
	}
	public void setSearchHouseId(String searchHouseId) {
		this.searchHouseId = searchHouseId;
	}
	public int getSearchHouseType() {
		return searchHouseType;
	}
	public void setSearchHouseType(int searchHouseType) {
		this.searchHouseType = searchHouseType;
	}

	public String getSearchSdateh() {
		return searchSdateh;
	}
	public void setSearchSdateh(String searchSdateh) {
		this.searchSdateh = searchSdateh;
	}
	public String getSearchEdateh() {
		return searchEdateh;
	}
	public void setSearchEdateh(String searchEdateh) {
		this.searchEdateh = searchEdateh;
	}
	public String getSearchSdated() {
		return searchSdated;
	}
	public void setSearchSdated(String searchSdated) {
		this.searchSdated = searchSdated;
	}
	public String getSearchEdated() {
		return searchEdated;
	}
	public void setSearchEdated(String searchEdated) {
		this.searchEdated = searchEdated;
	}
	private int searchBlock;
	   private String searchHouseId;
	   public int getLocation() {
		   if(this.getSearchBlock()>0){
				  return this.getSearchBlock();
			}
			if(this.getSearchDist()>0){
				 return this.getSearchDist();
			}
			if(this.getSearchCity()>0){
				 return this.getSearchCity();
			}
			return  0;
	}

	   private int  searchHouseType;
	   private String searchSdateh;
	   private String searchEdateh;
	   private String searchSdated;
	   private String searchEdated;
	   private int  searchWebsitePos;
	
	public Date getSearchSdate() {
		     if(this.searchSdated==null||this.searchSdated.trim().length()==0){
		    	  return null;
		     }
		     return getStringToDate(this.searchSdated,this.searchSdateh);
		}
		public Date getSearchEdate() {
			 if(this.searchEdated==null||this.searchEdated.trim().length()==0){
		    	  return null;
		     }
			 return getStringToDate(this.searchEdated,this.searchEdateh);
		}

		private int searchWebsite;
		public int getSearchWebsitePos() {
			return searchWebsitePos;
		}
		public void setSearchWebsitePos(int searchWebsitePos) {
			this.searchWebsitePos = searchWebsitePos;
		}
		public int getSearchWebsite() {
			return searchWebsite;
		}
		public void setSearchWebsite(int searchWebsite) {
			this.searchWebsite = searchWebsite;
		}
		public Date  getStringToDate(String dated,String dateh){
       	    DateFormat  df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       	    if(dated==null||dated.length()==0){
       	    	 return  null;
       	    }
       	   if(dateh==null||dateh.length()==0){
       		  dateh = "00";
   	        }
		    Date d=null;
		    try {
				d=df.parse(dated+" "+dateh+":00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return  d;
       }
       }
        
