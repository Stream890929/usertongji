package com.qfedu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfedu.entity.User;
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

    //查询 根据来源分类  每日  每月的 每年的

    /**
     * //查询  可选 分页
     * @return
     */
    List<User> selectAll();

}