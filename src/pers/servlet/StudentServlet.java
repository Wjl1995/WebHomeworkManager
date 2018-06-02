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
	 * ��������ʽ:
	 * type:1------ѧ���鿴�α���ҵ�ͷ�����Ϣ----------	������studentID
	 * type:2------ѧ��������ĳһ�ſ���ҵ-------------������classID
	 * type:3------ѧ���ϴ�ĳһ�ſε���ҵ----------	----������studentID, classID, homeworkName
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
				System.out.println("����Ĳ���Ϊ��"+serverType+","+studentID);
				result += studentDao.S_SelectClass(studentID);
			}
			if (serverType.equals("02"))
			{
				classID = request.getParameter("classID");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+classID);
				result += studentDao.S_SelectClass_Homework(classID);
			}
			if (serverType.equals("03"))
			{
				studentID = request.getParameter("studentID");
				classID = request.getParameter("classID");
				homeworkName = request.getParameter("homeworkName");
				homeworkName = URLDecoder.decode(homeworkName,"UTF-8");
				System.out.println("����Ĳ���Ϊ��"+serverType+","+studentID+","+classID+","+homeworkName);
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
