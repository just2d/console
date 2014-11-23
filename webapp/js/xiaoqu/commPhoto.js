
/**
 * 移动到精选库
 * @param {Object} cls
 */
function mv(cls, selNum) {
	var selIds = "";
	var unselIds = "";
	selIds = getSelIds(cls);
	//获得选中的复选框个数
	var num = $("input[class=" + cls + "]:checked").length;
	if (num > 0) {
		if (selNum < num) {
			alert("目前最多可选择" + selNum + "张图片进入精选库");
			return;
		} else {
			unselIds = getUnSelIds(cls);
			window.location.href = contextPath
					+ "/estate/commPhoto/moveToUsing?toUsingIds=" + selIds
					+ "&unUsingIds=" + unselIds + url;
		}
	}
}
function del(cls) {
	var num = $("input[class=" + cls + "]:checked").length;
	if (num > 0) {
		var selIds = getSelIds(cls);
		window.location.href = contextPath
				+ "/estate/commPhoto/delBackup?unUsingIds=" + selIds + url;
	}
}

function delToBackup(cls, category) {
	var num = $("input[class=" + cls + "]:checked").length;
	if (num > 0) {
		var selIds = getSelIds(cls);
		//category:1户型图,3:小区图.
		window.location.href = contextPath
				+ "/estate/commPhoto/mvToBackup?unUsingIds=" + selIds + url
				+ "&category=" + category;
	}
}

