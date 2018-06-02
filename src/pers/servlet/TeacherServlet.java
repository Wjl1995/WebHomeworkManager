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
	 * 处理请求方式:
	 * type:1------教师查询课表--------------------	参数：teacherID
	 * type:2------教师查看某一门课的学生名单和相关信息-----参数：classID
	 * type:3------教师查看某一门课的作业题目单----------	参数：classID
	 * type:4------教师上传某一门课作业---------------	参数：classID,homeworkID, homeworkName
	 * type:5------教师重命名某一门课作业--------------	参数：homeworkID, homeworkName
	 * type:6------教师删除某一门课作业---------------	参数：homeworkID
	 * type:7------教师给学生作业打分-----------------参数：classID, studentID, score
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
				// 教师查询课表
				teacherID = request.getParameter("teacherID");
				System.out.println("传入的参数为："+serverType+","+teacherID);
				result += teacherDao.T_SelectClass(teacherID);
			}
			if (serverType.equals("02"))
			{
				// 教师查看某一门课的学生名单和相关信息
				classID = request.getParameter("classID");
				System.out.println("传入的参数为："+serverType+","+classID);
				result += teacherDao.T_SelectClass_StudentList(classID);
			}
			if (serverType.equals("03"))
			{
				// 教师查看某一门课的作业题目单
				classID = request.getParameter("classID");
				System.out.println("传入的参数为："+serverType+","+classID);
				result += teacherDao.T_SelectClass_homeworkList(classID);
			}
			if (serverType.equals("04"))
			{
				// 教师上传某一门课作业
				classID = request.getParameter("classID");
				homeworkID = request.getParameter("homeworkID");
				homeworkName = request.getParameter("homeworkName");
				homeworkName = URLDecoder.decode(homeworkName,"UTF-8");
				System.out.println("传入的参数为："+serverType+","+classID+","+homeworkID+","+homeworkName);
				result += teacherDao.T_InsertHomework(homeworkID, classID, homeworkName);
			}
			if (serverType.equals("05"))
			{
				// 教师重命名某一门课作业
				homeworkID = request.getParameter("homeworkID");
				homeworkName = request.getParameter("homeworkName");
				homeworkName = URLDecoder.decode(homeworkName,"UTF-8");
				System.out.println("传入的参数为："+serverType+","+homeworkID+","+homeworkName);
				result += teacherDao.T_UpdateHomework(homeworkID, homeworkName);
			}
			if (serverType.equals("06"))
			{
				// 教师删除某一门课作业
				homeworkID = request.getParameter("homeworkID");
				System.out.println("传入的参数为："+serverType+","+homeworkID);
				result += teacherDao.T_DeleteHomework(homeworkID);
			}
			if (serverType.equals("07"))
			{
				// 教师给学生作业打分
				classID = request.getParameter("classID");
				studentID = request.getParameter("studentID");
				score = request.getParameter("score");
				System.out.println("传入的参数为："+serverType+","+classID+","+studentID+","+score);
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
