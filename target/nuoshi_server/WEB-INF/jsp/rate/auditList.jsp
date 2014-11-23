
 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript" src="/js/common/jquery.js"></script>
<script type="text/javascript" src="/js/common/common.js"></script>

<script type="text/javascript">
<!--
	$(document).ready(function(){
		var cityId = '${auditSearch.cityId}';
		if(cityId == null || cityId == "") {
			cityId = 0;
		}
		getDist("city",0);		
	});
	
	//ajax获取城市、区域 
	function getDist(selectId,pid) {
		var selectElem = "#" + selectId;
		$.ajax({
			type : "GET",
			async : false,
			url : "/agentManage/ajax/zone/"+pid,
			dataType : "json",
			success : function(data) {
				$(selectElem).empty();
				var list = data.distList;
				if (list != null && list.length > 0) {
					var global = "<option value='-1'>www全国</option>";
					$(global).appendTo(selectElem);
					for (i in list) {
						var local = list[i];
						var option;
						if(local.localid=='${auditSearch.cityId}'){
							option = "<option value='" + local.localid + "' selected='selected'>" + local.dirName + local.localname + "</option>";
						}else{
							option = "<option value='" + local.localid + "'>" + local.dirName + local.localname + "</option>";
						}
						 
						$(option).appendTo(selectElem);
					}
				}
			}
		});
	}
	function checkForm(){
		var startDate	= $("#start_date").val();
		var endDate		= $("#end_date").val();	
		//var startDateHour = $("#start_date_hour").val();
		//var endDateHour   = $("#end_date_hour").val();
		var errSpan = $("#err_span");
		var isCommit = true;
		errSpan.empty();
		/* if(isNaN(startDateHour) || isNaN(endDateHour)){//不是数字
			errSpan.append("小时只能填数字;");
			isCommit = false;
		} 
		if(startDateHour<0 || startDateHour>23 || endDateHour<0 || endDateHour>23){
			errSpan.append("小时只能在0~23之间;");
			isCommit = false;
		}*/
		if(startDate=="起始日期"){
			startDate="";
			$("#start_date").val("");
		}
		if(endDate=="终止日期"){
			endDate="";
			$("#end_date").val("");
		}
		if(startDate>endDate && startDate!="" && endDate!=""){
			errSpan.append("起始日期应不小于终止日期;");
			isCommit = false;
		}/* else if(startDate==endDate){
			if(startDateHour>endDateHour){
				errSpan.append("起始日期应不小于终止日期;");
				isCommit = false;
			}
		} */
		return isCommit;
	}
	function _submit(type){
		$("#rateForm").attr("action","");
		if(type==0){
			$("#rateForm").attr("action","/rate/auditList");
		}
		if(type ==1){
			$("#rateForm").attr("action","/rate/exportExcel");
		}
		$("#rateForm").submit();
	}
	
	function isEveryMonth(){
		if($("input[id='everyMonth']:checked").size()==1){
			$("#start_date").attr("disabled","disabled");
			$("#end_date").attr("disabled","disabled");
		}else{
			$("#start_date").attr("disabled",false);
			$("#end_date").attr("disabled",false);
		}
		
	}
	
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：数据管理 &gt; 房源图片审核统计</div><br />

