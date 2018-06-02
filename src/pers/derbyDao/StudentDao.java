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
    
    // ѧ���鿴�α���ҵ�ͷ�����Ϣ
 	public String S_SelectClass(String studentID)
 	{
 		String sqlGetClasses = "select classId,homework,finalmark from tb_stu_class where cast(studentId as char(20))='"+studentID+"'";
 		String classesIdHoFm = selectColsRowsValue(sqlGetClasses);
 		// ��;������
 		String[] idHoFms = classesIdHoFm.split(";");
 		String classesInfo = "�γ̺�,�γ�����,��ҵ�ύ���,�÷�;";
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
 			if (!idHoFm[1].equals("δ�ύ"))
 				classesInfo += "���ύ";
			else
				classesInfo += idHoFm[1];
 			classesInfo += ",";
			if (idHoFm[2].equals("-1"))
				classesInfo += "δ���";
			else
				classesInfo += idHoFm[2];
 			classesInfo += ";";
 		}
 		return classesInfo;
 	}
 	
 	// ѧ��������ĳһ�ſ���ҵ
 	public String S_SelectClass_Homework(String classID)
 	{
 		String sqlGetHomeworks = "select id,name from tb_homeworks where cast(classId as char(20))='"+classID+"'";
 		String homeworkIdNames = selectColsRowsValue(sqlGetHomeworks);
 		// ��;������
 		String result = "";
 		if (!homeworkIdNames.isEmpty())
 		{
 			String[] homeworks = homeworkIdNames.split(";");
 	 	 	int index = (int)Math.random()*homeworks.length;
 	 	 	result = homeworks[index];
 		}
 		return result;
 	}
 	
 	// ѧ���ϴ�ĳһ�ſε���ҵ
 	public boolean S_InsertHomework(String studentID, String classID, String homeworkName)
 	{
 		System.out.println("�ϴ���ҵ��"+studentID+","+classID+","+homeworkName);
 		String sqlUpdate = "update tb_stu_class set homework='"+homeworkName+"' where cast(studentId as char(20))='"+studentID+"' and cast(classId as char(20))='"+classID+"'";
		boolean result = insertOrUpdateInfo(sqlUpdate);
		return result;
 	}
 	
 	
}
