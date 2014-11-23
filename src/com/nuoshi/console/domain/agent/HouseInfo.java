package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.nuoshi.console.common.Resources;

/**
 * @author Administrator
 * 
 */
public class HouseInfo implements Serializable {
	private static final long serialVersionUID = 1946520431697777458L;
	private int id;
	private String name;
	private String title;
	private String flattype;
	private String price;
	private Date enterdate;
	private String houseStatus;
	private String houseType;
	private int status;
	public static String HOUSE_LABEL_VCR ="vcr";
	public static String HOUSE_LABEL_XT ="xt";
	public static String HOUSE_LABEL_SSKF ="sskf";
	
	// 标签
	private Set<String> labels = new TreeSet<String>();
	
	
	// 标签
	private String houseLabel;

	private String houseLabelName;
	
	
	// 基本分数
	private Double baseScore;
	/**
	 * @return 房源id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            房源id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 房源所在小区名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            房源所在小区名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 房源标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            房源标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 房源户型
	 */
	public String getFlattype() {
		return flattype;
	}

	/**
	 * @param flattype
	 *            房源户型
	 */
	public void setFlattype(String flattype) {
		this.flattype = flattype;
	}

	/**
	 * @return 房源价格
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            房源价格
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return 按照yyyy-mm-dd的形式返回房源录入日期
	 */
	public String getEnterdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(enterdate);
	}

	/**
	 * @param enterdate
	 *            房源录入日期
	 */
	public void setEnterdate(Date enterdate) {
		this.enterdate = enterdate;
	}

	/**
	 * @return 房源状态(在线房源、待发布房源、过期房源、违规房源)
	 */
	public String getHouseStatus() {
		return houseStatus;
	}

	/**
	 * @param houseStatus
	 *            房源状态(在线房源、待发布房源、过期房源、违规房源)
	 */
	public void setHouseStatus(String houseStatus) {
		this.houseStatus = houseStatus;
	}

	/**
	 * @return 房源类型 rent 出租房 resale 二手房
	 */
	public String getHouseType() {
		return houseType;
	}

	/**
	 * @param houseType
	 *            房源类型 rent 出租房 resale 二手房
	 */
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getHouseStatusLabel(){
		switch(getStatus()) {
		case 0:
			return "待发布房源";
		case 1:
			return "在线房源";
		case 2:
			return "违规房源";
		case 3:
			return "过期房源";
		case 4:
			return "删除房源";
		case 5:
			return "草稿箱";
		case 6:
			return "未审核经纪人房源";
			default:
				return "";
		}
	}

	public Double getBaseScore() {
		return baseScore;
	}
	public int getScore() {
		if(baseScore==null){
			return 0;
		}else{
			 return baseScore.intValue();
		}
	}

	public void setBaseScore(Double baseScore) {
		this.baseScore = baseScore;
	}
	
	public Set<String> getLabels() {
		return labels;
	}

	public void setLabels(Set<String> labels) {
		this.labels = labels;
	}

	public void setHouseLabel(String houseLabel) {
		if (houseLabel != null) {
			String[] labelList = houseLabel.split(",");
			labels = new TreeSet<String>();
			for (String label : labelList) {
				if (StringUtils.isNotBlank(label)) {
					labels.add(label);
				}
			}
		}

		this.houseLabel = houseLabel;
	}
	
	/**
	 * 获取标签，只是数据库插入时使用，
	 * 对象操作请操作labels
	 * @return
	 */
	public String getHouseLabel() {
		if (!CollectionUtils.isEmpty(labels)) {
			Iterator<String> i = labels.iterator();
			if (!i.hasNext()){
				return null;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(',');
			for (;;) {
				String label = i.next();
				sb.append(label);
				if (!i.hasNext()) {
					sb.append(',');
					break;
				}
				sb.append(",");
			}
			this.houseLabel = sb.toString();
			return this.houseLabel;
		} else {
			return null;
		}
	}
	
	public String getHouseLabelName(){
		String name = "";
		if (!CollectionUtils.isEmpty(labels)) {
		Iterator<String> i = labels.iterator();
		
		while(i.hasNext()){
			name =name+","+Resources.getString("house.label."+i.next()) ;
		}
		}
		return name;
	}
	public void setHouseLabelName(String houseLabelName){
		this.houseLabelName = houseLabelName;
	}
}
