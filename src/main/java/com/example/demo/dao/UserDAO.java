package com.example.demo.dao;

import com.example.demo.entity.Diary;
import com.example.demo.entity.Notice;
import com.example.demo.entity.User;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@EnableCaching
public interface UserDAO {
    public boolean checkUsername(String username);

    public boolean checkPass(String username, String pwd);

    public void register(String username, String userpwd, String email, String question, String answer, String photo);

    public void share(String username, String title, byte[] file, String filePath, String fileName);

    public User selectUser(String username);

    public List<Diary> seeShare(String time);

    public int setting(String username, String userpwd, String email, String question, String answer);

    void releaseNotice(String notice);

    List<Notice> seeNotice();

    public List<User> seeAllUser();
}
