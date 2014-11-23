<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>

<div style="width: 100%;text-align: left;">当前位置：数据管理 &gt; 审核质量统计</div>
<form name="auditQualityStatis" action="/rate/auditQualityStatis" method="post" id="rateForm">
  <!-- 查询条件 -->
  <div class="mag">
     <br />
     <div class="search" >
		<table>
			<tr>				
				<td>审核日期:
					<input id="searchStartDate" class="def" name="searchStartDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','')" readonly="readonly" value="${searchStartDate}"/>---
					<input id="searchEndDate" class="def" name="searchEndDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','')" readonly="readonly" value="${searchEndDate}"/>
				</td>
				
				<td>房源城市:
					<select id="city" style="width:100px;" name="cityId" >
						<option value="0" <c:if test="${cityId == 0}">selected="selected"</c:if>>请选择城市</option>
						<option value="1" <c:if test="${cityId == 1}">selected="selected"</c:if>>北京</option>
	               		<option value="2" <c:if test="${cityId == 2}">selected="selected"</c:if>>上海</option>
	               		<option value="3" <c:if test="${cityId == 3}">selected="selected"</c:if>>广州</option>
	               		<option value="4" <c:if test="${cityId == 4}">selected="selected"</c:if>>深圳</option>
	               		<option value="102" <c:if test="${cityId == 102}">selected="selected"</c:if>>成都</option>		
					</select>
				</td>
				
			</tr>
			<tr>	
				<td>审核人员:
					<%-- <input id="agentName" name="agentName" value="${agentName }" style="width:97px;" /> --%>
					<select id="auditUserId" name="auditUserId">
						<option value="">请选择</option>
						<c:forEach items="${userList }" var="user" >
							<option value="${user.id }" <c:if test="${user.id == auditUserId }">selected='selected'</c:if>  >${user.chnName }</option>
						</c:forEach>
					</select>
				</td>	
				<td>
					<select id="tableType" style="width:100px;" name="tableType" onchange="_check(this.value)">
						<option value="0" <c:if test="${tableType == 0}">selected="selected"</c:if>></option>
						<option value="1" <c:if test="${tableType == 1}">selected="selected"</c:if>>历史记录</option>
					</select>
				</td>
				<td><input type="submit"  value="查找" /></td>
			</tr>
		</table>
	</div>
  </div>

			<div class="todo">

				<table>

					<thead>

						<tr>
							<!-- <th style=" width:12.5%; text-align:center">序号</th> -->
							<th style=" width:6%; text-align:center">房源ID</th>
							<th style=" width:50%; text-align:center">房源标题</th>
							<th style=" width:6%; text-align:center">城市</th>
							<th style=" width:6%; text-align:center">审核人</th>
							<th style=" width:15%; text-align:center">审核时间</th>
							<th style=" width:6%; text-align:center">审核状态</th>
						</tr>
					</thead>
					
					<tbody class="rasent">
					<c:choose>	
						<c:when test="${empty resList or resList.size()<1}">
							<c:if test="${empty cityId}"><tr><td colspan="6">请输入查询条件查询数据${res.cityId }</td><tr></c:if>
							<c:if test="${not empty cityId}"><tr><td colspan="6">暂无数据</td><tr></c:if>
						</c:when>
						<c:otherwise>
							<c:forEach items="${resList }" var="res"  varStatus="var">
								<tr>
									<td>${res.houseId}</td>	
									<td>
										
										<a href="javascript:void(0)" class="link" onclick="openCheck('${res.houseId}')"  >${res.houseTitle }</a>
										<div id="box${res.houseId}" style="display: none;" >
										<table style="width: 95%;" >
											<tr>
												<th style="width: 25%; text-align: center">房源信息</th>
												<th style="width: 25%; text-align: center">户型图</th>
												<th style="width: 25%; text-align: center">室内图</th>
												<th style="width: 25%; text-align: center">小区图</th>
											</tr>
											<tr style="background-color: #FFFFFF">
												<td style="width: 25%; text-align: left; vertical-align: top;">
													<div style="width: 315px; padding: 5px; white-space: normal; line-height: 18px; text-align: left; font-size: 12px;">
														<h3>${res.houseTitle}(id:${res.houseId})</h3>
														<ul>
															<li>总价：<strong>${res.houseObj.price}</strong>万</li>
															<li class="price">单价：<span><fmt:formatNumber
																		type="number" pattern="0"
																		value="${res.houseObj.price/res.houseObj.area*10000}"
																		maxFractionDigits="0" />
															</span>/㎡</li>
															<li>户型：${res.houseObj.flattype}</li>
															<li class="area">面积：<span>${res.houseObj.area}</span>平米</li>
															<li>楼层：${res.houseObj.floorLabel}</li>
															<li>年份：${res.houseObj.completion}年</li>
															<li>小区：${res.houseObj.estatename}</li>
															<li>地址：${res.houseObj.brandAddress}</li>
															<c:if test="${res.houseObj.extinfo.length() == 0 }">
																<li class="isempty">没写描述信息</li>
															</c:if>
															<c:if test="${res.houseObj.extinfo.length() > 0 }">
																<li>房屋描述：${res.houseObj.extinfo}</li>
															</c:if>
														</ul>
													</div>
												</td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${res.layoutImgUrls}" var="img">
														<img src="${img.mURL}" width="200px" height="150" />
													</c:forEach></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${res.innerImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${res.estateImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
	
											</tr>
										</table>
									</div>
									</td>
									<td>${res.cityName }</td>
									
									<td>
										${res.auditUserName }
										<%-- 
										<c:if test="${tableType == 1}">${res.auditUserName }</c:if>
										<c:if test="${tableType == 0}">
											<c:if test=""><a href="/rate/auditQualityStatis?auditUserId=${res.auditUserId }" target="_blank" >${res.auditUserName }</a></c:if>
											<c:if test=""><a href="/rate/auditQualityStatis?auditUserId=${res.auditUserId }"  >${res.auditUserName }</a></c:if>
										</c:if> --%>
									</td>
									<td>${res.auditDate }</td>
									<td>
										<c:if test="${res.auditStatus == -1 }">通过</c:if>
										<c:if test="${res.auditStatus == -2 }">违规</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>			
					</c:choose>							
					</tbody>
					<tbody class="rasent">
							<c:if test="${not empty otherList and otherList.size()>0}"><tr><td colspan="6" align="left"><b>其他城市</b></td></tr></c:if>
							<c:if test="${empty otherList or otherList.size()<1}"><tr><td colspan="6" align="left"><b>其他城市无数据</b></td></tr></c:if>
							<c:forEach items="${otherList }" var="res"  varStatus="var">
								<tr>
									<td>${res.houseId}</td>	
									<td>
										
										<a href="javascript:void(0)" class="link" onclick="openCheck('${res.houseId}')"  >${res.houseTitle }</a>
										<div id="box${res.houseId}" style="display: none;" >
										<table style="width: 95%;" >
											<tr>
												<th style="width: 33%; text-align: center">户型图</th>
												<th style="width: 33%; text-align: center">室内图</th>
												<th style="width: 33%; text-align: center">小区图</th>
											</tr>
											<tr style="background-color: #FFFFFF">
												<td
													style="width: 33%; text-align: center; vertical-align: top;">
													<c:forEach items="${res.layoutImgUrls}" var="img">
														<img src="${img.mURL}" />
													</c:forEach></td>
												<td
													style="width: 33%; text-align: center; vertical-align: top;">
													<c:forEach items="${res.innerImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
												<td
													style="width: 33%; text-align: center; vertical-align: top;">
													<c:forEach items="${res.estateImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
	
											</tr>
										</table>
									</div>
									</td>
									<td>${res.cityName }</td>
									
									<td>${res.auditUserName } </td>
									<td>${res.auditDate } </td>
									<td>
										<c:if test="${res.auditStatus == -1 }">通过</c:if>
										<c:if test="${res.auditStatus == -2 }">违规</c:if>
									</td>
								</tr>
							</c:forEach>		
					</tbody>
				</table>

			</div>
		<%-- <c:if test="${tableType == 1}">
			<div class="page_and_btn" >
				<jsp:include page="/WEB-INF/snippets/page.jsp" />
			</div>
		</c:if> --%>
	</form>
<script type="text/javascript">
<!--
	$(function() {
		var h = $(window).height();
		// 初始化弹出窗口
		$("#divCheck").dialog({show : "slide",bgiframe : true,autoOpen : false,height :h*0.8,width : '80%',title : "查看房源",modal : true,resizable : false});
	});
	//显示审核窗口
	function openCheck(id) {
		$("*").stop();
		//$("#resaleId").html(id);
		$("#divCheck").html($("#box"+id).html());
		$("#divCheck").dialog("close");
		$("#divCheck").dialog("option", "position", "center");
		$("#divCheck").dialog("open");
	}
	
	function _check(){
		var tt = $("#tableType").val();
		if(tt=="0"){
			var sd = $("#searchDate").val();
			if(d==null || d==""){
				alert("请输入日期！")
				return false;
			}
		}
		return true;
	}
//-->
</script>
<div id = "divCheck" class="todo" style="text-align: center;" >
	
</div>

