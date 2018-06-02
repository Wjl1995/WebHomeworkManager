package pers.derbyDao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
	
	private static final String DRIVERCLASS = "org.apache.derby.jdbc.EmbeddedDriver"; // ����

    private static final String URL = "jdbc:derby:db_manager"; // Э��

    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); // �����������ݿ�����

    private static Connection conn = null; // ���ݿ�����

    static { // ͨ����̬�����������ݿ����������������ݿⲻ���ڵ�����´������ݿ�

        try {
            Class.forName(DRIVERCLASS); 				// �������ݿ�����

            File databaseFile = new File("db_manager");// �������ݿ��ļ�����

            if (!databaseFile.exists()) {// �ж����ݿ��ļ��Ƿ����

                String[] sqls = new String[14];// ���崴�����ݿ��SQL���

                sqls[0] = "create table tb_classes (id int primary key,name varchar(20) not null,teacher varchar(8) not null)";
                sqls[1] = "create table tb_teachers (id int primary key,name varchar(8) not null,password varchar(20) not null)";
                sqls[2] = "create table tb_students (id int primary key,name varchar(8) not null,password varchar(20) not null)";
                sqls[3] = "create table tb_stu_class (id int primary key,studentId int not null,classId int not null,homework varchar(30) not null,finalmark int not null)";
                sqls[4] = "create table tb_homeworks (id int primary key,name varchar(20) not null,classId int not null)";
                
                sqls[5] = "insert into tb_classes(id,name,teacher) values(1,'�����������','������')";
                sqls[6] = "insert into tb_classes(id,name,teacher) values(2,'�������','������')";
                sqls[7] = "insert into tb_teachers(id,name,password) values(100001,'������','100001')";
                sqls[8] = "insert into tb_students(id,name,password) values(200001,'����','200001')";
                sqls[9] = "insert into tb_students(id,name,password) values(200002,'����','200002')";
                sqls[10] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(1,200001,1,'δ�ύ',-1)";
                sqls[11] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(2,200001,2,'δ�ύ',-1)";
                sqls[12] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(3,200002,1,'δ�ύ',-1)";
                sqls[13] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(4,200002,2,'δ�ύ',-1)";
                
                conn = DriverManager.getConnection(URL + ";create=true");// �������ݿ�����

                threadLocal.set(conn);// �������ݿ�����

                Statement stmt = conn.createStatement();// �������ݿ�����״̬����

                for (int i = 0; i < sqls.length; i++) {// ͨ��ִ��SQL��䴴�����ݿ�

                    stmt.execute(sqls[i]);// ִ��SQL���

                }
                stmt.close();// �ر����ݿ�����״̬����

            }
        } catch (Exception e) {
        	System.out.println("�������ݿ�ʧ��");
            e.printStackTrace();
        }
    }

    protected static Connection getConnection() { // �������ݿ����ӵķ���

        conn = (Connection) threadLocal.get(); // ���߳��л�����ݿ�����

        if (conn == null) { // û�п��õ����ݿ�����
            try {
                conn = DriverManager.getConnection(URL);// �����µ����ݿ�����

                threadLocal.set(conn); // �����ݿ����ӱ��浽�߳���

            } catch (Exception e) {
                System.exit(0);// �ر�ϵͳ
                e.printStackTrace();
            }
        }
        return conn;
    }

    protected static boolean closeConnection() { // �ر����ݿ����ӵķ���

        boolean isClosed = true; // Ĭ�Ϲرճɹ�

        conn = (Connection) threadLocal.get(); // ���߳��л�����ݿ�����

        threadLocal.set(null); // ����߳��е����ݿ�����

        if (conn != null) { // ���ݿ����ӿ���

            try {
                conn.close(); // �ر����ݿ�����

            } catch (SQLException e) {
                isClosed = false; // �ر�ʧ��

                e.printStackTrace();
            }
        }
        return isClosed;
    }
}
