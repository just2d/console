package com.nuoshi.console.domain.estate;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.nuoshi.console.domain.topic.Estate;

public class EstateInfo extends Estate implements Serializable {
	private static final long serialVersionUID = -2156736636780701512L;
	/**
	 * 将basicInfo属性拆分
	 * 
	 * @param estateInfo
	 * @return
	 */
	public  EstateInfo setExtInfo(EstateInfo estateInfo) {
		String basicInfo = estateInfo.getBasicInfo();
		if (StringUtils.isNotBlank(basicInfo)) {
			String tmpInfo[] = basicInfo.split(";");
			for (int i = 0; i < tmpInfo.length; i++) {
				String info[] = { "", "" };
				if (StringUtils.isNotBlank(tmpInfo[i])) {
					if (tmpInfo[i].contains("：")) {
						if (tmpInfo[i].startsWith("教育配套") || tmpInfo[i].startsWith("商业配套") || tmpInfo[i].startsWith("交通配套")) {
							// 嗨。。冒号用的是全角字符。
							info = tmpInfo[i].split("：");
						}
					} else {
						info = tmpInfo[i].split(":");
					}
				}
				// if (tmpInfo[i].startsWith("物业类型")) {
				// estateInfo.setWyType(info[1]);
				// continue;
				// }// else if(tmpInfo[i].startsWith("楼盘地址")){
				// estateInfo.setAddress(info[1]);
				// continue;
				// }
				if (tmpInfo[i].startsWith("容积率")) {
					estateInfo.setAreaRate(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("绿化率")) {
					estateInfo.setGreenRate(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("建筑年代")) {
					estateInfo.setBuildYear(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("物业费")) {
					estateInfo.setWyFee(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("车位信息")) {
					estateInfo.setCarInfo(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("物业公司")) {
					estateInfo.setWyCompany(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("开发商")) {
					estateInfo.setDevCompany(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("占地面积")) {
					estateInfo.setArea(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("建筑面积")) {
					estateInfo.setBuildArea(info.length >= 2 ? info[1] : "");
					continue;
				} else if (tmpInfo[i].startsWith("教育配套")) {
					// 针对"："(全角冒号的拆分)
					for (int j = 0; j < info.length; j++) {
						// 幼儿园
						if (info[j].contains("幼儿园") && !info[j].contains("教育配套")) {
							String tmpNursery = info[j].replaceAll("中小学", "");
							if (StringUtils.isNotBlank(tmpNursery)) {
								estateInfo.setNursery(tmpNursery.trim());
							}
						} else if (info[j].contains("大学") && (info[j].contains("小学") || info[j].contains("中学"))) {
							// 中小学
							String tmpSchool = info[j].replaceAll("大学", "");
							if (StringUtils.isNotBlank(tmpSchool)) {
								estateInfo.setSchool(tmpSchool.trim());
							}
						} else if (!info[j].contains("教育配套")) {
							estateInfo.setUniversity(info[j]);
						}
					}
					continue;
				} else if (tmpInfo[i].startsWith("商业配套")) {
					for (int j = 0; j < info.length; j++) {
						if (StringUtils.isNotBlank(info[j])) {
							if (info[j].endsWith("医院")) {
								estateInfo.setMarket(info[j].replaceAll("医院", ""));
							} else if (info[j].endsWith("邮局")) {
								estateInfo.setHospital(info[j].replaceAll("邮局", ""));
							} else if (info[j].endsWith("银行")) {
								estateInfo.setPostOffice(info[j].replaceAll("银行", ""));
							} else if (info[j].endsWith("其他")) {
								estateInfo.setBank(info[j].replaceAll("其他", ""));
							} else {
								estateInfo.setOtherInfo(info[j].trim());
							}
						}
					}

				} else if (tmpInfo[i].startsWith("交通配套:公交") && info.length == 3) {
						estateInfo.setSubWay(info[2] != null ? info[2] : null);
						estateInfo.setBus(info[1] != null ? info[1].replaceAll("地铁", "") : null);
				} else if (tmpInfo[i].startsWith("交通配套:地铁") && info.length == 3) {
					estateInfo.setBus(info[2] != null ? info[2] : null);
					estateInfo.setSubWay(info[1] != null ? info[1].replaceAll("公交", "") : null);
			}
			}

		}
		return estateInfo;
	}

	

}
