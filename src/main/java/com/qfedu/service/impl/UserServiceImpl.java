package com.qfedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfedu.dao.UserMapper;
import com.qfedu.entity.User;
import com.qfedu.service.UserService;
import com.qfedu.vo.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Stream
 * @version 1.0
 * @date 2019/10/22 14:01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result uploadExcel(MultipartFile file) throws IOException {
        // 文件上传
        if (!file.isEmpty ()) {
            // 获取上传的内容并且用Excel读取
            List<User> userList = readExcel (file.getBytes ());
            // 数据批量新增
            userMapper.insertBatch (userList);
            return Result.setOk ("上传成功");
        }
        return Result.setError ();
    }

    @Override
    public Result queryAll() {
        return Result.setOk (userMapper.selectAll ());
    }

    /**
     * 读取表格
     *
     * @param data
     * @return
     */
    private List<User> readExcel(byte[] data) {
        List<User> list = new ArrayList<> ();
        try {
            //1、创建文档对象
            XSSFWorkbook workbook = new XSSFWorkbook (new ByteArrayInputStream (data));
            //2、读取表单
            XSSFSheet sheet = workbook.getSheetAt (0);
            //获取行数
            int last = sheet.getLastRowNum ();
            //获取第一行
            XSSFRow fisrtRow = sheet.getRow (0);
            //获取列数
            int lastCell = fisrtRow.getLastCellNum ();
            //循环遍历数据
            for (int i = 1; i < last; i++) {
                XSSFRow row = sheet.getRow (i);
                User user = new User ();
                user.setId ((int) row.getCell (0).getNumericCellValue ());
                user.setUsername (row.getCell (1).getStringCellValue ());
                user.setOrigin (row.getCell (2).getStringCellValue ());
                user.setRtime (parseTime (row.getCell (3).getStringCellValue ()));
                list.add (user);
            }
            workbook.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return list;
    }

    /**
     * 字符串转时间格式
     *
     * @param time 时间字符串
     * @return
     */
    private Date parseTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        try {
            return sdf.parse (time);
        } catch (ParseException e) {
            e.printStackTrace ();
            return new Date ();
        }
    }

}
