package com.nuoshi.console.dao;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.persistence.write.history.rent58.HistoryRent58WriteMapper;



/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class HistoryRent58Dao {
	@Resource
	private HistoryRent58WriteMapper historyRent58WriteMapper;
	
	public int intoHistory(HashMap<String, Object> rent){
		return historyRent58WriteMapper.intoHistory(rent);
	}
	
}
