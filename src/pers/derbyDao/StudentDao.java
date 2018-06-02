package pers.derbyDao;

public class StudentDao extends Dao {

	private static StudentDao studentDao = new StudentDao();

    public static StudentDao getInstance() {
        return studentDao;
    }
    
    public boolean checkPass(String studentID, String password)
    {
    	String sqlGetPass = "select password from tb_students where cast(id as char(20))='"+studentID+"'";
    	String passwordDB = selectOnlyValue(sqlGetPass);
    	if (passwordDB == null)
    		return false;
    	else
    		if (!passwordDB.equals(password))
    			return false;
    	return true;
    }
    
    // 学生查看课表及作业和分数信息
 	public String S_SelectClass(String studentID)
 	{
 		String sqlGetClasses = "select classId,homework,finalmark from tb_stu_class where cast(studentId as char(20))='"+studentID+"'";
 		String classesIdHoFm = selectColsRowsValue(sqlGetClasses);
 		// 按;区分行
 		String[] idHoFms = classesIdHoFm.split(";");
 		String classesInfo = "课程号,课程名称,作业提交情况,得分;";
 		for (int i=0; i<idHoFms.length; i++)
 		{
 			String[] idHoFm = idHoFms[i].split(",");
			String id = idHoFm[0];
 			String sqlGetClassName = "select name from tb_classes where cast(id as char(20))='"+id+"'";
 			String name = selectOnlyValue(sqlGetClassName);
 			classesInfo += id;
 			classesInfo += ",";
 			classesInfo += name;
 			classesInfo += ",";
 			if (!idHoFm[1].equals("未提交"))
 				classesInfo += "已提交";
			else
				classesInfo += idHoFm[1];
 			classesInfo += ",";
			if (idHoFm[2].equals("-1"))
				classesInfo += "未打分";
			else
				classesInfo += idHoFm[2];
 			classesInfo += ";";
 		}
 		return classesInfo;
 	}
 	
 	// 学生随机获得某一门课作业
 	public String S_SelectClass_Homework(String classID)
 	{
 		String sqlGetHomeworks = "select id,name from tb_homeworks where cast(classId as char(20))='"+classID+"'";
 		String homeworkIdNames = selectColsRowsValue(sqlGetHomeworks);
 		// 按;区分行
 		String result = "";
 		if (!homeworkIdNames.isEmpty())
 		{
 			String[] homeworks = homeworkIdNames.split(";");
 	 	 	int index = (int)Math.random()*homeworks.length;
 	 	 	result = homeworks[index];
 		}
 		return result;
 	}
 	
 	// 学生上传某一门课的作业
 	public boolean S_InsertHomework(String studentID, String classID, String homeworkName)
 	{
 		System.out.println("上传作业："+studentID+","+classID+","+homeworkName);
 		String sqlUpdate = "update tb_stu_class set homework='"+homeworkName+"' where cast(studentId as char(20))='"+studentID+"' and cast(classId as char(20))='"+classID+"'";
		boolean result = insertOrUpdateInfo(sqlUpdate);
		return result;
 	}
 	
 	
}
