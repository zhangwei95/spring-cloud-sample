package com.zhangwei95.mapper;


import com.zhangwei95.entity.Two;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yueyi2019
 */
@Mapper
public interface TwoMapper {
    /**
     * 根据id删除短信记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入短信记录
     * @param record
     * @return
     */
    int insertSelective(Two record);


    /**
     * 更新短信记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Two record);

   
}