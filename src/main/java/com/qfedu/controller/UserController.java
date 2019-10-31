package com.qfedu.controller;

import com.qfedu.service.UserService;
import com.qfedu.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Stream
 * @version 1.0
 * @date 2019/10/31 07:39
 */
@RestController
@Api(value = "用户来源统计",tags = "统计模块实现")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/excel/upload")
    @ApiOperation(value = "upload",notes = "Excel的上传")
    public Result upload(MultipartFile file) throws IOException {
        return userService.uploadExcel(file);
    }

    @GetMapping(value = "/excel/selectAll")
    @ApiOperation(value = "selectAll",notes = "统计数据查询")
    public Result selectAll(){
        return userService.queryAll ();
    }

}
