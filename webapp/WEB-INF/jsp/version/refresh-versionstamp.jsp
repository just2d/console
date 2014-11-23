<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
<!--
function refresh(){
	$.ajax({
		type : "post",
		async : false,
		url : "/refresh-versionstamp",
		dataType : "json",
		data : $("#version").serialize() ,
		success : function(data) {
			if(data.error){
				alert(data.error);
				return;
			}else{
				alert("刷新成功");
			}
		}
	});
}
function selectAll(){
	$("input[name='url']").attr("checked",true);   
}
function unSelectAll(){
	$("input[name='url']").attr("checked",false);   
}

$(function(){
	$('#addVersionDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "添加项目",
		modal : true,
		resizable : false
	});
	$('#editVersionDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "编辑项目",
		modal : true,
		resizable : false
	});
});

function addVersion() {
	$("#addVersionDiv").dialog("option", "position", "center");
	$('#addVersionDiv').dialog('open');
}
function edit(id) {
	$.ajax({
		type : "get",
		async : false,
		url : "/refresh-versionstamp/edit/"+id,
		dataType : "json",
		success : function(data) {
			if(data.error){
				alert(data.error);
				return;
			}
			$("#editVersionId").val(data.pv.id);
			$("#editName").val(data.pv.name);
			$("#editVersionUrl").val(data.pv.versionUrl);
		}
	});
	$("#editVersionDiv").dialog("option", "position", "center");
	$('#editVersionDiv').dialog('open');
}

function checkAddVersionInfo(){
	var isCommit = true;
	var name = $('#name').val();
	var versionUrl = $("#versionUrl").val();
	if(name == "") {
		$("#nameSp").show();
		isCommit = false;
	} else {
		$("#nameSp").hide();
	}
	
	
	if(versionUrl=="") {
		 $("#versionUrlSp").show();
		 isCommit = false;
	} else {
		$("#versionUrlSp").hide();
	}
	 
	$.ajax({
		type : "post",
		async : false,
		url : "/refresh-versionstamp/add",
		dataType : "json",
		data : {"name":name,"versionUrl" : versionUrl},
		success : function(data) {
			if(data.error){
				alert(data.error);
				 isCommit = false;
				return;
			}else{
				alert(data.info);
				window.location='/refresh-versionstamp';
			}
		}
	});
}

function confirmDelVersion(id) {
	if(confirm("确定要删除该项目信息吗?")) {
		$.ajax({
			type : "post",
			async : false,
			url : "/refresh-versionstamp/delete/" + id,
			dataType : "json",
			success : function(data) {
				if(data.error){
					alert(data.error);
					 isCommit = false;
					return;
				}else{
					alert(data.info);
					window.location='/refresh-versionstamp';
				}
			}
		});
	}
}
function checkEditVersionInfo(){
	var id = $("#editVersionId").val();
	var name = $('#editName').val();
	var versionUrl = $("#editVersionUrl").val();
	if(name == "") {
		$("#editnameSp").show();
	} else {
		$("#editnameSp").hide();
	}
	
	
	if(versionUrl=="") {
		$("#editVersionUrlSp").show();
	} else {
		$("#editVersionUrlSp").hide();
	}

	$.ajax({
		type : "post",
		async : false,
		url : "/refresh-versionstamp/edit/"+id,
		dataType : "json",
		data : {"name":name,"versionUrl" : versionUrl},
		success : function(data) {
			if(data.error){
				alert(data.error);
				 isCommit = false;
				return;
			}else{
				alert(data.info);
				window.location='/refresh-versionstamp';
			}
		}
	});
}

//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：版本控制&gt;刷新版本号  </div> 
<div style="width: 100%;">
	<label style="color:#000;line-height:25px;width:100%;font-size:16px;font-weight:bold">刷新版本号</label>&nbsp;&nbsp;<input type="button" value="添加项目" onclick="addVersion()">
	<form name="version" action="/refresh-versionstamp" id="version" method="post" style="width:100%">
		<input type="hidden" name="id" id="userId" value="${user.id }" />
		<table border="0" cellpadding="3" cellspacing="1" align="center">
			<tr><td>版本号:</td><td>   <input name="ver" id="ver" /> </td></tr>
			<tr><td>项目:</td><td> 
			  <table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
			
			<c:forEach items="${versions }" var="version">
			<tr>
			<td align="left" style="border: solid 1px #a0c6e5; height: 20px;">
			    <input type="checkbox" name="url" value="${version.versionUrl }"><a herf="javascript:;" style="cursor: pointer;" onclick="edit(${version.id});" >${version.name }</a>
			</td>
			<td style="border: solid 1px #a0c6e5; height: 20px;">
			&nbsp; <input type="button" onclick="confirmDelVersion(${version.id})" value="删除">
			</td>
			</tr>
			</c:forEach>
			</table>
			 </td></tr>
			 
			<tr><td colspan="2" align="center"><input type="button" onclick="selectAll();" value="全选" />&nbsp;&nbsp;<input type="button"   onclick="unSelectAll();" value="取消选择" />&nbsp;&nbsp;<input type="button" value="刷新" onclick="refresh();"/> </td></tr>
		</table>
	</form>
</div>


<div id="addVersionDiv" style="display: none">
	<label style="color:gray;">项目信息</label>
	<form name="role" action="/refresh-versionstamp/add" id="addRoleForm" method="post">
		<input type="hidden" name="addRoleInfo" id="addRoleInfo" />
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>名称:</td><td style="width:300px;"><input style="width: 300px;"  type="text"  id="name" name="name" ></td><td><span id="nameSp" class="action_po_top">名称不能为空</span></td></tr>
			<tr><td><span class="important">*</span>接口路径:</td><td><input style="width: 300px;"  type="text" id="versionUrl" name="versionUrl" ></td><td><span id="versionUrlSp" class="action_po_top">路径不能为空</span></td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkAddVersionInfo();"/><input type="reset" value="重置"/></td></tr>
		</table>
	</form>
</div>
<div id="editVersionDiv" style="display: none">
	<label style="color:gray;">编辑项目</label>
	<form name="role" action="/refresh-versionstamp/edit" id="editVersionForm" method="post">
		<input type="hidden" name="id" id="editVersionId" />
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>名称:</td><td style="width:300px;"><input style="width: 300px;"  type="text"  id="editName" name="name" ></td><td><span id="editnameSp" class="action_po_top">名称不能为空</span></td></tr>
			<tr><td><span class="important">*</span>接口路径:</td><td><input style="width: 300px;"  type="text" id="editVersionUrl" name="versionUrl" ></td><td><span id="editVersionUrlSp" class="action_po_top">路径不能为空</span></td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkEditVersionInfo();"/></td></tr>
		</table>
	</form>
</div>