package com.qfedu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfedu.entity.User;
import com.qfedu.vo.TongjiBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 批量新增
     * @param list
     * @return
     */
    int insertBatch(List<User> list);
    /**
     * //查询  可选 分页
     * @return
     */
    List<User> selectAll();
    //查询所有的渠道
    List<String> selectOrigins();
    //查询指定日期的数据
    List<TongjiBean> selectDay(String day);
    //查询指定月份的数据
    List<TongjiBean> selectMonth(String month);

}