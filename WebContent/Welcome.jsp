<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page  import="java.util.*"%>
<%@ page  import="java.lang.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="jsFile/jquery-3.3.1.min.js"></script>
<style>

#whole {
    background-image: url(image/background/welcome.jpg);
    color:white;
    text-align:center;
    height:600px;
    padding:5px;
}
.align-center{ 
	position:fixed;left:50%;top:30%;margin-left:width/2;margin-top:height/2;
} 
  
</style>
<title>学生作业管理</title>
</head>

<body>

<script type="text/javascript">

	var errorSent = <%=(String)request.getSession().getAttribute("result")%>;
	
	window.onload = function(){
		// 文档夹在结束时执行，用于文档
		$("#error").html(errorSent);
	};
	
</script>		

	<div id="whole">
		<div class="align-center">
			<h1>作业管理系统</h1>
			<br>
			<label id="error" style="color:red; text-align:center"></label>
			<form action="WelcomeServlet" method="get">
				<table align="center">
					<tr align="center">
						<td align="right">账号:</td>
						<td align="left"><input type="text" name="name" /></td>
					</tr>
					<tr align="center">
						<td align="right">密码:</td>
						<td align="left"><input type="text" name="pass" /></td>
					</tr>
					<tr align="center">
						<td colspan="2"><input type="submit" value="登录" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>

</html>