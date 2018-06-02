<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page  import="java.util.*"%>
<%@ page  import="java.lang.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="jsFile/jquery-3.3.1.min.js"></script>
<style>
#header {
    background-image: url(image/background/head.jpg);
    color:white;
    text-align:center;
    padding:5px;
}
#nav {
	cursor:hand;
    line-height:30px;
    background-color:#FFFFFF;
    height:300px;
    width:150px;
    float:left;
    text-align: center;	
    border-style: solid;
  	border-top-width: 1px;
  	border-right-width: 1px;
  	border-bottom-width: 1px;
  	border-left-width: 1px;    
}
#section {
    text-align: center;	 	 
}
#footer {
    color:white;
    clear:both;
    text-align:center;
    padding:5px;
    background-image: url(image/background/footer.jpg);
}	

.input_div{margin:auto;width:600px;}
.input_div label {width:200px;float:left;text-align:left;}
.input_div select {width:200px;float:left;}
.input_div table {float:left;margin-left:width/2;}

	


.width{
    width:100px;    
}
.widthBtn{
    width:50px;    
}
  
</style>
<title>学生作业管理</title>
</head>

<body>

<script type="text/javascript"> 
	// 使用Ajax异步通讯
	function uploadHomeworkName(HomeworkID, ClassID, HomeworkName){  
   		$.ajax({  
    	type:"GET",  
    	url:"TeacherServlet",  
   		data:{type:"04", homeworkID:HomeworkID, classID:ClassID, homeworkName:HomeworkName},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){ 
    		if (data != "true"){
    			var tableName = "table_homework";
    			addTr(tableName, HomeworkID, HomeworkName, ClassID);
    			alert("上传成功");
    		}
    		else 
    			alert("上传失败，请检查信息是否完全");
    	} 
    	});  
  	}
	
	function addTr(tab, data1, data2, data3){
	    var trHtml1 = "<tr align='center'><td>"+ data1 +"</td>";
	    var trHtml2 = "<td>"+ data2 +"</td>";
	    var trHtml3 = "<td>"+ data3 +"</td></tr>";
	    var trHtml = trHtml1 + trHtml2 + trHtml3;
	    $("#"+tab+" tr:eq(-1)").after(trHtml);
	  }
	
	function renameHomeworkName(HomeworkID, HomeworkName){  
   		$.ajax({  
    	type:"GET",  
    	url:"TeacherServlet",  
   		data:{type:"05", homeworkID:HomeworkID, homeworkName:HomeworkName},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){ 
    		alert(data);
    	} 
    	});  
  	}
	
	function deleteHomeworkName(HomeworkID){  
   		$.ajax({  
    	type:"GET",  
    	url:"TeacherServlet",  
   		data:{type:"06", homeworkID:HomeworkID},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){ 
    		alert(data);
    	} 
    	});  
  	}
	
	function uploadFinalmark(ClassID, StudentID, Score){  
   		$.ajax({  
    	type:"GET",  
    	url:"TeacherServlet",  
   		data:{type:"07", classID:ClassID, studentID:StudentID, score:Score},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){ 
    	} 
    	});  
  	}

	window.onload = function(){
		// 文档夹在结束时执行，用于文档
		
		$("#btn1").click(function(){
			// 上传作业题
			var T_HomeworkID = $("#In_homeworkID").val();
			var T_HomeworkName = $("#In_homeworkName").val();
			var T_H_ClassID = $("#In_H_ClassID").val();
			uploadHomeworkName(T_HomeworkID, T_H_ClassID, T_HomeworkName);
		});
		
		$("#btn2").click(function(){
			// 重命名作业题
			var T_HomeworkID = $("#In_homeworkID").val();
			var T_HomeworkName = $("#In_homeworkName").val();
			renameHomeworkName(T_HomeworkID, T_HomeworkName);
		});
		
		$("#btn3").click(function(){
			// 删除作业题
			var T_HomeworkID = $("#In_homeworkID").val();
			deleteHomeworkName(T_HomeworkID);
		});
		
		$("#btn4").click(function(){
			// 下载作业题
		});
		
		$("#btn5").click(function(){
			// 提交分数录入表   
		    var set = new Array();
			$("#table_S_Finalmark tr").each(function() {
    			var row = new Array();
    			$(this).find('td').each(function() {
      				row.push($(this).text());
   				});
    			set.push(row);
			});
			var in_set = new Array();
			$("#table_S_Finalmark input").each(function(){
				in_set.push($(this).val());
			});
			for (var i=1; i<set.length-1; i++){
				var row = set[i];
				var varClassID = row[0];
				var varStudentID = row[1];
				var varScore = in_set[i-1];
				uploadFinalmark(varClassID, varStudentID, varScore);
			}
			
		});
	};
	
	<%
	String T_teacherID = (String)request.getSession().getAttribute("teacherID");
	String strWelcome = "欢迎，"+T_teacherID+"  角色：教师";
	List<String[]> T_Classes = (List<String[]>)request.getSession().getAttribute("T_Classes");
	List<ArrayList<String[]>> T_Classess_StudentList = (List<ArrayList<String[]>>)request.getSession().getAttribute("T_Classess_StudentList");
	List<ArrayList<String[]>> T_Classess_HomeworkList = (List<ArrayList<String[]>>)request.getSession().getAttribute("T_Classess_HomeworkList");
	%>
	
