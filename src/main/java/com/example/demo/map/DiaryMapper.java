package com.example.demo.map;

import com.example.demo.entity.Diary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper//说明这是个一个mapper
public interface DiaryMapper {
    public int insertDiary(Diary diary) throws Exception;

    public List<Diary> selectShare();

    public int findTotal();

    public List<Diary> pageShare(int startRow);

    public List<Diary> selectShareByName(String fileName,String username);

    public void deleteDiary(int diaryid);
}
