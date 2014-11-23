<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/agent/agentVerify.js"></script>
<script type="text/javascript">
function searchAgent() {
	
	var id = $('#agentIdSearch').val() == '请输入搜索内容' ? 0 : $('#agentIdSearch').val();
	var mobile = $('#agentMobile').val() == '请输入搜索内容' ? '=' : $('#agentMobile').val();
	var checked=$('#onlyBeiJing').attr("checked")=='checked';
	//12-2 wangjh改 只查询北京经纪人
	if(checked){
		window.location.href="/agentVerify/search/"+id+"/"+mobile + "/verify?onlyBeiJing='true'";
	}else{
		window.location.href="/agentVerify/search/"+id+"/"+mobile + "/verify";
	
	}
} 
</script>
<div style="width: 100%;text-align: left;">当前位置：经纪人管理&gt; 经纪人审核 </div> 
      
<div class="zxt_lg">
	<label>经纪人编号：</label>
	<input type="text" class="xt_ipt" id="agentIdSearch" name="searchtxt" value="${agentIdSearch}" style="color:gray;"/>
	<label class="xt_1">手机：</label><input type="text" class="xt_ipt" id="agentMobile" name="searchtxt" value="${agentMobile}" style="color:gray"/>
	<input class="xt_button" type="button" value="搜索" onClick="searchAgent();"/>
	
	
	<label class="xt_1">经纪人编号：</label><input type="text" class="xt_ipt" id="agentIdFor400" name="searchtxt" value="请输入搜索内容" style="color:gray"/>
	<label class="xt_1">只搜索北京经纪人:</label>
				<c:choose>
					<c:when test="${onlyBeiJing}">
						<input id="onlyBeiJing" type="checkbox" value="true" checked="checked" name="onlyBeiJing"  onclick="searchAgent();"/>
					</c:when>
					<c:otherwise>
						<input id="onlyBeiJing" type="checkbox" value="true" name="onlyBeiJing"  onclick="searchAgent();"/>
					</c:otherwise>
				</c:choose>
</div>

