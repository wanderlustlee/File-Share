package com.example.demo.service;

import com.example.demo.entity.Diary;
import com.example.demo.entity.Notice;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    public boolean checkPass(String username, String pwd);

    public void register(String username, String userpwd, String email, String question, String answer, String photo);

    public void share(String username, String title, byte[] file, String filePath, String fileName) ;

    public User selectUser(String username);

    public List<Diary> seeShare();

    public int setting(String username, String userpwd, String email, String question, String answer);

    public void releaseNotice(String notice);

    public List<Notice> seeNotice();

    public List<User> seeAllUser();

    public int findTotal();

    public List<Diary> pageShare(int startRow);

    public List<Diary> selectShareByName(String fileName,String username);

    public void deleteDiary(String id);
}
