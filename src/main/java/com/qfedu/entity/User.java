package com.qfedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_originuser")
@ApiModel(value = "user实体", description = "用于接收和传递用户信息")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户名",name = "username",required = true,dataType = "String")
    private String username;
    private String origin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rtime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ctime;

    public User() {
    }

}