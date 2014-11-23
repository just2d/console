<div class="rel"><div id="editor_dialogs" class="editor-dialogs" style="width:500px; "></div></div>
	   <script type="text/plain" id="editor" style="width:1000px">
		${post.message}
		</script>
		<script language="JavaScript">
		// 输出编辑器模板内容
		var ehtml =  $("#editor").html();
		// 引入ueditor
		//var js_editor = Script.add("http://"+window.location.host+"/bbsflash/editor_bbs/editor.js");
		var js_editor = Script.add("http://static.taofang.com/flash/editor_bbs/editor.js");
		
		Script.on(js_editor,function(){ // 编辑器加载后
			window.editor = new UE.ui.Editor({UEDITOR_HOME_URL:"http://static.taofang.com/flash/editor_bbs/"});
			editor.render('editor');
			editor.ready(function(){
				
				editor.setContent(ehtml);
				if(${empty sessionScope.user}){
					editor.setDisabled();
				}
			});
			
		});
		</script>