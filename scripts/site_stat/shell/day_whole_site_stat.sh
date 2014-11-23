#!/bin/sh
. /data/shell/setenv.sh $1

sql_file=$dir_tmp/day_whole_site_stat.sql
rs_file=$dir_tmp/day_whole_site_stat.rs

echo "
-- 设置时间变量
SET @start_date = DATE_SUB($cur_date,  INTERVAL 1 DAY);
SET @end_date = $cur_date;

-- 保存当日PV和UV数据
SELECT @pv:=pv, @uv:=uv FROM stat.day_whole_site WHERE city_id = 0 AND dist_id = 0 AND store_id = 0 AND entry_date = DATE_FORMAT(@start_date, '%Y%m%d') LIMIT 1;

-- 清除当日数据
DELETE FROM stat.day_whole_site WHERE entry_date = DATE_FORMAT(@start_date, '%Y%m%d');

-- 计算当天注册用户总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
       SELECT cityid, distid, 0, brokerid, COUNT(1), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
	     FROM broker.user
       WHERE cts < @end_date 
           AND role = 2
   GROUP BY distid,  brokerid
            ON DUPLICATE KEY UPDATE user_count = VALUES(user_count);

-- 计算当天验证用户总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
	SELECT city_id, dist_id, company_id, store_id, 0, COUNT(1), 0, 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
	      FROM broker.agent_master
        WHERE last_update_date < @end_date 
            AND status = 111
    GROUP BY dist_id,  store_id
             ON DUPLICATE KEY UPDATE verify_user_count = VALUES(verify_user_count);

-- 计算当天活跃用户总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
       SELECT city_id, dist_id, company_id, store_id, 0, 0, COUNT(1), 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
          FROM broker.user 
		          INNER JOIN broker.agent_master ON id = agent_id
        WHERE logindate >= @start_date 
	        AND logindate < @end_date 
            AND status = 111
		    AND role = 2
    GROUP BY distid,  brokerid
	         ON DUPLICATE KEY UPDATE active_user_count = VALUES(active_user_count);

-- 计算当天出租房源新上线房源总数+新添加到刷新列表的房源总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
       SELECT city_id, dist_id, company_id, store_id, 0, 0, 0, COUNT(1), 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
          FROM broker.rent_search 
                  LEFT JOIN broker.agent_master ON authorid = agent_id
        WHERE (pubdate < @end_date OR rts < @end_date) 
	        AND sourceid = 0
    GROUP BY distid,  store_id
	         ON DUPLICATE KEY UPDATE tf_rent_count = VALUES(tf_rent_count);

-- 计算当天二手房源新上线房源总数+新添加到刷新列表的房源总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
       SELECT city_id, dist_id, company_id, store_id, 0, 0, 0, 0, COUNT(1), 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
          FROM broker.resale_search 
	              LEFT JOIN broker.agent_master ON authorid = agent_id
        WHERE (pubdate < @end_date OR rts < @end_date) 
	        AND sourceid = 0
    GROUP BY distid,  store_id
	         ON DUPLICATE KEY UPDATE tf_resale_count = VALUES(tf_resale_count);
			 
-- 计算当天其他出租房源总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
       SELECT cityid, distid, 0, 0, 0, 0, 0, 0, 0, COUNT(1), 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
         FROM broker.rent_search 
       WHERE (pubdate < @end_date OR rts < @end_date) 
	       AND sourceid <> 0
   GROUP BY cityid, distid
	        ON DUPLICATE KEY UPDATE other_rent_count = VALUES(other_rent_count);
	
-- 计算当天其他二手房源总数
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
       SELECT cityid, distid, 0, 0, 0, 0, 0, 0, 0, 0, COUNT(1), 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
          FROM broker.resale_search 
        WHERE (pubdate < @end_date OR rts < @end_date) 
	        AND sourceid <> 0
    GROUP BY cityid, distid
	         ON DUPLICATE KEY UPDATE other_resale_count = VALUES(other_resale_count);
			 
