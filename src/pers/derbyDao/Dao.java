package pers.derbyDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {
	
	// 打印所有表的内容
	protected void printAllTables(){
		 Connection conn = JDBC.getConnection();// 获得数据库连接
		 try {
	            Statement stmt = conn.createStatement();// 创建连接状态对象
	            ResultSet rs1 = stmt.executeQuery("select * from tb_classes");
	            printResultSet("tb_classes", rs1);
	            rs1.close();
	            ResultSet rs2 = stmt.executeQuery("select * from tb_teachers");
	            printResultSet("tb_teachers", rs2);
	            rs2.close();
	            ResultSet rs3 = stmt.executeQuery("select * from tb_students");
	            printResultSet("tb_students", rs3);
	            rs3.close();
	            ResultSet rs4 = stmt.executeQuery("select * from tb_stu_class");
	            printResultSet("tb_stu_class", rs4);
	            rs4.close();
	            ResultSet rs5 = stmt.executeQuery("select * from tb_homeworks");
	            printResultSet("tb_homeworks", rs5);
	            rs5.close();
	            stmt.close(); 
		 }catch (SQLException e) {
	            e.printStackTrace();
	     }
		 finally{
			 JDBC.closeConnection();
		 }
	}
	
	// 打印ResultSet，并给与名称
	protected void printResultSet(String tableName, ResultSet rs)
	{
		 try {
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			System.out.println(tableName+"内容如下：");
			//显示列,表格的表头
			for(int i=1; i<=columns; i++)
			{
				System.out.print(md.getColumnName(i));
				System.out.print("\t\t");
			}
			//显示表格内容
			System.out.println();
			while(rs.next())
			{
				for(int i=1; i<=columns; i++)
				{
				System.out.print(rs.getString(i));
				System.out.print("\t\t");
				}
				System.out.println();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 返回ResultSet的标准字符串形式
	 * 以;区分行
	 * 以,区分列
	 */
	protected String strStandardReturn(ResultSet rs)
	{
		String standardString = "";
		try {
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			/*
			for(int i=1; i<=columns; i++)
			{
				standardString += md.getColumnName(i);
				if (i < columns)
					standardString += ",";
				else
					standardString += ";";
			}
			*/
			while(rs.next())
			{
				for(int i=1; i<=columns; i++)
				{
					standardString += rs.getString(i);
					if (i < columns)
						standardString += ",";
					else
						standardString += ";";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return standardString;
	}
	
	// 只查找单个值
	protected String selectOnlyValue(String sql)
	{
		String value = null;
        Connection conn = JDBC.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                value = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
	}
	
	// 查找多列多行值 
	protected String selectColsRowsValue(String sql)
	{
		String valueV = null;

        Connection conn = JDBC.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            valueV = strStandardReturn(rs);
            rs.close();
            stmt.close();									// 关闭连接状态对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valueV;
	}
	
	// 插入一行数据
	protected boolean insertOrUpdateInfo(String sql)
	{
		Connection conn = JDBC.getConnection();
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
