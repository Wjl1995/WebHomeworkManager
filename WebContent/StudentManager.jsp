<%@ page language="java" contentType="text/html; charset=utf-8"%>
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

.input_div{margin:auto;width:500px;}
.input_div label {width:200px;float:left;text-align:left;}
.input_div select {width:200px;float:left;}
.input_div input {width:200px;float:left;}
.input_div table {}
  
</style>
<title>学生作业管理</title>
</head>

<body>

<script type="text/javascript"> 

	function StudentObj(id, classIds, classNames){
		this.id = id;
		this.classIds = classIds;
		this.classNames = classNames;
	}
	var student = new StudentObj(null, null, null);
	
	<%
	String S_StudentID = (String)request.getSession().getAttribute("studentID");
	String strWelcome = "欢迎，"+S_StudentID+"  角色：学生";
	%>
	
	student.id = <%=S_StudentID%>;
	
	// 使用Ajax异步通讯
	function getClassessInfo(StudentID){  
   		$.ajax({  
    	type:"GET",  
    	url:"StudentServlet",  
   		data:{type:"01", studentID:StudentID},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){ 
    		deleteTableLine();
    		var spit = ";";
    		var myAllData = spitStringWith(data,spit);
    		var spit2 = ",";
    		var classIds = new Array();
    		var classNames = new Array();
    		for (var i=0; i<myAllData.length; i++){
    			myAllData[i] = myAllData[i] + ",";
    			var myData = spitStringWith(myAllData[i], spit2);
    			setTableLine(myData);
    			if (i>0){
    				classIds.push(myData[0]);
    				classNames.push(myData[1]);
    			}	
    		}
    		student.classIds = classIds;
    		student.classNames = classNames;
    	}
    	});  
  	}
	
	function getHomeworkInfo(ClassID){  
   		$.ajax({  
    	type:"GET",  
    	url:"StudentServlet",  
   		data:{type:"02", classID:ClassID},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){  
    		$("#homeworkName").val(data);	
    	} 
    	});  
  	}
	
	function uploadHomeworkInfo(StudentID, ClassID, HomeworkName){  
   		$.ajax({  
    	type:"GET",  
    	url:"StudentServlet",  
   		data:{type:"03", studentID:StudentID, classID:ClassID, homeworkName:HomeworkName},
    	statusCode: {404: function() {  
        	alert('page not found'); }  
     	 }, 
    	success:function(data,textStatus){ 
    		if (data == "ture")
    			alert("作业提交成功！");
    	} 
    	});  
  	}
	   
	function getHomework(classId) {  
		classId.style.color = "blue"; //将被点击的单元格设置为蓝色
		var id = classId.innerHTML;  
    }
	
    function setTableLine(tdData) {  
        var _table = document.getElementById("table");   
        var _row = document.createElement("tr");  
        _table.appendChild(_row);  
        for(var j = 0; j < tdData.length; j++) {  
             var _cell = document.createElement("td");
             _cell.innerText = tdData[j];  
             _row.appendChild(_cell);  
        }
    }
    
    function deleteTableLine(){
    	var _table = document.getElementById("table");
    	for(var i=_table.rows.length-1;i>=0;i--)
        {
    		_table.deleteRow(i);
        } 
    }
    
    function setClassP(classId){
    	var _div = document.getElementById("Classes");
    	var _class = document.createElement("p");
    	_class.innerHTML = classId;
    	_class.onclick = function(){getHomework(this)};
    	_div.appendChild(_class);
    }
    
    function spitStringWith(data, spit){
    	var myAllData = new Array();
		var mySpit = new Array();
		mySpit.push(-1);
		for(var i = 0; i < data.length; i++) {
            if (data[i] == spit)
            	mySpit.push(i);	  
        }
		var colData = "";
		for (var i=0; i<mySpit.length-1; i++){
			var j=mySpit[i]+1;
			var colData = "";
			for (; j<mySpit[i+1]; j++){
				colData = colData+data[j];
			}
			myAllData.push(colData);	
		}
		return myAllData;
    }
    
	window.onload = function(){
		
		$("#btn1").click(function(){
			var checkIndex = $("#classSeleDown option:selected").val();
			getHomeworkInfo(student.classIds[checkIndex]);
		});
		
		$("#btn2").click(function(){
			var checkIndex = $("#classSeleUp option:selected").val();
			var homeworkName = $("#homework").val();
			homeworkName = encodeURI(homeworkName, "UTF-8");
			alert(checkIndex+","+student.classIds);
			uploadHomeworkInfo(student.id ,student.classIds[checkIndex], homeworkName);
		});
	};
