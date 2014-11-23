<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="/css/publish.css" type="text/css" />
<link rel="stylesheet" href="/css/layout.css" type="text/css" />
<script src="/js/forum/bbs.js" type="text/javascript"></script>
<script type="text/javascript">
//ajax获取城市、区域 
$(function(){
	$(".firtsforum").hide();
});
</script> 
<div style="width: 100%;text-align: left;">当前位置：板块管理 &nbsp;&gt;板块添加</div> 
 <div class="con2">
<form action="/bbs/manager/saveforum" method="post"  id="addforum" >
    <input  type="hidden" id="author" name="author" size="35" value="${sessionScope.sessionUser.user.chnName}"/><br/>
    <input type="hidden"  name="userRole"  id="userRole" value=""/>
    <table  border="0" style="text-align: left;">
      <tr>
        <td ><span class="important">*</span>板块名称：</td>
        <td ><input  type="text"  id="name" name="name" /></td>
  	    <td><span id="nameSp" class="action_po_top">板块名称不能为空</span></td>
      </tr>
      <tr>
       <td><span class="important">*</span><span class="wd334">选择城市：</span></td>
       <td><select id="cityId" name="cityId"   onchange="changeCityForum(this.value,0,0,false)">
   	     <c:forEach items="${citys}" var="city">
            <option value="${city.key}">${city.value}</option>
         </c:forEach>
            <option value="-1">其他</option>
      </select></td>
     </tr>
     <tr>
       <td ><span class="important">*</span>板块标题：</td>
       <td ><input  type="text"  id="title" name="title" /></td>
       <td><span id="titleSp" class="action_po_top">板块标题不能为空</span></td>
     </tr>
     <tr>
       <td ><span class="important">*</span>板块描述：</td>
       <td ><input  type="text"  id="descript" name="descript" /></td>
       <td><span id="descriptSp" class="action_po_top">板块描述不能为空</span></td>
     </tr>
     <tr>
	   <td><span class="important">*</span>关键词：</td>
	   <td ><input  type="text"  id="keyword" name="keyword" /></td>
	   <td><span id="keywordSp" class="action_po_top">关键词不能为空</span></td>
     </tr>
     <tr>
        <td class="wd334">板块属性：</td>
        <td >
            <select name="visibleRole" id="visibleRole"  onchange="changeRoleGetForum2(this.value,0,0,false)">
 			    <option value="1">普通用户</option>
 			    <option value="2">经纪人</option>
           </select>
        </td>
     </tr>
	 <tr>
        <td >上级板块：</td>
        <td class="wd334">
          <input type="hidden"  name="forumname"  id="forumname"  value=""/>
          <select name="idAndForumType" id="forumList" onchange="changeForum()">
            <option value="0">请选择</option>
          </select>
        </td>
     </tr>
     <tr>
       <td ><span class="important">*</span>显示顺序</td>
       <td ><input  type="text"  id="displayOrder" name="displayOrder" value="" /></td>
       <td><span id="displayOrderSp" class="action_po_top">显示顺序不能为空</span></td>
     </tr>
     <tr>
       <td></td>
	   <td colspan="2" >
  		 <input type="button"  class="btn" value="提交" onclick='checkInfo("add");'/>&nbsp; 
  		<input type="button"  class="btn" value="返回" onClick='javaScript:window.location.href="/bbs/manager/forum"'>
  	   </td>
     </tr> 
   </table>
</form>
</div>

<script type="text/javascript">
     changeCityGetForum(-1,1,0,0,false);
</script>

