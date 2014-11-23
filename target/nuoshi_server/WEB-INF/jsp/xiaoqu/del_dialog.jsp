<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 删除小区弹出框 -->
 <div id="uploadlp">
    <form name="role" action="${contextPath}/msg/sendestateDelMsg" method="post" >
        <input type="hidden" name="createUserId" id="createUserId"/>
        <table border="0" cellpadding="3" cellspacing="1">
            <tr><td>亲:小区已经被删除了,需要发送站内信吗?发送,请在文本框输入正确的小区名.并点击确定按钮,要是不想发了,点击取消按钮吧!</td></tr>
            <tr><td align="center" colspan="2"><textarea name="content" cols="35" rows="4" id="content">您申请的小区没有获得通过，已经删除，该小区正确的小区名称为:</textarea></td></tr>
            <tr>
            <td align="center" colspan="2">
            <input type="button" value="确定" id="sendBt" />
            <input type="button" value="取消" id="cannelBt" />
            </td></tr>
        </table>
    </form>
</div>