package com.example.demo;

import com.example.demo.entity.Diary;
import com.example.demo.entity.Notice;
import com.example.demo.entity.User;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@EnableCaching
@ComponentScan
@Controller
@SessionAttributes("sessusername")
public class NoticeController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/releaseNotice", method = RequestMethod.POST)
    public ModelAndView releaseNotice(@RequestParam("notice") String notice) {
        userService.releaseNotice(notice);
        ModelAndView modelAndView = new ModelAndView("notice/releaseNotice");
        return modelAndView;
        }

    @RequestMapping(value = "/seeNotice", method = RequestMethod.GET)
    public String seeNotice(Model model,HttpServletRequest request) {
        List<Notice> Noticelist = new ArrayList<>();
        Noticelist = userService.seeNotice();
        model.addAttribute("Noticelist", Noticelist);
        return "notice/seeNotice";
    }



}
