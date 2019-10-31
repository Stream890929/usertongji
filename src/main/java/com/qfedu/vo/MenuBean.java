package com.qfedu.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Stream
 */
@Data
public class MenuBean {
    private Integer id;
    private String name;
    private String murl;
    private String icons;

    //菜单的级别
    private int level;

    //内部子菜单 下属的菜单
    private List<MenuBean> childs;
}
