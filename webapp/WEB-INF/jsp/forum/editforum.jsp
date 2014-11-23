<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="/css/publish.css" type="text/css" />
<link rel="stylesheet" href="/css/layout.css" type="text/css" />
<script src="/js/forum/bbs.js" type="text/javascript"></script>

<div id="tab2" class="tabs">  

</div>

<div style="width: 100%;text-align: left;">当前位置：板块管理 &nbsp;&gt;板块编辑</div> 
 <div class="con2">
<form action="/bbs/manager/editforum" method="post"  id="eidtforum" >
<input  type="hidden" id="forumId" name="id" size="35" value="${newforum.id}"/>
<input  type="hidden" id="author" name="author" size="35" value="${sessionScope.sessionUser.user.chnName}"/>


                   <table  border="0" style="text-align: left;">
                      <tr>
                        <td ><span class="important">*</span>板块名称：</td>
                        <td ><input  type="text"  id="name" name="name" value="${newforum.name}"/></td>
	                 	<td><span id="nameSp" class="action_po_top">板块名称不能为空</span></td>
                      </tr>
                       <tr>
                        <td><span class="important">*</span><span class="wd334">选择城市：</span></td>
                       <td >
                      首字母:<select name="ch" id="ch" onchange='setCityInfo(this.value,"cityLocale")'>
	  			<option value="">-请选择-</option>
	  			<option value="a">A</option>
	  			<option value="b">B</option>
	  			<option value="c">C</option>
	  			<option value="d">D</option>
	  			<option value="e">E<J/option>
	  			<option value="f">F</option>
	  			<option value="g">G</option>
	  			<option value="h">H</option>
	  			<option value="i">I</option>
	  			<option value="j">J</option>
	  			<option value="k">K</option>
	  			<option value="l">L</option>
	  			<option value="m">M</option>
	  			<option value="n">N</option>
	  			<option value="o">O</option>
	  			<option value="p">P</option>
	  			<option value="q">Q</option>
	  			<option value="r">R</option>
	  			<option value="s">S</option>
	  			<option value="t">T</option>
	  			<option value="u">U</option>
	  			<option value="v">V</option>
	  			<option value="w">W</option>
	  			<option value="x">X</option>
	  			<option value="y">Y</option>
	  			<option value="z">Z</option>
	     </select>
	  城市:<select name="cityId" id="cityLocale"  onchange="changeCityForum(this.value,0,${newforum.id},false)">
	  		<option value="">-请选择-</option>
			<c:if test="${cityName ne null}">
	 			<option value="${condition.cityId}" selected="selected">${cityName}</option>
	 		</c:if>
	     </select>
					
					    
                       </td>
                        </tr>
                      <tr>
                        <td ><span class="important">*</span>板块标题：</td>
                        <td ><input  type="text"  id="title" name="title" value="${newforum.title}" /></td>
	                 	<td><span id="titleSp" class="action_po_top">板块标题不能为空</span></td>
                      </tr>
                                            <tr>
                        <td ><span class="important">*</span>板块描述：</td>
                        <td ><input  type="text"  id="descript" name="descript" value="${newforum.descript}"/></td>
	                 	<td><span id="descriptSp" class="action_po_top">板块描述不能为空</span></td>
                      </tr>
                         <tr>
                        <td><span class="important">*</span>关键词：</td>
                        <td ><input  type="text"  id="keyword" name="keyword" value="${newforum.keyword}"/></td>
	                   	<td><span id="keywordSp" class="action_po_top">关键词不能为空</span></td>
                      </tr>
                      <tr>
					        <td class="wd334">板块属性：</td>
                        <td >
                            <select name="visibleRole" id="visibleRole"  onchange="changeRoleGetForum2(this.value,0,${newforum.id},false)">
				  			<option value="1">普通用户</option>
				  			<option value="2">经纪人</option>
			             </select>
                        </td>
					</tr>
						<tr>
					     <td >上级板块：</td>
                        <td class="wd334">
		                 <select name="idAndForumType" id="forumList" onchange="getfirstforum(this.value)">
		                    <option value="2">请选择</option>
			              	
			            </select>
                       </td>
					</tr>
					<tr>
                        <td ><span class="important">*</span>显示顺序</td>
                        <td ><input  type="text"  id="displayOrder" name="displayOrder" value="${newforum.displayOrder}" /></td>
	                 	<td><span id="displayOrderSp" class="action_po_top">显示顺序不能为空</span></td>
                      </tr>
	                <tr>
	                <td></td>              
                       <td></td>
                       <td></td>
	        		<td colspan="2" >
	           		 <input type="submit" name="button" class="btn" value="发布确认" onclick='checkInfo("update");'/>
	           		&nbsp; <input type="button"  class="btn" value="返回" onClick='javaScript:window.location.href="/bbs/manager/forum"'>
 						 </td>
 					<td>
	           		</td>
	                </tr> 
              </table>

</form>
</div>
<script type="text/javascript">
$("#ch").val("${ch}");
setCityInfo("${ch}","cityLocale");
$("#cityLocale").val("${newforum.cityId}");
$("#visibleRole").val("${newforum.visibleRole}");
     changeCityGetForum("${newforum.cityId}","${newforum.visibleRole}","${newforum.parentId}-${newforum.forumType-1}",${newforum.id});
</script>
