package com.mmciel.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author mmciel
 * 2018年12月17日17:12:57
 * 完成对javaoa本地数据库的连接  增删改查与关闭
 * update：
 */
public class MysqlLink {
    //声明Connection对象
    private Connection con;
    //2.创建statement类对象，用来执行SQL语句！！
    private Statement statement;
    
  /**
   * 拿到连接
   */
    public Connection getCon() {
    	return con;
    }
    public Statement getSta() {
    	return statement;
    }
	public MysqlLink(String url,String user,String password) {
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
        } catch(ClassNotFoundException e) {   
            System.out.println("no find Driver!");   
            e.printStackTrace();   
        } catch(SQLException e) {
            e.printStackTrace();  
        }catch (Exception e) {
            e.printStackTrace();
        }
	}
	public boolean getLinkTest() {
		if(con==null || statement == null) {
			return false;
		}else {
			return true;
		}
	}
	public void Close() {
		try {
			statement.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("mysql close not success");
			e.printStackTrace();
		}
		
		
	}
//	public static void main(String[] args) {
//		new MysqlLink();
//	}
}
