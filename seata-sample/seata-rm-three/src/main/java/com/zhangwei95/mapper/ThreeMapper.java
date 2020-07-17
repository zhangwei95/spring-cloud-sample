package com.zhangwei95.mapper;


import com.zhangwei95.entity.Three;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yueyi2019
 */
@Mapper
public interface ThreeMapper {

    /**
     * 插入短信记录
     * @param record
     * @return
     */
    int insertSelective(Three record);



   
}