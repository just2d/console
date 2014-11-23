#!/bin/sh
. /data/shell/setenv.sh $1

sql_file=$dir_tmp/yesterday_zixun.sql
rs_file=$dir_tmp/yesterday_zixun.rs

# 避免重复运行生成重复数据
echo "UPDATE tf_zixun SET click_count_today = click_count - click_count_tmp;" > $sql_file

echo "UPDATE tf_zixun SET click_count_tmp = click_count;" >> $sql_file

echo "UPDATE tf_zixun SET click_count_month = click_count - click_count_tmp2;" >> $sql_file

echo "UPDATE tf_zixun SET click_count_tmp2 = click_count;" >> $sql_file

sql_file_exec $sql_file

echo "昨日咨询点击量生成完毕。"