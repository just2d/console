package com.nuoshi.console.persistence.write.adv;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.nuoshi.console.domain.adv.AdvHouse;
public interface AdvHouseWriterMapper {

	@Insert("insert into advertise_house (website,websitepos, location, f1, f2, f3, f4, f5, f6, " +
			"sdate, edate, houseType,cityid,distid,blockid)values(#{website},#{websitePos},#{location}, #{f1}, #{f2}, #{f3}, " +
			"#{f4}, #{f5}, #{f6}, #{sdate}, #{edate}, #{houseType}, #{cityId}, #{distId}, #{blockId})")
	public void addAdvHouse(AdvHouse house);

	@Update("update  advertise_house set website = #{website},websitepos = #{websitePos}," +
			"location = #{location},f1 = #{f1} , f2 = #{f2} ,f3 = #{f3} , f4 = #{f4} ," +
			" f5 = #{f5} , f6 = #{f6} , sdate = #{sdate} , edate = #{edate} , " +
			"housetype = #{houseType},cityid = #{cityId},distid = #{distId},blockid = #{blockId}  " +
			"where  id = #{id}")
	public void updateAdvHouse(AdvHouse house);


	 @Delete("DELETE FROM advertise_house WHERE id  =#{id}")
	public void  delAdvHouse(@Param("id") int  id);
}
