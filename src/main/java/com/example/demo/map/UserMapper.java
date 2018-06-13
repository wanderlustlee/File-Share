package com.example.demo.map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.User;

import java.util.List;

@Mapper//说明这是个一个mapper
public interface UserMapper {
    public String selectPwdByUsername(String username);//根据姓名读取密码

    public int insertUser(User user) throws Exception;

    public User selectUser(String username);

    public String selectUsername(String username);

    public int updateUser(String username, String userpwd, String email, String question, String answer);

    List<User> selectAllUser();
}


