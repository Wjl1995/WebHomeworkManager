package pers.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.derbyDao.TeacherDao;

/**
 * Servlet implementation class TeacherServlet
 */
@WebServlet("/TeacherServlet")
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	/**
	 * ��������ʽ:
	 * type:1------��ʦ��ѯ�α�--------------------	������teacherID
	 * type:2------��ʦ�鿴ĳһ�ſε�ѧ�������������Ϣ-----������classID
	 * type:3------��ʦ�鿴ĳһ�ſε���ҵ��Ŀ��----------	������classID
	 * type:4------��ʦ�ϴ�ĳһ�ſ���ҵ---------------	������classID,homeworkID, homeworkName
	 * type:5------��ʦ������ĳһ�ſ���ҵ--------------	������homeworkID, homeworkName
	 * type:6------��ʦɾ��ĳһ�ſ���ҵ---------------	������homeworkID
	 * type:7------��ʦ��ѧ����ҵ���-----------------������classID, studentID, score
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		String result = "";
		TeacherDao teacherDao = TeacherDao.getInstance();
		try{
			String serverType = request.getParameter("type");
			String classID = "";
			String teacherID = "";
			String studentID = "";
			String homeworkID = "";
			String homeworkName = "";
			String score = "";
			if (serverType.equals("01"))
			{
				// ��ʦ��ѯ�α�
				teacherID = request.getParameter("teacherID");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+teacherID);
				result += teacherDao.T_SelectClass(teacherID);
			}
			if (serverType.equals("02"))
			{
				// ��ʦ�鿴ĳһ�ſε�ѧ�������������Ϣ
				classID = request.getParameter("classID");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+classID);
				result += teacherDao.T_SelectClass_StudentList(classID);
			}
			if (serverType.equals("03"))
			{
				// ��ʦ�鿴ĳһ�ſε���ҵ��Ŀ��
				classID = request.getParameter("classID");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+classID);
				result += teacherDao.T_SelectClass_homeworkList(classID);
			}
			if (serverType.equals("04"))
			{
				// ��ʦ�ϴ�ĳһ�ſ���ҵ
				classID = request.getParameter("classID");
				homeworkID = request.getParameter("homeworkID");
				homeworkName = request.getParameter("homeworkName");
				homeworkName = URLDecoder.decode(homeworkName,"UTF-8");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+classID+","+homeworkID+","+homeworkName);
				result += teacherDao.T_InsertHomework(homeworkID, classID, homeworkName);
			}
			if (serverType.equals("05"))
			{
				// ��ʦ������ĳһ�ſ���ҵ
				homeworkID = request.getParameter("homeworkID");
				homeworkName = request.getParameter("homeworkName");
				homeworkName = URLDecoder.decode(homeworkName,"UTF-8");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+homeworkID+","+homeworkName);
				result += teacherDao.T_UpdateHomework(homeworkID, homeworkName);
			}
			if (serverType.equals("06"))
			{
				// ��ʦɾ��ĳһ�ſ���ҵ
				homeworkID = request.getParameter("homeworkID");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+homeworkID);
				result += teacherDao.T_DeleteHomework(homeworkID);
			}
			if (serverType.equals("07"))
			{
				// ��ʦ��ѧ����ҵ���
				classID = request.getParameter("classID");
				studentID = request.getParameter("studentID");
				score = request.getParameter("score");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+classID+","+studentID+","+score);
				result += teacherDao.T_updateStudentMark(classID, studentID, score);
			}
			System.out.println(result);
			out.write(result);
		}
		finally{
			out.close();
		}
	}

}
