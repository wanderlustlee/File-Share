package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.entity.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@EnableCaching
@Controller
public class ShareController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/usershare", method = RequestMethod.POST)
    public String share(Model model, HttpServletRequest request,@RequestParam("file") MultipartFile file) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sessusername");
        System.out.println("用户名："+username);
        String title = request.getParameter("title");
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
//        String filePath = request.getSession().getServletContext().getRealPath("fileupload/");
        String filePath = "D:\\fileupload\\";//固定保存路径
        File fileP = new File(filePath);
        try {
            userService.share(username, title, file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/share/share.html";
    }

    @RequestMapping(value = "/seeShare", method = RequestMethod.GET)
    public String seeshare(Model model, HttpServletRequest request) {
        List<Diary> list = new LinkedList<Diary>();
        list = userService.seeShare();
        model.addAttribute("list", list);
        return "/share/seeShare.html";
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到要下载的文件名
        String fileName = URLDecoder.decode(request.getParameter("diarycontent"),"utf-8");
        //fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
        //上传的文件都是保存在D:\fileupload\目录下的子目录当中
        String fileSaveRootPath="D:\\fileupload\\";
        System.out.println(URLDecoder.decode(request.getParameter("diarycontent"),"utf-8"));
        //通过文件名找出文件的所在目录
        //String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
        //String path = fileSaveRootPath;
        //得到要下载的文件
        File file = new File(fileSaveRootPath + "\\" + fileName);
        //如果文件不存在
        if(!file.exists()){
            request.setAttribute("message", "您要下载的资源已被删除！！");
        }
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_")+1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len=in.read(buffer))>0){
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }

    /**
     * @Method: findFileSavePathByFileName
     * @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
     * @Anthor:孤傲苍狼
     * @param filename 要下载的文件名
     * @param saveRootPath 上传文件保存的根目录，也就是/WEB-INF/upload目录
     * @return 要下载的文件的存储目录
     */

//    public String findFileSavePathByFileName(String filename,String saveRootPath){
////        int hashcode = filename.hashCode();
////        int dir1 = hashcode&0xf;  //0--15
////        int dir2 = (hashcode&0xf0)>>4;  //0-157
////        String dir = saveRootPath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
//        String dir = saveRootPath+filename;
//        File file = new File(dir);
//        if(!file.exists()){
//            //创建目录
//            file.mkdirs();
//        }
//        return dir;
//    }


}
