
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript" src="http://static.taofang.com/js/jquery.taofang.js"></script>
<script type="text/javascript" src="http://static.taofang.com/js/jquery.autocomplete.js"></script>
<style>
<!--
/* >>> 小区自动搜索 */
.estate-ac { padding:0 0px; border:1px solid #7F9DB9; background:white; overflow:hidden; z-index:999999; font-size:12px; }
.estate-ac-header { height:26px; padding:0 5px; font:12px/26px 宋; color:#CC0000; border-bottom:1px solid #bdbdbd; }
.estate-ac-footer { height:28px; padding:0 5px; font:12px/28px 宋体; background:#d0d0d0; }
.estate-ac-footer .unfound { color:#CC0000; display:none; }
.estate-ac-footer .next,.estate-ac-footer .prev { display:none;}
.estate-ac-lastpage .unfound { display:inline; }
.estate-ac-shownext .next { display:inline; margin-left:12px;}
.estate-ac-showprev .prev { display:inline; }

.estate-ac li { height:24px; color:#18517A; line-height:24px; font-size:12px; background:white; padding:0 5px; border-bottom:1px dashed #d0d0d0; cursor:default;  }
.estate-ac li.ac_over { background:#7F9DB9; color:white;}
.estate-ac .estate-name{float:left;  }
.estate-ac .estate-addr{ float:right; color:#a8a8a8; }
.estate-ac li.ac_over .estate-addr { color:#f0f0f0; }
/* <<< 小区自动搜索 */
-->
</style>
<script type="text/javascript">
<!--

	//ajax获取城市、区域 
	function getDist(selectId, pid) {
		var selectElem = "#" + selectId;
		$.ajax({
					type : "GET",
					async : false,
					url : "/agentManage/ajax/zone/" + pid,
					dataType : "json",
					success : function(data) {
						$(selectElem).empty();
						var list = data.distList;
						if (list != null && list.length > 0) {
							for (i in list) {
								var local = list[i];
								var option = "<option value='" + local.localid + "'>"
										+ local.dirName
										+ local.localname
										+ "</option>";
								$(option).appendTo(selectElem);
							}
						}
					}
				});
	}

	$(document).ready(function() {
		getDist("city", 0);
	});
	
function check(){
	if($("#estateid").val()==''){
		alert("请输入小区名称");
		$("#estatename").focus();
		return false;
	}
	if($("#agentPhone").val()==''){
		alert("请输入经纪人手机号");
		$("#agentPhone").focus();
		return false;
	}
	if($("#startTime").val()==''){
		alert("请输入开始日期");
		$("#startTime").focus();
		return false;
	}
	
	if($("#endTime").val()==''){
		alert("请输入结束日期");
		$("#endTime").focus();
		return false;
	}
	var endTimes = $("#endTime").val().split("-");
	var startTimes = $("#startTime").val().split("-");
	var endDate = new Date(endTimes[0],endTimes[1]-1,endTimes[2]);
	var startDate= new Date(startTimes[0],startTimes[1]-1,startTimes[2]);
	if(startDate>=endDate){
		alert("结束日期必须大于开始日期");
		$("#endTime").focus();
		return false;
	}
	$.ajax({
		type : "post",
		async : false,
		url : "/estateexpert/save",
		dataType : "text",
		data: $("#estateExpertForm").serialize(),
		success : function(back) {
			var data = eval("("+back+")");
			if(data.result =='fail'){
				alert(data.error);
			}else{
				alert("添加成功");
			}
			return;
		}
	});
}	
//-->
</script>
<div style="width: 100%; text-align: left;">当前位置：小区专家管理 &gt;
	添加小区专家</div>
<br />

<form action="/estateexpert/save" method="post" id="estateExpertForm" >
<input type="hidden" id="estateid" name="estateId"  />
	<div id="addExpert" class="todo">
		<table>
			<tr>
				<td>城市:</td>
				<td><select id="city" name="cityId"></select>
				</td>
				<td>小区名称</td>
				<td>
				<input type="text"
							class="codingIpt" name="estateName" 
							value="" id="estatename" /> 
							<span id="searchEstateInfo" style="color: red;display: none">请点击相匹配的小区或新建小区</span>
							<br />&nbsp;&nbsp;&nbsp;&nbsp;
				
				</td>
			</tr>
			<tr>
				<td>经纪人手机号</td>
				<td><input type="text" id="agentPhone" name="agentPhone"   onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
				<td>等级</td>
				<td><select name="expertType"><option value="1">金牌</option>
						<option value="2">银牌</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>开始日期</td>
				<td><input readonly="readonly" name="startTimeStr" id="startTime"
					onclick="InitSelectDate(1,this,'yyyy-MM-dd','')" /></td>
				<td>结束日期</td>
				<td><input readonly="readonly" name="endTimeStr" id="endTime"
					onclick="InitSelectDate(1,this,'yyyy-MM-dd','')" />
				</td>
			</tr>
			
			<tr><td align="center" colspan="4"><a href="#"><input type="button" value="保存" onclick="javaScript:check();"/></a></td></tr>
		</table>
	</div>
</form>

<script type="text/javascript">
<!--



/* <<< 自动完成 */
$(document).ready(function(){
	var cityid = $("#city").val();
	var $keyword = $("#estatename");
	var cityId =$("#city").val();
	// http://bj.taofang.com/searchestate/select/
	var record_start = 0;
	var record_num = 0;
	var page_count = 0;
	var page_cur = 0;
	var limit = 10;
	var last_kw = "";
	var s_data = [];
	var noRecord = false;
	$keyword.keyup(function(){
		if( this.value != last_kw ) { record_start = 0; }
		last_kw = this.value;
	}).autocomplete( "/estate/getestate", {
		extraParams:{
			"cityId":function(){return $("#city").val()},
			"estatename":function(){ return $("#estatename").val() },
			"start":function(){ return record_start }
		},
		cacheLength:0,max:limit, width:301, delay:0, minChars:1, scroll:false, selectFirst:false, resultsClass:"estate-ac",
		dataType:"json",
		parse:function(data){
			var list = data.elist||[];
			noRecord = false;
			record_num = data.records;
			page_count = Math.ceil(record_num/limit);
			page_cur = parseInt((record_start+1)/limit);
			// 追加新建小区
			var $val  = $keyword.val();
			if(!list.length){
				list.push({noRecord:true});
				noRecord = true;
			}
			s_data = $.map(list, function(item){
				return {
					data:item, // data for formatitem
					value:item.estateId, //value for clicked
					result:item.estateName //input label for clicked
				};
			});
			
			return s_data;
			
		},
		formatItem: function(item, i , max){
			item.taddr = (item.address||"");
			item.taddr= (item.taddr.length>12)? (item.taddr.truncate(11,"")+"..."):item.taddr;
			item.tname = (item.estateName||"");
			item.tname= (item.tname.length>8)? (item.tname.truncate(7,"")+"..."):item.tname;
			return [item.noRecord?"": '<p class="c" eid="{estateId}" distid="{distId}" blockid="{blockId}">', '<span class="estate-name">{tname}</span>', '<span class="estate-addr">{taddr}</span>','</p>'].join('').format(item);
		},
		hide: function() {
			$("#searchEstateInfo").hide();
		},
		show:function(list){
			var pos = $keyword.offset();
			var $list = $(list);
			$(list).css({top:pos.top+$keyword.outerHeight()-1});
			
			
			if(!$list.find('.estate-ac-header').size()){
				$list.prepend( '<div class="estate-ac-header">请点击相匹配的小区</div>');
			}
			if(!$list.find('.estate-ac-footer').size()){
				$list.append( [
					'<div class="estate-ac-footer">',
						'<div class=r><a href="javascript:;" class="prev">&laquo;上一页</a><a href="javascript:;" class="next">下一页&raquo;</a></div>',
					'</div>'].join(''));
			}
			var select = $keyword[0].autocomplete_select;
			var $f = $list.find(".estate-ac-footer");
			
			
			
			$f[( page_cur >= page_count -1)?"addClass":"removeClass"]("estate-ac-lastpage");
			$f[( page_cur < page_count -1)?"addClass":"removeClass"]("estate-ac-shownext");
			$f[( page_cur > 0)?"addClass":"removeClass"]("estate-ac-showprev");
			if(noRecord){ //无记录情况
				$list.find("ul").html('');
				$list.find('.estate-ac-header').remove();
				$f.addClass("estate-ac-lastpage");
			}
			$f.find("a.prev,a.next").unbind("click").each(function(i){
				$(this).click(function(){
					record_start = record_start + [-10,10][i];
					$keyword.search(function(){ select.display(s_data,"")});
				})
			});
		},
		lostFocus:function(){ 
			return false 
		}
	}).result(function(event, item){
		if(item) $("#estateid").val(item.estateId);
	});
});

//-->
</script>