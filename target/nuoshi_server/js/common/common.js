String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g,"");
	};
function openImgWindow(url){
	window.open(url, "img", "scrollbars=yes");
}
function checkAll(obj){
	var ids = document.getElementsByName("ids");
	for(var i = 0;i<ids.length;i++){
			ids[i].checked = obj.checked;
	}
}
function postform(url,params){
	var temp = document.createElement("form");
	temp.action = url;
	temp.method= "post";
	temp.style.display = "none";
	for(var x in params) {
		var opt = document.createElement("textarea");
		opt.name = x;
		opt.value = params[x];
		temp.appendChild(opt);
	}
	document.body.appendChild(temp);
	temp.submit();
}
function display(boxid){
	var box  = document.getElementById(boxid);
	if(box.style.display!="block"){
		box.style.display="block"; 
	}
  }
function disappear(boxid){
	var box  = document.getElementById(boxid);
	if(box.style.display!="none"){
		box.style.display="none"; 
	}
 }
 
 //统计有中文的字符串长度   equivalently  表示一个汉字相当于多少个英文
function getStrLength(str,equivalently){
	var chLength=0;
	if (str == null||str=="") { 
		return 0; 
	} 
	if(equivalently==null){
		equivalently=1;
	}
	if(equivalently<=0){
		equivalently=1;
	}
	else { 
		chLength = str.replace(/[\u0000-\u00ff]/g, "").length;
		if(chLength!=null&&equivalently>0){
			chLength=chLength*(equivalently-1);
		}
		return (str.length + chLength); 	
	} 
	return 0;
}