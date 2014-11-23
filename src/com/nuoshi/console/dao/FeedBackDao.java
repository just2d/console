package com.nuoshi.console.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.feedback.FeedBack;
import com.nuoshi.console.persistence.read.taofang.feedback.FeedBackReadMapper;
import com.nuoshi.console.persistence.write.taofang.feedback.FeedBackWriteMapper;

@Repository
public class FeedBackDao {
	@Resource
	private FeedBackReadMapper feedBackReadMapper;
	@Resource
	private FeedBackWriteMapper feedBackWriteMapper;

	public List<FeedBack> getListByPage(HashMap<String, StringBuffer> params) {
		return feedBackReadMapper.getListByPage(params);
	}

	public void delOne(int id) {
		feedBackWriteMapper.delOne(id);
	}

	/**
	 * 判断并调用删除逻辑
	 * @param id
	 * @throws Exception
	 * @date 2012-1-31下午2:09:12
	 * @author dongyj
	 */
	public void delOne(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			try {
				delOne(Integer.parseInt(id));
			} catch (Exception e) {
				throw new Exception("未能删除id为." + id + "的记录.");
			}
		}
	}

	/**
	 * 组成查询
	 * @param mobile
	 * @param condition
	 * @param params
	 * @date 2012-1-31下午2:09:05
	 * @author dongyj
	 */
	public void consistQuery(String mobile, StringBuffer condition, HashMap<String, StringBuffer> params) {
		if (StringUtils.isNotBlank(mobile)) {
			condition.append(" AND mobile ='" + mobile + "'");
		}
		if (StringUtils.isNotBlank(condition.toString())) {
			params.put("condition", condition);
		}
	}

	/**
	 * 组成查询
	 * @param mobile
	 * @return
	 * @date 2012-1-31下午2:12:30
	 * @author dongyj
	 */
	public List<FeedBack> getList(String mobile) {
		List<FeedBack> feedBacks = new ArrayList<FeedBack>();
		HashMap<String, StringBuffer> params = new HashMap<String, StringBuffer>();
		StringBuffer condition = new StringBuffer();
		this.consistQuery(mobile,condition,params);
		feedBacks = this.getListByPage(params);
		return feedBacks;
	}

	/**
	 * 取得一个
	 * @param id
	 * @return
	 * @throws Exception
	 * @date 2012-1-31下午3:21:31
	 * @author dongyj
	 */
	public FeedBack getOne(String id) throws Exception {
		FeedBack feedBack = new FeedBack();
		try {
			feedBack = feedBackReadMapper.getOne(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return feedBack;
	}

	public void updateOne(FeedBack feedBack) {
		feedBackWriteMapper.updateOne(feedBack);
	}

}
