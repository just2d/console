package com.nuoshi.console.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.user.Publisher;
import com.nuoshi.console.persistence.read.admin.publish.ReleaseAccountReadMapper;
import com.nuoshi.console.persistence.write.admin.publish.ReleaseAccountWriteMapper;

@Repository
public class ReleaseAccountDao {
	protected Logger log = Logger.getLogger(this.getClass());
	@Resource
	ReleaseAccountReadMapper releaseAccountReadMapper;
	@Resource
	ReleaseAccountWriteMapper releaseAccountWriteMapper;

	public List<Publisher> query(Map<String, String> map) {
		return releaseAccountReadMapper.query(map);
	}

	public Publisher queryById(int id) {
		return releaseAccountReadMapper.queryById(id);
	}
	public List<Publisher> queryByUserId(int userId) {
		return releaseAccountReadMapper.queryByUserId(userId);
	}

	public void delete(List<Integer> id) {
		releaseAccountWriteMapper.delete(id);
	}

	public void add(Publisher user) {
		releaseAccountWriteMapper.add(user);
	}

}
