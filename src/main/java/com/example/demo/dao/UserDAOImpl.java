package com.example.demo.dao;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.example.demo.entity.Notice;
import com.example.demo.map.NoticeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Diary;
import com.example.demo.entity.User;
import com.example.demo.map.DiaryMapper;
import com.example.demo.map.UserMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "userCache") // 本类内方法指定使用缓存时，默认的名称就是userCache
public class UserDAOImpl implements UserDAO {

    String resource = null;
    InputStream is = null;
    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    private static Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();

    public UserDAOImpl() throws IOException {
        resource = "sqlMapConfig.xml";
        is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
    }

    //检查注册时用户名是否可用
    public boolean checkUsername(String username) {
        boolean flag = true;
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        String name = userMapper.selectPwdByUsername(username);
        sqlSession.commit();
        if (name == username) {
            flag = true;
        }
        return flag;
    }

    //登录时密码验证
    //@Cacheable()
    public boolean checkPass(String username, String pwd) {
        boolean flag = false;
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        String pass = userMapper.selectPwdByUsername(username);
        System.out.println("查询数据库");
        sqlSession.commit();
        if (pass != null) {
            if (pass.equals(pwd)) {
                flag = true;
            }

        } else {

        }
        return flag;
    }

    //注册
    //@CachePut()
    public void register(String username, String userpwd, String email, String question, String answer, String photo) {
        User user = new User();
        userpwd = md5encoder.encodePassword(userpwd,"hongxf");
        user.setUsername(username);
        user.setPwd(userpwd);
        user.setEmail(email);
        user.setQuestion(question);
        user.setAnswer(answer);
        user.setImage("1");
        user.setRole("ROLE_USER");
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            int flag = userMapper.insertUser(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sqlSession.commit();

    }

    //修改
    public int setting(String username, String userpwd, String email, String question, String answer) {
        User user = new User();
        userpwd = md5encoder.encodePassword(userpwd,"hongxf");
        user.setUsername(username);
        user.setPwd(userpwd);
        user.setEmail(email);
        user.setQuestion(question);
        user.setAnswer(answer);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int flag = 0;
        try {
            flag = userMapper.updateUser(username, userpwd, email, question, answer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sqlSession.commit();
        return flag;
    }

    @Override
    public void releaseNotice(String notice) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        Notice noticeObj = new Notice();
        noticeObj.setNotice(notice);
        noticeObj.setReleaseTime(time);
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
        try {
            noticeMapper.insertNotice(noticeObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqlSession.commit();
    }

    @Override
    public List<Notice> seeNotice() {
        List<Notice> Noticelist = new ArrayList<>();
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
        Noticelist = noticeMapper.selectNotice();
        sqlSession.commit();
        return Noticelist;
    }

    //分享
    //@CachePut(key = "#title")
    public void share(String username, String title, byte[] file, String filePath, String fileName) {
        //判断上传文件的保存目录是否存在
        File targetFile = new File(filePath);
        if(!targetFile.exists() && !targetFile.isDirectory()){
            System.out.println(filePath+"  目录不存在，需要创建");
            //创建目录
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath+fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        Diary diary = new Diary();
        diary.setTitle(title);
        diary.setContent(filePath+fileName);
        diary.setFileName(fileName);
        System.out.println(diary.getContent());
        diary.setWriteTime(time);
        diary.setUsername(username);
        DiaryMapper diaryMapper = sqlSession.getMapper(DiaryMapper.class);
        try {
            int flag = diaryMapper.insertDiary(diary);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sqlSession.commit();
    }

    //@Cacheable(key = "#username") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
    public User selectUser(String username) {
        User user = null;
        System.out.println("Select User! " + username);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        user = userMapper.selectUser(username);
        sqlSession.commit();
        return user;
    }

    //@Cacheable(key = "#time") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
    public List<Diary> seeShare(String time) {

        List<Diary> list = new LinkedList<Diary>();
        DiaryMapper diaryMapper = sqlSession.getMapper(DiaryMapper.class);
        list = diaryMapper.selectShare();
        sqlSession.commit();
        return list;
    }
    //@Cacheable(key = "#time") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
    public List<User> seeAllUser() {

        List<User> list = new ArrayList<>();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        list = userMapper.selectAllUser();
        sqlSession.commit();
        return list;
    }
}
