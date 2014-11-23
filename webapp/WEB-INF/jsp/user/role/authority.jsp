<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/ztree/jquery.ztree.js"></script>

<link rel="stylesheet" href="/js/ztree/zTreeStyle/zTreeStyle.css" type="text/css" />
<SCRIPT type="text/JavaScript">
 var zTree;
	var demoIframe;

	var setting = {
		isSimpleData: true,
		checkable : true,
		treeNodeKey: "id",
		treeNodeParentKey: "pId",
		showLine: true,
		root:{ 
			isRoot:true,
			nodes:[]
		}
	};
	zNodes =${ZtreeNodes};

	$(document).ready(function(){
		setting.expandSpeed = ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast";
		zTree = $("#tree").zTree(setting, zNodes);
	});
  
  function check() {
		var tmp = zTree.getCheckedNodes();
		var ids = "";
			for (var i=0; i<tmp.length; i++) {
				ids += "_"+tmp[i].id;
			}
			$("#functionids").val(ids);
			$("#editRoleForm").submit();
	}
 
//-->

  </SCRIPT>
<div style="width: 100%;text-align: left;">当前位置：用户管理 &gt; 角色管理  &gt; 权限设置 </div> 

	<label style="color:gray;">设置角色权限</label>
	<form name="role" action="/role/authority/save" id="editRoleForm" method="post">
		<input type="hidden" name="id" id="roleId" value="${role.id }" />
		<input type="hidden" name="ids" id="functionids"   />
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;">角色名称:</td><td style="width:200px;">${role.name }</td></tr>
			<tr><td>角色权限:</td><td> <ul id="tree" class="tree" ></ul> </td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="保存" onclick="check();"/> </td></tr>
		</table>
	</form>
