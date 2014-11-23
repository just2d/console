<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content_left">

	<c:if test="${sessionScope.sessionUser.isPass('/user/manager') }">
		<h1 style=" border-top:none">用户管理</h1>
			<div class="menu_line">
				<ul>
					<c:if test="${sessionScope.sessionUser.isPass('/user/list') }">
							<li><a href="/user/list">用户管理</a></li>
					</c:if>	
					<c:if test="${sessionScope.sessionUser.isPass('/role/list') }">
							<li><a href="/role/list">角色管理</a></li>
					</c:if>	
					<c:if test="${sessionScope.sessionUser.isPass('/function/main') }">
								<li><a href="/function/main">功能菜单</a></li>
					</c:if>	
						
				
				</ul>
			</div>
	</c:if>
	<c:if test="${sessionScope.sessionUser.isPass('/feedBack/list') }">
		<h1 style="border-top:none;">反馈信息管理</h1>
		<div class="menu_line">
			<ul>
				<c:if test="${sessionScope.sessionUser.isPass('/feedBack/list') }">
						<li><a href="/feedBack/list">反馈信息</a></li>
				</c:if>	
			</ul>
		</div>
	</c:if>		
	<c:if test="${sessionScope.sessionUser.isPass('/history/manager') }">
		<h1 style="border-top:none;">审核记录管理</h1>
		<div class="menu_line">
			<ul>
				<c:if test="${sessionScope.sessionUser.isPass('/auditHistory/menu/all') }">
						<li><a href="/auditHistory/list/1">审核记录</a></li>
				</c:if>	
				<c:if test="${sessionScope.sessionUser.isPass('/auditHistory/menu/per') }">
						<li><a href="/auditHistory/list/1?per=1">个人审核记录</a></li>
				</c:if>	
				<c:if test="${sessionScope.sessionUser.isPass('/auditHistory/menu/statist') }">
						<li><a href="/auditHistory/statist">审核统计</a></li>
				</c:if>	
			<!-- 	<c:if test="${sessionScope.sessionUser.isPass('/rate/audit/houseQuality') }">
					<li><a href="/rate/auditQualityStatis">房源审核质量检查</a></li>
				</c:if>
				 -->
					
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.sessionUser.isPass('/agent/manager') }">
		<h1 style="border-top:none;">经纪人管理</h1>
		<div class="menu_line">
			<ul>
					<c:if test="${sessionScope.sessionUser.isPass('/auditHistory/menu/agent/photo') }">
								<li><a href="/agentVerify/photoList">经纪人审核</a></li>
					</c:if>	
					<c:if test="${sessionScope.sessionUser.isPass('/agentManage/list') }">
								<li><a href="/agentManage/list" >经纪人管理</a></li>
					</c:if>	
					<c:if test="${sessionScope.sessionUser.isPass('/agentVerify/rejectReason') }">
							<li><a href="/agentVerify/rejectReason" >拒绝理由管理</a></li>
					</c:if>	
					<c:if test="${sessionScope.sessionUser.isPass('/auditHistory/menu/phone') }">
						<li><a href="/agentVerify/verifyMobileList/1">人工手机号码验证</a></li>
					</c:if>	
			</ul>
		</div>
	</c:if>	
	
	<c:if test="${sessionScope.sessionUser.isPass('/tfuser/manager') }">
		<h1 style="border-top:none;">个人用户管理</h1>
		<div class="menu_line">
			<ul>
					<c:if test="${sessionScope.sessionUser.isPass('/tfuserManage/list') }">
								<li><a href="/tfuserManage/list">个人用户管理</a></li>
					</c:if>	
			</ul>
		</div>
	</c:if>	
	
	<c:if test="${sessionScope.sessionUser.isPass('/resale/manager') }">
	
	<h1 style=" border-top:none">房源管理</h1>
	<div class="menu_line">
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/resale/verify/list') }">
				<li><a href="/resale/verify/allresale/list/home">二手房管理</a></li>
			</c:if>	
			<c:if test="${sessionScope.sessionUser.isPass('/rent/verify/list') }">
				<li><a href="/rent/verify/allrent/list/home">出租房管理</a></li>
			</c:if>	
			<c:if test="${sessionScope.sessionUser.isPass('/audit/vcr/list/1/1') }">
				<li><a href="/audit/vcr/list/1/1">视频审核</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/audit/vcr/history/0/1/0/0') }">
				<li><a href="/audit/vcr/history/0/1/0/0">视频审核历史 </a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/resale/eval/list') }">
				<li><a href="/resale/eval/list">二手房星级评价</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/rent/eval/list') }">
				<li><a href="/rent/eval/list">租房星级评价</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
	
	<c:if test="${sessionScope.sessionUser.isPass('/audit/photo/setting') }">
	
	<h1 style=" border-top:none">房源抽取</h1>
	<div class="menu_line">
		<ul>
				<li><a href="/audit/photo/manelist/0/2">图片名单设置</a></li>
				<li><a href="/audit/photo/setting/1">图片审核设置</a></li>
		</ul>
	</div>
	</c:if>
	
	<c:if test="${sessionScope.sessionUser.isPass('/resale58/manager') }">
	<h1 style=" border-top:none">58房源管理</h1>
	<div class="menu_line">
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/resale58/allresalesearch/list') }">
				<li><a href="/resale58/allresalesearch/list">二手房管理</a></li>
			</c:if>	
			<c:if test="${sessionScope.sessionUser.isPass('/rent58/allrentsearch/list') }">
				<li><a href="/rent58/allrentsearch/list">出租房管理</a></li>
			</c:if>	
		</ul>
	</div>
	</c:if>	
	<c:if test="${sessionScope.sessionUser.isPass('/estate/manager') }">
	<h1 style=" border-top:none">小区管理</h1>
	<div class="menu_line">
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/estate/list') }">
					<li><a href="${contextPath}/estate/list?authStatus=0&page.pageNo=0&cityId=1">小区管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/estate/layout') }">
					<li><a href="${contextPath}/estate/photoList?cityId=1">小区照片管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/estate/unionLog') }">
					<li><a href="${contextPath}/estate/unionLog">小区合并日志</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/estate/add') }">
					<li><a href="${contextPath}/estate/add">新建小区</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/estate/mapping/list') }">
					<li><a href="${contextPath}/estate/mapping/list">映射管理</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
	
	<c:if test="${sessionScope.sessionUser.isPass('/estateupc/manager') }">
	<h1 style=" border-top:none">小区完善管理</h1>
	<div class="menu_line">
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/estateupc/getupctext') }">
					<li><a href="${contextPath}/estateupc/getupctext">小区完善审核</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/estateupcaudithistory/list') }">
					<li><a href="${contextPath}/estateupcaudithistory/list?actionType=1&cityId=1">审核纪录</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
	<c:if test="${sessionScope.sessionUser.isPass('/estateexpert/list') }">
	<h1 style=" border-top:none">小区专家管理</h1>
	<div class="menu_line">
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/estateexpert/list') }">
					<li><a href="${contextPath}/estateexpert/list">小区专家管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/estateexpert/add') }">
					<li><a href="${contextPath}/estateexpert/add">添加小区专家</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>		
	<c:if test="${sessionScope.sessionUser.isPass('/statis/manager') }">
	<h1 style=" border-top:none">数据管理</h1>
	<div class="menu_line">
		<ul>
		
			<c:if test="${sessionScope.sessionUser.isPass('/statis/webStatis/getDayCount') }">
					<li><a href="/statis/webStatis/getDayCount">网站数据统计 </a></li>
			</c:if>	
			<%-- <c:if test="${sessionScope.sessionUser.isPass('/statis/houseQuality/getcount') }">
					<li><a href="/statis/houseQuality/getcount">房源质量统计</a></li>
			</c:if>	 --%>
			<c:if test="${sessionScope.sessionUser.isPass('/statis/estate/getcount') }">
				<li><a href="/statis/estate/getcount">小区房源数据</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/statis/houselabel/getcount') }">
				<li><a href="/statis/houselabel/getcount">标签统计</a></li>
			</c:if>	
			<c:if test="${sessionScope.sessionUser.isPass('/rate/audit/list') }">
					<li><a href="/rate/auditList">房源图片审核统计</a></li>
			</c:if>	
			<%-- <c:if test="${sessionScope.sessionUser.user.userName == 'taofangadmin' }">
					<li><a href="/rate/auditList">房源图片审核统计</a></li>
			</c:if>	 --%>
			<c:if test="${sessionScope.sessionUser.isPass('/report/agent') }">
					<li><a href="http://report.taofang.com" target="_blank">经纪人统计</a></li>
			</c:if>	
		</ul>
	</div>
	</c:if>	
	<c:if test="${sessionScope.sessionUser.isPass('/control/manager') }">
	<h1 style=" border-top:none">信息质量控制</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/control/sensitiveWord') }">
				<li><a href="/control/sensitiveWord">敏感词管理</a></li>
			</c:if>	
		</ul>
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/control/blacklist') }">
				<li><a href="/control/blacklist">黑名单管理</a></li>
			</c:if>	
		</ul>
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/control/linkword/list') }">
				<li><a href="/control/linkword/list">内链关键词</a></li>
			</c:if>	
		</ul>
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/control/housedesclinkword/list') }">
				<li><a href="/control/housedesclinkword/list">房源描述关键词</a></li>
			</c:if>	
		</ul>
	</div>
	</c:if>
	 
	<c:if test="${sessionScope.sessionUser.isPass('/order/manager') }">
	<h1 style=" border-top:none">订单管理</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/order/successmanage') }">
				<li><a href="/order/successmanage">支付成功订单管理</a></li>
			</c:if>	
		</ul>
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/order/notsuccessmanage') }">
				<li><a href="/order/notsuccessmanage">未支付成功订单管理</a></li>
			</c:if>	
		</ul>
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/order/query') }">
				<li><a href="/order/query">后台用户明细查询</a></li>
			</c:if>	
		</ul>
	</div>
	</c:if>	
	 
	<c:if test="${sessionScope.sessionUser.isPass('/package/manage') }">
	<h1 style=" border-top:none">套餐管理</h1>
	<div class="menu_line">
		<ul>
			<c:if test="${sessionScope.sessionUser.isPass('/package/manage') }">
				<li><a href="/package/manage/list/0">套餐管理</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
	
	<c:if test="${sessionScope.sessionUser.isPass('/adv/advhouse') }">
		<h1 style=" border-top:none">广告房源管理</h1>
			<div class="menu_line">
				<ul>
						<li><a href="/adv/advhouse/search">房源管理</a></li>
				</ul>
			</div>
	</c:if>
	
	<c:if test="${sessionScope.sessionUser.isPass('/bbs/manager') }">
	<h1 style=" border-top:none">论坛管理后台</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/forum/role/list') }">
					<li><a href="/forum/role/list">角色管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/bbs/manager/forum') }">
					<li><a href="/bbs/manager/forum">板块管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/forumuser/list') }">
					<li><a href="/forumuser/list">版主管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/forum/black/list') }">
                <li><a href="/forum/black/list">黑名单管理</a></li>
            </c:if>
            

		</ul>
	</div>
	</c:if>	
	<c:if test="${sessionScope.sessionUser.isPass('/rate/manager') }">
	<h1 style=" border-top:none">列表页转化率统计</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/rate/menu/list') }">
					<li><a href="/rate/rateList">统计列表</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>	
	<c:if test="${sessionScope.sessionUser.isPass('/wenda/manager') }">
	<h1 style=" border-top:none">问答管理</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/wenda/question/list') }">
					<li><a href="/wenda/verify/1">问题审核</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/wenda/answer/list') }">
					<li><a href="/wenda/verify/2">回答审核</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/wenda/category/list') }">
					<li><a href="/wenda/category/list/0">分类管理</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/wenda/queryQuestionList') }">
					<li><a href="/wenda/queryQuestionList">问题列表</a></li>
			</c:if>
			<c:if test="${sessionScope.sessionUser.isPass('/wenda/queryAnswerList') }">
					<li><a href="/wenda/queryAnswerList">回答列表</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
	<c:if test="${sessionScope.sessionUser.isPass('/refresh-versionstamp') }">
	<h1 style=" border-top:none">项目版本</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/refresh-versionstamp') }">
					<li><a href="/refresh-versionstamp">刷新版本号</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
	<c:if test="${sessionScope.sessionUser.isPass('/sendmsg') }">
	<h1 style=" border-top:none">站内信</h1>
	<div class="menu_line">
		<ul>
		    <c:if test="${sessionScope.sessionUser.isPass('/sendmsg') }">
					<li><a href="/sendmsg">发送站内信</a></li>
			</c:if>
		</ul>
	</div>
	</c:if>
</div> 

