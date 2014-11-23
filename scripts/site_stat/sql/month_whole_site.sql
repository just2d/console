-- 设置时间变量
SET @start_date = DATE_FORMAT(DATE_SUB(CURDATE(),  INTERVAL 1 DAY), '%Y%m01');
SET @end_date = DATE_FORMAT(CURDATE(), '%Y%m%d');

-- 设置日表最大日期
SELECT @max_date:= entry_date FROM stat.day_whole_site WHERE entry_date = (SELECT MAX(entry_date) FROM stat.day_whole_site) LIMIT 1;

-- 清除当月所有数据
DELETE FROM stat.month_whole_site WHERE month_no = DATE_FORMAT(@start_date,  '%Y%m');

-- 将day_whole_site表(活跃用户数、淘房用户数、其他房源数、淘房展示信息用户量、其他来源展示信息用户量、uv、pv、来电量、销售额)
-- 数据导入到month_whole_site表中，按照月份分组
INSERT INTO stat.month_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, active_user_count, 
            tf_rent_count, tf_resale_count, other_rent_count, other_resale_count, phone_count, sale_amount, month_no,
			tf_rent_show_count, tf_resale_show_count,other_rent_show_count,other_resale_show_count)
    SELECT city_id, dist_id, company_id, store_id, 0, 0, SUM(active_user_count), SUM(tf_rent_count), SUM(tf_resale_count), 
    	   SUM(other_rent_count), SUM(other_resale_count),SUM(phone_count), SUM(sale_amount), DATE_FORMAT(entry_date, '%Y%m'), 
    	   SUM(tf_rent_show_count), SUM(tf_resale_show_count), SUM(other_rent_show_count), SUM(other_resale_show_count)
       FROM stat.day_whole_site 
     WHERE entry_date >= @start_date 
         AND entry_date < @end_date
     GROUP BY dist_id, store_id, DATE_FORMAT(entry_date, '%Y%m') 
ON DUPLICATE KEY UPDATE  
	 user_count = VALUES(user_count),
	 verify_user_count = VALUES(verify_user_count),
     active_user_count = VALUES(active_user_count), 
     tf_rent_count = VALUES(tf_rent_count), 
     tf_resale_count = VALUES(tf_resale_count), 
     other_rent_count = VALUES(other_rent_count), 
     other_resale_count = VALUES(other_resale_count), 
     phone_count = VALUES(phone_count), 
     sale_amount = VALUES(sale_amount),
	 tf_rent_show_count = VALUES(tf_rent_show_count),
	 tf_resale_show_count = VALUES(tf_resale_show_count),
	 other_rent_show_count = VALUES(other_rent_show_count),
	 other_resale_show_count = VALUES(other_resale_show_count);

-- 将day_whole_site表(用户数、认证用户数)数据导入到month_whole_site表中，按照月份分组
INSERT INTO stat.month_whole_site(city_id, dist_id, company_id, store_id, user_count, verify_user_count, month_no)
    SELECT city_id, dist_id, company_id, store_id, SUM(user_count), SUM(verify_user_count), DATE_FORMAT(entry_date, '%Y%m')
       FROM stat.day_whole_site 
     WHERE entry_date = @max_date
     GROUP BY dist_id, store_id, DATE_FORMAT(entry_date, '%Y%m') 
ON DUPLICATE KEY UPDATE  
     user_count = VALUES(user_count), 
     verify_user_count = VALUES(verify_user_count);

-- 创建临时表存储pv、uv数据
CREATE TEMPORARY TABLE stat.temp(pv INT(11),uv INT(11),month_no VARCHAR(10), KEY idx_month_no (month_no) USING BTREE);

-- 将日表pv、uv数据插入到临时表temp中
INSERT INTO stat.temp
	SELECT SUM(DISTINCT pv) AS pv,SUM(DISTINCT uv) AS uv,DATE_FORMAT(entry_date, '%Y%m') AS month_no
		FROM stat.day_whole_site
	WHERE entry_date >= @start_date 
		AND entry_date < @end_date;

-- 将临时表数据更新到month_whole_site表中
UPDATE stat.month_whole_site s,stat.temp t
	SET s.pv = t.pv ,s.uv = t.uv
WHERE s.month_no = t.month_no;