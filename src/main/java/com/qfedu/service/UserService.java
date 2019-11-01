package com.qfedu.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qfedu.entity.User;
import com.qfedu.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Stream
 * @version 1.0
 * @date 2019/10/22 14:00
 */
public interface UserService extends IService<User> {
    Result uploadExcel(MultipartFile file) throws IOException;

    Result queryAll();

    //生成Excel --下载
    void downdload(HttpServletResponse response) throws IOException;
    //统计分析
    Result queryTongJi(int type);

    Result queryTongJiDay();

}