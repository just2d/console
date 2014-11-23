package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.FeedBackDao;
import com.nuoshi.console.domain.feedback.FeedBack;
import com.nuoshi.console.web.session.SessionUser;

@Service
public class FeedBackService {
	protected static Logger LOG = Logger.getLogger(FeedBackService.class);
	@Resource
	private FeedBackDao feedBackDao;


	/**
	 * 组成查询
	 * @param mobile
	 * @return
	 * @date 2012-1-31下午2:12:30
	 * @author dongyj
	 * @param flags 
	 */
	public List<FeedBack> getList(String mobile, String flags) {
		List<FeedBack> feedBacks = new ArrayList<FeedBack>();
		HashMap<String, StringBuffer> params = new HashMap<String, StringBuffer>();
		StringBuffer condition = new StringBuffer();
		consistQuery(mobile,flags,condition,params);
		feedBacks = getListByPage(params);
		return feedBacks;
	}
	
	public List<FeedBack> getListByPage(HashMap<String, StringBuffer> params) {
		return feedBackDao.getListByPage(params);
	}
	
	/**
	 * 组成查询
	 * @param mobile
	 * @param condition
	 * @param params
	 * @date 2012-1-31下午2:09:05
	 * @author dongyj
	 * @param flags 
	 */
	public void consistQuery(String mobile, String flags, StringBuffer condition, HashMap<String, StringBuffer> params) {
		if (StringUtils.isNotBlank(mobile)) {
			condition.append(" AND mobile ='" + mobile + "'");
		}
		if (StringUtils.isNotBlank(flags)) {
			condition.append(" AND flags =" + flags);
		}
		if (StringUtils.isNotBlank(condition.toString())) {
			params.put("condition", condition);
		}
	}
	
	public void delOne(int id) {
		feedBackDao.delOne(id);
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
	 * 取得一个
	 * @param id
	 * @return
	 * @throws Exception
	 * @date 2012-1-31下午3:21:31
	 * @author dongyj
	 */
	public FeedBack getOne(String id) throws Exception {
		FeedBack feedBack = new FeedBack();
		feedBack = feedBackDao.getOne(id);
		return feedBack;
	}

	/**
	 * 更新
	 * @param id
	 * @param value
	 * @date 2012-1-31下午4:17:50
	 * @author dongyj
	 * @param sessionUser 
	 * @throws Exception 
	 */
	public void updateOne(String id, String value, SessionUser sessionUser) throws Exception {
		try {
			FeedBack feedBack = new FeedBack();
			Integer iid = 0;
			iid = Integer.parseInt(id);
			feedBack.setId(iid);
			feedBack.setReply(value);
			feedBack.setFlags(1);
			feedBack.setAdminid(sessionUser.getUser().getId());
			feedBack.setUts(new Date());
			feedBackDao.updateOne(feedBack);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
