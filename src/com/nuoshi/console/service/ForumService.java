package com.nuoshi.console.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.nuoshi.console.common.NetClient;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.page.impl.Pagination;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.domain.bbs.TfBbsForums;
import com.nuoshi.console.persistence.write.forum.BbsThreadWriteMapper;
/**
 * 
 * @author tangwei
 *
 */
@Service
public class ForumService extends BaseService {
	private  static Map<Integer,String>  forumMap=new TreeMap<Integer,String>();
	private static Map<Integer,TfBbsForums>  forumListMap=new TreeMap<Integer,TfBbsForums>();
	private  static Map<String,String>  forumCitysMap=new TreeMap<String,String>();
	@Resource
	private BbsThreadWriteMapper bbsThreadWriteMapper;

	public static Map<String, String> getForumCitysMap() {
		if(forumCitysMap.size()==0){
			ForumService.initForumCitys();
		}
		return forumCitysMap;
	}

	private Logger logger=Logger.getLogger(ForumService.class);
	private static String urlStr=Resources.getString("sys.url.bbs");
	static{
//		ForumService.init();
//		ForumService.initForumCitys();
	}
	
	public static  void   doMapAddForum(TfBbsForums  forum){
		//ForumService.init();
		if(forum==null){
			return;
		}
		 forumListMap.put(forum.getId(), forum);
		 forumMap.put(forum.getId(), forum.getName());
	}
	public static  void   doMapUpdateForum(TfBbsForums  forum){
		ForumService.init();
	}
	private static void initForumCitys(){
		try {
			String citys = Resources.getString("forum.citys");
			String cityids = Resources.getString("forum.cityids");
			if (citys != null && citys.length() > 0) {
				String[] citytmp = citys.split(",");
				String[] cityidtmp = cityids.split(",");
				for (int i = 0; i < citytmp.length; i++) {
					forumCitysMap.put(cityidtmp[i], citytmp[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 private static void init() {
			List<TfBbsForums> list=ForumService.list(0,-1);
			if(list!=null&&list.size()>0){
				for(TfBbsForums forum:list){
					forumMap.put(forum.getId(),forum.getName());
					forumListMap.put(forum.getId(), forum);
				}
			}
		}
	
	public static  String getForumName(Integer forumId){
		if(forumMap.size()==0){
			ForumService.init();
		}
		String tmp=forumMap.get(forumId);
		return tmp==null?"":tmp;
	}
	public static TfBbsForums  getForum(Integer id){
		if(forumListMap.size()==0){
			ForumService.init();
		}
		TfBbsForums forum=null;
		if(forumListMap!=null&&id!=null&&forumListMap.get(id)!=null){
			forum=forumListMap.get(id);
		}
		return forum;
	}
	/**
	 * 增加论坛
	 * @param tfBbsForums
	 * @return
	 */
	public int add(TfBbsForums tfBbsForums){
		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("data", gson.toJson(tfBbsForums));
		JsonResult jsonResult =null;
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbsforum/add",param);
			jsonResult = gson.fromJson(result, JsonResult.class);
			
			if(jsonResult != null && jsonResult.isResult()){
				tfBbsForums.setId(Integer.parseInt(jsonResult.getData()));
				
				ForumService.doMapAddForum(tfBbsForums);
			}
			
		} catch (Exception e) {
			logger.error("添加论坛时调用出错"+e.getMessage());
			e.printStackTrace();
		}
		
		return new Integer(jsonResult != null &&  jsonResult.getData() != null ?Integer.valueOf(jsonResult.getData()) :0);
	}
	
	/**
	 * 修改论坛
	 * @param tfBbsForums
	 * @return
	 */
	public int update(TfBbsForums tfBbsForums){
		String result="";
		HashMap<String,String> param=new HashMap<String,String>();
		param.put("data", gson.toJson(tfBbsForums));
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbsforum/update", param);
			ForumService.doMapUpdateForum(tfBbsForums);
		} catch (Exception e) {
			logger.error("更新论坛时出现错误"+e.getMessage());
			e.printStackTrace();
		}
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	/**
	 * 修改论坛的状态
	 * @param tfBbsForums
	 * @return
	 */
	public int updateoff(TfBbsForums tfBbsForums){
		String result="";
		HashMap<String,String> param=new HashMap<String,String>();
		param.put("data", gson.toJson(tfBbsForums));
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbsforum/updateoff", param);
		} catch (Exception e) {
			logger.error("更新论坛时出现错误"+e.getMessage());
			e.printStackTrace();
		}
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	/**
	 * 删除论坛
	 * @param id
	 * @return
	 */
	public int delete(int id){
		int forumId=id;
		String result =  callService(urlStr + "/bbsforum/delete", forumId);
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	/**
	 * 取得论坛的列表
	 * @param pagination
	 * @return
	 */
	public  List<TfBbsForums> list(Pagination pagination){
		String url=null;
		if(pagination!=null){
			url=urlStr + "/bbs/forumlist?dataType=json&page.pageSize="+pagination.getPageSize()+"&page.pageNo="+pagination.getPageNo();
		}else{
			url=urlStr + "/bbs/forumlist?dataType=json";
		}
		String result =  callService(url);
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		if(jsonResult!=null){
			if(pagination!=null){
				pagination.setTotalCount((int)jsonResult.getNumFound());
			}
			
			if(jsonResult.isResult()) {
				Type type = new TypeToken<List<TfBbsForums>>(){}.getType();
				return gson.fromJson(jsonResult.getData(), type);
			} 
		}
		
		
		return null;
	}
	public  List<TfBbsForums> list(Map<String,String>  map,Pagination pagination){
		String url=null;
		
		if(pagination!=null){
			url=urlStr + "/bbs/forumlist?dataType=json&page.pageSize="+pagination.getPageSize()+"&page.pageNo="+pagination.getPageNo();
		}else{
			url=urlStr + "/bbs/forumlist?dataType=json";
		}
		if(map!=null&&map.size()>0){
			for(Map.Entry<String, String> entry:map.entrySet()){   
				if(entry.getValue()!=null&&entry.getValue().length()>0){
					url+="&"+entry.getKey()+"="+entry.getValue();
				}
			}
		}
		url+="&rts="+System.currentTimeMillis();
		String result =  callService(url);
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		if(jsonResult!=null){
			if(pagination!=null){
				pagination.setTotalCount((int)jsonResult.getNumFound());
			}
			
			if(jsonResult.isResult()) {
				Type type = new TypeToken<List<TfBbsForums>>(){}.getType();
				return gson.fromJson(jsonResult.getData(), type);
			} 
		}
		
		
		return null;
	}
	public static List<TfBbsForums> list(Integer cityId,int visibleRole){
		String url=null;
			url=urlStr + "/bbs/forumlist?dataType=json";
			if(cityId!=null&&cityId.intValue()>0){
				url=url + "&cityId="+cityId;
			}
			if(visibleRole>0){
				url=url + "&visibleRole="+visibleRole;
			}
			
		String result =  callService(url);
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		if(jsonResult!=null && jsonResult.isResult()){
				Type type = new TypeToken<List<TfBbsForums>>(){}.getType();
				return gson.fromJson(jsonResult.getData(), type);
		}
		return null;
	}
	
	
	
	
	/**
	 * 根据id取得单个论坛
	 * @param id
	 * @return
	 */
	public TfBbsForums selectByforumId(int id){
		String result =  callService(urlStr + "/bbs/forumlist?dataType=json&id="+id);
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		
		if(jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsForums>>(){}.getType();
			List<TfBbsForums> list= gson.fromJson(jsonResult.getData(), type);
			if(list.size()>0){
			
				TfBbsForums tfBbsForums=list.get(0);
				return tfBbsForums;
			}
		} 
		return null;
	}
	
	public TfBbsForums selectByforumName(String cityId,String name){
		String result =  callService(urlStr + "/bbs/forumlist?dataType=json&name="+name+"&cityId="+cityId);
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		
		if(jsonResult!=null&&jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsForums>>(){}.getType();
			System.out.println(jsonResult.getData());
			List<TfBbsForums> list= gson.fromJson(jsonResult.getData(), type);
			if(list!=null&&list.size()>0){
				TfBbsForums tfBbsForums=list.get(0);
				return tfBbsForums;
			}
		} 
		return null;
	}
	/**
	 * 下拉框的选择
	 * @return
	 */
	public List<TfBbsForums> chooseForum(int parentId){
		String result =  callService(urlStr + "/bbs/forumlist?dataType=json&parentId="+parentId);
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		
		if(jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsForums>>(){}.getType();
			
			return gson.fromJson(jsonResult.getData(), type);
		} 
		return null;
	}
	
	/**
	 * 通过用户更新帖子状态
	 * @param authorId 用户id
	 * @param toStatus 更新状态
	 * @param origStatus 原始状态
	 * @author wangjh
	 * Dec 12, 2012 2:38:14 PM
	 */
	public void updateStatusByAuthorId(Integer authorId, Integer toStatus, Integer origStatus) {
		if(authorId==null||toStatus==null||origStatus==null){
			return ;
		}
		bbsThreadWriteMapper.updateThreadsStatusByAuthorId(authorId, toStatus, origStatus);
		bbsThreadWriteMapper.updatePostsStatusByAuthorId(authorId, toStatus, origStatus);
	}
	
}
