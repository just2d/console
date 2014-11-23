<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.photolist li img {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #CCCCCC;
    display: block;
    height: 100px;
    margin: 3px auto;
    width: 150px;
}
</style>
<div class="estatephoto">
	<ul class="photolist clearfix">
		<c:forEach items="${layoutList}" var="layoutPhoto">
	 		<li>
	 			<a target="_blank" href="${layoutPhoto.sPhoto}" class="showlarge"> 
	 				<img src="${layoutPhoto.sPhoto}">
	 			</a>
	 		</li>	
	 	</c:forEach>
		
	</ul>
</div>
