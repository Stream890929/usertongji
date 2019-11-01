package com.qfedu.vo;

import lombok.Data;

import java.util.List;

@Data
public class TongjiVo {
    private List<String> types; //所有渠道名称
    private List<TongjiBean> ydata; //对应渠道和对应的用户数
}
