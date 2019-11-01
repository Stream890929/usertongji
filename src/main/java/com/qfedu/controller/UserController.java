package com.qfedu.controller;

import com.qfedu.service.UserService;
import com.qfedu.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/user/upload")
    @ApiOperation(value = "upload",notes = "Excel文件的上传")
    public Result upload(MultipartFile file) throws IOException {
        return userService.uploadExcel(file);
    }

    @GetMapping(value = "/user/selectAll")
    @ApiOperation(value = "selectAll",notes = "统计数据查询")
    public Result selectAll(){
        return userService.queryAll ();
    }

    @GetMapping(value = "/user/download")
    @ApiOperation(value = "download",notes = "Excel文件的下载")
    public void download(HttpServletResponse response) throws IOException {
        userService.downdload (response);
    }

    @CrossOrigin
    @GetMapping("/user/originuser")
    @ApiOperation(value = "渠道用户的统计",notes = "渠道用户的统计")
    public Result tongji(int type) {
        return userService.queryTongJi(type);
    }

    @CrossOrigin
    @GetMapping("/user/originuserday")
    @ApiOperation(value = "渠道用户的统计每日数据",notes = "渠道用户的统计每日数据")
    public Result tongji() {
        return userService.queryTongJiDay();
    }

}
