<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="width: 100%;text-align: left;">当前位置：数据管理>><b>网站数据统计</b></div> 
<div class="mag">
	<select id="city" class="stat_one"></select> 
	<select id="dist" class="stat_one"><option value="-2">请选择区域</option></select>
</div>
<div id="divspan" class="mag">
	<span id="sp1" class="stat_two" data="12" type="m">近12月数据</span> 
	<span id="sp2" class="stat_two" data="6" type="m">近半年数据</span> 
	<span id="sp3" class="stat_two stat_high" data="30" type="d">近30日数据</span> 
</div>
<div id="statDiv"></div>
<div class="mag">
	<div class="todo">
		<table id="statTable">
			<thead>
				<tr>
					<th>日期\统计</th>
					<th>用户数</th>
					<th>认证用户数</th>
					<th>活跃用户数</th>
					<th>淘房房源数</th>
					<th>其他房源数</th>
					<th>淘房展示信息用户量</th>
					<th>其他来源展示信息用户量</th>
					<th>UV</th>
					<th>PV</th>
					<th>来电量</th>
					<th>销售额</th>
				</tr>
			</thead>
			<tbody>
			</tbody> 
		</table>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		//初始化地标
		getDist("city",0);
		getStatis("d", -2, -2, 30);
		//为控件绑定事件
		$("#dist").bind("change",function(){getChangeStatis();});
		$("#city").bind("change",function(){getDist("dist",$(this).val());getChangeStatis();});
		$("#divspan").find(".stat_two").bind("click",function(){$(this).addClass("stat_high").siblings().removeClass("stat_high");getChangeStatis();});
	});
	
	//根据地标、日期ajax获取统计数据
	function getChangeStatis(){
		var cityid = $("#city").val();
		var distid = $("#dist").val();
		var type = $("#divspan").find(".stat_high").attr("type");
		var date = $("#divspan").find(".stat_high").attr("data");
		getStatis(type, cityid, distid, date);
	}
	
	// ajax获取城市、区域 
	function getDist(selectId,pid) {
		var selectElem = "#" + selectId;
		var def;
		if (selectId == "city") {
			def = "全国城市";
		} else {
			def = "请选择区域";
		}
		$.ajax({
			type : "GET",
			async : false,
			url : "/agentManage/ajax/zone/"+pid,
			dataType : "json",
			success : function(data) {
				$(selectElem).empty();
				var option = "<option value='-2'>" + def + "</option>";
				$(option).appendTo(selectElem);
				var list = data.distList;
				if (list != null && list.length > 0) {
					for (i in list) {
						var local = list[i];
						var option = "<option value='" + local.localid + "'>"
									+ local.localname + "</option>";
						$(option).appendTo(selectElem);
					}
				}
			}
		});
	}
	
	// ajax获取统计数据，动态添加表格
	function getStatis(type,cityid,distid,date) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/taofangstatis/wholesite/"+type+"/"+cityid+"/"+distid+"/"+date,
			dataType : "json",
			success : function(data) {
				$("#statTable tbody tr").remove();
				if(data == null || data[0] == null){
					var tr = "<tr><td colspan='12'>该查询区间没有统计数据</td></tr>";
					$("#statTable").append(tr);
				}else{
					var len = data.length;
					$.each(data,function(i,obj){
						var tr;
						var date = new Date(obj.date);
						var d = date.getDay();
						if(d == 6 || d == 0){
							tr = "<tr style='color:#0AA5AA'>";
						}else{
							tr = "<tr>";
						}
						if(i == (len-1)){
							tr +="<td>"+obj.date+"&nbsp;&nbsp;&nbsp;&nbsp;</td><td>"+obj.userCount+"</td><td>"+obj.verifyUserCount+"</td><td>"+obj.activeUserCount+"</td><td>"+(obj.tfRentCount+obj.tfResaleCount)+"</td><td>"+(obj.otherRentCount+obj.otherResaleCount)+"</td><td>"+(obj.tfRentShowCount+obj.tfResaleShowCount)+"</td><td>"+(obj.otherRentShowCount+obj.otherResaleShowCount)+"</td><td>"+obj.uv+"</td><td>"+obj.pv+"</td><td>"+obj.phoneCount+"</td><td>"+obj.saleAmount+"</td></tr>";
							$("#statTable").append(tr);
						}else{
							tr +="<td>"+obj.date+" <img class='compimg' width='12' src='/images/tri.png'/></td><td>"+obj.userCount+"</td><td>"+obj.verifyUserCount+"</td><td>"+obj.activeUserCount+"</td><td>"+(obj.tfRentCount+obj.tfResaleCount)+"</td><td>"+(obj.otherRentCount+obj.otherResaleCount)+"</td><td>"+(obj.tfRentShowCount+obj.tfResaleShowCount)+"</td><td>"+(obj.otherRentShowCount+obj.otherResaleShowCount)+"</td><td>"+obj.uv+"</td><td>"+obj.pv+"</td><td>"+obj.phoneCount+"</td><td>"+obj.saleAmount+"</td></tr>";
							$("#statTable").append(tr);
						}
					});
				}
				$(".compimg").bind("click",function(){
					var src = $(this).attr("src");
					var $curTR = $(this).parent().parent();
					if(src.indexOf("tria.png") > -1){
						 $(this).attr("src","/images/tri.png");
						 $curTR.next().remove();
					}else{
						$(this).attr("src","/images/tria.png");
						var $nextTR = $curTR.next(); 
						var curdate = $curTR.children().eq(0).html();
						curdate = curdate.substr(0,curdate.indexOf("<")-1);
						var curusercount = $curTR.children().eq(1).html();
						var curverifycount = $curTR.children().eq(2).html();
						var curactivecount = $curTR.children().eq(3).html();
						var curhousecount = $curTR.children().eq(4).html();
						var curotherhousecount = $curTR.children().eq(5).html();
						var curtfshowcount = $curTR.children().eq(6).html();
						var curothershowcount = $curTR.children().eq(7).html();
						var curuv = $curTR.children().eq(8).html();
						var curpv = $curTR.children().eq(9).html();
						var curphonecount = $curTR.children().eq(10).html();
						var cursaleamount = $curTR.children().eq(11).html();
						
						var nextusercount = $nextTR.children().eq(1).html();
						var nextverifycount = $nextTR.children().eq(2).html();
						var nextactivecount = $nextTR.children().eq(3).html();
						var nexthousecount = $nextTR.children().eq(4).html();
						var nextotherhousecount = $nextTR.children().eq(5).html();
						var nexttfshowcount = $nextTR.children().eq(6).html();
						var nextothershowcount = $nextTR.children().eq(7).html();
						var nextuv = $nextTR.children().eq(8).html();
						var nextpv = $nextTR.children().eq(9).html();
						var nextphonecount = $nextTR.children().eq(10).html();
						var nextsaleamount = $nextTR.children().eq(11).html();
						
						var compareusercount = curusercount - nextusercount;
						var compareverifycount = curverifycount - nextverifycount;
						var compareactivecount = curactivecount - nextactivecount;
						var comparehousecount = curhousecount - nexthousecount;
						var compareotherhousecount = curotherhousecount - nextotherhousecount;
						var comparetfshowcount = curtfshowcount - nexttfshowcount;
						var compareothershowcount = curothershowcount - nextothershowcount;
						var compareuv = curuv - nextuv;
						var comparepv = curpv - nextpv;
						var comparephonecount = curphonecount - nextphonecount;
						var comparesaleamount = cursaleamount - nextsaleamount;
						
						if(compareusercount > 0){
							compareusercount = "<span style='color:#FF3300;'>+"+compareusercount+"</span>";
						}else if(compareusercount < 0){
							compareusercount = "<span style='color:green;'>"+compareusercount+"</span>";
						} 
						if(compareverifycount > 0){
							compareverifycount = "<span style='color:#FF3300;'>+"+compareverifycount+"</span>";
						}else if(compareverifycount < 0){
							compareverifycount = "<span style='color:green;'>"+compareverifycount+"</span>";
						} 
						if(compareactivecount > 0){
							compareactivecount = "<span style='color:#FF3300;'>+"+compareactivecount+"</span>";
						}else if(compareactivecount < 0){
							compareactivecount = "<span style='color:green;'>"+compareactivecount+"</span>";
						} 
						if(comparehousecount > 0){
							comparehousecount = "<span style='color:#FF3300;'>+"+comparehousecount+"</span>";
						}else if(comparehousecount < 0){
							comparehousecount = "<span style='color:green;'>"+comparehousecount+"</span>";
						} 
						if(compareotherhousecount > 0){
							compareotherhousecount = "<span style='color:#FF3300;'>+"+compareotherhousecount+"</span>";
						}else if(compareotherhousecount < 0){
							compareotherhousecount = "<span style='color:green;'>"+compareotherhousecount+"</span>";
						} 
						if(comparetfshowcount > 0){
							comparetfshowcount = "<span style='color:#FF3300;'>+"+comparetfshowcount+"</span>";
						}else if(comparetfshowcount < 0){
							comparetfshowcount = "<span style='color:green;'>"+comparetfshowcount+"</span>";
						} 
						if(compareothershowcount > 0){
							compareothershowcount = "<span style='color:#FF3300;'>+"+compareothershowcount+"</span>";
						}else if(compareothershowcount < 0){
							compareothershowcount = "<span style='color:green;'>"+compareothershowcount+"</span>";
						} 
						if(compareuv > 0){
							compareuv = "<span style='color:#FF3300;'>+"+compareuv+"</span>";
						}else if(compareuv < 0){
							compareuv = "<span style='color:green;'>"+compareuv+"</span>";
						} 
						if(comparepv > 0){
							comparepv = "<span style='color:#FF3300;'>+"+comparepv+"</span>";
						}else if(comparepv < 0){
							comparepv = "<span style='color:green;'>"+comparepv+"</span>";
						} 
						if(comparephonecount > 0){
							comparephonecount = "<span style='color:#FF3300;'>+"+comparephonecount+"</span>";
						}else if(comparephonecount < 0){
							comparephonecount = "<span style='color:green;'>"+comparephonecount+"</span>";
						} 
						if(comparesaleamount > 0){
							comparesaleamount = "<span style='color:#FF3300;'>+"+comparesaleamount+"</span>";
						}else if(comparesaleamount < 0){
							comparesaleamount = "<span style='color:green;'>"+comparesaleamount+"</span>";
						} 
						var addTR = "<tr><td style='background-color:#FF9933'>"+curdate+"增量</td><td>"+compareusercount+"</td><td>"+compareverifycount+"</td><td>"+compareactivecount+"</td><td>"+comparehousecount+"</td><td>"+compareotherhousecount+"</td><td>"+comparetfshowcount+"</td><td>"+compareothershowcount+"</td><td>"+compareuv+"</td><td>"+comparepv+"</td><td>"+comparephonecount+"</td><td>"+comparesaleamount+"</td></tr>";
						$curTR.after(addTR);
					}
				});
			}
		});
	}
</script>