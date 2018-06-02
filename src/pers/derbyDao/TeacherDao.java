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
	
	// ��ʦ�鿴�α�
	public String T_SelectClass(String teacherID)
	{
		// derby ��int��ת��Ϊchar�ͽ��бȽ�
		String sqlGetTName = "select name from tb_teachers where cast(id as char(20))='"+teacherID+"'";
		String teacherName = selectOnlyValue(sqlGetTName);
		String sqlGetClasses = "select id, name from tb_classes where teacher='"+teacherName+"'";
		String classes = "�γ̴���,�γ�����;";
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
		
	// ��ʦ�鿴ĳһ�ſε�ѧ�������������Ϣ
	public String T_SelectClass_StudentList(String classID)
	{
		String sqlGetStudentsId = "select studentId,homework,finalmark from tb_stu_class where cast(classId as char(20))='"+classID+"'";
		String studentsId = selectColsRowsValue(sqlGetStudentsId);
		String strStudentInfo = "ѧ��,����,��ҵ���,����;";
		// ��;������
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
			if (!idHoFm[1].equals("δ�ύ"))
				strStudentInfo += "���ύ";
			else
				strStudentInfo += idHoFm[1];
			strStudentInfo += ",";
			if (idHoFm[2].equals("-1"))
				strStudentInfo += "δ���";
			else
				strStudentInfo += idHoFm[2];
			strStudentInfo += ";";
		}
		
		return strStudentInfo;
	}
		
	// ��ʦ�鿴ĳһ�ſε���ҵ��Ŀ��
	public String T_SelectClass_homeworkList(String classID)
	{
		String sqlGetHomeworkInfo = "select id,name from tb_homeworks where cast(classId as char(20))='"+classID+"'";
		String homeWordInfo = "���,����;";
		homeWordInfo += selectColsRowsValue(sqlGetHomeworkInfo);
		return homeWordInfo;
	}
		
	// ��ʦ�ϴ�ĳһ�ſ���ҵ��
	public boolean T_InsertHomework(String homeworkID, String classID, String homeworkName)
	{
		String sqlGetInsert = "insert into tb_homeworks values (cast("+homeworkID+" as int), '" + homeworkName + "', cast("+classID+" as int))";
		boolean result = insertOrUpdateInfo(sqlGetInsert);
		return result;
	}
		
	// ��ʦ������ĳһ�ſ���ҵ��
	public boolean T_UpdateHomework(String homeworkID, String newName)
	{
		String sqlUpdate = "update tb_homeworks set name='"+newName+"' where cast(id as char(20))='"+homeworkID+"'";
		boolean result = insertOrUpdateInfo(sqlUpdate);
		return result;
	}
	// ��ʦɾ��ĳһ�ſ���ҵ��
	public boolean T_DeleteHomework(String homeworkID)
	{
		String sqlDelete = "delete from tb_homeworks where cast(id as char(20))='"+homeworkID+"'";
		boolean result = insertOrUpdateInfo(sqlDelete);
		return result;
	}
		
	// ��ʦ��ѧ����ҵ���
	public boolean T_updateStudentMark(String classID, String studentID, String score)
	{
		String sqlUpdate = "update tb_stu_class set finalmark=cast("+score+" as int) where cast(studentId as char(20))='"+studentID+"' and cast(classId as char(20))='"+classID+"'";
		boolean result = insertOrUpdateInfo(sqlUpdate);
		return result;
	}
}
