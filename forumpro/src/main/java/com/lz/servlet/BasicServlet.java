package com.lz.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BasicServlet extends HttpServlet {
    public void forward(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/"+path+".jsp").forward(req,resp);
    }

    public void renderTxt(String str,HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out=response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
    public void renderJson(Object obj,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(new Gson().toJson(obj));
        writer.flush();
        writer.close();
    }
}
