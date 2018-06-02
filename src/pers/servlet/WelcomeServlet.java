package pers.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.derbyDao.StudentDao;
import pers.derbyDao.TeacherDao;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() {
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
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		TeacherDao teacherDao = TeacherDao.getInstance();
		StudentDao studentDao = StudentDao.getInstance();
		try{
			String ID = (String)request.getParameter("name");
			String password = (String)request.getParameter("pass");
			if (teacherDao.checkPass(ID, password))
				processTeacherRequest(request, response, ID);
			else if(studentDao.checkPass(ID, password))
					processStudentRequest(request, response, ID);
				else
				{
					HttpSession mySession = request.getSession();
					mySession.setAttribute("result", "用户名或密码错误！");
					request.getRequestDispatcher("Welcome.jsp").forward(request, response);
				}
		}
		finally{
			out.close();
		}
	}
	
	protected void processTeacherRequest(HttpServletRequest request, HttpServletResponse response,
			String teacherID)throws ServletException, IOException
	{
		List<String[]> T_Classes = new ArrayList<String[]>();
		List<ArrayList<String[]>> T_Classess_StudentList = new ArrayList<ArrayList<String[]>>();
		List<ArrayList<String[]>> T_Classess_HomeworkList = new ArrayList<ArrayList<String[]>>();
		
		TeacherDao teacherDao = TeacherDao.getInstance();
		String classesInfo = teacherDao.T_SelectClass(teacherID);
		String[] classInfo = classesInfo.split(";");
		for (int i=1; i<classInfo.length; i++){
			String[] T_Class = classInfo[i].split(",");
			T_Classes.add(T_Class);
			ArrayList<String[]> T_StudentList = new ArrayList<String[]>();
			ArrayList<String[]> T_HomeworkList = new ArrayList<String[]>();
			String studentsInfo = teacherDao.T_SelectClass_StudentList(T_Class[0]);
			String homeworkListInfo = teacherDao.T_SelectClass_homeworkList(T_Class[0]);
			String[] studentInfo = studentsInfo.split(";");
			for (int j=1; j<studentInfo.length; j++){
				String[] T_student = studentInfo[j].split(",");
				T_StudentList.add(T_student);
			}
			T_Classess_StudentList.add(T_StudentList);
			String[] homeworkInfo = homeworkListInfo.split(";");
			for (int j=1; j<homeworkInfo.length; j++){
				String[] T_homework = homeworkInfo[j].split(",");
				T_HomeworkList.add(T_homework);
			}
			T_Classess_HomeworkList.add(T_HomeworkList);
		}
		
		HttpSession mySession = request.getSession();
		mySession.setAttribute("teacherID", teacherID);
		mySession.setAttribute("T_Classes", T_Classes);
		mySession.setAttribute("T_Classess_StudentList", T_Classess_StudentList);
		mySession.setAttribute("T_Classess_HomeworkList", T_Classess_HomeworkList);
		request.getRequestDispatcher("TeacherManager.jsp").forward(request, response); 
	}
	
	protected void processStudentRequest(HttpServletRequest request, HttpServletResponse response,
			String studentID)throws ServletException, IOException
	{
		
		
		HttpSession mySession = request.getSession();
		mySession.setAttribute("studentID", studentID);
		request.getRequestDispatcher("StudentManager.jsp").forward(request, response);
	}

}