<form name="auditSearch" action="" method="post" id="rateForm" onsubmit="return checkForm();">
  <!-- 查询条件 -->
  <div class="mag">
     <br />
     <div class="search" >
		<table>
			<tr>
				
				<td colspan="2">图片上传时间:
					<input id="start_date" class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyyMMdd','起始日期')" readonly="readonly" value="${auditSearch.startDate}"  <c:if test="${auditSearch.everyMonth =='everyMonth' }">disabled</c:if>  />
					至<input id="end_date" class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyyMMdd','终止日期')" readonly="readonly" value="${auditSearch.endDate}"  <c:if test="${auditSearch.everyMonth =='everyMonth' }">disabled</c:if>  />
				</td>
				<td><span id="err_span" style="color:red"></span></td>
				<td><input  type="checkbox" id="everyMonth" name="everyMonth" value="everyMonth"   <c:if test="${auditSearch.everyMonth =='everyMonth' }">checked='checked'</c:if>    onclick="isEveryMonth();"/>每月</td>
			</tr>
			<tr>
				<td>城市:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="city" style="width:100px;" name="cityId" ></select></td>
				<td>类型:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="type" name="rateType" >
						<option value="0" 	<c:if test="${auditSearch.rateType == 0}">selected="selected"</c:if> >全部</option>
						<option value="1" 	<c:if test="${auditSearch.rateType == 1}">selected="selected"</c:if> >出租房</option>
						<option value="2" 	<c:if test="${auditSearch.rateType == 2}">selected="selected"</c:if> >二手房</option>
					</select>
				</td>
				<td>状态:
					<select id="status" name="rateStatus" >
						<option value="-1" <c:if test="${auditSearch.rateStatus == -1}">selected="selected"</c:if> >全部</option>
						<option value="0"  <c:if test="${auditSearch.rateStatus == 0}">selected="selected"</c:if> >待审核</option>
						<option value="1"  <c:if test="${auditSearch.rateStatus == 1}">selected="selected"</c:if> >通过</option>
						<option value="2"  <c:if test="${auditSearch.rateStatus == 2}">selected="selected"</c:if> >拒绝</option>
					</select>
				</td>	
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;搜素经纪人<input id="rateAgent" name="rateAgent" value="${auditSearch.rateAgent}" />
					<select id="rateAgentType" name="rateAgentType" >
						<option value="0"  <c:if test="${auditSearch.rateAgentType == 0}">selected="selected"</c:if> >经纪人手机</option>
						<option value="1"  <c:if test="${auditSearch.rateAgentType == 1}">selected="selected"</c:if> >经纪人姓名</option>
						<option value="2"  <c:if test="${auditSearch.rateAgentType == 2}">selected="selected"</c:if> >经纪人ID</option>
					</select>
				</td>			
				<td><input type="button"  value="查找" onclick="_submit(0);"  /></td>
				<td><input type="button"  value="导出excel" onclick="_submit(1);" /></td>
			</tr>
		</table>
	</div>
  </div>

			<div class="todo">

				<table>

					<thead>

						<tr>
							<th style=" width:12.5%; text-align:center">时间</th>
							<th style=" width:12.5%; text-align:center">经纪人ID</th>
							
							<th style=" width:12.5%; text-align:center">
								<c:choose>
									<c:when test="${empty auditSearch.rateType or auditSearch.rateType eq 0 }">全部</c:when>
									<c:when test="${auditSearch.rateType eq 1 }">租房</c:when>
									<c:otherwise>二手房</c:otherwise>
								</c:choose>
								-已审核量
							</th>
							<th style=" width:12.5%; text-align:center">
								<c:choose>
									<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">上传总量</c:when>
									<c:when test="${auditSearch.rateStatus eq 0 }">待审率</c:when>
									<c:when test="${auditSearch.rateStatus eq 1 }">通过率</c:when>
									<c:otherwise>违规率</c:otherwise>
								</c:choose>
							</th>
							<th style=" width:12.5%; text-align:center">
								<c:choose>
									<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">总上传</c:when>
									<c:when test="${auditSearch.rateStatus eq 0 }">待审</c:when>
									<c:when test="${auditSearch.rateStatus eq 1 }">通过</c:when>
									<c:otherwise>拒绝</c:otherwise>
								</c:choose>
								-小区图
							</th>
							<th style=" width:12.5%; text-align:center">
								<c:choose>
									<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">总上传</c:when>
									<c:when test="${auditSearch.rateStatus eq 0 }">待审</c:when>
									<c:when test="${auditSearch.rateStatus eq 1 }">通过</c:when>
									<c:otherwise>拒绝</c:otherwise>
								</c:choose>
								-室内图
							</th>
							<th style=" width:12.5%; text-align:center">
								<c:choose>
									<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">总上传</c:when>
									<c:when test="${auditSearch.rateStatus eq 0 }">待审</c:when>
									<c:when test="${auditSearch.rateStatus eq 1 }">通过</c:when>
									<c:otherwise>拒绝</c:otherwise>
								</c:choose>
								-户型图
							</th>
						</tr>
					</thead>
					
					<tbody class="rasent">
					<c:choose>	
						<c:when test="${empty resList or resList.size()<1}">
							<c:if test="${empty auditSearch.cityId}"><tr><td colspan="4">请输入查询条件查询数据${res.cityId }</td><tr></c:if>
							<c:if test="${not empty auditSearch.cityId}"><tr><td colspan="4">暂无数据</td><tr></c:if>
						</c:when>
						<c:otherwise>
							<c:forEach items="${resList }" var="res"  varStatus="var">
								<tr>
									<td>${res.uploadDate }</td>
									<td>${res.agentId }</td>
									
									<td>${res.pAllCount+res.rAllCount } </td>
									<td>
										<c:choose>
											<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">${res.allCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 0 }"><c:if test="${res.allCount != 0 }"><fmt:formatNumber value="${(res.wAllCount*100)/res.allCount }" pattern="#,###.#" />%</c:if> </c:when>
											<c:when test="${auditSearch.rateStatus eq 1 }"><c:if test="${(res.pAllCount+res.rAllCount) != 0 }"><fmt:formatNumber value="${(res.pAllCount*100)/(res.pAllCount+res.rAllCount) }" pattern="#,###.#" />%</c:if> </c:when>
											<c:otherwise><c:if test="${(res.pAllCount+res.rAllCount) != 0 }"><fmt:formatNumber value="${(res.rAllCount*100)/(res.pAllCount+res.rAllCount) }" pattern="#,###.#" />%</c:if> </c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">${res.wEstateCount+res.pEstateCount+res.rEstateCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 0 }">${res.wEstateCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 1 }">${res.pEstateCount }</c:when>
											<c:otherwise>${res.rEstateCount }</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">${res.wInnerCount+res.pInnerCount+res.rInnerCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 0 }">${res.wInnerCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 1 }">${res.pInnerCount }</c:when>
											<c:otherwise>${res.rInnerCount }</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty auditSearch.rateStatus or auditSearch.rateStatus eq -1 }">${res.wLayoutCount+res.pLayoutCount+res.rLayoutCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 0 }">${res.wLayoutCount }</c:when>
											<c:when test="${auditSearch.rateStatus eq 1 }">${res.pLayoutCount }</c:when>
											<c:otherwise>${res.rLayoutCount }</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>			
					</c:choose>							
					</tbody>

				</table>

			</div>

		<div class="page_and_btn" >
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	
	</form>


