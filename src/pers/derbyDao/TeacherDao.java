package pers.derbyDao;


public class TeacherDao extends Dao{

	private static TeacherDao teacherDao = new TeacherDao();

    public static TeacherDao getInstance() {
        return teacherDao;
    }
    
    public boolean checkPass(String teacherID, String password)
    {
    	String sqlGetPass = "select password from tb_teachers where cast(id as char(20))='"+teacherID+"'";
    	String passwordDB = selectOnlyValue(sqlGetPass);
    	if (passwordDB == null)
    		return false;
    	else
    		if (!passwordDB.equals(password))
    			return false;
    	return true;
    }
	
	// 教师查看课表
	public String T_SelectClass(String teacherID)
	{
		// derby 将int型转化为char型进行比较
		String sqlGetTName = "select name from tb_teachers where cast(id as char(20))='"+teacherID+"'";
		String teacherName = selectOnlyValue(sqlGetTName);
		String sqlGetClasses = "select id, name from tb_classes where teacher='"+teacherName+"'";
		String classes = "课程代号,课程名称;";
		classes += selectColsRowsValue(sqlGetClasses);
			
		return classes;
	}
	
	public String T_SelectClassName(String classID)
	{
		String sqlGetClassName = "select name from tb_classes where cast(id as char(20))='"+classID+"'";
		String className = "";
		className += selectOnlyValue(sqlGetClassName);
		return className;
	}
		
	// 教师查看某一门课的学生名单和相关信息
	public String T_SelectClass_StudentList(String classID)
	{
		String sqlGetStudentsId = "select studentId,homework,finalmark from tb_stu_class where cast(classId as char(20))='"+classID+"'";
		String studentsId = selectColsRowsValue(sqlGetStudentsId);
		String strStudentInfo = "学号,姓名,作业情况,分数;";
		// 按;区分行
		String[] idHoFms = studentsId.split(";");
		for (int i=0; i<idHoFms.length; i++)
		{
			String[] idHoFm = idHoFms[i].split(",");
			String id = idHoFm[0];
			String sqlGetStudentsInfo = "select name from tb_students where cast(id as char(20))='"+id+"'";
			id +=",";
			id += selectOnlyValue(sqlGetStudentsInfo);
			strStudentInfo += id;
			strStudentInfo += ",";
			if (!idHoFm[1].equals("未提交"))
				strStudentInfo += "已提交";
			else
				strStudentInfo += idHoFm[1];
			strStudentInfo += ",";
			if (idHoFm[2].equals("-1"))
				strStudentInfo += "未打分";
			else
				strStudentInfo += idHoFm[2];
			strStudentInfo += ";";
		}
		
		return strStudentInfo;
	}
		
	// 教师查看某一门课的作业题目单
	public String T_SelectClass_homeworkList(String classID)
	{
		String sqlGetHomeworkInfo = "select id,name from tb_homeworks where cast(classId as char(20))='"+classID+"'";
		String homeWordInfo = "题号,题名;";
		homeWordInfo += selectColsRowsValue(sqlGetHomeworkInfo);
		return homeWordInfo;
	}
		
	// 教师上传某一门课作业题
	public boolean T_InsertHomework(String homeworkID, String classID, String homeworkName)
	{
		String sqlGetInsert = "insert into tb_homeworks values (cast("+homeworkID+" as int), '" + homeworkName + "', cast("+classID+" as int))";
		boolean result = insertOrUpdateInfo(sqlGetInsert);
		return result;
	}
		
	// 教师重命名某一门课作业题
	public boolean T_UpdateHomework(String homeworkID, String newName)
	{
		String sqlUpdate = "update tb_homeworks set name='"+newName+"' where cast(id as char(20))='"+homeworkID+"'";
		boolean result = insertOrUpdateInfo(sqlUpdate);
		return result;
	}
	// 教师删除某一门课作业题
	public boolean T_DeleteHomework(String homeworkID)
	{
		String sqlDelete = "delete from tb_homeworks where cast(id as char(20))='"+homeworkID+"'";
		boolean result = insertOrUpdateInfo(sqlDelete);
		return result;
	}
		
	// 教师给学生作业打分
	public boolean T_updateStudentMark(String classID, String studentID, String score)
	{
		String sqlUpdate = "update tb_stu_class set finalmark=cast("+score+" as int) where cast(studentId as char(20))='"+studentID+"' and cast(classId as char(20))='"+classID+"'";
		boolean result = insertOrUpdateInfo(sqlUpdate);
		return result;
	}
}
