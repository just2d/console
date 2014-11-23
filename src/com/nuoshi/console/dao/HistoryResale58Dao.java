package com.nuoshi.console.dao;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.persistence.write.history.resale58.HistoryResale58WriteMapper;


/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class HistoryResale58Dao {
	
	@Resource
	private HistoryResale58WriteMapper historyResale58WriteMapper;
	
	public int intoHistory(HashMap<String, Object> resale){
		return historyResale58WriteMapper.intoHistory(resale);
	}
}
