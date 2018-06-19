package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.service.UserServiceImpl;

import java.util.List;
import java.util.Map;

@EnableCaching
@ComponentScan
@Controller
@SessionAttributes("sessusername")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    public ModelAndView userLogin(@RequestParam("userName") String username, @RequestParam("userPwd") String userpwd, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("sessusername", username);
        boolean flag = userService.checkPass(username, userpwd);
        String status = null;
        if (flag) {
            status = "成功";
            ModelAndView modelAndView = new ModelAndView("home/home");
            modelAndView.addObject("status", status);
            User user = userService.selectUser(username);
            session.setAttribute("user", user);
            return modelAndView;
//			return "home/home.html";
        } else {
            status = "用户名或密码错误";
            ModelAndView modelAndView = new ModelAndView("login-signin/login");
            modelAndView.addObject("status", status);
            return modelAndView;
//			return "login-signin/login.html";
        }
    }

    @RequestMapping(value = "/userregister", method = RequestMethod.POST)
    public String userRegister(HttpServletRequest request) {
        String username = request.getParameter("nickName");
        String userpwd = request.getParameter("password");
        String email = request.getParameter("email");
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        String photo = "1";
        System.out.println("注册！"+username+userpwd);
        userService.register(username, userpwd, email, question, answer, photo);
        return "login-signin/login";
    }

    @RequestMapping(value = "/usersetting", method = RequestMethod.POST)
    public String userSetting(Model model, HttpSession session, HttpServletRequest request) {
        String username = (String) session.getAttribute("sessusername");
        System.out.println("设置 " + username);
        String userpwd = request.getParameter("password");
        String email = request.getParameter("email");
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        int flag = userService.setting(username, userpwd, email, question, answer);
        String setStatus = null;
        if (flag > 0) {
            setStatus = "修改成功";

        } else {
            setStatus = "修改失败";
        }
        User user = userService.selectUser(username);
        session.setAttribute("user", user);
        model.addAttribute("setStatus", setStatus);
        return "setting/setting";
//        ModelAndView modelAndView = new ModelAndView("/setting/setting.html",model.asMap());
//        return modelAndView;
    }

    @RequestMapping(value = "/seeUsers", method = RequestMethod.GET)
    public String seeUsers(Model model) {
        List<User> userList =userService.seeAllUser();
        model.addAttribute("userList",userList);
        return "setting/seeUser";
    }

    @RequestMapping("/home")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String page2() {
        return "home/home";
    }


    @RequestMapping("/share")
    public String page4() {
        return "share/share";
    }

    @RequestMapping("/thoughts")
    public String page5() {
        return "thoughts/thoughts";
    }

    @RequestMapping("/setting")
    public String page8() {
        return "setting/setting";
    }

    @GetMapping("/login")
    public String page9(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("status", "用户名或密码错误");
        }
        return "login-signin/login";
    }

    @RequestMapping("/signin")
    public String page10() {
        return "login-signin/signin";
    }

    @RequestMapping("/adminHome")
    public String page11() {
        return "home/adminHome";
    }

    @RequestMapping("/gotoreleaseNotice")
    public String page12() {
        return "notice/releaseNotice";
    }

    @RequestMapping("/adminSeeShare")
    public String page13() {
        return "share/adminSeeShare";
    }



}