</script>

	<div id="header">
	<h1>作业管理系统</h1>
	</div>

	<div id="nav">
		<h4 id = "showClasses" title="课程相关信息">课程信息</h3>
		<h4 id = "downloadHomework" title="课程作业下载">课程作业下载</h3>
		<h4 id = "uploadHomework" title="课程作业">课程作业上传</h3>
	</div>

	<div id="Classes" class="section">
		<div id="div_zer" class="input_div">
			<label><%= strWelcome%></label>
		</div>
		<br>
		<br>
		<br>
		<br>
		<div id="div_fir" class="input_div" style="display: none">
			<table border="1">  
            	<tbody id="table">
            	<caption>我的课表：</caption>
            	<tr>  
       		 </table>
		</div>
		
		<div id="div_sec" class="input_div" style="display: none">
			<p>*注意，教师没有出题之前获取不到作业题目！</p>
			<br>
			<label>课程编号：</label>
			<select id="classSeleDown">
			</select>
			<br>
			<br>
			<label>随机获取一道题：</label>
			<input id="homeworkName">
			<button type="button" id="btn1">获取</button>
		</div>
		
		<div id="div_thi" class="input_div" style="display: none">
			<p>*注意，相同课程提交多次会覆盖之前作业！</p>
			<br>
			<label>课程编号：</label>
			<select id="classSeleUp">
			</select>
			<br>
			<br>
			<label>作业名称：</label>
			<input id="homework">
			<button type="button" id="btn2">提交</button>
		</div>
		<br>	
	</div>
	
	<div id="footer">
		欢迎使用作业管理系统
	</div>

<script type="text/javascript">  

$(function(){  
    $("#showClasses").click(function(){  
    	$("#showClasses").css("background-color","DarkRed");
    	$("#downloadHomework").css("background-color","#FFFFFF");
    	$("#uploadHomework").css("background-color","#FFFFFF");
    	$("#div_fir").show();
    	$("#div_sec").hide();
    	$("#div_thi").hide();
    	student.id = "200001";
    	getClassessInfo(student.id);
    });
    $("#downloadHomework").click(function(){  
    	$("#downloadHomework").css("background-color","DarkRed");
    	$("#showClasses").css("background-color","#FFFFFF");
    	$("#uploadHomework").css("background-color","#FFFFFF");
    	$("#div_sec").show();
    	$("#div_fir").hide();
    	$("#div_thi").hide();
    	$("#classSeleDown").empty();
    	var optionHead = "<option value='";
    	var optionMid = "'>";
    	var optionFoot = "</option>";
    	var Ids = student.classIds;
    	var Names = student.classNames;
    	for (var i=0; i<Ids.length; i++){
    		var optionTag = optionHead+i+optionMid+Ids[i]+"  "+Names[i]+optionFoot;
    		$("#classSeleDown").append(optionTag);
    	}
    	
    });
    $("#uploadHomework").click(function(){  
    	$("#uploadHomework").css("background-color","DarkRed");
    	$("#showClasses").css("background-color","#FFFFFF");
    	$("#downloadHomework").css("background-color","#FFFFFF");
    	$("#div_sec").hide();
    	$("#div_fir").hide();
    	$("#div_thi").show();
    	$("#classSeleUp").empty();
    	var optionHead = "<option value='";
    	var optionMid = "'>";
    	var optionFoot = "</option>";
    	var Ids = student.classIds;
    	var Names = student.classNames;
    	for (var i=0; i<Ids.length; i++){
    		var optionTag = optionHead+i+optionMid+Ids[i]+"  "+Names[i]+optionFoot;
    		$("#classSeleUp").append(optionTag);
    	}
    });
}); 

</script>

</body>
</html>