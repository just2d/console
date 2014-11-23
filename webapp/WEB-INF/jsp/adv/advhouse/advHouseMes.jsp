<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
       <p>
        <label class="lbl">投放的网站:</label>
	    <select class="cs" name="website" id="website" style="width: 150px;">
		<option value="1">58.com</option>
	   </select>
	    <select class="cs" name="websitePos" id="websitePos" style="width: 170px;">
		<option value="1">二手房</option>
		<option value="2">租房-整租</option>
		<option value="3">租房-合租</option>
	   </select>
	   </p>
	   <p>
	     <label class="lbl">房源的类别:</label>
	    <select class="cs" name="housetype" id="housetype" style="margin-left: -170px;width: 150px;">
		<option value="1">二手房</option>
		<option value="2">租房-整租</option>
		<option value="3">租房-合租</option>
	   </select>
	   </p>
	   <p>
	      <label class="lbl">展示时间:</label>
		 开始时间:<input type="text" id="sdated" name="sdated" style="width:100px;" maxLength="10"   onclick="InitSelectDate(1,this,'yyyy-MM-dd','起始日期')" readonly="readonly" />日
		    <select class="cs" id="sdateh" name="sdateh" style="width:70px;">
		    <c:forEach  var="hour"   begin="0"  end="23"  >
		     <c:if test="${hour<10}"> <option value="0${hour}">0${hour}时</option></c:if>
		        <c:if test="${hour>=10}"> <option value="${hour}">${hour}时</option></c:if>
		      
		      </c:forEach>
	        </select><span style="width:50px;">&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
	        </p>
        <p> <label class="lbl">&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		 结束时间:<input type="text" id="edated" name="edated" style="width:100px;" maxLength="10"  onclick="InitSelectDate(1,this,'yyyy-MM-dd','结束日期')" readonly="readonly" />日
		 <select class="cs" id="edateh" name="edateh" style="width:70px;">
		    <c:forEach  var="hour"   begin="0"  end="23"  >
		       <c:if test="${hour<10}">  <option value="0${hour}">0${hour}时 </option></c:if>
		        <c:if test="${hour>=10}"><option value="${hour}">${hour}时 </option></c:if>
		    </c:forEach>
	      </select><span style="width:50px;">&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
	   </p>
	    <p>
	    <label class="lbl">广告的位置:</label>
	    <select class="cs" name="cityId" id="city" style="width: 100px;">
		   <option value="-2">请选择城市</option>
	    </select>
	    <select class="cs" id="dist" name="distId" style="width: 100px;">
		    <option value="-2">请选择区域</option>
	    </select>
	    <select class="cs" id="block" name="blockId" style="width: 100px;">
		    <option value="-1">请选择商圈</option>
	    </select>
	
		<p><label class="lbl">推荐的房源:</label>
		 <input type="text" id="f1" name="f1" style="width:100px;"/>
		 <input type="text" id="f2" name="f2" style="width:100px;"/>
		 <input type="text" id="f3" name="f3" style="width:100px;"/></p>
		  <p ><label class="lbl">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		 <input type="text" id="f4" name="f4" style="width:100px;"/>
		 <input type="text" id="f5" name="f5" style="width:100px;"/>
		 <input type="text" id="f6" name="f6" style="width:100px;"/>
		 </p>
		 	