package com.mmciel.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mmciel.tool.Tools;

/**
 * 数据上传到数据库
 * @author mmciel
 *
 */
public class DataMain {
	private MysqlLink link;
	private Thread thread;
	private boolean threadflag = true;
	public DataMain(MysqlLink link) {
		 this.link = link;
	}
	public void go() {
		thread = new Thread(new DataMainThread());
		thread.start();
		threadflag = true;
	}
	public void stop() {
		threadflag = false;
	}
	class Data{
		String time;
		String tem;
		String hum;
		String sun;
	}
	
	private class DataMainThread implements Runnable{

		@Override
		public void run() {
			
			while(threadflag) {
				int sumi = 0;
				int sumtem = 0;
				int sumhum = 0;
				int sumsun = 0;
				if(Tools.UploadData != null) {
					for(String i : Tools.UploadData) {
						String[] t = i.split("%");
						sumtem += Integer.parseInt(t[1]);
						sumhum += Integer.parseInt(t[2]);
						sumsun += Integer.parseInt(t[3]);
						sumi ++;
					}
					Data data = new Data();
					data.hum  = String.valueOf(sumhum/sumi);
					data.tem  = String.valueOf(sumtem/sumi);
					data.sun  = String.valueOf(sumsun/sumi);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					data.time = df.format(new Date()).toString();
					
					
					//把Data写到数据库
					
					Connection con = link.getCon();
					Statement state;
					try {
						state = con.createStatement();
				        String sql="insert into "+DataIni.mmciel_mysql_table+" values('"+
				        		data.time+"','"+
				        		data.tem+"','"+
				        		data.hum+"','"+
				        		data.sun+
				        		"')"; 
				        state.executeUpdate(sql);  
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					System.out.println(data.time+" 环境数据上传成功"+"("+data.tem+","+data.hum+","+data.sun+")");
				}
				
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
