package com.mmciel.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mmciel.Dao.SunDao;

import net.sf.json.JSONArray;


/**
 * 传输json：光照强度
 * @author mmciel
 *
 */
public class SunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        SunDao sundao = new SunDao();
        String barArr = sundao.query();
       // System.out.println(barArr);
        resp.setContentType("text/html; charset=utf-8");
        JSONArray json = JSONArray.fromObject(barArr);
        //System.out.println(json.toString());
        PrintWriter writer = resp.getWriter();
        writer.println(json);
        writer.flush();
        writer.close();
    }

}