-- 计算当天来电量
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
     SELECT city_id, dist_id, company_id, store_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, COUNT(1), 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, 0
        FROM broker.agent_call_detail 
				INNER JOIN broker.agent_master ON agent_call_detail.agent_id = agent_master.agent_id 
      WHERE start_date >= @start_date 
	      AND start_date < @end_date 
  GROUP BY dist_id, store_id
           ON DUPLICATE KEY UPDATE phone_count = VALUES(phone_count); 
			  
-- 计算当天淘房出租房被展现信息的用户总量
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
     SELECT b.city_id, b.dist_id, b.company_id, b.store_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'),COUNT(DISTINCT s.user_id), 0, 0, 0
        FROM stat.day_rent_header s 
				INNER JOIN broker.agent_master b ON s.user_id = b.agent_id
	  WHERE entry_datetime = DATE_FORMAT(@start_date,'%Y%m%d')
          AND s.click_freq > 0
  GROUP BY b.dist_id, b.store_id
           ON DUPLICATE KEY UPDATE tf_rent_show_count = VALUES(tf_rent_show_count); 
			  
-- 计算当天淘房二手房被展现信息的用户总量
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
     SELECT b.city_id, b.dist_id, b.company_id, b.store_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, COUNT(DISTINCT s.user_id), 0, 0
        FROM stat.day_resale_header s
				INNER JOIN broker.agent_master b ON s.user_id = b.agent_id
	  WHERE entry_datetime = DATE_FORMAT(@start_date,'%Y%m%d')
          AND s.click_freq > 0
  GROUP BY b.dist_id, b.store_id
           ON DUPLICATE KEY UPDATE tf_resale_show_count = VALUES(tf_resale_show_count); 
	
-- 计算当天其他出租房被展现信息的用户总量
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
     SELECT city_id, dist_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, COUNT(DISTINCT user_id), 0
        FROM stat.day_rent_detail
	  WHERE entry_datetime = DATE_FORMAT(@start_date,'%Y%m%d')
		  AND click_freq > 0
	      AND source_id <> 0
  GROUP BY dist_id
           ON DUPLICATE KEY UPDATE other_rent_show_count = VALUES(other_rent_show_count); 
 
 -- 计算当天其他出租房被展现信息的用户总量
INSERT INTO stat.day_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, tf_rent_count, 
            tf_resale_count, other_rent_count, other_resale_count, uv, pv, phone_count, sale_amount, entry_date, tf_rent_show_count,  
			tf_resale_show_count, other_rent_show_count, other_resale_show_count)
     SELECT city_id, dist_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DATE_FORMAT(@start_date, '%Y%m%d'), 0, 0, 0, COUNT(DISTINCT user_id)
        FROM stat.day_resale_detail
	  WHERE entry_datetime = DATE_FORMAT(@start_date,'%Y%m%d')
	      AND click_freq > 0 
		  AND source_id <> 0
  GROUP BY dist_id
           ON DUPLICATE KEY UPDATE other_resale_show_count = VALUES(other_resale_show_count); 
			  
-- 更新stat数据库day_whole_site的company_id
UPDATE stat.day_whole_site
           LEFT JOIN broker.broker ON day_whole_site.store_id = broker.id
     SET day_whole_site.company_id = broker.brandid
 WHERE day_whole_site.company_id = 0 
       OR day_whole_site.company_id IS NULL;
		   
-- 更新PV数据
UPDATE stat.day_whole_site SET pv = @pv, uv = @uv WHERE entry_date = DATE_FORMAT(@start_date, '%Y%m%d');

-- 删除当日PV和UV数据
DELETE FROM stat.day_whole_site WHERE city_id = 0 AND dist_id = 0 AND store_id = 0 AND entry_date = DATE_FORMAT(@start_date, '%Y%m%d') LIMIT 1;" > $sql_file

sql_file_exec $sql_file

echo "全站日统计数据计算完成。"