</script>

	<div id="header">
	<h1>作业管理系统</h1>
	</div>

	<div id="nav">
		<h4 id = "showClasses" title="课程相关信息">课程信息</h3>
		<h4 id = "uploadHomeworkName" title="课程作业题目管理">课程作业题目管理</h3>
		<h4 id = "downloadHomework" title="课程作业下载">学生作业下载</h3>
		<h4 id = "uploadFinalmark" title="学生分数录入">学生分数录入</h3>
	</div>

	<div id="Classes" class="section">
		<div id="div_zer" class="input_div">
			<label id="welcomeLab"><%=strWelcome%></label>
		</div>
		<br>
		<br>
		<br>
		<br>
		<div id="div_fir" class="input_div">
			<table border="1">  
            	<tbody id="table">
            	<caption>我的课表：</caption>
            	<tr align="center"> 
            		<td class="width" align="center">课程号</td>
            		<td class="width" align="center">课程名称</td>
            	</tr>
            	<%	for(int i=0; i<T_Classes.size(); i++){ 
            			String[] classInfo = T_Classes.get(i);
            	%>
           				<tr align="center">
            				<td class="width" align="center"><%=classInfo[0]%></td>
            				<td class="width" align="center"><%=classInfo[1]%></td>
            			</tr>
            	<%} %>
       		 </table>
		</div>
		
		<div id="div_sec" class="input_div" style="display: none">
			<table id="table_homework" border="1">  
            	<tbody>
            	<caption>已有题目</caption>
            	<tr align="center"> 
            		<td class="width" align="center">题号</td>
            		<td class="width" align="center">题名称</td>
            		<td class="width" align="center">课程编号</td>
            	</tr> 
            	<%	for(int i=0; i<T_Classess_HomeworkList.size(); i++){ 
            			ArrayList<String[]> arrClass_HomeworkInfo = T_Classess_HomeworkList.get(i);
            			for (int j=0; j<arrClass_HomeworkInfo.size(); j++){
            				String[] strHomeworkInfo = arrClass_HomeworkInfo.get(j);
            	%>
           					<tr align="center">
            					<td class="width" align="center"><%=strHomeworkInfo[0]%></td>
            					<td class="width" align="center"><%=strHomeworkInfo[1]%></td>
            					<td class="width" align="center"><%=T_Classes.get(i)[0]%></td>
            				</tr>
            			<%} %>
            	<%} %>
       		</table>	
			<form>
				<table align="center">
					<tr align="center">
						<td class="width" align="center">题号：</td>
						<td class="width"  colspan="2">
						<input id="In_homeworkID" type="text" name="homeworkID" /></td>
					</tr>
					<tr align="center">
						<td class="width"align="center">题名：</td>
						<td class="width"  colspan="2">
						<input id="In_homeworkName" type="text" name="homeworkName" /></td>
					</tr>
					<tr align="center">
						<td class="width"align="center">课程编号：</td>
						<td class="width"  colspan="2">
						<input id="In_H_ClassID" type="text" name="h_classID" /></td>
					</tr>
					<tr align="center">
						<td center><input class="widthBtn" type="button" value="上传" id="btn1"/></td>
						<td center><input class="widthBtn" type="button" value="重命名" id="btn2"/></td>
						<td center><input class="widthBtn" type="button" value="删除" id="btn3"/></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="div_thi" class="input_div" style="display: none">
			<table border="1">  
            	<tbody id="table_S_homework">
            	<caption>学生已提交作业下载：</caption>
            	<tr align="center">
					<td class="width" align="center">课程号</td>
					<td class="width" align="center">学生学号</td>
            		<td class="width" align="center">学生姓名</td>
            		<td class="width" align="center">作业状态</td>
				</tr>
				<%	for(int i=0; i<T_Classess_StudentList.size(); i++){ 
            			ArrayList<String[]> arrClass_StudentInfo = T_Classess_StudentList.get(i);
            			for (int j=0; j<arrClass_StudentInfo.size(); j++){
            				String[] strStudentInfo = arrClass_StudentInfo.get(j);
            	%>
           					<tr align="center">
           						<td class="width" align="center"><%=T_Classes.get(i)[1]%></td>
            					<td class="width" align="center"><%=strStudentInfo[0]%></td>
            					<td class="width" align="center"><%=strStudentInfo[1]%></td>
            					<td class="width" align="center"><%=strStudentInfo[2]%></td>
            				</tr>
            			<%} %>
            	<%} %>
            	<tr align="center">
						<td center colspan="4"><input class="widthbtn" type="button" value="一键下载" id="btn4"/></td>
				</tr>
       		</table>
		</div>
		
		<div id="div_fou" class="input_div" style="display: none">
			<form>
				<table id="table_S_Finalmark" align="center">
				<tbody>
            	<caption>学生成绩录入表</caption>
					<tr align="center">
						<td class="width" align="center">课程号</td>
						<td class="width" align="center">学生学号</td>
            			<td class="width" align="center">学生姓名</td>
            			<td class="width" align="center">分数</td>
					</tr>
					<% for(int i=0; i<T_Classess_StudentList.size(); i++){ 
            				ArrayList<String[]> arrClass_StudentInfo = T_Classess_StudentList.get(i);
            				for (int j=0; j<arrClass_StudentInfo.size(); j++){
            					String[] strStudentInfo = arrClass_StudentInfo.get(j);
            		%>
           						<tr align="center">
           							<td class="width" align="center"><%=T_Classes.get(i)[0]%></td>
            						<td class="width" align="center"><%=strStudentInfo[0]%></td>
            						<td class="width" align="center"><%=strStudentInfo[1]%></td>
            						<td class="width" align="center"><input id="In_Score" type="text" name="S_finalmark" /></td>
            					</tr>
            				<%} %>
            		<%} %>
            		<tr align="center">
						<td center colspan="4"><input class="widthbtn" type="button" value="提交" id="btn5"/></td>
					</tr>
				</table>
			</form>
		</div>
		
		<br>	
	</div>
	
	<div id="footer">
		欢迎使用作业管理系统
	</div>

<script type="text/javascript">

$(function(){  
    $("#showClasses").click(function(){  
    	$("#div_fir").show();
    	$("#div_sec").hide();
    	$("#div_thi").hide();
    	$("#div_fou").hide();
    });
    
    $("#uploadHomeworkName").click(function(){  
    	$("#div_fir").hide();
    	$("#div_sec").show();
    	$("#div_thi").hide();
    	$("#div_fou").hide();
    });
    
    $("#downloadHomework").click(function(){  
    	$("#div_fir").hide();
    	$("#div_sec").hide();
    	$("#div_thi").show();
    	$("#div_fou").hide();
    });
    
    $("#uploadFinalmark").click(function(){  
    	$("#div_fir").hide();
    	$("#div_sec").hide();
    	$("#div_thi").hide();
    	$("#div_fou").show();
    });

});

$(document).ready(function(){
	$("#nav h4").click( 
  		function() {
           $(this).css("background","DarkRed").siblings().css("background","#ffffff"); 
		});
});

</script>

</body>
</html>