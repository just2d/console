package com.nuoshi.console.persistence.read.stat;

import java.util.HashMap;
import java.util.List;

import com.nuoshi.console.domain.stat.HouseLabelInfo;

public interface HouseLabelReadMapper {

	List<HouseLabelInfo> getHouseLabelCount(HashMap<String, String> params);

	List<HouseLabelInfo> getHouseLabelCountCountry(	HashMap<String, String> params);

}
