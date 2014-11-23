package com.nuoshi.console.persistence.read.stat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.stat.AuditSearch;
import com.nuoshi.console.domain.stat.ConvertRate;
import com.nuoshi.console.domain.stat.PhotoAuditStatis;
import com.nuoshi.console.domain.stat.RateSearch;

public interface ConvertRateReadMapper {
	public List<ConvertRate> queryRateByPage(RateSearch rateSearch);
	public List<AuditSearch> listAuditRate(AuditSearch as);
	public List<PhotoAuditStatis> getPhotoAuditStatisListByPage(@Param("as")AuditSearch as,@Param("cons")String conditions);
	
	public List<PhotoAuditStatis> getPhotoAuditStatisListToExcel(@Param("as")AuditSearch as,@Param("cons")String conditions);
}
