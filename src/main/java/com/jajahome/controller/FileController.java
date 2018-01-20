package com.jajahome.controller;


import com.jajahome.po.Product;
import com.jajahome.service.impl.DownLoadFileServiceImpl;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FileController {
    @Autowired
    DownLoadFileServiceImpl loadFileService;

    @RequestMapping(value = "/terry", method = {RequestMethod.GET})
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSONObject.fromObject(new Product(1, username, password)).toString());
    }

    @RequestMapping(value = "/tom", method = {RequestMethod.GET})
    public void doGetHeader(HttpServletRequest req, HttpServletResponse resp) {

        String head1 = req.getHeader("head1");
        String head2 = req.getHeader("head2");
        System.out.println("已成功接收请求头--head1" + head1 + "--head2" + head2);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public void doPostLogin(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("post请求登录成功");
    }


    @RequestMapping(value = "/model", method = {RequestMethod.GET})
    public void doGetModel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSONObject.fromObject(new Product(1, "terry", "123456")).toString());
    }

    /**
     * @param req  ? ?
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public void upFile(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        resp.setContentType("text/html;charset=utf-8");

        // 1.创建 DiskFileItemFactory
        File file = new File("F:\\Android\\upload");// 获取temp目录部署到tomcat后的绝对磁盘路径
        DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 100, file); // 使用默认的.
        InputStream in = null;
        OutputStream out = null;
        // 2.创建ServletFileUpload
        ServletFileUpload upload = new ServletFileUpload(factory);
        boolean flag = upload.isMultipartContent(req); // 用于判断是否是上传操作.
        if (flag) {
            // 解决上传文件名称中文乱码
            upload.setHeaderEncoding("utf-8");

            String author = req.getParameter("author");
            String timer = req.getParameter("timer");

            MultipartHttpServletRequest multReq = (MultipartHttpServletRequest) req;
            MultipartFile image = multReq.getFile("image");

            int state = 0;
            String code = "上传成功";

            in = multReq.getInputStream();
            out = new FileOutputStream("F:\\Android\\upload\\" + image.getOriginalFilename());
            int len = 0;
            byte[] bs = new byte[1024 * 10];
            while ((len = in.read(bs)) != -1) {
                out.write(bs, 0, bs.length);
            }

        } else {
            resp.getWriter().write("不是上传操作");
            return;
        }
    }

    @RequestMapping(value = "/json", method = {RequestMethod.POST})
    public void JsonAndSession(HttpServletRequest req, HttpServletResponse resp) {

        String json = req.getParameter("json");
        String session = req.getParameter("session");
        System.out.println("json" + json);
        System.out.println("session" + session);

    }

}
