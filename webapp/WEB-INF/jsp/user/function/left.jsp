
<iframe id="menu" name="menu" frameborder="0" scrolling="no" width="180px"   height="100%"    src="/function/menu"  ></iframe>
<script type="text/javascript">
<!--


function reinitIframe(){

var iframe = document.getElementById("menu");

try{

iframe.height =  iframe.contentWindow.document.documentElement.scrollHeight;

}catch (ex){}

}

window.setInterval("reinitIframe()", 200);

//-->
</script>