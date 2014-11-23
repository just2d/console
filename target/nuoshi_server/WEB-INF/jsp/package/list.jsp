<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/package/package.js"></script>
<div style="width: 100%;text-align: left;">当前位置：套餐管理</div> 

<form action="/package/manage/list" method="post" id="packageForm" style="margin-top:0px;margin-top:0px;">
	<input type="hidden" id="displayType" value="0" />
	<div style="text-align:left;">
		<label>城市：</label><select id="city" name="cityId" style="width:100px;" onchange="changeCity(this.value);"></select>
		<input type="button" value="添加" onclick="addPackage();">
		<%-- <div>
			<input type="radio" name="deafultPackage" checked="checked"/>免费套餐&nbsp;&nbsp;
			<label>房源数量：</label><input type="text" id="defaultPackageHouseNum" name="defaultPackageHouseNum" value="${defaultPackage.houseAmount}"/>
			<label>标签数量：</label><input type="text" id="defaultPackageLabelNum" name="defaultPackageLabelNum" value="${defaultPackage.labelAmount}" readonly="readonly"/>
			<label>持续时间：</label><input type="text" id="defaultPackageMonth" name="defaultPackageMonth" value="${defaultPackage.effectiveMonth}"/>月&nbsp;
			<label>执行城市：</label><select id="exeDefaultCity" style="width:100px;" onchange="$('#exeDefaultButton').attr('disabled', false);"></select>
			<input type="button" id="exeDefaultButton" value="设置默认套餐过期期限" onclick="exeDefault();"><br/>
			<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;延长免费套餐的期限：</label><input type="text" id="addDays" name="addDays" value="0"/>天&nbsp;
			<label>执行城市：</label><select id="exeDelayCity" style="width:100px;" onchange="$('#exeDelayButton').attr('disabled', false);"></select>
			<input type="button" id="exeDelayButton" value="执行延长" onclick="exeDelay();"/>
		</div> --%>
	</div>
	<div class="todo">
		<table id="packageTable" class="c" style="text-align:center">
			<thead>
				<tr>
					<th style="width:8%;">编号</th>
					<th style="widht:8%">免费套餐</th>
					<th style="width:6%;">房源</th>
					<th style="width:6%;">标签</th>
					<th style="width:6%;">VIP专区</th>
					<th style="width:6%;">预约刷新数</th>
					<th style="width:7%;">使用期限(月)</th>
					<th style="width:7%;">使用期限(天)</th>
					<th style="width:10%;">价格(元/季度)</th>
					<th style="width:10%;">单价(元/月)</th>
					<th style="width:12%;">操作</th>
					<th style="width:10%;">可见性</th>
					<th style="width:20%;">&nbsp;</th>
				</tr>
			</thead>
			<tbody align="center" >
			<c:choose>
				<c:when test="${not empty packages}">
					<c:forEach items="${packages}" var="packageItem" varStatus="var">
						<tr>
							<td>${var.index + 1}</td>
							<td>
								<c:choose>
									<c:when test="${packageItem.defaultFree == 1}">
										免费套餐
									</c:when>
									<c:otherwise>
										--
									</c:otherwise>
								</c:choose>
							</td>
							<td>${packageItem.houseAmount}</td>
							<td>${packageItem.labelAmount}</td>
							<td>${packageItem.vipPicNum}</td>
							<td>${packageItem.refreshHouseTimes}</td>
							<td>${packageItem.effectiveMonth}</td>
							<td>${packageItem.effectiveDays}</td>
							<td>${packageItem.price}</td>
							<td>${packageItem.avgPrice}</td>
							<td><a href="javascript:getPackage('${packageItem.id}');">修改</a>-<a href="javascript:delPackage('${cityId}','${packageItem.id}');">删除</a></td>
							<td>
								<c:choose>
									<c:when test="${packageItem.defaultValue == '1'}">
										经纪人后台、CRM系统
									</c:when>
									<c:when test="${packageItem.defaultValue == '0'}">
										CRM系统
									</c:when>
									<c:otherwise>
										个别经纪人后台、CRM系统
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${packageItem.defaultValue == '1' || packageItem.defaultValue == '2'}">
										${packageItem.packageName}
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="8">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
	<div id="divAdd">
		<input type="hidden" id="packageCityId" value="${cityId}"/>
		<c:choose>
			<c:when test="${requestScope.defaultPackage != null}">
				<p><span class="regtlx">免费套餐:</span><input type="checkbox" id="defaultFree" disabled="disabled"/></p>
			</c:when>
			<c:otherwise>
				<p><span class="regtlx">免费套餐:</span><input type="checkbox" id="defaultFree"/></p>
			</c:otherwise>
		</c:choose>
		<p><span class="regtlx">房源:</span><input type="text" id="houseAmount" style="width: 169px;"/></p>
		<p><span class="regtlx">标签:</span><input type="text" id="labelAmount" style="width: 169px;"/></p>
		<p><span class="regtlx">VIP专区:</span><input type="text" id="vipPicNum" style="width: 169px;"/></p>
		<p><span class="regtlx">预约刷新次数:</span><input type="text" id="refreshHouseTimes" style="width: 169px;"/></p>
		<p><span class="regtlx">价格:</span><input type="text" id="price" style="width: 169px;"/></p>
		<p><span class="regtlx">使用期限:</span><input type="text" id="effectiveMonth" style="width: 155px;"/><span>月</span></p>
		<p><span class="regtlx">使用期限:</span><input type="text" id="effectiveDays" style="width: 155px;"/><span>天</span></p>
		<p><span class="regtlx">排序:</span><input type="text" id="orderIndex" style="width: 169px;"/></p>
		<p>
			<span class="regtlx">默认显示:</span>
			<input type="checkbox" id="defaultValue" onclick="enablePackageName('defaultValue', '1');"/><label for="defaultValue">经纪人后台、CRM系统</label><br/>
			<span class="regtlx">&nbsp;</span><input type="checkbox" id="defaultValue2" onclick="enablePackageName('defaultValue2', '0');"/><label for="defaultValue2">CRM系统</label><br/>
			<span class="regtlx">&nbsp;</span><input type="checkbox" id="defaultValue3" onclick="enablePackageName('defaultValue3', '2');"/><label for="defaultValue3">个别经纪人后台、CRM系统</label>
		</p>
		<p><span class="regtlx">名称:</span><input type="text" id="packageName" disabled="disabled" style="width: 169px;"/></p>
	</div>
	<div id="divEdit">
		<input type="hidden" id="editPackageCityId" value="${cityId}"/>
		<input type="hidden" id="editId"/>
		<input type="hidden" id="defaultPackageId" value="${requestScope.defaultPackage.id}"/>
		<c:choose>
			<c:when test="${requestScope.defaultPackage != null}">
				<p><span class="regtlx">免费套餐：</span><input type="checkbox" id="editDefaultFree" disabled="disabled"/></p>
			</c:when>
			<c:otherwise>
				<p><span class="regtlx">免费套餐：</span><input type="checkbox" id="editDefaultFree" /></p>
			</c:otherwise>
		</c:choose>
		
		<p><span class="regtlx">房源：</span><input type="text" id="editHouseAmount"  style="width: 169px;"/></p>
		<p><span class="regtlx">标签：</span><input type="text" id="editLabelAmount" style="width: 169px;"/></p>
		<p><span class="regtlx">VIP专区：</span><input type="text" id="editVipPicNum" style="width: 169px;"/></p>
		<p><span class="regtlx">预约刷新次数：</span><input type="text" id="editRefreshHouseTimes" style="width: 169px;"/></p>
		<p><span class="regtlx">价格：</span><input type="text" id="editPrice" style="width: 169px;"/></p>
		<p><span class="regtlx">使用期限：</span><input type="text" id="editEffectiveMonth" style="width: 155px;"/><span>月</span></p>
		<p><span class="regtlx">使用期限：</span><input type="text" id="editEffectiveDays" style="width: 155px;"/><span>天</span></p>
		<p><span class="regtlx">排序：</span><input type="text" id="editOrderIndex" style="width: 169px;"/></p>
		<p>
			<span class="regtlx">默认显示:</span>
			<input type="checkbox" id="editDefaultValue" onclick="enablePackageName2('editDefaultValue', '1');"/><label for="editDefaultValue">经纪人后台、CRM系统</label><br/>
			<span class="regtlx">&nbsp;</span><input type="checkbox" id="editDefaultValue2" onclick="enablePackageName2('editDefaultValue2', '0');"/><label for="editDefaultValue2">CRM系统</label><br/>
			<span class="regtlx">&nbsp;</span><input type="checkbox" id="editDefaultValue3" onclick="enablePackageName2('editDefaultValue3', '2');"/><label for="editDefaultValue3">个别经纪人后台、CRM系统</label>
		</p>
		<p><span class="regtlx">名称：</span><input type="text" id="editPackageName" style="width: 169px;"/></p>
	</div>
</form>
<script type="text/javascript">
	var cityId = ${requestScope.cityId};
	var delResult = '${requestScope.delResult}';
	$(function(){
		getDist("city");
		if(cityId >= 0) {
			setSelected("city",cityId);
		}
		
		if(delResult != null && delResult != '') {
			alert(delResult);
		}
	});
</script>