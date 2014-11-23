DROP TABLE IF EXISTS admin.black_list;
CREATE TABLE  admin.black_list (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  mobile VARCHAR(15) COLLATE utf8_bin COMMENT '用户电话',
  TYPE INT(4) COMMENT '类别  0 未注册用户  1 普通用户  2 经纪人',
  reason INT(4) COMMENT '原因 0 违法信息  1 冒用电话发信息',
  COMMENT VARCHAR(50) COLLATE utf8_bin COMMENT '说明',
  madeby VARCHAR(20) COLLATE utf8_bin COMMENT '添加人',
  entry_date DATETIME COMMENT '添加时间',
  PRIMARY KEY (id),
  KEY idx_type_rsn_com (TYPE,reason,COMMENT)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS admin.sensitive_word;
CREATE TABLE  admin.sensitive_word (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键id',
  TYPE INT(11) COMMENT '所属类别    0全局关键词   1房源信息   2评论    3小区内容',
  sensitive_word_type INT(11) COMMENT '关键字类型   0敏感词     1非法词',
  illegal_type INT(11) COMMENT '非法类型    0涉政   1黄赌毒   2灌水    3其他',
  content VARCHAR(50) COLLATE utf8_bin COMMENT '内容',
  madeby VARCHAR(30) COLLATE utf8_bin COMMENT '添加人',
  entry_date DATETIME COMMENT '添加时间',
  PRIMARY KEY (id),
  KEY idx_type_sensitivetype_cnt (TYPE,sensitive_word_type,content)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;