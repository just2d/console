package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.ReleaseAccountDao;
import com.nuoshi.console.domain.user.Publisher;
import com.nuoshi.console.domain.user.TFUser;

/**
 * 发布账户管理
 * 用于问答和论坛的随机选择发布人员
 * 是t_user的子集，不影响t_user
 * @author wangjh
 *
 */
@Service
public class ReleaseAccountService {
	protected static Logger log = Logger.getLogger(ReleaseAccountService.class);
	@Resource
	TaofangUserService taofangUserService;
	@Resource
	ReleaseAccountDao releaseAccountDao;
	/**
	 * 增:通过用户id 添加发帖的人员，存在则直接添加，不存在则报错
	 * @param userId
	 * @throws Exception
	 * @author wangjh
	 * May 29, 2013 10:59:39 AM
	 */
	public void add(Integer userId)throws Exception{
		TFUser user =null;
		if(userId!=null&&userId>0){
			user = taofangUserService.getUserByUserId(userId);
		}
		if(user==null){
			throw new Exception("用户不存在！");
		}
		//列表中是否存在
		List<Publisher> publisherDB=releaseAccountDao.queryByUserId(userId);
		if(CollectionUtils.isNotEmpty(publisherDB)){
			throw new Exception("用户已经被添加过了！");
		}
		
 		Publisher publisher =this.getPublisher(user);
		releaseAccountDao.add(publisher);
	}
	
	private Publisher getPublisher(TFUser user){
		if(user==null){
			return null;
		}
		Publisher publisher =new Publisher();
		publisher.setCityId(user.getCityId());
		publisher.setUserId(user.getId());
		publisher.setLoginName(user.getUserName());
		publisher.setRole(user.getRole());
		publisher.setStatus(0);
		publisher.setType(0);
		String userName=user.getRole()==TFUser.ROLE_AGENT?user.getName():user.getUserName();
		publisher.setUserName(userName);
		return publisher;
	}
	/**
	 * 删：通过列表中的id删除用户
	 * @param id
	 * @author wangjh
	 * May 29, 2013 10:59:46 AM
	 */
	public void delete(List<Integer> ids)throws Exception{
		if(CollectionUtils.isEmpty(ids)){
			return;
		}
		releaseAccountDao.delete(ids);
	}
	/**
	 * 查：1、通过角色查2、通过id或用户名查 3、查询全部
	 * role:1 or 2
	 * userId:
	 * userName:like'%%'
	 * @param map
	 * @author wangjh
	 * May 29, 2013 11:09:45 AM
	 */
	public List<Publisher> query(Map<String,String> map){
		List<Publisher> user=releaseAccountDao.query(map);
		return user;
	}
	
	public List<Publisher> queryByRole(int role,int cityId){
		Map<String,String> map=new HashMap<String, String>();
		map.put("role", ""+role);
		if(cityId!=0){
			map.put("cityId", ""+cityId);
		}
		List<Publisher> list =releaseAccountDao.query(map);
		return list;
	}

}
