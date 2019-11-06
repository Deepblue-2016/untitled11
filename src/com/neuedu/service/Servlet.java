package com.neuedu.service;

import com.neuedu.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 2018/11/22.
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        System.out.println("开始对比");
        String filepath = request.getServletContext().getRealPath("images");

        HttpSession hs = request.getSession();

        String f1FileName = (String) hs.getAttribute("face1");
        String f2FileName = (String) hs.getAttribute("face2");

        if (f1FileName != null && f2FileName != null)
        {
            String face1 = filepath + File.separator + f1FileName;
            String face2 = filepath + File.separator + f2FileName;

            String json = match(face1, face2);
            response.getWriter().write(json);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected String match(String face1, String face2)
    {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try
        {

            byte[] bytes1 = FileUtil.readFileByBytes(face1);
            byte[] bytes2 = FileUtil.readFileByBytes(face2);
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();

            //参考，请求参数.xlsx
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
//            map1.put("liveness_control", "NORMAL");
            map1.put("liveness_control", "LOW");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
//            map2.put("liveness_control", "NORMAL");
            map2.put("liveness_control", "LOW");

            images.add(map1);
            images.add(map2);

            String param = GsonUtils.toJson(images);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // 如果提示token过期，就需要重新getAuth一次
            String accessToken = AuthService.getAuth();
//            System.out.println(accessToken);
//            String accessToken = "25.73cf7c137767f2304d5c997d612c9070.315360000.1888365247.282335-14906909";

            //System.out.println("accessToken:"+accessToken);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
//            System.out.println(result);
            //System.out.println(result);
            return result;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
