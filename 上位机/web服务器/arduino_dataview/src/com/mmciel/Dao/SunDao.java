package com.mmciel.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.mmciel.bean.Bar;

/**
 * 读取光照强度数据库
 * @author mmciel
 *
 */
public class SunDao {
    public String query() {
        ArrayList<Bar> barArr = new ArrayList<Bar>();
        try {
        //JDBC方式连接MySQL数据库
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://111.230.198.57:3306/arduinodata?characterEncoding=utf8", "root", "123456");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tabledata");
            ResultSet rs = stmt.executeQuery();
            int num = 1;
            while(rs.next()) {
                Bar bar = new Bar();
                bar.setName(String.valueOf(num));
                bar.setNum(rs.getInt("sun"));
               	barArr.add(bar);
               	num++;
                //System.out.println(bar.getName()+bar.getNum());
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson =new Gson();
        String str=gson.toJson(barArr);
        System.out.println(str);
        return str;
    }
}
