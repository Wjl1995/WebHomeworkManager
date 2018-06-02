package pers.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.derbyDao.*;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
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
	 * type:1------学生查看课表及作业和分数信息----------	参数：studentID
	 * type:2------学生随机获得某一门课作业-------------参数：classID
	 * type:3------学生上传某一门课的作业----------	----参数：studentID, classID, homeworkName
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";
		StudentDao studentDao = StudentDao.getInstance();
		
		try{
			String serverType = request.getParameter("type");
			String classID = "";
			String studentID = "";
			String homeworkName = "";
			if (serverType.equals("01"))
			{
				studentID = request.getParameter("studentID");
				System.out.println("传入的参数为："+serverType+","+studentID);
				result += studentDao.S_SelectClass(studentID);
			}
			if (serverType.equals("02"))
			{
				classID = request.getParameter("classID");
				System.out.println("传入的参数为："+serverType+","+classID);
				result += studentDao.S_SelectClass_Homework(classID);
			}
			if (serverType.equals("03"))
			{
				studentID = request.getParameter("studentID");
				classID = request.getParameter("classID");
				homeworkName = request.getParameter("homeworkName");
				homeworkName = URLDecoder.decode(homeworkName,"UTF-8");
				System.out.println("传入的参数为："+serverType+","+studentID+","+classID+","+homeworkName);
				result += studentDao.S_InsertHomework(studentID, classID, homeworkName);
			}
			
			System.out.println(result);
			out.write(result);
		}
		finally{
			out.close();
		}
	}

}
