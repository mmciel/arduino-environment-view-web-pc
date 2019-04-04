package com.mmciel.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author mmciel
 * 2018��12��17��17:12:57
 * ��ɶ�javaoa�������ݿ������  ��ɾ�Ĳ���ر�
 * update��
 */
public class MysqlLink {
    //����Connection����
    private Connection con;
    //2.����statement���������ִ��SQL��䣡��
    private Statement statement;
    
  /**
   * �õ�����
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
