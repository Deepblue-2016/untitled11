package com.neuedu.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/9/29.
 */
@MultipartConfig
@WebServlet("/ServletUploadPicAjax")
public class ServletUploadPicAjax extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //获得上传的头像信息
//        System.out.println("上传第一张人脸");
        Part part = request.getPart("file1");

        String filename = part.getSubmittedFileName();
        String filepath = request.getServletContext().getRealPath("images");
        String fullpath = filepath + File.separator + filename;
        part.write(fullpath);

        HttpSession hs = request.getSession();
        hs.setAttribute("face1", filename);

        response.getWriter().write("../images/" + filename);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}