<form action="" method="post" id="verifyForm" style="margin-top:0px;margin-top:0px;">
	<div class="todo">
		<table id="readyAgentTable" class="c" style="text-align:center">
			<thead>
				<tr>
					<th style="width:10%;">ID</th>
					<th style="width:30%;">身份证信息</th>
					<th style="width:20%;">头像信息</th>
					<th style="width:30%;">名片信息</th>
					<c:if test="${sessionScope.sessionUser.isPass('/privilege/agent/verify') }">
					 <th style="width:10%;">操作</th> 
					 </c:if>
				</tr>
			</thead>
			<tbody align="center" >
			<c:choose>
				<c:when test="${not empty verifyList}">
					<c:forEach items="${verifyList}" var="verify" varStatus="var">
						<input type="hidden" name="agentId" value="${verify.agentId}" />
						<tr class="main_info">
							<td style="width:10%; text-align:center">
								${verify.agentId}
							</td>
							<td style="width:30%; text-align:center">
								<div class="imgDiv">
									<div style=" margin-bottom:10px; margin-top:20px;"><img src="${verify.idCardImg}" style="width:260px; height:161px; margin:auto;"/></div>
									<label>身份证号：</label><span>${verify.idcardNumber}</span><br/>
									<label>真实姓名：</label><span>${verify.name}</span><br/>
								</div>
								<c:if test="${sessionScope.sessionUser.isPass('/privilege/agent/verify') }">
								<div style="text-align:center;">
									<input class="rejectRadio" name="idCardRadio_${verify.agentId}" type="radio" id="idCard_appr_${verify.agentId}" value="1" <c:if test="${verify.idcardOk}">checked="checked"</c:if>  <c:if test="${(verify.idcardRejected && !verify.idcardEdited) || verify.idcardOk}">disabled="disabled"</c:if> />
	                           		<label for="idCard_appr_${verify.agentId}" class="cor472">通过</label>
	                           		<input class="rejectRadio" name="idCardRadio_${verify.agentId}" type="radio" id="idCard_reject_${verify.agentId}" value="2" <c:if test="${verify.idcardRejected}">checked="checked"</c:if> <c:if test="${(verify.idcardRejected && !verify.idcardEdited) || verify.idcardOk}">disabled="disabled"</c:if> />
	                           		<label for="idCard_reject_${verify.agentId}" class="cor472">拒绝</label>
	                           		<c:choose>
	                           			<c:when test="${verify.idcardOk}">
	                           				<input type="hidden" id="idCardResult_${verify.agentId}" name="idCardResult" value="1"/>
	                           			</c:when>
	                           			<c:when test="${verify.idcardRejected}">
	                           				<input type="hidden" id="idCardResult_${verify.agentId}" name="idCardResult" value="2"/>
	                           			</c:when>
	                           			<c:otherwise>
	                           				<input type="hidden" id="idCardResult_${verify.agentId}" name="idCardResult" value="0"/>
	                           			</c:otherwise>
	                           		</c:choose>
	                           		
								    <div id="idCardMsg_${verify.agentId}" class="rejectReasonDiv" <c:if test="${verify.idcardRejected}">style="display:block;"</c:if>>
										<h4>拒绝原因：</h4>
	                                	<textarea class="rejectTextArea" id="idCardMsg_text_${verify.agentId}" name="idCardMsg" cols="40" rows="3">${verify.msgIdcard}</textarea>
	                                	<ul>
	                                		<c:forEach items="${idCardReason}" var="reason" varStatus="vs">
	                                			<li><input type="checkbox" id="icmsg_${vs.index + 1}_${verify.agentId}"><label for="icmsg_${vs.index + 1}_${verify.agentId}">${reason}</label></li>
	                                		</c:forEach>
	                                	</ul>
	                               	</div>
								</div> 
								</c:if>
								<c:if test="${!sessionScope.sessionUser.isPass('/privilege/agent/verify') }">
									 <c:if test="${verify.idcardRejected}"><h4>审核结果：拒绝</h4>
				                            <label>拒绝原因：&nbsp;${verify.msgIdcard}</label>
									 </c:if>
										 <c:if test="${verify.idcardOk}"><h4>审核结果：通过</h4> 
									</c:if>
									<c:if test="${!verify.idcardRejected && !verify.idcardOk}"><h4>未进行审核</h4></c:if>
								</c:if>
							</td>
							<td style="width:20%; text-align:center">
								<div class="imgDiv">
									<div style=" margin-bottom:10px; margin-top:20px;"><img src="${verify.headImg}"/></div>
									<%-- <label>400号码：</label><span>${verify.agentCallNumber}</span><br/> --%>
								</div>
								<c:if test="${sessionScope.sessionUser.isPass('/privilege/agent/verify') }">
								<div style="text-align:center">
									<input class="rejectRadio" name="headRadio_${verify.agentId}" type="radio" id="head_appr_${verify.agentId}" value="1" <c:if test="${verify.headOk}">checked="checked"</c:if> <c:if test="${(verify.headRejected && !verify.headEdited) || verify.headOk}">disabled="disabled"</c:if> />
	                           		<label for="head_appr_${verify.agentId}" class="cor472">通过</label>
	                           		<input class="rejectRadio" name="headRadio_${verify.agentId}" type="radio" id="head_reject_${verify.agentId}" value="2" <c:if test="${verify.headRejected}">checked="checked"</c:if> <c:if test="${(verify.headRejected && !verify.headEdited) || verify.headOk}">disabled="disabled"</c:if> />
	                           		<label for="head_reject_${verify.agentId}" class="cor472">拒绝</label>
	                           		<c:choose>
	                           			<c:when test="${verify.headOk}">
	                           				<input type="hidden" id="headResult_${verify.agentId}" name="headResult" value="1"/>
	                           			</c:when>
	                           			<c:when test="${verify.headRejected}">
	                           				<input type="hidden" id="headResult_${verify.agentId}" name="headResult" value="2"/>
	                           			</c:when>
	                           			<c:otherwise>
	                           				<input type="hidden" id="headResult_${verify.agentId}" name="headResult" value="0"/>
	                           			</c:otherwise>
	                           		</c:choose>
									
									<div id="headMsg_${verify.agentId}" class="rejectReasonDiv" <c:if test="${verify.headRejected}">style="display:block;"</c:if>>
										<h4>拒绝原因：</h4>
			                            <textarea  class="rejectTextArea" id="headMsg_text_${verify.agentId}" name="headMsg" cols="35" rows="3">${verify.msgHead}</textarea>
			                            <ul>
			                            	<c:forEach items="${headReason}" var="reason" varStatus="vs">
			                            		<li><input id="headmsg_${vs.index + 1}_${verify.agentId}" type="checkbox"><label for="headmsg_${vs.index + 1}_${verify.agentId}">${reason}</label></li>
			                            	</c:forEach>
			                            </ul>
		                            </div>
								</div>
								</c:if>
								<c:if test="${!sessionScope.sessionUser.isPass('/privilege/agent/verify') }">
									 <c:if test="${verify.headRejected}"><h4>审核结果：拒绝</h4>
				                            <label>拒绝原因：&nbsp;${verify.msgHead}</label>
									 </c:if>
										 <c:if test="${verify.headOk}"><h4>审核结果：通过</h4> 
									</c:if>
									<c:if test="${!verify.headRejected && !verify.headOk}"><h4>未进行审核</h4></c:if>
								</c:if>
							</td> 
							<td style="width:30%; text-align:center">
								<div class="imgDiv">
									<div style=" margin-bottom:10px; margin-top:20px;"><img src="${verify.nameCardImg}" /></div>
									<label>手机：</label><span>${verify.mobile}</span><br />
									<label>公司：</label><span>${verify.company}</span><br />
									<label>门店：</label><span>${verify.store}</span><br/>
								</div>
							</td>
							<c:if test="${sessionScope.sessionUser.isPass('/privilege/agent/verify') }">
							 <td style="width:10%; text-align:center">
							 			<input type="button" value="提交" onClick="submitVerify(${verify.agentId})"/>
							</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="7">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
	<div class="page_and_btn">
		<jsp:include page="/WEB-INF/snippets/page.jsp" />
	</div>
</form>