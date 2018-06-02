package pers.derbyDao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
	
	private static final String DRIVERCLASS = "org.apache.derby.jdbc.EmbeddedDriver"; // 驱动

    private static final String URL = "jdbc:derby:db_manager"; // 协议

    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); // 用来保存数据库连接

    private static Connection conn = null; // 数据库连接

    static { // 通过静态方法加载数据库驱动，并且在数据库不存在的情况下创建数据库

        try {
            Class.forName(DRIVERCLASS); 				// 加载数据库驱动

            File databaseFile = new File("db_manager");// 创建数据库文件对象

            if (!databaseFile.exists()) {// 判断数据库文件是否存在

                String[] sqls = new String[14];// 定义创建数据库的SQL语句

                sqls[0] = "create table tb_classes (id int primary key,name varchar(20) not null,teacher varchar(8) not null)";
                sqls[1] = "create table tb_teachers (id int primary key,name varchar(8) not null,password varchar(20) not null)";
                sqls[2] = "create table tb_students (id int primary key,name varchar(8) not null,password varchar(20) not null)";
                sqls[3] = "create table tb_stu_class (id int primary key,studentId int not null,classId int not null,homework varchar(30) not null,finalmark int not null)";
                sqls[4] = "create table tb_homeworks (id int primary key,name varchar(20) not null,classId int not null)";
                
                sqls[5] = "insert into tb_classes(id,name,teacher) values(1,'软件技术基础','高连生')";
                sqls[6] = "insert into tb_classes(id,name,teacher) values(2,'面向对象','高连生')";
                sqls[7] = "insert into tb_teachers(id,name,password) values(100001,'高连生','100001')";
                sqls[8] = "insert into tb_students(id,name,password) values(200001,'张三','200001')";
                sqls[9] = "insert into tb_students(id,name,password) values(200002,'李四','200002')";
                sqls[10] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(1,200001,1,'未提交',-1)";
                sqls[11] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(2,200001,2,'未提交',-1)";
                sqls[12] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(3,200002,1,'未提交',-1)";
                sqls[13] = "insert into tb_stu_class(id,studentId,classId,homework,finalmark) values(4,200002,2,'未提交',-1)";
                
                conn = DriverManager.getConnection(URL + ";create=true");// 创建数据库连接

                threadLocal.set(conn);// 保存数据库连接

                Statement stmt = conn.createStatement();// 创建数据库连接状态对象

                for (int i = 0; i < sqls.length; i++) {// 通过执行SQL语句创建数据库

                    stmt.execute(sqls[i]);// 执行SQL语句

                }
                stmt.close();// 关闭数据库连接状态对象

            }
        } catch (Exception e) {
        	System.out.println("创建数据库失败");
            e.printStackTrace();
        }
    }

    protected static Connection getConnection() { // 创建数据库连接的方法

        conn = (Connection) threadLocal.get(); // 从线程中获得数据库连接

        if (conn == null) { // 没有可用的数据库连接
            try {
                conn = DriverManager.getConnection(URL);// 创建新的数据库连接

                threadLocal.set(conn); // 将数据库连接保存到线程中

            } catch (Exception e) {
                System.exit(0);// 关闭系统
                e.printStackTrace();
            }
        }
        return conn;
    }

    protected static boolean closeConnection() { // 关闭数据库连接的方法

        boolean isClosed = true; // 默认关闭成功

        conn = (Connection) threadLocal.get(); // 从线程中获得数据库连接

        threadLocal.set(null); // 清空线程中的数据库连接

        if (conn != null) { // 数据库连接可用

            try {
                conn.close(); // 关闭数据库连接

            } catch (SQLException e) {
                isClosed = false; // 关闭失败

                e.printStackTrace();
            }
        }
        return isClosed;
    }
}
